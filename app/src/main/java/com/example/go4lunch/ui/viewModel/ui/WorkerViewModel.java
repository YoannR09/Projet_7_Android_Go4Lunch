package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.mapper.DinerEntityToModel;
import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.google.firebase.database.core.Repo;

import java.util.List;
import java.util.Objects;

public class WorkerViewModel {

    MutableLiveData<List<DinerModel>> dinersData = new MutableLiveData<>();
    MutableLiveData<DinerModel> dinerData = new MutableLiveData<>();

    public void loadDinersFromRestaurant(String restaurantId) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(
                (data) -> dinersData.setValue(data), restaurantId);
    }

    public void loadDinerFromWorkmate() {
        Repositories.getDinerRepository().getDinerFromWorkmate(data -> {
            dinerData.setValue(data);
        });
    };

    public List<DinerViewModel> getCurrentDiners() {
        return new DinerModelToDinerViewModel().maps(Objects.requireNonNull(dinersData.getValue()));
    }

    public void loadDinersListFromRestaurantId(String restaurantId) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(data -> {
            this.dinersData.setValue(data);
        }, restaurantId);
    }

    public DinerViewModel getCurrentDiner() {
        return new DinerModelToDinerViewModel().map(Objects.requireNonNull(dinerData.getValue()));
    }
}
