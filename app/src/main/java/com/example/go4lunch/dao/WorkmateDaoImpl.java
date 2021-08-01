package com.example.go4lunch.dao;


import com.example.go4lunch.entity.WorkmateEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WorkmateDaoImpl implements WorkmateDao{

    private static final String COLLECTION_NAME = "workmate";
    private static final String USERNAME_FIELD = "username";

    // Get the Collection Reference
    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create User in Firestore
    public void createUser() {
        FirebaseUser user = getCurrentUser();
        if(user != null){
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String username = user.getDisplayName();
            String uid = user.getUid();

            WorkmateEntity userToCreate = new WorkmateEntity(uid, username, urlPicture);

            Task<DocumentSnapshot> userData = getUserData();
            userData.addOnSuccessListener(documentSnapshot -> this.getUsersCollection().document(uid).set(userToCreate));
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
