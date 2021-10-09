package com.example.go4lunch.ui.viewModel.ui;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private Location location;

    private MutableLiveData<DinerViewModel> diner = new MutableLiveData<>();

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

    public void createUser() {
        Repositories.getWorkmateRepository().createUser();
    }

    public Location getCurrentPosition() {
        return location;
    }

    public LiveData<DinerViewModel> getCurrentDiner() {
        return diner;
    }

    public void loadCurrentDiner() {
        Repositories.getDinerRepository().getDinerFromWorkmate(data -> {
            diner.setValue(new DinerModelToDinerViewModel().map(data));
        });
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}