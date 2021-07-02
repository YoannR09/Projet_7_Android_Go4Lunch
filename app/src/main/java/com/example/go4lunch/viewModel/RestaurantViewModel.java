package com.example.go4lunch.viewModel;

import com.example.go4lunch.entity.MateEntity;

import java.util.List;

public class RestaurantViewModel {

    public RestaurantViewModel(String id,
                               String decription,
                               int phone,
                               String webSite,
                               int range,
                               String opening,
                               double latitude,
                               double longitude) {
        this.id = id;
        this.decription = decription;
        this.phone = phone;
        this.webSite = webSite;
        this.range = range;
        this.opening = opening;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private String id;
    private String decription;
    private double latitude;
    private double longitude;
    private int phone;
    private String webSite;
    private int range;
    private String opening;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }
}
