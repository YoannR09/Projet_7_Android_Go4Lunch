package com.example.go4lunch.entity;


import java.util.List;

public class RestaurantEntity {

    private String id;
    private long rangePosition;
    private String name;
    private double latitude;
    private double longitude;
    private String imgUrl;
    private double rating;
    private String photoReference;
    // private RestaurantType type;
    private String address;
    private int countMatesPassed;
    private List<MateEntity> interestMates;
    private float opinion;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getRangePosition() {
        return rangePosition;
    }

    public void setRangePosition(long rangePosition) {
        this.rangePosition = rangePosition;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
