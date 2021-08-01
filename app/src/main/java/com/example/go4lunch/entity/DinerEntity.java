package com.example.go4lunch.entity;

import java.util.Date;

public class DinerEntity {

    private String restaurantId;
    private String workmateId;
    private Date date;

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
}
