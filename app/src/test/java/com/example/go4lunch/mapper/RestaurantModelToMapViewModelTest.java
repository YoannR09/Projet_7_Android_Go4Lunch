package com.example.go4lunch.mapper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestaurantModelToMapViewModelTest {

    RestaurantModelToMapViewModel mapper = new RestaurantModelToMapViewModel();

    @Test
    public void map() {
        /*
        // GIVEN
        RestaurantModel model = new RestaurantModel(
                "id",
                "name",
                10L,
                12L,
                "adresse",
                new ArrayList<>(),
                4,
                "photo",
                "true"
                );

        // WHEN
        MarkerOptions viewModel = mapper.map(model);

        // THEN
        assertEquals(viewModel.getPosition().latitude, model.getLatitude());
        assertEquals(viewModel.getPosition().longitude, model.getLongitude());

         */
    }

    @Test
    public void maps() {
        /*
        // GIVEN
        RestaurantModel model1 = new RestaurantModel(
                "id1",
                "name1",
                101L,
                121L,
                "adresse1",
                new ArrayList<>(),
                4,
                "photo1",
                "true1"
        );
        // GIVEN
        RestaurantModel model2 = new RestaurantModel(
                "id",
                "name",
                102L,
                122L,
                "adresse",
                new ArrayList<>(),
                4,
                "photo",
                "true"
        );
        List<RestaurantModel> modelList = new ArrayList<>();
        modelList.add(model1);
        modelList.add(model2);

        // WHEN
        List<MarkerOptions> viewModels = mapper.maps(modelList);

        // THEN
        assertEquals(viewModels.get(0).getPosition().latitude, model1.getLatitude());
        assertEquals(viewModels.get(0).getPosition().longitude, model1.getLongitude());
        assertEquals(viewModels.get(1).getPosition().latitude, model2.getLatitude());
        assertEquals(viewModels.get(1).getPosition().longitude, model2.getLongitude());

         */
    }
}
