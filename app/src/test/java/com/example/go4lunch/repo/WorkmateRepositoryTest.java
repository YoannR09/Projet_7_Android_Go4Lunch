package com.example.go4lunch.repo;

import com.example.go4lunch.dao.DaoEmptyOnSuccessListener;
import com.example.go4lunch.dao.DaoOnSuccessListener;
import com.example.go4lunch.dao.WorkmateDao;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.mapper.WorkmateEntityToModel;
import com.example.go4lunch.model.WorkmateModel;

import org.junit.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorkmateRepositoryTest {

    WorkmateDao daoMock = mock(WorkmateDao.class);
    WorkmateRepository repo = new WorkmateRepository(daoMock);


    @Test
    public void getUser() {
        // GIVEN
        RepositoryOnSuccessListener<WorkmateModel> listener = data -> {

            // THEN

        };

        // WHEN
        repo.getUser("id", listener);
    }
}
