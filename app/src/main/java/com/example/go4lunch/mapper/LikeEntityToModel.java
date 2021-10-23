package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.LikeEntity;
import com.example.go4lunch.model.Like;

public class LikeEntityToModel {

    public Like map(LikeEntity likeEntity) {
        return new Like(
                likeEntity.getId(),
                likeEntity.getWorkmateId(),
                likeEntity.getRestaurantId(),
                likeEntity.isStatus());
    }
}
