package com.example.go4lunch.repo;

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
        dao.getUser(id, data -> listener.onSuccess(new WorkmateEntityToModel().map(data)));
    }

    public void getWorkmatesList(RepositoryOnSuccessListener<List<WorkmateModel>> listener) {
        dao.getWorkmatesLits(data -> listener.onSuccess(new WorkmateEntityToModel().maps(data)));
    }

    public void createUser() {
        dao.createUser();
    }

    public void deleteUser() {
        dao.deleteUser();
    }
}
