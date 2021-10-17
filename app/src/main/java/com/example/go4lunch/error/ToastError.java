package com.example.go4lunch.error;

import android.widget.Toast;

import com.example.go4lunch.Go4LunchApplication;

public class ToastError {

    public static void showError(String error) {
        Toast toast = Toast.makeText(Go4LunchApplication.getContext(), error, Toast.LENGTH_LONG);
        toast.show();
    }
}
