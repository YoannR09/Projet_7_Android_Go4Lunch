package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.LikeEntity;

import org.junit.Test;

public class LikeEntityToModel {

    LikeEntityToModel mapper = new LikeEntityToModel();

    @Test
    public void map() {

        // GIVEN
        LikeEntity entity = new LikeEntity();
        entity.setId("id");
        entity.setStatus(false);

    }

    @Test
    public void maps() {

    }
}
