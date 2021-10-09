package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import java.util.List;

public class RestaurantDetailsActivityViewModel extends ViewModel {

    MutableLiveData<List<DinerModel>> dinersData = new MutableLiveData<>();
    MutableLiveData<DinerModel> dinerData = new MutableLiveData<>();

    public void createDiner(DinerEntity dinerEntity) {
        Repositories
                .getDinerRepository()
                .createDiner(dinerEntity, () -> {
                    loadDinersFromRestaurant(dinerEntity.getRestaurantId());
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
