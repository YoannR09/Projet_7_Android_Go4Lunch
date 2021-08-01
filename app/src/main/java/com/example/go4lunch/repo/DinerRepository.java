package com.example.go4lunch.repo;

import com.example.go4lunch.dao.DinerDao;
import com.example.go4lunch.entity.DinerEntity;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class DinerRepository {

    private DinerDao dao;

    public DinerRepository(DinerDao dao) {
        this.dao = dao;
    }

    public void createDiner(DinerEntity dinerEntity) {
        dao.createDiner(dinerEntity);
    }

    public Task<DinerEntity> getDinerFromWorkmate() {
        return dao.getDinerFromWorkmate();
    }

    public Task<List<DinerEntity>> getListDiner() {
        return dao.getListDiner();
    }

    public Task<List<DinerEntity>> getListDinerFromRestaurant() {
        return dao.getListDinerFromRestaurant();
    }

    void deleteDiner() {
        dao.deleteDiner();
    }
}
