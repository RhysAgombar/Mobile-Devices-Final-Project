package com.example.caffeine_hunter.caffeinehunter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class App_Start extends AppCompatActivity {

    private PlacePicker.IntentBuilder builder;
    private static final int PLACE_PICKER_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__start);

        builder = new PlacePicker.IntentBuilder();
        AutoCompleteTextView myLocation = (AutoCompleteTextView) findViewById(R.id.myLocation);
        Button  pickerBtn = (Button) findViewById(R.id.pickerBtn);

        pickerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    builder = new PlacePicker.IntentBuilder();
                    Intent intent = builder.build(App_Start.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_FLAG);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), App_Start.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(App_Start.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PLACE_PICKER_FLAG:
                        Place place = PlacePicker.getPlace(data, this);
                        myLocation.setText(place.getName() + ", " + place.getAddress());
                        break;
                }
            }

    }
}
