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
    private boolean opening;
    private String address;
    private int countMatesPassed;
    private List<WorkmateEntity> interestMates;
    private float opinion;
    private boolean workmateDiner;
    private String webSite;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

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

    public List<WorkmateEntity> getInterestMates() {
        return interestMates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterestMates(List<WorkmateEntity> interestMates) {
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

    public boolean getOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    public boolean isOpening() {
        return opening;
    }

    public boolean isWorkmateDiner() {
        return workmateDiner;
    }

    public void setWorkmateDiner(boolean workmateDiner) {
        this.workmateDiner = workmateDiner;
    }
}
