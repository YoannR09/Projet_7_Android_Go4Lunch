package com.example.go4lunch.repo;

import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.go4lunch.dao.RestaurantDao;
import com.example.go4lunch.mapper.RestaurantEntityToModel;
import com.example.go4lunch.model.RestaurantModel;

import java.util.List;

public class RestaurantRepository {

    RestaurantDao dao;

    public RestaurantRepository(RestaurantDao dao) {
        this.dao = dao;
    }

    public List<RestaurantModel> getCurrentRestaurants() {
        return new RestaurantEntityToModel().maps(dao.getCurrentRestaurants());
    }

    public LiveData<RestaurantModel> getRestaurantById(String placeId) {
        return null; // dao.getRestaurantById(placeId);
    }

    public void findByName(Location location,String name) {
        dao.findByName(location, name);
    }

    public void refreshList(double latitude, double longitude) {
        dao.refreshList(latitude, longitude);
    }


}
