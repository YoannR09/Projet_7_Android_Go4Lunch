package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.go4lunch.util.Util.checkDiner;

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
            List<DinerModel> vList = new ArrayList<>();
            for(DinerModel d: data) {
                if(checkDiner(d)) {
                    vList.add(d);
                }
            }
            dinersData.setValue(vList);
        }, restaurantId);
    }

    public DinerViewModel getCurrentDiner() {
        return new DinerModelToDinerViewModel().map(Objects.requireNonNull(dinerData.getValue()));
    }
}
