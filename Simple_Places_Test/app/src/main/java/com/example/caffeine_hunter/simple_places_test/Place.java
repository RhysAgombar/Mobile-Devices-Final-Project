package com.example.caffeine_hunter.simple_places_test;

import android.graphics.drawable.Drawable;

import java.net.URL;

/**
 * Created by 100515147 on 11/5/2016.
 */
public class Place {
    public String id;
    public String name;
    public String address;
    public float lat;
    public float lng;
    public String photoRef;
    public String iconURL;
    public Drawable image;
    public boolean visited = false;

    public Place (String id, String name, String address, float lat, float lng, String photoRef, String iconURL, boolean visited) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.photoRef = photoRef;
        this.iconURL = iconURL;
        this.visited = visited;
    }

    public void setImage(Drawable image){
        this.image = image;
    }

    public String toString(){
        return name;
    }
}
