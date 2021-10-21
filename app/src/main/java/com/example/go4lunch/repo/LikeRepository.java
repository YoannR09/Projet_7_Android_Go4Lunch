package com.example.go4lunch.repo;

import com.example.go4lunch.dao.DaoEmptyOnSuccessListener;
import com.example.go4lunch.dao.DaoOnSuccessListener;
import com.example.go4lunch.dao.LikeDao;
import com.example.go4lunch.entity.LikeEntity;
import com.example.go4lunch.mapper.LikeEntityToModel;
import com.example.go4lunch.model.Like;

public class LikeRepository {

    private LikeDao dao;

    public LikeRepository(LikeDao likeDao) {
        this.dao = likeDao;
    }

    public void createLike(LikeEntity like, RepositoryEmptySuccessListener listener) {
        dao.createLike(like, listener::onSuccess);
    }

    void getLikeFromRestaurant(String restaurantId, RepositoryOnSuccessListener<Like> listener) {
        dao.getLikeFromRestaurant(restaurantId, data -> {
            listener.onSuccess(new LikeEntityToModel().map(data));
        });
    }
}
