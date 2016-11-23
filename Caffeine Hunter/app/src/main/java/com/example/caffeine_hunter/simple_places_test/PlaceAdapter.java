package com.example.caffeine_hunter.simple_places_test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raven on 11/6/2016.
 */
public class PlaceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Place> data;
    private static final String GOOGLE_KEY = "AIzaSyCDNRpAddGY0u0wE2VZidReEQ1PomT4uG4";

    public PlaceAdapter(Context context, ArrayList<Place> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Function used for calculating the distance, given the current long and lat, and the target long and lat
    public double calcDistance(double latitude, double longitude) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                DecimalFormat df = new DecimalFormat("#.#");
                double currentLong = location.getLongitude();
                double currentLat = location.getLatitude();
                double dLong = longitude - currentLong;
                double dLat = latitude - currentLat;
                double a = ((Math.sin(toRadian(dLat)/2))*(Math.sin(toRadian(dLat)/2))) + Math.cos(toRadian(currentLat)) * Math.cos(toRadian(latitude))* ((Math.sin(toRadian(dLong)/2))*(Math.sin(toRadian(dLong)/2)));
                double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                double distance = 6371 * c;
                return Double.parseDouble(df.format(distance));
            } else {
                return -1.0;
            }
        } catch (Exception e){
            int j = 0;
            j++;
        }
        return -1.0;

    }

    protected double toRadian(double value){
        return value*Math.PI/180;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Place placeToDisplay = data.get(position);                  // The current place to display

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        final PlaceDBHelper db = new PlaceDBHelper(convertView.getContext());           // Populate a list of places
        List<Place> visitedPlaces = db.getAllElements();
        for(int i = 0; i < visitedPlaces.size(); i++){
            if(visitedPlaces.get(i).getId().equals(placeToDisplay.getId())){            // Check if the list is full, or if there is room for another item
                placeToDisplay.setVisited(true);
            }
        }

        TextView lblPlaceName = (TextView)convertView.findViewById(R.id.tv_shopName);   // Display the name
        lblPlaceName.setText(placeToDisplay.getName());

        TextView lblAddress = (TextView)convertView.findViewById(R.id.tv_address);      // Display the address
        lblAddress.setText(placeToDisplay.getAddress());

        TextView lblDistance = (TextView)convertView.findViewById(R.id.tv_distance);

        double dist = calcDistance(placeToDisplay.getLat(), placeToDisplay.getLng());
        String displTxt = dist + "km";   // Create the string to display

        if (dist < 0) {
            displTxt = "ERR";           // Error checking, provide a default value
        }
        lblDistance.setText(displTxt);  // Populate the view with the calculated distance

        ImageView imgPlacePic = (ImageView)convertView.findViewById(R.id.iv_picture);
        imgPlacePic.setImageDrawable(placeToDisplay.getImage());        // Display the image corresponding to the place

        final CheckBox chbx = (CheckBox)convertView.findViewById(R.id.cb_visited);
        chbx.setChecked(placeToDisplay.isVisited());

        chbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isChecked = chbx.isChecked();

                data.get(position).setVisited(isChecked);

                if(isChecked){
                    db.addNewElement(data.get(position));               // If the box is checked, add it to the 'Visited' database
                } else {
                    db.deleteElementByID(data.get(position).getId());   // If it's unchecked, remove it
                }
            }
        });

        db.close();
        return convertView;
    }

}
