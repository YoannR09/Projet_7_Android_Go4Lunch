package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.LikeEntity;
import com.example.go4lunch.model.Like;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LikeEntityToModel {

    LikeEntityToModel mapper = new LikeEntityToModel();

    @Test
    public void map() {

        // GIVEN
        LikeEntity entity = new LikeEntity();
        entity.setId("id");
        entity.setStatus(false);
        entity.setRestaurantId("restId");
        entity.setWorkmateId("workId");

        // WHEN
        Like like = mapper.map(entity);

        // THEN
        assertFalse(like.isStatus());
        assertEquals(like.getRestaurantId(), "restId");
        assertEquals(like.getWorkmateId(), "workId");
        assertEquals(like.getId(), "id");
    }
}
