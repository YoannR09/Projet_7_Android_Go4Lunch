package com.example.go4lunch.ui.viewModel.ui;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.dao.DaoEmptyOnSuccessListener;
import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.mapper.RestaurantModelToViewModel;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.repo.RepositoryEmptySuccessListener;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;
import com.example.go4lunch.ui.viewModel.ViewModelEmptyOnSuccessListener;
import com.example.go4lunch.ui.viewModel.ViewModelOnSuccessListener;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private Location location;
    private int currentTab;

    private MutableLiveData<Integer> tabIndex = new MutableLiveData<>();
    private MutableLiveData<DinerViewModel> diner = new MutableLiveData<>();

    public MainActivityViewModel () {
    }

    public LiveData<List<RestaurantModel>> getCurrentRestaurants() {
        return Repositories.getRestaurantRepository().getCurrentRestaurants();
    }

    public void refreshList(double latitude, double longitude) {
       Repositories.getRestaurantRepository().refreshList(latitude, longitude);
    }

    public void createUser(ViewModelEmptyOnSuccessListener listener) {
        Repositories.getWorkmateRepository().createUser(listener::onSuccess);
    }

    public Location getCurrentPosition() {
        return location;
    }

    public void getCurrentDinerSnapshot(ViewModelOnSuccessListener<DinerViewModel> listener) {
        Repositories.getDinerRepository().getDinerFromWorkmate(
                data -> {
                    if(data != null) {
                        listener.onSuccess(new DinerModelToDinerViewModel().map(data));
                    } else {
                        listener.onSuccess(null);
                    }
                });
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LiveData<Integer> getCurrentTab() {
        return tabIndex;
    }

    public void setCurrentTab(int tab) {
        this.tabIndex.setValue(tab);
    }
}
