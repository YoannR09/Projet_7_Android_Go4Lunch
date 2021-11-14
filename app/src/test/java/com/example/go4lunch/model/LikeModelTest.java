package com.example.go4lunch.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LikeModelTest {

    private Like like = new Like(
            null,
            null,
            null,
            false);

    @Test
    public void setterAndGetterWorkmateId() {
        // GIVEN
        String id = "idW";
        // WHEN
        like.setWorkmateId(id);
        // THEN
        assertEquals(like.getWorkmateId(), "idW");
    }

    @Test
    public void setterAndGetterRestaurantId() {
        // GIVEN
        String restaurantId = "idR";
        // WHEN
        like.setRestaurantId(restaurantId);
        // THEN
        assertEquals(like.getRestaurantId(), "idR");
    }

    @Test
    public void setterAndGetterId() {
        // GIVEN
        String id = "id";
        // WHEN
        like.setId(id);
        // THEN
        assertEquals(like.getId(), "id");
    }

    @Test
    public void setterAndGetterStatus() {
        // GIVEN
        // WHEN
        like.setStatus(true);
        // THEN
        assertEquals(like.isStatus(), true);
    }
 }
