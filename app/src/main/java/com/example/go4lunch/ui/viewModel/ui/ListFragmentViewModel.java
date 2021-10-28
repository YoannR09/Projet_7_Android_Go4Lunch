package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;
import com.example.go4lunch.ui.viewModel.ViewModelOnSuccessListener;

import java.util.List;

public class ListFragmentViewModel extends ViewModel {

    private MainActivityViewModel mainActivityViewModel;

    public ListFragmentViewModel(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }

    public LiveData<List<RestaurantViewModel>> getCurrentRestaurants() {
        return Transformations.map(
                mainActivityViewModel
                        .getCurrentRestaurants(), restaurants
                        -> new RestaurantModelToViewModel().mapsForListFragment(restaurants, mainActivityViewModel.getCurrentPosition()));
    }

    public void getDinersFromRestaurantSnapshot(String restaurantId, ViewModelOnSuccessListener<List<DinerViewModel>> listener) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(data -> {
            listener.onSuccess(new DinerModelToDinerViewModel().maps(data));
        }, restaurantId);
    }
}


