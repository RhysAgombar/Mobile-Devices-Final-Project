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
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;

import android.support.v4.app.FragmentActivity;
import android.widget.Filter;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
            implements OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;
    private PlacePicker.IntentBuilder builder;
    private static final int PLACE_PICKER_FLAG = 1;

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

        try {
            Intent test = builder.build(MainActivity.this);

            startActivityForResult(test, PLACE_PICKER_FLAG);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}