package com.example.go4lunch.dao;

import com.example.go4lunch.entity.LikeEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface LikeDao {

    void createLike(LikeEntity like, DaoEmptyOnSuccessListener listener);

    void getLikeFromRestaurant(String restaurantId, DaoOnSuccessListener<LikeEntity> listener);

    void deleteLike();
}
