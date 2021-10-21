package com.example.go4lunch.ui.viewModel;

public class LikeViewModel {

    public LikeViewModel(String workmateId, String restaurantId, boolean status) {
        this.workmateId = workmateId;
        this.restaurantId = restaurantId;
        this.status = status;
    }

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
}
