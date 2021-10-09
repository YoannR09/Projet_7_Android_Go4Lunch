package com.example.go4lunch.ui.viewModel.ui;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.mapper.RestaurantModelToMapViewModel;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragmentViewModel extends ViewModel {

    private MainActivityViewModel mainActivityViewModel;

    public MapFragmentViewModel(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }

    public LiveData<List<MarkerOptions>> getRestaurantMarkersList() {
        return Transformations.map(mainActivityViewModel.getCurrentRestaurants()
                , restaurants -> new RestaurantModelToMapViewModel().maps(restaurants));
    }

    public void refreshList(double latitude, double longitude) {
        mainActivityViewModel.refreshList(latitude, longitude);
    }

    public Location getLocation() {
        return mainActivityViewModel.getCurrentPosition();
    }
}
