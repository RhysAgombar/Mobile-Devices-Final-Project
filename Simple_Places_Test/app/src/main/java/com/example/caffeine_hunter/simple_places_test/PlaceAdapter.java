package com.example.caffeine_hunter.simple_places_test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Raven on 11/6/2016.
 */
public class PlaceAdapter extends BaseAdapter implements ImageListener {
    private Context context;
    private ArrayList<Place> data;
    private static final String GOOGLE_KEY = "AIzaSyCDNRpAddGY0u0wE2VZidReEQ1PomT4uG4";

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

        //https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&photoreference=&key=YOUR_API_KEY

        String ref = null;
        String preURL;

        if (placeToDisplay.photoRef == null){
            preURL = placeToDisplay.iconURL;
        } else {
            ref = placeToDisplay.photoRef;
            preURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=75&photoreference=" +  ref + "&key=" + GOOGLE_KEY;
        }



        URL url = null;
        try {
           // url = new URL(preURL);

            ImageDownloader task = new ImageDownloader(this);
            task.execute(new ImageData(preURL, convertView));

            //InputStream content = (InputStream)url.getContent();
            //Drawable d = Drawable.createFromStream(content , "src");
            //imgPlacePic.setImageDrawable(d);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public void handleImage(ImageData imgD) {
        ImageView imgPlacePic = (ImageView)imgD.view.findViewById(R.id.iv_picture);
        imgPlacePic.setImageDrawable(imgD.d);
    }


}
