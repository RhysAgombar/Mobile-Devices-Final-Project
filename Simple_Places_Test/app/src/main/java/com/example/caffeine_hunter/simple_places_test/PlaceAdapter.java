package com.example.caffeine_hunter.simple_places_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Raven on 11/6/2016.
 */
public class PlaceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Place> data;
    private static final String GOOGLE_KEY = "AIzaSyBma_v3QYFn_TargQVk701kzcddODqHIYo";

    public PlaceAdapter(Context context, ArrayList<Place> data){
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Place placeToDisplay = data.get(position);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView lblPlaceName = (TextView)convertView.findViewById(R.id.tv_shopName);
        lblPlaceName.setText(placeToDisplay.name);

        TextView lblAddress = (TextView)convertView.findViewById(R.id.tv_address);
        lblAddress.setText(placeToDisplay.address);

        TextView lblDistance = (TextView)convertView.findViewById(R.id.tv_distance);
        lblDistance.setText("10.5km");

        ImageView imgPlacePic = (ImageView)convertView.findViewById(R.id.iv_picture);
        imgPlacePic.setImageDrawable(placeToDisplay.image);

        return convertView;
    }


}
