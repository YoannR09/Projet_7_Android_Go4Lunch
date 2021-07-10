package com.example.go4lunch.mapper;

import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModelToViewModel {

    public RestaurantViewModel map(RestaurantModel restaurantModel) {
        return new RestaurantViewModel(
                restaurantModel.getId(),
                restaurantModel.getAddress(),
                3234,
                "url web site",
                123,
                "1hour",
                restaurantModel.getLatitude(),
                restaurantModel.getLongitude(),
                restaurantModel.getPhotoReference());
    }

    public List<RestaurantViewModel> maps(List<RestaurantModel> restaurantModels) {
        List<RestaurantViewModel> restaurantViewModels = new ArrayList<>();
        for(RestaurantModel r: restaurantModels) {
            restaurantViewModels.add(map(r));
        }
        return restaurantViewModels;
    }
}
