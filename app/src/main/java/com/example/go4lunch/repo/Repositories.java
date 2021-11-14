package com.example.go4lunch.repo;

import com.example.go4lunch.dao.DinerDaoImpl;
import com.example.go4lunch.dao.LikeDaoImpl;
import com.example.go4lunch.dao.RestaurantDaoImpl;
import com.example.go4lunch.dao.RestaurantDaoImplMock;
import com.example.go4lunch.dao.WorkmateDaoImpl;

public class Repositories {

    public Repositories() {
    }



    static RestaurantRepository restaurantRepository;
    static WorkmateRepository workmateRepository;
    static DinerRepository dinerRepository;
    static LikeRepository likeRepository;

    public static RestaurantRepository getRestaurantRepository() {
        if(restaurantRepository == null) {
            createRestaurantRepository();
        }
        return restaurantRepository;
    }

    public static WorkmateRepository getWorkmateRepository() {
        if(workmateRepository == null) {
            createWorkmateRepository();
        }
        return workmateRepository;
    }

    public static DinerRepository getDinerRepository() {
        if(dinerRepository == null) {
            createDinerRepository();
        }
        return dinerRepository;
    }

    public static LikeRepository getLikeRepository() {
        if(likeRepository == null) {
            createLikeRepository();
        }
        return likeRepository;
    }

    public static void createRestaurantRepository() {
        restaurantRepository = new RestaurantRepository(new RestaurantDaoImpl());
    }

    public static void createWorkmateRepository() {
        workmateRepository = new WorkmateRepository(new WorkmateDaoImpl());
    }

    public static void createDinerRepository() {
        dinerRepository = new DinerRepository(new DinerDaoImpl());
    }

    public static void createLikeRepository() {
        likeRepository = new LikeRepository(new LikeDaoImpl());
    }

    public static void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        Repositories.restaurantRepository = restaurantRepository;
    }

    public static void setWorkmateRepository(WorkmateRepository workmateRepository) {
        Repositories.workmateRepository = workmateRepository;
    }

    public static void setDinerRepository(DinerRepository dinerRepository) {
        Repositories.dinerRepository = dinerRepository;
    }

    public static void setLikeRepository(LikeRepository likeRepository) {
        Repositories.likeRepository = likeRepository;
    }

    /**
     * Test mock restaurant dao impl
     */
    public static void createRestaurantRepoMock(){
        restaurantRepository = new RestaurantRepository(new RestaurantDaoImplMock());
    }
}
