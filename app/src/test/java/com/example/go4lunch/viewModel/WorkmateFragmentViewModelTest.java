package com.example.go4lunch.viewModel;

import com.example.go4lunch.LiveDataTestUtil;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.repo.RepositoryOnSuccessListener;
import com.example.go4lunch.repo.WorkmateRepository;
import com.example.go4lunch.ui.viewModel.ui.WorkmatesFragmentViewModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class WorkmateFragmentViewModelTest {

    WorkmatesFragmentViewModel viewModel = new WorkmatesFragmentViewModel();
    WorkmateRepository wRepoMock = mock(WorkmateRepository.class);
    RepositoryOnSuccessListener<WorkmateModel> repoOnSuccessListener;

    @Test
    public void loadWorkmatesList() {
        // THEN
        repoOnSuccessListener = Assert::assertNotNull;

        // GIVEN
        Repositories.setWorkmateRepository(wRepoMock);
        doAnswer(invocation -> {
            repoOnSuccessListener.onSuccess(new WorkmateModel("id",
                    "username",
                    "mail",
                    "picture",
                    false
            ));
            return null;
        }).when(wRepoMock).getWorkmatesList(any());

        // WHEN
        viewModel.loadWorkmatesList();
        //List<WorkmateModel> list = LiveDataTestUtil.getValue(viewModel.getWorkmatesList());

        // THEN
        //assertNotNull(list);
    }
}
