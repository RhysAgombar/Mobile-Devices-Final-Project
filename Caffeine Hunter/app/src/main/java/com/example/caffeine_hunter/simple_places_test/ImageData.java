package com.example.caffeine_hunter.simple_places_test;

import android.graphics.drawable.Drawable;

/**
 * Created by Raven on 11/6/2016.
 */
public class ImageData {
    int id;
    String url;
    Drawable d = null;

    public ImageData(int id, String url){
        this.id = id;
        this.url = url;
    }

    public ImageData(int id, Drawable d){
        this.id = id;
        this.d = d;
    }

}
