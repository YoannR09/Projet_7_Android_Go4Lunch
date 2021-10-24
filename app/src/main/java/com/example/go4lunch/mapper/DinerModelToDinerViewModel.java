package com.example.go4lunch.mapper;

import androidx.lifecycle.LiveData;

import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import java.util.ArrayList;
import java.util.List;

public class DinerModelToDinerViewModel {

    public DinerViewModel map(DinerModel diner) {
        return new DinerViewModel(
                diner.getWorkmateModel().getUsername(),
                diner.getWorkmateModel().getPictureUrl(),
                diner.getRestaurantId(),
                diner.isStatus(),
                diner.getDate(),
                diner.getInfo());
    }

    public List<DinerViewModel> maps(List<DinerModel> list) {
        List<DinerViewModel> vList = new ArrayList<>();
        for(DinerModel v: list) {
            vList.add(map(v));
        }
        return vList;
    }
}
