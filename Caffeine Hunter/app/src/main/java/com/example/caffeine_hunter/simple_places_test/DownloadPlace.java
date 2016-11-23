package com.example.caffeine_hunter.simple_places_test;

import android.content.Context;
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
    private Context context = null;
    private double radius = 0.0;
    private Exception exception = null;
    private PlaceListener listener = null;
    private static final String GOOGLE_KEY = "AIzaSyCDNRpAddGY0u0wE2VZidReEQ1PomT4uG4";     // API Key

    ArrayList<Place> placeList = new ArrayList<Place>();       // ArrayList to store the downloaded places

    public DownloadPlace(PlaceListener listener, Context context, double radius) {          // Constructor
        this.listener = listener;
        this.context = context;
        this.radius = radius;
    }

    @Override
    protected ArrayList<Place> doInBackground(String... params) {
        try {
            // download the XML data from the service
            URL url = new URL(params[0]);                   // Get the current URL

            InputStream is = url.openStream();              // Open an InputStream
            String jsonText = "";
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));    // Create a BufferedReader
                String tempLine = null;
                while ((tempLine = rd.readLine()) != null ){
                    jsonText += tempLine;                   // Populate the string with the downloaded data
                }

            } finally {
                is.close();                                 // Close the InputStream
            }

// CoQBdwAAACC-uRAo9QRA3m_usydxkeqjeSUDPWNUjU5SofvIkB3JTdPN0tOfg8ZgYI_Hd5xnrgNy8jNNe_uKOnLBOlxEnPSYMC_0sNCHITy1m_nKXr96jQpNbR8TzkuLIhgnSF2erVOeBQ8-Knp8AeIh8hIwK6scnprBnjVwpAJAc9BFJro2EhCqi3Hn9ECnXE65XgVcIMaPGhRvIrZs2hryBlL33QKiFyeqmiifbA

            JSONValue.parse(jsonText);

            JSONObject obj = (JSONObject) JSONValue.parse(jsonText);
            JSONArray arr = (JSONArray) obj.get("results");
            for (int i = 0; i < arr.size(); i++) {              // Iterate through the downloaded places
                JSONObject place = (JSONObject) arr.get(i);     // Get the current place

                //Place nPlace = new Place();

                String id = (String) place.get("id");           // Get the ID
                String name = (String) place.get("name");       // Get the name

                JSONObject geo = (JSONObject) place.get("geometry");
                JSONObject loc = (JSONObject) geo.get("location");

                float lat = Float.parseFloat(loc.get("lat").toString());    // Get the latitude
                float lng = Float.parseFloat(loc.get("lng").toString());    // Get the longitude

                String iconURL = (String) place.get("icon");    // Get the image URL

                String address = (String) place.get("vicinity"); // Get the address

                JSONArray photo = (JSONArray) place.get("photos"); // Get the photo

                String ref = null;
                String preURL;

                String pRef = null;

                if (photo != null) {                                    // Download the photo (If one exists, otherwise use default image)
                    JSONObject photoRef = (JSONObject) photo.get(0);
                    pRef = (String) photoRef.get("photo_reference");
                    preURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&photoreference=" +  (String) photoRef.get("photo_reference") + "&key=" + GOOGLE_KEY;
                }

                Place nPlace = new Place(id, name, address, lat, lng, pRef, iconURL, false);    // Create a new place object based on the gathered data

                nPlace.setDist((new PlaceAdapter(context, null)).calcDistance(lat,lng));        // Calculate the dictance for the new place

                placeList.add(nPlace);                                                          // Add the place to the list of places

            }

        } catch (Exception e) {
            exception = e;
            placeList = null;
        } finally {

            placeList = selectionSort(placeList);       // Sort the list of places

            int i = placeList.size() - 1;

            while (i > 0){
                if (placeList.get(i).getDist() > radius){   // Remove any places which are too far away
                    placeList.remove(i);
                }
                i--;
            }


            return placeList;
        }
    }

    protected ArrayList<Place> selectionSort (ArrayList<Place> arr) {

        ArrayList<Place> nArr = new ArrayList<Place>();

        try {

            int pos = 0;
            double nDist = 1e9;
            while (arr.size() > 0){
                for (int i = 0; i < arr.size(); i++){
                    if (arr.get(i).getDist() <= nDist) {        // Sorts all places in the list by distance from the user
                        nDist = arr.get(i).getDist();
                        pos = i;
                    }
                }

                nArr.add(arr.get(pos));

                arr.remove(pos);

                pos = 0;
                nDist = 1e9;

            }

        } catch (Exception e){
            e.printStackTrace();
        }


        return nArr;

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
