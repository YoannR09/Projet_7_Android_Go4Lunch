package com.example.go4lunch.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.repo.Repositories;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DinerDaoImpl implements DinerDao {

    private static final String COLLECTION_NAME = "diner";
    private int i;

    // Get the Collection Reference
    private CollectionReference getDinersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    @Override
    public void createDiner(DinerEntity dinerEntity, DaoEmptyOnSuccessListener listener) {
        try {
            if(dinerEntity != null) {
                dinerEntity.setDate(new Date());
                dinerEntity.setWorkmateId(getCurrentUser().getUid());
                Task<DocumentSnapshot> dinerData = getDinerData();
                dinerData.addOnSuccessListener(documentSnapshot
                        -> {
                    this.getDinersCollection()
                            .document(dinerEntity.getWorkmateId())
                            .set(dinerEntity);
                    listener.onSuccess();
                })
                        .addOnFailureListener(Throwable::printStackTrace);;

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDinerFromWorkmateId(String workmateId, DaoOnSuccessListener<DinerEntity> listener) {
        this.getDinersCollection()
                .document(workmateId).get()
                .continueWith(task -> task.getResult().toObject(DinerEntity.class))
                .addOnSuccessListener(data -> {
                    if(data != null) {
                        Repositories.getWorkmateRepository().getUser(data.getWorkmateId(), workmate -> {
                            data.setWorkmateEntity(
                                    new WorkmateEntity(
                                            workmate.getId(),
                                            workmate.getUsername(),
                                            workmate.getPictureUrl(),
                                            workmate.getMail())
                            );
                            listener.onSuccess(data);
                        });
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getDinerFromRestaurant(String restaurantId, DaoOnSuccessListener<List<DinerEntity>> listener) {
        this.getDinersCollection()
                .whereEqualTo("workmateId", getCurrentUser().getUid())
                .whereEqualTo("restaurantId", restaurantId).get()
                .continueWith(task -> task.getResult().toObjects(DinerEntity.class))
                .addOnSuccessListener(data -> {
                    if(data.size() == 0) listener.onSuccess(data);
                    i = 0;
                    data.forEach(item -> Repositories.getWorkmateRepository()
                            .getUser(item.getWorkmateId(), workmate -> {
                                i++;
                                item.setWorkmateEntity(new WorkmateEntity(
                                        workmate.getId(),
                                        workmate.getUsername(),
                                        workmate.getPictureUrl(),
                                        workmate.getMail()));
                                if(i == data.size()) {
                                    listener.onSuccess(data);
                                }
                            }));
                }).addOnFailureListener(Throwable::printStackTrace);;
    }

    @Override
    public void getDinerFromWorkmate(DaoOnSuccessListener<DinerEntity> listener) {
        this.getDinersCollection()
                .document(getCurrentUser().getUid()).get()
                .continueWith(task -> task.getResult().toObject(DinerEntity.class))
                .addOnSuccessListener(data -> {
                    if(data != null) {
                        Repositories.getWorkmateRepository().getUser(data.getWorkmateId(), workmate -> {
                            data.setWorkmateEntity(
                                    new WorkmateEntity(
                                            workmate.getId(),
                                            workmate.getUsername(),
                                            workmate.getPictureUrl(),
                                            workmate.getMail())
                            );
                            listener.onSuccess(data);
                        });
                    }
                }).addOnFailureListener(Throwable::printStackTrace);;
    }

    @Override
    public void getListDiner(DaoOnSuccessListener<List<DinerEntity>> listener) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getListDinerFromRestaurant(DaoOnSuccessListener<List<DinerEntity>> listener, String restaurantId) {
        this.getDinersCollection().whereEqualTo("restaurantId", restaurantId).get()
                .continueWith(task -> task.getResult().toObjects(DinerEntity.class))
                .addOnSuccessListener(data -> {
                    if(data.size() == 0) {
                        listener.onSuccess(data);
                    }
                    i = 0;
                    data.forEach(item -> Repositories.getWorkmateRepository()
                            .getUser(item.getWorkmateId(), workmate -> {
                                i++;
                                item.setWorkmateEntity(new WorkmateEntity(
                                        workmate.getId(),
                                        workmate.getUsername(),
                                        workmate.getPictureUrl(),
                                        workmate.getMail()));
                                if(i == data.size()) {
                                    listener.onSuccess(data);
                                }
                            }));
                }).addOnFailureListener(Throwable::printStackTrace);;
    }

    @Override
    public void deleteDiner(DaoOnSuccessListener<Void> listener) {

    }

    // Get Diner Data from Firestore
    public Task<DocumentSnapshot> getDinerData(){
        String uid = this.getCurrentDinerUID();
        if(uid != null) {
            return this.getDinersCollection().document(uid).get();
        } else{
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
