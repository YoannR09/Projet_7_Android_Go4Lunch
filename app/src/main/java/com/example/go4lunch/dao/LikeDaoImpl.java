package com.example.go4lunch.dao;

import com.example.go4lunch.entity.LikeEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LikeDaoImpl implements LikeDao{

    public LikeDaoImpl() {

    }

    private static final String COLLECTION_NAME = "like";

    // Get the Collection Reference
    private CollectionReference getLikeCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    @Override
    public void createLike(LikeEntity like, DaoEmptyOnSuccessListener listener) {
        try {
            if(like != null) {
                like.setWorkmateId(getCurrentUser().getUid());
                Task<DocumentSnapshot> dinerData = getLikeData();
                dinerData.addOnSuccessListener(documentSnapshot
                        -> {
                    this.getLikeCollection()
                            .document(like.getWorkmateId())
                            .set(like);
                    listener.onSuccess();
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLikeFromRestaurant(
            String restaurantId,
            DaoOnSuccessListener<LikeEntity> listener) {
        this.getLikeCollection()
                .whereEqualTo("workmateId", getCurrentUser().getUid())
                .whereEqualTo("restaurantId", restaurantId).get()
                .continueWith(task -> task.getResult().toObjects(LikeEntity.class))
                .addOnSuccessListener((data) -> {
                    if(data.size() > 0) {
                        listener.onSuccess(data.get(0));
                    }
                });
    }



    public Task<DocumentSnapshot> getLikeData(){
        String uid = this.getCurrentLikeUID();
        if(uid != null) {
            return this.getLikeCollection().document(uid).get();
        } else{
            return null;
        }
    }

    public String getCurrentLikeUID() {
        return FirebaseAuth.getInstance().getUid();
    }

    @Override
    public void deleteLike() {

    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
