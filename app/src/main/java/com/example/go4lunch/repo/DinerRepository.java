package com.example.go4lunch.repo;

import androidx.lifecycle.LiveData;

import com.example.go4lunch.dao.DinerDao;
import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.mapper.DinerEntityToModel;
import com.example.go4lunch.model.DinerModel;

import java.util.List;

public class DinerRepository {

    private DinerDao dao;

    public DinerRepository(DinerDao dao) {
        this.dao = dao;
    }

    public void createDiner(DinerEntity dinerEntity, RepositoryEmptySuccessListener listener) {
        dao.createDiner(dinerEntity, listener::onSuccess);
    }

    public void getDinerFromWorkmate(
            RepositoryOnSuccessListener<DinerModel> listener) {
        dao.getDinerFromWorkmate(
                data -> listener.onSuccess(new DinerEntityToModel().map(data)));
    }

    public void getDinerFromWorkmateId(
            String workmateId,
            RepositoryOnSuccessListener<DinerModel> listener) {
        dao.getDinerFromWorkmateId(
                workmateId,
                data -> listener.onSuccess(new DinerEntityToModel().map(data)));
    }

    public void getDinerFromRestaurant(
            String restaurantId,
            RepositoryOnSuccessListener<List<DinerModel>> listener) {

        dao.getDinerFromRestaurant(
                restaurantId,
                data -> listener.onSuccess(new DinerEntityToModel().maps(data)));
    }

    public void getListDiner(RepositoryOnSuccessListener<List<DinerModel>> listener) {
        dao.getListDiner(data -> listener.onSuccess(new DinerEntityToModel().maps(data)));
    }

    public void getListDinersFromRestaurant(RepositoryOnSuccessListener<List<DinerModel>> listener, String restaurantId) {
        dao.getListDinerFromRestaurant(data -> listener.onSuccess(new DinerEntityToModel().maps(data)), restaurantId);
    }

    void deleteDiner(RepositoryOnSuccessListener<Void> listener) {
        dao.deleteDiner(listener::onSuccess);
    }
}
