package com.example.caffeine_hunter.simple_places_test;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Raven on 11/6/2016.
 */
public class ImageData {
    String url = null;
    View view = null;
    Drawable d = null;

    public ImageData(String url, View view){
        this.url = url;
        this.view = view;
    }

    public ImageData(View view, Drawable d){
        this.view = view;
        this.d = d;
    }

}
