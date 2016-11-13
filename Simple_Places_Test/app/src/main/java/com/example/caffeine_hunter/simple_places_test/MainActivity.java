package com.example.caffeine_hunter.simple_places_test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;

import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
            implements OnConnectionFailedListener, PlaceListener, ImageListener {

    private GoogleApiClient mGoogleApiClient;
    private PlacePicker.IntentBuilder builder;
    private static final int PLACE_PICKER_FLAG = 1;
    private static final String GOOGLE_KEY = "AIzaSyBma_v3QYFn_TargQVk701kzcddODqHIYo";

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    ArrayList<com.example.caffeine_hunter.simple_places_test.Place> places = new ArrayList<com.example.caffeine_hunter.simple_places_test.Place>();

    //https://maps.googleapis.com/maps/api/place/search/xml?location=40.7463956,-73.9852992&radius=100&sensor=true&key=AIzaSyCDNRpAddGY0u0wE2VZidReEQ1PomT4uG4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(LOCATION_PERMS, 23);
        }

        int test = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        test = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        test = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);


        builder = new PlacePicker.IntentBuilder();

        final String latitude = "40.7463956";
        final String longtitude = "-73.9852992";

        String url;

        url = "https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&keyword=coffee&type=cafe&radius=50000&sensor=true&key=" + GOOGLE_KEY;

        DownloadPlace task = new DownloadPlace(this);
        task.execute(url);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    Place place = PlacePicker.getPlace(data, this);
                    //myLocation.setText(place.getName() + ", " + place.getAddress());
                    break;
            }
        }
    }

    @Override
    public void handlePlace(ArrayList<com.example.caffeine_hunter.simple_places_test.Place> place) {

        //ArrayAdapter<com.example.caffeine_hunter.simple_places_test.Place> adapter = new ArrayAdapter<com.example.caffeine_hunter.simple_places_test.Place>(this, android.R.layout.simple_list_item_1, android.R.id.text1, place);

        ListView listView = (ListView)findViewById(R.id.lv_PlacesList);
        listView.setAdapter(new PlaceAdapter(this, place));

        int test = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        places = place;

        String preURL = null;

        for (int i = 0; i < place.size(); i++){

            if (place.get(i).photoRef != null) {
                preURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&photoreference=" +  place.get(i).photoRef + "&key=" + GOOGLE_KEY;
            } else {
                preURL = place.get(i).iconURL;
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

        places.get(image.id).image = image.d;

        ListView listView = (ListView)findViewById(R.id.lv_PlacesList);
        listView.setAdapter(new PlaceAdapter(this, places));

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}