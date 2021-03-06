package com.example.caffeine_hunter.simple_places_test;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;


/**
 * Created by Raven on 11/6/2016.
 */
public class ImageDownloader extends AsyncTask<ImageData, Void, ImageData> {
    private Exception exception = null;
    private ImageData imgD = null;
    private ImageListener listener = null;


    public ImageDownloader(ImageListener listener) {
        this.listener = listener;
    }

    @Override
    protected ImageData doInBackground(ImageData... params) {

        try {
            // download the XML data from the service
            URL url = new URL(params[0].url);           // Get the URL passed to the function
            int id = params[0].id;                      // Get the ID associated with the url

            InputStream content = (InputStream)url.getContent();        // Open an InputStream
            imgD = new ImageData(id, Drawable.createFromStream(content, "src"));    // Create a new ImageData object based on the input


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return imgD;
        }
    }

    @Override
    protected void onPostExecute(ImageData result) {
        // handle any error
        if (exception != null) {
            exception.printStackTrace();
            return;
        }

        // show the data
        listener.handleImage(result);
    }


}
