package com.example.go4lunch.repo;

import com.example.go4lunch.dao.DaoOnSuccessListener;
import com.example.go4lunch.dao.WorkmateDao;
import com.example.go4lunch.dao.WorkmateDaoImpl;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.model.WorkmateModel;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class WorkmateRepositoryTest {

    WorkmateDao daoMock = mock(WorkmateDao.class);
    WorkmateRepository repo = new WorkmateRepository(daoMock);
    DaoOnSuccessListener<WorkmateEntity> daoOnSuccessListener;



    @Test
    public void getUser() {
        // THEN
        daoOnSuccessListener = Assert::assertNotNull;

        // GIVEN
        RepositoryOnSuccessListener<WorkmateModel> listener = Assert::assertNull;
        doAnswer(invocation -> {
            daoOnSuccessListener.onSuccess(new WorkmateEntity());
            return null;
        }).when(daoMock).getUser(anyString(), any());

        daoMock.getUser(anyString(), any(DaoOnSuccessListener.class));
        // GIVEN
        // WHEN
        repo.getUser("id", listener);

    }
}
