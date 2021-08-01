package com.example.go4lunch.dao;

import com.example.go4lunch.entity.DinerEntity;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface DinerDao {

    void createDiner(DinerEntity dinerEntity);

    Task<DinerEntity> getDinerFromWorkmate();

    Task<List<DinerEntity>> getListDiner();

    Task<List<DinerEntity>> getListDinerFromRestaurant();

    void deleteDiner();
}
