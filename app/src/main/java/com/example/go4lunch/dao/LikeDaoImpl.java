package com.example.go4lunch.dao;

import com.example.go4lunch.entity.LikeEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LikeDaoImpl implements LikeDao {

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
            like.setWorkmateId(getCurrentUser().getUid());
            like.setId(getCurrentUser().getUid() + like.getRestaurantId());
            Task<DocumentSnapshot> dinerData = getLikeData(like.getRestaurantId());
            dinerData.addOnSuccessListener(documentSnapshot
                    -> {
                this.getLikeCollection()
                        .document(like.getId())
                        .set(like);
                listener.onSuccess();
            }).addOnFailureListener(Throwable::printStackTrace);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLikeFromRestaurant(
            String restaurantId,
            DaoOnSuccessListener<LikeEntity> listener) {
        this.getLikeCollection()
                .document(
                        getCurrentUser().getUid() +
                                restaurantId).get()
                .continueWith(like ->
                        like.getResult()
                                .toObject(LikeEntity.class))
                .addOnSuccessListener(l -> {
                    if (l != null) {
                        listener.onSuccess(l);
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }



    public Task<DocumentSnapshot> getLikeData(String restaurantId){
        String uid = this.getCurrentLikeUID();
        if(uid != null) {
            return this.getLikeCollection().document(
                    uid
                            +
                            restaurantId).get();
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
