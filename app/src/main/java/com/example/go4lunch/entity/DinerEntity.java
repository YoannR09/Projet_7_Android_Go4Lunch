package com.example.go4lunch.entity;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class DinerEntity {

    private String restaurantId;
    private String workmateId;
    private String info;
    private Date date;
    private boolean status;

    @Exclude
    private WorkmateEntity workmateEntity;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getWorkmateId() {
        return workmateId;
    }

    public void setWorkmateId(String workmateId) {
        this.workmateId = workmateId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public WorkmateEntity getWorkmateEntity() {
        return workmateEntity;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setWorkmateEntity(WorkmateEntity workmateEntity) {
        this.workmateEntity = workmateEntity;
    }
}
