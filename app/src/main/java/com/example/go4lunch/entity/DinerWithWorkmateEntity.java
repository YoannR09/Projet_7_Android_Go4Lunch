package com.example.go4lunch.entity;

public class DinerWithWorkmateEntity {

    String restaurantId;
    WorkmateEntity workmate;

    public DinerWithWorkmateEntity(String restaurantId, WorkmateEntity workmate) {
        this.restaurantId = restaurantId;
        this.workmate = workmate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public WorkmateEntity getWorkmate() {
        return workmate;
    }

    public void setWorkmate(WorkmateEntity workmate) {
        this.workmate = workmate;
    }
}
