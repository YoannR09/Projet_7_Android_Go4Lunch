package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.model.WorkmateModel;

import java.util.ArrayList;
import java.util.List;

public class WorkmateEntityToModel {

    public WorkmateModel map(WorkmateEntity workmateEntity) {
        return new WorkmateModel(
                workmateEntity.getId(),
                workmateEntity.getUsername(),
                workmateEntity.getMail(),
                workmateEntity.getUrlPicture(),
                workmateEntity.hasDiner());
    }

    public List<WorkmateModel> maps(List<WorkmateEntity> workmateEntities) {
        List<WorkmateModel> models = new ArrayList<>();
        for(WorkmateEntity workmateEntity: workmateEntities) {
            models.add(map(workmateEntity));
        }
        return models;
    }
}
