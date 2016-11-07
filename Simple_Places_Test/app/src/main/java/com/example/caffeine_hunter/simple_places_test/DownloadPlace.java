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
public class DownloadPlace extends AsyncTask<String, Void, ArrayList<Place>> {
    private String definition = null;
    private Exception exception = null;
    private PlaceListener listener = null;
    private static final String GOOGLE_KEY = "AIzaSyDSW3LLlaM50dEQfxuqARLin2uMZ3xOhcI";

    ArrayList<Place> placeList = new ArrayList<Place>();

    public DownloadPlace(PlaceListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Place> doInBackground(String... params) {
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

// CoQBdwAAACC-uRAo9QRA3m_usydxkeqjeSUDPWNUjU5SofvIkB3JTdPN0tOfg8ZgYI_Hd5xnrgNy8jNNe_uKOnLBOlxEnPSYMC_0sNCHITy1m_nKXr96jQpNbR8TzkuLIhgnSF2erVOeBQ8-Knp8AeIh8hIwK6scnprBnjVwpAJAc9BFJro2EhCqi3Hn9ECnXE65XgVcIMaPGhRvIrZs2hryBlL33QKiFyeqmiifbA

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

                nPlace.address = (String) place.get("vicinity");

                JSONArray photo = (JSONArray) place.get("photos");

                String ref = null;
                String preURL;

                if (photo != null) {
                    JSONObject photoRef = (JSONObject) photo.get(0);
                    nPlace.photoRef = (String) photoRef.get("photo_reference");
                    preURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&photoreference=" +  (String) photoRef.get("photo_reference") + "&key=" + GOOGLE_KEY;
                } else {
                    nPlace.photoRef = null;
                }


                //URL url = null;
                //try {
                    // url = new URL(preURL);

                    //ImageDownloader task = new ImageDownloader(this);
                    //task.execute(preURL);

                    //InputStream content = (InputStream)url.getContent();
                    //Drawable d = Drawable.createFromStream(content , "src");
                    //imgPlacePic.setImageDrawable(d);

                //} catch (Exception e) {
               //     e.printStackTrace();
               // }




















                placeList.add(nPlace);

            }

        } catch (Exception e) {
            exception = e;
            placeList = null;
        } finally {
            return placeList;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Place> result) {
        // handle any error
        if (exception != null) {
            exception.printStackTrace();
            return;
        }

        // show the data
        listener.handlePlace(result);
    }
}
