package com.example.go4lunch.repo;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.dao.RestaurantDao;
import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.mapper.RestaurantEntityToModel;
import com.example.go4lunch.model.RestaurantModel;

import java.util.List;

public class RestaurantRepository {

    RestaurantDao dao;

    public RestaurantRepository(RestaurantDao dao) {
        this.dao = dao;
    }

    public LiveData<RestaurantEntity> getCurrentRestaurant() {
        return dao.getCurrentRestaurant();
    }
    public void getRestaurantNotFoundOnMapById(String placeId) {
        dao.getRestaurantNotFoundOnMapById(placeId);
    }

    public LiveData<List<RestaurantModel>> getCurrentRestaurants() {
        return mappers(dao.getCurrentRestaurants());
    }

    public RestaurantModel getRestaurantById(String placeId) {
        RestaurantEntity result = dao.getRestaurantById(placeId);
        if(result != null) {
            return new RestaurantEntityToModel().map(result);
        } else {
            return null;
        }
    }

    public LiveData<List<RestaurantModel>> mappers(LiveData<List<RestaurantEntity>> list) {
        return Transformations.map(list, restaurants -> new RestaurantEntityToModel().maps(restaurants));
    }

    public LiveData<RestaurantModel> mapper(LiveData<RestaurantEntity> data) {
        return Transformations.map(data, restaurant -> new RestaurantEntityToModel().map(restaurant));
    }

    public void findByName(Location location,String name) {
        dao.findByName(location, name);
    }

    public void refreshList(double latitude, double longitude) {
        dao.refreshList(latitude, longitude);
    }


}
