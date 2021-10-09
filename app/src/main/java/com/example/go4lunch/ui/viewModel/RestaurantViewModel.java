package com.example.go4lunch.ui.viewModel;

import java.io.Serializable;

public class RestaurantViewModel implements Serializable {

    public RestaurantViewModel(String id,
                               String name,
                               String decription,
                               int phone,
                               String webSite,
                               String range,
                               String opening,
                               double latitude,
                               double longitude,
                               String photoReference,
                               String opinion) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.phone = phone;
        this.webSite = webSite;
        this.range = range;
        this.opening = opening;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoReference = photoReference;
        this.opinion = opinion;
    }

    private String name;
    private String id;
    private String decription;
    private String opinion;
    private double latitude;
    private double longitude;
    private int phone;
    private String range;
    private String opening;
    private String photoReference;
    private String webSite;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
