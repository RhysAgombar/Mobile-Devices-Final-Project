package com.example.caffeine_hunter.simple_places_test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

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


    public double calcDistance(double longitude, double latitude) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        try {

            //int test = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                DecimalFormat df = new DecimalFormat("#.#");
                double currentLong = location.getLongitude();
                double currentLat = location.getLatitude();
                double dLong = currentLong - longitude;
                double dLat = currentLat - latitude;
                double a = ((Math.sin((dLat)*Math.PI/180)/2))*(Math.sin((dLat)*Math.PI/180)/2) + Math.cos((latitude)*Math.PI/180) * Math.cos((currentLat)*Math.PI/180)* ((Math.sin((dLong)*Math.PI/180)/2))*(Math.sin((dLong)*Math.PI/180)/2);
                double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                double distance = 6373 * c;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Place placeToDisplay = data.get(position);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView lblPlaceName = (TextView)convertView.findViewById(R.id.tv_shopName);
        lblPlaceName.setText(placeToDisplay.getName());

        TextView lblAddress = (TextView)convertView.findViewById(R.id.tv_address);
        lblAddress.setText(placeToDisplay.getAddress());

        TextView lblDistance = (TextView)convertView.findViewById(R.id.tv_distance);

        double dist = calcDistance(placeToDisplay.getLat(), placeToDisplay.getLng());
        String displTxt = dist + "km";

        if (dist < 0) {
            displTxt = "ERR";
        }
        lblDistance.setText(displTxt);

        ImageView imgPlacePic = (ImageView)convertView.findViewById(R.id.iv_picture);
        imgPlacePic.setImageDrawable(placeToDisplay.getImage());

        final CheckBox chbx = (CheckBox)convertView.findViewById(R.id.cb_visited); // TODO: Actually update based on data

        chbx.setChecked(placeToDisplay.isVisited());

        final PlaceDBHelper db = new PlaceDBHelper(convertView.getContext());

        chbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isChecked = chbx.isChecked();
                if(isChecked){
                    db.addNewElement(data.get(position));
                } else {
                    db.deleteElementByID(data.get(position).getId());
                }
                data.get(position).setVisited(isChecked);

            }
        });


        return convertView;
    }




}
