package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.model.DinerModel;

import java.util.ArrayList;
import java.util.List;

public class DinerEntityToModel {

    public DinerModel map(DinerEntity entity) {
        return new DinerModel(
                entity.getWorkmateId(),
                entity.getRestaurantId(),
                entity.getDate(),
                entity.isStatus(),
                new WorkmateEntityToModel().map(entity.getWorkmateEntity()));
    }

    public List<DinerModel> maps(List<DinerEntity> vList) {
        List<DinerModel> dinerViewModels = new ArrayList<>();
        for(DinerEntity d: vList) {
            dinerViewModels.add(map(d));
        }
        return dinerViewModels;
    }
}
