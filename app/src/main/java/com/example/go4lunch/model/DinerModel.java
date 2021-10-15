package com.example.go4lunch.model;

import java.util.Date;

public class DinerModel {

    private String restaurantId;
    private String workmateId;
    private WorkmateModel workmateModel;
    private String info;
    private Date date;
    private boolean status;

    public DinerModel(String workmateId,
                      String restaurantId,
                      Date date,
                      boolean status,
                      WorkmateModel workmateModel,
                      String info) {
        this.workmateId = workmateId;
        this.restaurantId = restaurantId;
        this.date = date;
        this.status = status;
        this.info = info;
        if(workmateModel != null) this.workmateModel = workmateModel;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
