package com.example.go4lunch.dao;

import com.example.go4lunch.entity.DinerEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class DinerDaoImpl implements DinerDao {

    private static final String COLLECTION_NAME = "diner";
    private static final String USERNAME_FIELD = "username";

    // Get the Collection Reference
    private CollectionReference getDinersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    @Override
    public void createDiner(DinerEntity dinerEntity) {
        try {
            if(dinerEntity != null) {
                dinerEntity.setDate(new Date());
                dinerEntity.setWorkmateId(getCurrentUser().getUid());
                Task<DocumentSnapshot> dinerData = getDinerData();
                dinerData.addOnSuccessListener(documentSnapshot
                        -> this.getDinersCollection().document(dinerEntity.getWorkmateId()).set(dinerEntity));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task<DinerEntity> getDinerFromWorkmate() {
        return null;
    }

    @Override
    public Task<List<DinerEntity>> getListDiner() {
        return null;
    }

    @Override
    public Task<List<DinerEntity>> getListDinerFromRestaurant() {
        return null;
    }

    @Override
    public void deleteDiner() {

    }

    // Get Diner Data from Firestore
    public Task<DocumentSnapshot> getDinerData(){
        String uid = this.getCurrentDinerUID();
        if(uid != null){
            return this.getDinersCollection().document(uid).get();
        }else{
            return null;
        }
    }

    public String getCurrentDinerUID() {
        return FirebaseAuth.getInstance().getUid();
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
