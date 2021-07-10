package com.example.go4lunch.ui.viewModel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.repo.Repositories;

import java.util.List;

public class MainActivityViewModel {

    public MainActivityViewModel () {
    }

    public LiveData<List<RestaurantModel>> getCurrentRestaurants() {
        return Repositories.getRestaurantRepository().getCurrentRestaurants();
    }

    public LiveData<List<RestaurantViewModel>> mappers(LiveData<List<RestaurantModel>> list) {
        return Transformations.map(list, restaurants -> new RestaurantModelToViewModel().maps(restaurants));
    }

    public void findByName(Location location, String name) {
        Repositories.getRestaurantRepository().findByName(location, name);
    }

    public void refreshList(double latitude, double longitude) {
       Repositories.getRestaurantRepository().refreshList(latitude, longitude);
    }
}
