package com.example.go4lunch.repo;

import com.example.go4lunch.dao.RestaurantDaoImpl;

public class Repositories {

    public Repositories() {
    }

    static RestaurantRepository restaurantRepository;

    public static RestaurantRepository getRestaurantRepository() {
        if(restaurantRepository == null) {
            createRestaurantRepository();
        }
        return restaurantRepository;
    }

    public static void createRestaurantRepository() {
        restaurantRepository = new RestaurantRepository(new RestaurantDaoImpl());
    }
}
