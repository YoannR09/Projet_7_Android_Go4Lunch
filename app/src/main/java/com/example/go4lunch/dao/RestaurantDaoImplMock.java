package com.example.go4lunch.dao;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.entity.RestaurantEntity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDaoImplMock implements RestaurantDao{

    MutableLiveData<List<RestaurantEntity>> currentList =  new MutableLiveData<>();
    MutableLiveData<RestaurantEntity> restaurantData = new MutableLiveData<>();



    @Override
    public RestaurantEntity getRestaurantById(String placeId) {
        return getFirstMockRestaurant();
    }

    @Override
    public LiveData<List<RestaurantEntity>> getCurrentRestaurants() {
        return currentList;
    }

    @Override
    public LiveData<RestaurantEntity> getCurrentRestaurant() {
        return getCurrentRestaurant();
    }

    @Override
    public void getRestaurantNotFoundOnMapById(String placeId, DaoOnSuccessListener listener) {
        restaurantData.setValue(getSecondMockRestaurant());
    }

    @Override
    public void refreshList(double latitude, double longitude) {
        currentList.setValue(getMockList());
    }

    public RestaurantEntity getFirstMockRestaurant() {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setAddress("adresse 123");
        restaurant.setLatitude(14);
        restaurant.setLongitude(15);
        restaurant.setName("name first");
        restaurant.setId("id");
        restaurant.setOpening(true);
        restaurant.setImgUrl("img");
        restaurant.setOpinion(4);
        restaurant.setPhoneNumber("0605060506");
        restaurant.setWebSite("url");
        restaurant.setPhotoReference("photo");
        restaurant.setWorkmateDiner(false);
        return restaurant;
    }

    public RestaurantEntity getSecondMockRestaurant() {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setAddress("adresse 321");
        restaurant.setLatitude(11);
        restaurant.setLongitude(25);
        restaurant.setName("name second");
        restaurant.setId("id2");
        restaurant.setOpening(true);
        restaurant.setImgUrl("img2");
        restaurant.setOpinion(3);
        restaurant.setPhoneNumber("0605060406");
        restaurant.setWebSite("url2");
        restaurant.setPhotoReference("photo2");
        restaurant.setWorkmateDiner(true);
        return restaurant;
    }

    public List<RestaurantEntity> getMockList() {
        List<RestaurantEntity> list = new ArrayList<>();
        list.add(getFirstMockRestaurant());
        list.add(getSecondMockRestaurant());
        return  list;
    }
}
