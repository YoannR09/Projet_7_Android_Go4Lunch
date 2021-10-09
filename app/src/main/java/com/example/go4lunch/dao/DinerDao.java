package com.example.go4lunch.dao;

import com.example.go4lunch.entity.DinerEntity;

import java.util.List;

public interface DinerDao {

    void createDiner(DinerEntity dinerEntity, DaoEmptyOnSuccessListener listener);

    void getDinerFromWorkmateId(DaoOnSuccessListener<DinerEntity> listener);

    void getDinerFromWorkmate(DaoOnSuccessListener<DinerEntity> listener);

    void getDinerFromRestaurant(String restaurantId, DaoOnSuccessListener<List<DinerEntity>> listener);

    void getListDiner(DaoOnSuccessListener<List<DinerEntity>> listener);

    void getListDinerFromRestaurant(DaoOnSuccessListener<List<DinerEntity>> listener, String restaurantId);

    void deleteDiner(DaoOnSuccessListener<Void> listener);


}
