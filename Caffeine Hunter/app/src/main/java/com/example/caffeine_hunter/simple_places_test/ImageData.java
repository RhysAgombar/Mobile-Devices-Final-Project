package com.example.caffeine_hunter.simple_places_test;

import android.graphics.drawable.Drawable;

/**
 * Created by Raven on 11/6/2016.
 * Data For the images
 */
public class ImageData {
    int id;                                 // Image ID
    String url;                             // Image URL
    Drawable d = null;                      // Drawable for the image

    public ImageData(int id, String url){   // -
        this.id = id;                       //  |
        this.url = url;                     //  |
    }                                       //  |
                                            //  | - Constructors
    public ImageData(int id, Drawable d){   //  |
        this.id = id;                       //  |
        this.d = d;                         //  |
    }                                       // -

}
