package com.example.go4lunch.util;

public class MapPosition {

    public MapPosition(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double lat;
    public double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
