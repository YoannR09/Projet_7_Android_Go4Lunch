package com.example.go4lunch;

import android.app.Application;
import android.content.Context;

import com.example.go4lunch.workers.DinerWorker;
import com.google.android.libraries.places.api.Places;

import java.util.Locale;

public class Go4LunchApplication extends Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DinerWorker.start();
        Places.initialize(getApplicationContext(),
                "AIzaSyBxg-nI5L_4b2NXhUXlz6L6xkpiLH0-AE4",
                Locale.FRANCE);
    }

    public static Context getContext() {
        return context;
    }
}
