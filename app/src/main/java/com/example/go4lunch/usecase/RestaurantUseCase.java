package com.example.go4lunch.usecase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.repo.Repositories;

public class RestaurantUseCase {

    private MutableLiveData data = new MutableLiveData();

    public LiveData<RestaurantEntity> getRestaurantUseCase() {
        return Repositories.getRestaurantRepository().getCurrentRestaurant();
    }

    public void loadRestaurantUseCase(String id) {
        Repositories.getRestaurantRepository().getRestaurantNotFoundOnMapById(id);
    }
}
