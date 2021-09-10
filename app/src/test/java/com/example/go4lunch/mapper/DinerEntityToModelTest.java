package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.model.DinerModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DinerEntityToModelTest {

    DinerEntityToModel mapper = new DinerEntityToModel();

    @Test
    public void map() {
        // GIVEN
        DinerEntity entity = new DinerEntity();
        entity.setStatus(true);
        entity.setDate(new Date());
        entity.setWorkmateId("workmateid");
        entity.setRestaurantId("restaurantid");
        entity.setWorkmateEntity(new WorkmateEntity("id","user","url","mail"));

        // WHEN
        DinerModel model = mapper.map(entity);

        // THEN
        assertEquals(model.getDate().getDay(), entity.getDate().getDay());
        assertNotNull(model.getWorkmateModel());
        assertEquals(model.getRestaurantId(), "restaurantid");
        assertEquals(model.getWorkmateId(), "workmateid");
    }

    @Test
    public void maps() {
        // GIVEN
        DinerEntity entity1 = new DinerEntity();
        entity1.setStatus(true);
        entity1.setDate(new Date());
        entity1.setWorkmateId("workmateid1");
        entity1.setRestaurantId("restaurantid1");
        entity1.setWorkmateEntity(new WorkmateEntity("id","user","url","mail"));
        DinerEntity entity2 = new DinerEntity();
        entity2.setStatus(true);
        entity2.setDate(new Date());
        entity2.setWorkmateId("workmateid2");
        entity2.setRestaurantId("restaurantid2");
        entity2.setWorkmateEntity(new WorkmateEntity("id","user","url","mail"));
        List<DinerEntity> vList = new ArrayList<>();
        vList.add(entity1);
        vList.add(entity2);

        // WHEN
        List<DinerModel> models = mapper.maps(vList);

        // THEN
        assertEquals(models.get(0).getDate().getDay(), entity1.getDate().getDay());
        assertNotNull(models.get(0).getWorkmateModel());
        assertEquals(models.get(0).getRestaurantId(), "restaurantid1");
        assertEquals(models.get(0).getWorkmateId(), "workmateid1");
        assertEquals(models.get(1).getDate().getDay(), entity2.getDate().getDay());
        assertNotNull(models.get(1).getWorkmateModel());
        assertEquals(models.get(1).getRestaurantId(), "restaurantid2");
        assertEquals(models.get(1).getWorkmateId(), "workmateid2");
    }
}
