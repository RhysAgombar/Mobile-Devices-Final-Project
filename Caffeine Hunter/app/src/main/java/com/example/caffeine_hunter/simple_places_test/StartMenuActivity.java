package com.example.caffeine_hunter.simple_places_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void show_favourites (View v) {

    }

    public void show_nearby (View v){
        Intent NearbyIntent = new Intent(this, MainActivity.class);
        startActivity(NearbyIntent);

    }
}
