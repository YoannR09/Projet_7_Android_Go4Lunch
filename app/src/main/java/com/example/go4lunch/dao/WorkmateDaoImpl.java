package com.example.go4lunch.dao;


import com.example.go4lunch.entity.WorkmateEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.go4lunch.ui.ToastError.errorMessage;

public class WorkmateDaoImpl implements WorkmateDao{

    private static final String COLLECTION_NAME = "workmate";

    // Get the Collection Reference
    protected CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public void getUser(String userId, DaoOnSuccessListener<WorkmateEntity> listener) {
        this.getUsersCollection().document(userId).get()
                .continueWith(task -> task.getResult().toObject(WorkmateEntity.class))
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(err -> {
                    errorMessage(err.getMessage());
                });
    }

    @Override
    public void getWorkmatesLits(DaoOnSuccessListener<List<WorkmateEntity>> listener) {
        this.getUsersCollection().get()
                .continueWith(task -> task.getResult().toObjects(WorkmateEntity.class))
                .addOnSuccessListener(listener::onSuccess);
    }

    // Create User in Firestore
    public void createUser() {
        FirebaseUser user = getCurrentUser();
        if(user != null){
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;

            WorkmateEntity userToCreate = new WorkmateEntity(
                    user.getUid(),
                    user.getDisplayName(),
                    urlPicture,
                    user.getEmail());

            Task<DocumentSnapshot> userData = getUserData();
            userData.addOnSuccessListener(documentSnapshot
                    -> this.getUsersCollection().document(user.getUid()).set(userToCreate));
        }
    }

    // Get User Data from Firestore
    public Task<DocumentSnapshot> getUserData(){
        String uid = this.getCurrentUserUID();
        if(uid != null){
            return this.getUsersCollection().document(uid).get();
        }else{
            return null;
        }
    }

    // Delete the User from Firestore
    public void deleteUser() {
        String uid = this.getCurrentUserUID();
        if(uid != null){
            this.getUsersCollection().document(uid).delete();
        }
    }

    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getCurrentUserUID() {
        return FirebaseAuth.getInstance().getUid();
    }

}
