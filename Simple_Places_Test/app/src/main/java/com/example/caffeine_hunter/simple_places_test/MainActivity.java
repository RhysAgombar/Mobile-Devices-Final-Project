package com.example.caffeine_hunter.simple_places_test;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Filter;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
            implements OnConnectionFailedListener, PlaceListener {

    private GoogleApiClient mGoogleApiClient;
    private PlacePicker.IntentBuilder builder;
    private static final int PLACE_PICKER_FLAG = 1;
    private static final String GOOGLE_KEY = "AIzaSyCDNRpAddGY0u0wE2VZidReEQ1PomT4uG4";

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

        builder = new PlacePicker.IntentBuilder();

        final String latitude = "40.7463956";
        final String longtitude = "-73.9852992";

        String url;
        // make Call to the url
        url = "https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=100&sensor=true&key=" + GOOGLE_KEY;

        DownloadPlace task = new DownloadPlace(this);
        task.execute(url);

        /*
        try {
           // Intent test = builder.build(MainActivity.this);

           // startActivityForResult(test, PLACE_PICKER_FLAG);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } */



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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void handlePlace(String place) {

    }
}