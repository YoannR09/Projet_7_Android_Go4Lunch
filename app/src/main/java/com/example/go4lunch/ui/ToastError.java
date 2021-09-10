package com.example.go4lunch.ui;

import android.widget.Toast;

import com.example.go4lunch.Go4LunchApplication;

public class ToastError {

    public static void errorMessage(String err) {
        Toast toast = Toast.makeText(Go4LunchApplication.getContext(), err, Toast.LENGTH_SHORT);
        toast.show();
    }
}
