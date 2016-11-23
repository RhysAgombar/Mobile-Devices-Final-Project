package com.example.caffeine_hunter.simple_places_test;

import android.graphics.drawable.Drawable;

/**
 * Created by 100515147 on 11/5/2016.
 */
public class Place {
    private String id;
    private String name;
    private String address;
    private float lat;                      // Latitude
    private float lng;                      // Longitude
    private double dist;                    // Distance from user
    private String photoRef;
    private String iconURL;
    private Drawable image;
    private boolean visited = false;        // Used for the visited database

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public double getDist() {
        return dist;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public String getIconURL() {
        return iconURL;
    }

    public Drawable getImage() {
        return image;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

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
        return id + " " + name + " " + address + " " + Float.toString(lat) + " " + Float.toString(lng) + " " + photoRef + " " + iconURL + " " + visited;
    }
}
