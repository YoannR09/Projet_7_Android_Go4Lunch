package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;

import java.util.List;

public class ListFragmentViewModel {

    private MainActivityViewModel mainActivityViewModel;
    private MutableLiveData diners = new MutableLiveData();

    public ListFragmentViewModel() {
    }

    public ListFragmentViewModel(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }

    public LiveData<List<RestaurantViewModel>> getCurrentRestaurants() {
        return Transformations.map(
                Repositories.getRestaurantRepository()
                        .getCurrentRestaurants(), restaurants
                        -> new RestaurantModelToViewModel().mapsForListFragment(restaurants, mainActivityViewModel.getCurrentPosition()));
    }

    public LiveData<List<DinerViewModel>> getDinersFromRestaurant() {
        return diners;
    }

    public void loadDinersFromRestaurant(String restaurantId) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(data -> diners.setValue(data), restaurantId
                );
    }
}
