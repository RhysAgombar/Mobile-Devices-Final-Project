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


    public String toString(){
        return name;
    }
}
