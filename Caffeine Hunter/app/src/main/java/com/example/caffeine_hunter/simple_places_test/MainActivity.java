package com.example.caffeine_hunter.simple_places_test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends FragmentActivity
            implements OnConnectionFailedListener, PlaceListener, ImageListener {

    private static final int PLACE_PICKER_FLAG = 1;
    private static final String GOOGLE_KEY = "AIzaSyCDNRpAddGY0u0wE2VZidReEQ1PomT4uG4";

    double radius = 5.0; // To be changed using a slider or something...

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    ArrayList<com.example.caffeine_hunter.simple_places_test.Place> places = new ArrayList<com.example.caffeine_hunter.simple_places_test.Place>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.landing_menu);

        SeekBar seek = (SeekBar)findViewById(R.id.searchRadius);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius = Double.parseDouble(String.format("%.2f", progress/10.0f));
                TextView radiusDisplay = (TextView)findViewById(R.id.tv_radiusDisplay);
                radiusDisplay.setText("Search Radius: " + Double.toString(radius) + "km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

        });
    }

    public void beginSearch(View view) {
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(LOCATION_PERMS, 23);
        }

        final Double latitude, longtitude;

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            DecimalFormat df = new DecimalFormat("#.#");
            latitude = location.getLatitude();
            longtitude = location.getLongitude();
        } else {
            latitude = 43.9454;
            longtitude = -78.8964;
        }

        String url;

        url = "https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude.toString() + "," + longtitude.toString() + "&keyword=coffee&type=cafe&radius=" + (radius * 1000) +"&sensor=true&key=" + GOOGLE_KEY;

        DownloadPlace task = new DownloadPlace(this, this, radius);
        task.execute(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG: // ?
                    Place place = PlacePicker.getPlace(data, this);
                    break;
            }
        }
    }

    @Override
    public void handlePlace(final ArrayList<com.example.caffeine_hunter.simple_places_test.Place> place) {

        ListView listView = (ListView)findViewById(R.id.lv_PlacesList);
        listView.setAdapter(new PlaceAdapter(this, place));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MapActivity.class);

                try {
                    i.putExtra("place", new PlaceLite(place.get(position)));

                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        places = place;

        String preURL = null;

        for (int i = 0; i < place.size(); i++){

            if (place.get(i).getPhotoRef() != null) {
                preURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&photoreference=" +  place.get(i).getPhotoRef() + "&key=" + GOOGLE_KEY;
            } else {
                preURL = place.get(i).getIconURL();
            }

            try {
                ImageDownloader task = new ImageDownloader(this);
                task.execute(new ImageData(i,preURL));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void handleImage(ImageData image) {
        places.get(image.id).setImage(image.d);
        ListView listView = (ListView)findViewById(R.id.lv_PlacesList);
        listView.invalidateViews();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}