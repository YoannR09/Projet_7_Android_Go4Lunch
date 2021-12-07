package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.entity.LikeEntity;
import com.example.go4lunch.mapper.DinerModelToDinerViewModel;
import com.example.go4lunch.mapper.LikeModelToLikeViewModel;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.model.Like;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.LikeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.go4lunch.util.Util.checkDiner;

public class RestaurantDetailsActivityViewModel extends ViewModel {

    MutableLiveData<List<DinerModel>> dinersData = new MutableLiveData<>();
    MutableLiveData<DinerModel> dinerData = new MutableLiveData<>();
    MutableLiveData<Like> likeData = new MutableLiveData<>();

    public void createDiner(DinerEntity dinerEntity) {
        Repositories
                .getDinerRepository()
                .createDiner(dinerEntity, () -> {
                    loadDinersFromRestaurant(dinerEntity.getRestaurantId());
                });
    }

    public void createLike(LikeEntity likeEntity) {
        Repositories.getLikeRepository().createLike(
                likeEntity, () -> loadLikeFromRestaurant(likeEntity.getRestaurantId()));
    }

    public void loadDinersFromRestaurant(String restaurantId) {
        Repositories.getDinerRepository().getListDinersFromRestaurant(
                (data) -> {
                    List<DinerModel> vList = new ArrayList<>();
                    for(DinerModel d: data) {
                        if(checkDiner(d)) {
                            vList.add(d);
                        }
                    }
                    dinersData.setValue(vList);
                }, restaurantId);
    }

    public void loadDinerFromWorkmate() {
        Repositories.getDinerRepository().getDinerFromWorkmate(data -> {
            if(data != null) {
                dinerData.setValue(data);
            }
        });
    };

    public void loadLikeFromRestaurant(String restaurantId) {
        Repositories.getLikeRepository().getLikeFromRestaurant(restaurantId, data
                -> {
            likeData.setValue(data);
                }
        );
    }

    public LiveData<LikeViewModel> getCurrentLike() {
        return Transformations.map(likeData, like
                -> new LikeModelToLikeViewModel().map(like));
    }

    public LiveData<List<DinerViewModel>> getCurrentDiners() {
        return Transformations.map(dinersData, diners
                -> new DinerModelToDinerViewModel().maps(diners));
    }

    public LiveData<DinerViewModel> getCurrentDiner() {
        return Transformations.map(dinerData, diner
                -> new DinerModelToDinerViewModel().map(diner));
    }
}
