package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.patternBuilder.RestaurantEntityBuilder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RestaurantEntityToRestaurantModelTest {

    RestaurantEntityToModel mapper = new RestaurantEntityToModel();

    @Test
    public void map() {
        // GIVEN
        RestaurantEntity entity = new RestaurantEntityBuilder()
                .address("address")
                .name("name")
                .latitude(12d)
                .longitude(14d)
                .id("id")
                .opinion(4)
                .opening(true).build();

        // WHEN
        RestaurantModel model = mapper.map(entity);

        // THEN
        assertEquals(model.getId(), "id");
        assertEquals(model.getAddress(), "address");
        assertEquals(model.getOpening(),"true");
        assertNotNull(model.getLatitude());
        assertNotNull(model.getLongitude());
    }

    @Test
    public void maps() {
        // GIVEN
        RestaurantEntity entity1 = new RestaurantEntityBuilder()
                .address("address1")
                .name("name1")
                .latitude(121d)
                .longitude(141d)
                .id("id1")
                .opinion(41)
                .opening(true).build();
        RestaurantEntity entity2 = new RestaurantEntityBuilder()
                .address("address2")
                .name("name2")
                .latitude(122d)
                .longitude(142d)
                .id("id2")
                .opinion(42)
                .opening(true).build();
        List<RestaurantEntity> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);

        // WHEN
        List<RestaurantModel> models = mapper.maps(entities);

        // THEN
        assertEquals(models.get(0).getId(), "id1");
        assertEquals(models.get(0).getAddress(), "address1");
        assertEquals(models.get(0).getOpening(),"true");
        assertNotNull(models.get(0).getLatitude());
        assertNotNull(models.get(0).getLongitude());
        assertEquals(models.get(1).getId(), "id2");
        assertEquals(models.get(1).getAddress(), "address2");
        assertEquals(models.get(1).getOpening(),"true");
        assertNotNull(models.get(1).getLatitude());
        assertNotNull(models.get(1).getLongitude());
    }
}
