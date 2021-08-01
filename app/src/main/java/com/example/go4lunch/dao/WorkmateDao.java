package com.example.go4lunch.dao;

import com.example.go4lunch.entity.WorkmateEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public interface WorkmateDao {

    void createUser();

    void deleteUser();
}
