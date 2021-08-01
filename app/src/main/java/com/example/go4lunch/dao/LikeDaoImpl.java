package com.example.go4lunch.dao;

import com.example.go4lunch.entity.LikeEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LikeDaoImpl implements LikeDao{

    private static final String COLLECTION_NAME = "diner";
    private static final String USERNAME_FIELD = "username";

    // Get the Collection Reference
    private CollectionReference getLikesCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    @Override
    public void createLike(LikeEntity like) {

    }

    @Override
    public Task<QuerySnapshot> getLikeFromRestaurant(String restaurantId) {
        return this.getLikesCollection()
                .whereEqualTo("workmateId", getCurrentUser().getUid())
                .whereEqualTo("restaurantId", restaurantId).get();
    }

    @Override
    public void deleteLike() {

    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
