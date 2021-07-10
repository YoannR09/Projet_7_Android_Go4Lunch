package com.example.go4lunch.dao;

import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.go4lunch.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantDao {

    RestaurantEntity getRestaurantById(String placeId);
    LiveData<List<RestaurantEntity>> getCurrentRestaurants();
    void findByName(Location location, String name);
    void refreshList(double latitude, double longitude);
}
