package com.example.caffeine_hunter.simple_places_test;

import java.io.Serializable;

/**
 * Created by 100515147 on 11/13/2016.
 */

public class PlaceLite implements Serializable{
    private String id;
    private String name;
    private String address;
    private float lat;
    private float lng;

    public PlaceLite(Place placeToTrim){
        this.id = placeToTrim.getId();
        this.name = placeToTrim.getName();
        this.address = placeToTrim.getName();
        this.lat = placeToTrim.getLat();
        this.lng = placeToTrim.getLng();
    }

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

    public String toString(){
        return id + " " + name + " " + address + " " + Float.toString(lat) + " " + Float.toString(lng);
    }

}
