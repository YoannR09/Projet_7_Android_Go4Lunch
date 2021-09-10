package com.example.go4lunch.mapper;

import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.model.WorkmateModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WorkMateEntityToWorkmateModelTest {

    WorkmateEntityToModel mapper = new WorkmateEntityToModel();

    @Test
    public void map() {
        // GIVEN
        WorkmateEntity entity = new WorkmateEntity(
                "id",
                "username",
                "url",
                "mail");

        // WHEN
        WorkmateModel model = mapper.map(entity);

        // THEN
        assertEquals(model.getMail(), "mail");
        assertEquals(model.getPictureUrl(), "url");
        assertEquals(model.getUsername(), "username");
    }

    @Test
    public void maps() {
        // GIVEN
        WorkmateEntity entity1 = new WorkmateEntity(
                "id1",
                "username1",
                "url1",
                "mail1");
        WorkmateEntity entity2 = new WorkmateEntity(
                "id2",
                "username2",
                "url2",
                "mail2");
        List<WorkmateEntity> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);

        // WHEN
        List<WorkmateModel> models = mapper.maps(entities);

        // THEN
        assertEquals(models.get(0).getMail(), "mail1");
        assertEquals(models.get(0).getPictureUrl(), "url1");
        assertEquals(models.get(0).getUsername(), "username1");
        assertEquals(models.get(1).getMail(), "mail2");
        assertEquals(models.get(1).getPictureUrl(), "url2");
        assertEquals(models.get(1).getUsername(), "username2");
    }
}
