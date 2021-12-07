package com.example.go4lunch.dao;


import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.repo.Repositories;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;

import static com.example.go4lunch.util.Util.checkDiner;

public class WorkmateDaoImpl implements WorkmateDao{

    private static final String COLLECTION_NAME = "workmate";
    int indexWorkmate = 0;

    // Get the Collection Reference
    protected CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public void getUser(String userId, DaoOnSuccessListener<WorkmateEntity> listener) {
        this.getUsersCollection().document(userId).get()
                .continueWith(task -> task.getResult().toObject(WorkmateEntity.class))
                .addOnSuccessListener(listener::onSuccess)
                .addOnFailureListener(Throwable::printStackTrace);
    }

    public void freeUsername(String id, DaoOnSuccessListener<Boolean> listener) {
        this.getUsersCollection().get()
                .continueWith(task -> task.getResult().toObjects(WorkmateEntity.class))
                .addOnSuccessListener(data -> {
                    if(data.size() == 0) {
                        listener.onSuccess(true);
                    }
                    boolean canCreate = true;
                    for(WorkmateEntity w: data) {
                        if(w.getId().equals(id)) {
                            canCreate = false;
                        }
                    }
                    listener.onSuccess(canCreate);
                });
    }

    @Override
    public void getWorkmatesLits(DaoOnSuccessListener<List<WorkmateEntity>> listener) {
        this.getUsersCollection().get()
                .continueWith(task -> task.getResult().toObjects(WorkmateEntity.class))
                .addOnSuccessListener(data -> {
                    if(data.size() == 0) {
                        listener.onSuccess(data);
                    } else {
                        for(WorkmateEntity w: data) {
                            Repositories.getDinerRepository().getDinerFromWorkmateId(w.getId(), d -> {
                                if(d == null) {
                                    w.setHasDiner(false);
                                } else {
                                    w.setHasDiner(checkDiner(d));
                                }
                                indexWorkmate++;
                                if(indexWorkmate == data.size()) {
                                    Collections.sort(data,
                                            (s1, s2) ->
                                                    (s1.hasDiner() ? 0 : 1) - (s2.hasDiner() ? 0 : 1));
                                    listener.onSuccess(data);
                                    indexWorkmate = 0;
                                }
                            });
                        }
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }

    // Create User in Firestore
    public void createUser(DaoEmptyOnSuccessListener listener) {
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
                    -> this.getUsersCollection().document(user.getUid()).set(userToCreate)
                            .addOnSuccessListener(success -> listener.onSuccess()))
                    .addOnFailureListener(Throwable::printStackTrace);;
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
