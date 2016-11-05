package com.example.caffeine_hunter.simple_places_test;

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * Created by 100515147 on 11/5/2016.
 */
public class DownloadPlace extends AsyncTask<String, Void, String> {
    private String definition = null;
    private Exception exception = null;
    private PlaceListener listener = null;

    public DownloadPlace(PlaceListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // download the XML data from the service
            URL url = new URL(params[0]);

            InputStream is = url.openStream();
            String jsonText = "";
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String tempLine = null;
                while ((tempLine = rd.readLine()) != null ){
                    jsonText += tempLine;
                }

            } finally {
                is.close();
            }


            // Parse JSON array

            ArrayList<Place> placeList = new ArrayList<Place>();

            JSONValue.parse(jsonText);

            JSONObject obj = (JSONObject) JSONValue.parse(jsonText);
            JSONArray arr = (JSONArray) obj.get("results");
            for (int i = 0; i < arr.size(); i++) {
                JSONObject place = (JSONObject) arr.get(i);

                Place nPlace = new Place();
                nPlace.id = (String) place.get("id");
                nPlace.name = (String) place.get("name");

                JSONObject geo = (JSONObject) place.get("geometry");
                JSONObject loc = (JSONObject) geo.get("location");

                nPlace.lat = Float.parseFloat(loc.get("lat").toString());
                nPlace.lng = Float.parseFloat(loc.get("lng").toString());

                nPlace.iconURL = (String) place.get("icon");

                JSONArray photo = (JSONArray) place.get("photos");

                if (photo != null){
                    JSONObject photoRef = (JSONObject) photo.get(0);
                    nPlace.photoRef = (String) photoRef.get("photo_reference");
                } else {
                    nPlace.photoRef = null;
                }

                placeList.add(nPlace);

            }
            
            // remember the data */
            return definition;
        } catch (Exception e) {
            exception = e;
            definition = "";
        } finally {
            return definition;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // handle any error
        if (exception != null) {
            exception.printStackTrace();
            return;
        }

        // show the data
        listener.handlePlace(result);
    }
}
