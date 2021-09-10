package com.example.go4lunch.ui.viewModel.ui;

import android.view.animation.Transformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.mapper.DinerEntityToModel;
import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.mapper.WorkmateEntityToModel;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import java.util.List;
import java.util.Objects;

public class RestaurantDetailsActivityViewModel {

    MutableLiveData<List<DinerModel>> dinersData = new MutableLiveData<>();
    MutableLiveData<DinerModel> dinerData = new MutableLiveData<>();

    int count = 0;

    public void createDiner(DinerEntity dinerEntity) {
        Repositories
                .getDinerRepository()
                .createDiner(dinerEntity, data -> {
                    System.out.println("Diner added !");
                });
    }

    public void loadDinersFromRestaurant(String restaurantId) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(
                (data) -> dinersData.setValue(data), restaurantId);
    }

    public void loadDinerFromWorkmate() {
        Repositories.getDinerRepository().getDinerFromWorkmate(data -> {
            dinerData.setValue(data);
        });
    };



    public LiveData<List<DinerViewModel>> getCurrentDiners() {
        return Transformations.map(dinersData, diners
                -> new DinerModelToDinerViewModel().maps(diners));
    }

    public LiveData<DinerViewModel> getCurrentDiner() {
        return Transformations.map(dinerData, diner
                -> new DinerModelToDinerViewModel().map(diner));
    }
}
