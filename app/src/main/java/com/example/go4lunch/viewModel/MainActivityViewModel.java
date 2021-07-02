package com.example.go4lunch.viewModel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.repo.Repositories;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel {

    public MainActivityViewModel () {
    }

    public LiveData<List<RestaurantViewModel>> getCurrentRestaurants() {
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.setValue(new RestaurantModelToViewModel().maps(
                Repositories.getRestaurantRepository().getCurrentRestaurants()));
        return mutableLiveData;
    }

    public void findByName(Location location, String name) {
        Repositories.getRestaurantRepository().findByName(location, name);
    }
    public void refreshList(double latitude, double longitude) {
        Repositories.getRestaurantRepository().refreshList(latitude, longitude);
    }
}
