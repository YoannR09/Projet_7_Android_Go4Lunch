package com.example.go4lunch.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.repo.Repositories;

import java.util.List;

public class ListFragmentViewModel {

    private MainActivityViewModel mainActivityViewModel;

    public ListFragmentViewModel(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }

    public LiveData<List<RestaurantViewModel>> getCurrentRestaurants() {
        return Transformations.map(
                Repositories.getRestaurantRepository()
                        .getCurrentRestaurants(), restaurants
                        -> new RestaurantModelToViewModel().mapsForListFragment(restaurants, mainActivityViewModel.getCurrentPosition()));
    }
}
