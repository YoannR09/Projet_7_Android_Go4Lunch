package com.example.go4lunch.repo;

import com.example.go4lunch.dao.WorkmateDao;

public class WorkmateRepository {

    WorkmateDao dao;

    public WorkmateRepository(WorkmateDao dao) {
        this.dao = dao;
    }

    public void createUser() {
        dao.createUser();
    }

    public void deleteUser() {
        dao.deleteUser();
    }
}
