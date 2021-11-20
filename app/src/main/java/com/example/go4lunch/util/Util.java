package com.example.go4lunch.util;

import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import java.util.Date;

public class Util {

    public static boolean checkDiner (DinerViewModel diner) {
        if(diner == null) { return false; }
        return (diner.getDate().getDay() == new Date().getDay())
                && (diner.isStatus())
                && (diner.getDate().getMonth() == new Date().getMonth());
    }

    public static boolean checkDiner (DinerModel diner) {
        if(diner == null) { return false; }
        return (diner.getDate().getDay() == new Date().getDay())
                && (diner.isStatus())
                && (diner.getDate().getMonth() == new Date().getMonth());
    }

    public static boolean checkDiner (DinerEntity diner) {
        if(diner == null) { return false; }
        return (diner.getDate().getDay() == new Date().getDay())
                && (diner.isStatus())
                && (diner.getDate().getMonth() == new Date().getMonth());
    }
}
