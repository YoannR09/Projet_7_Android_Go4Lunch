package com.example.go4lunch.entity;

public class LikeEntity {

    public LikeEntity() {}

    public LikeEntity(String id, String workmateId, String restaurantId, boolean status) {
        this.id = id;
        this.workmateId = workmateId;
        this.restaurantId = restaurantId;
        this.status = status;
    }

    public LikeEntity(String restaurantId, boolean status) {
        this.restaurantId = restaurantId;
        this.status = status;
    }

    private String id;
    private String workmateId;
    private String restaurantId;
    private boolean status;

    public String getWorkmateId() {
        return workmateId;
    }

    public void setWorkmateId(String workmateId) {
        this.workmateId = workmateId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
