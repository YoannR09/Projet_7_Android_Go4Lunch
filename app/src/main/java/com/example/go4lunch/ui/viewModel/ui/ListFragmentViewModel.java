package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;
import com.example.go4lunch.ui.viewModel.ViewModelOnSuccessListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.go4lunch.util.Util.*;

public class ListFragmentViewModel extends ViewModel {

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

    public void getDinersFromRestaurantSnapshot(String restaurantId, ViewModelOnSuccessListener<List<DinerViewModel>> listener) {
        Repositories.getDinerRepository().getListDinersFromRestaurantSnapshot(restaurantId, data -> {
            listener.onSuccess(new DinerModelToDinerViewModel().maps(data));
        });
    }

    public void loadDinersFromRestaurant(String restaurantId) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(data -> {
                    List<DinerModel> vList = new ArrayList<>();
                    for(DinerModel d: data) {
                        if(checkDiner(d)) {
                            vList.add(d);
                        }
                    }
                    diners.setValue(vList);
                }, restaurantId
        );
    }
}


