package com.example.go4lunch.ui.viewModel;

import java.io.Serializable;

public class DinerViewModel implements Serializable {

    private String restaurantId;
    private String workmate;
    private String workmatePictureUrl;
    private boolean status;

    public DinerViewModel(
            String workmate,
            String workmatePictureUrl,
            String restaurantId,
            boolean status) {
        this.workmate = workmate;
        this.workmatePictureUrl = workmatePictureUrl;
        this.restaurantId = restaurantId;
        this.status = status;
    }

    public String getWorkmate() {
        return workmate;
    }

    public void setWorkmate(String workmate) {
        this.workmate = workmate;
    }

    public String getWorkmatePictureUrl() {
        return workmatePictureUrl;
    }

    public void setWorkmatePictureUrl(String workmatePictureUrl) {
        this.workmatePictureUrl = workmatePictureUrl;
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
