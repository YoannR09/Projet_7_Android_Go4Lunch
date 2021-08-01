package com.example.go4lunch;

import android.app.Application;
import android.content.Context;

import com.example.go4lunch.dao.WorkmateDaoImpl;
import com.google.firebase.auth.FirebaseAuthException;

public class Go4LunchApplication extends Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
