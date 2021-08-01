package com.example.go4lunch.mapper;

import android.location.Location;

import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModelToViewModel {

    public RestaurantViewModel map(RestaurantModel restaurantModel) {
        return new RestaurantViewModel(
                restaurantModel.getId(),
                restaurantModel.getName(),
                restaurantModel.getAddress(),
                3234,
                "url web site",
                "undefined",
                restaurantModel.getOpening(),
                restaurantModel.getLatitude(),
                restaurantModel.getLongitude(),
                restaurantModel.getPhotoReference(),
                restaurantModel.getOpinion()+ "/5");
    }

    public RestaurantViewModel mapForListFragment(RestaurantModel restaurantModel, Location location) {
        Location loc = new Location("pos");
        loc.setLatitude(restaurantModel.getLatitude());
        loc.setLongitude(restaurantModel.getLongitude());
        return new RestaurantViewModel(
                restaurantModel.getId(),
                restaurantModel.getName(),
                restaurantModel.getAddress(),
                3234,
                "url web site",
                (int) location.distanceTo(loc) + "m",
                openingToText(restaurantModel.getOpening()),
                restaurantModel.getLatitude(),
                restaurantModel.getLongitude(),
                restaurantModel.getPhotoReference(),
                restaurantModel.getOpinion()+ "/5");
    }

    public List<RestaurantViewModel> mapsForListFragment(
            List<RestaurantModel> restaurantModels,
            Location location) {
        List<RestaurantViewModel> restaurantViewModels = new ArrayList<>();
        for(RestaurantModel r: restaurantModels) {
            restaurantViewModels.add(mapForListFragment(r,location));
        }
        return restaurantViewModels;
    }

    public List<RestaurantViewModel> maps(List<RestaurantModel> restaurantModels) {
        List<RestaurantViewModel> restaurantViewModels = new ArrayList<>();
        for(RestaurantModel r: restaurantModels) {
            restaurantViewModels.add(map(r));
        }
        return restaurantViewModels;
    }

    public String openingToText(String s) {
        return s.equals("true") ? "Open" : "Close";
    }
}
