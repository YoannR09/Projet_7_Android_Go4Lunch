package com.example.go4lunch.repo;

import com.example.go4lunch.dao.DaoEmptyOnSuccessListener;
import com.example.go4lunch.dao.WorkmateDao;
import com.example.go4lunch.mapper.WorkmateEntityToModel;
import com.example.go4lunch.model.WorkmateModel;

import java.util.List;

public class WorkmateRepository {

    WorkmateDao dao;

    public WorkmateRepository(WorkmateDao dao) {
        this.dao = dao;
    }

    public void getUser(String id, RepositoryOnSuccessListener<WorkmateModel> listener) {
        if(id != null) {
            dao.getUser(id, data -> {
                if (data != null) {
                    listener.onSuccess(new WorkmateEntityToModel().map(data));
                }
            });
        } else {
            System.out.println("id null can return data");
        }
    }

    public void getWorkmatesList(RepositoryOnSuccessListener<List<WorkmateModel>> listener) {
        dao.getWorkmatesLits(data -> listener.onSuccess(new WorkmateEntityToModel().maps(data)));
    }

    public void freeUsername(String id, RepositoryOnSuccessListener<Boolean> listener) {
        dao.freeUsername(id, listener::onSuccess);
    }

    public void createUser(RepositoryEmptySuccessListener listener) {
        dao.createUser(listener::onSuccess);
    }

    public void deleteUser() {
        dao.deleteUser();
    }
}
