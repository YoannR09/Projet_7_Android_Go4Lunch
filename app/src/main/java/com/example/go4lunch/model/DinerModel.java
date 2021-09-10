package com.example.go4lunch.model;

import com.example.go4lunch.entity.WorkmateEntity;

import java.util.Date;

public class DinerModel {

    private String restaurantId;
    private String workmateId;
    private WorkmateModel workmateModel;
    private Date date;
    private boolean status;

    public DinerModel(String workmateId, String restaurantId, Date date, boolean status, WorkmateModel workmateModel) {
        this.workmateId = workmateId;
        this.restaurantId = restaurantId;
        this.date = date;
        this.status = status;
        if(workmateModel != null) this.workmateModel = workmateModel;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWorkmateId() {
        return workmateId;
    }

    public void setWorkmateId(String workmateId) {
        this.workmateId = workmateId;
    }

    public WorkmateModel getWorkmateModel() {
        return workmateModel;
    }

    public void setWorkmateModel(WorkmateModel workmateModel) {
        this.workmateModel = workmateModel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
