package com.example.go4lunch;

import android.app.Application;
import android.content.Context;

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
