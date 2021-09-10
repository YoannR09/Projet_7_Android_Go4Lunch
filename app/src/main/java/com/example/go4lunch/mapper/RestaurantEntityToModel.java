package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantEntityToModel {

    public RestaurantModel map(RestaurantEntity restaurantEntity) {
        return new RestaurantModel(
                restaurantEntity.getId(),
                restaurantEntity.getName(),
                restaurantEntity.getLatitude(),
                restaurantEntity.getLongitude(),
                restaurantEntity.getAddress(),
                restaurantEntity.getInterestMates(),
                restaurantEntity.getOpinion(),
                restaurantEntity.getPhotoReference(),
                String.valueOf(restaurantEntity.getOpening()),
                restaurantEntity.isWorkmateDiner());
    }

    public List<RestaurantModel> maps(List<RestaurantEntity> restaurantEntity) {
        List<RestaurantModel> modelList = new ArrayList<>();
        for(RestaurantEntity entity: restaurantEntity) {
            modelList.add(map(entity));
        }
        return modelList;
    }
}
