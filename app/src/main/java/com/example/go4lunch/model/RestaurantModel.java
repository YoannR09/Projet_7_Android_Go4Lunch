package com.example.go4lunch.model;

import com.example.go4lunch.entity.MateEntity;

import java.util.List;

public class RestaurantModel {

    public RestaurantModel(String id,
                           double latitude,
                           double longitude,
                           String address,
                           List<MateEntity> interestMates,
                           float opinion) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.interestMates = interestMates;
        this.opinion = opinion;
    }

    private String id;
    private long rangePosition;
    private double latitude;
    private double longitude;
    private String imgUrl;
    // private RestaurantType type;
    private String address;
    private int countMatesPassed;
    private List<MateEntity> interestMates;
    private float opinion;


    public long getRangePosition() {
        return rangePosition;
    }

    public void setRangePosition(long rangePosition) {
        this.rangePosition = rangePosition;
    }


    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCountMatesPassed() {
        return countMatesPassed;
    }

    public void setCountMatesPassed(int countMatesPassed) {
        this.countMatesPassed = countMatesPassed;
    }

    public List<MateEntity> getInterestMates() {
        return interestMates;
    }

    public void setInterestMates(List<MateEntity> interestMates) {
        this.interestMates = interestMates;
    }

    public float getOpinion() {
        return opinion;
    }

    public void setOpinion(float opinion) {
        this.opinion = opinion;
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
}
