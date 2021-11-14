package com.example.go4lunch.viewModel;

import com.example.go4lunch.LiveDataTestUtil;
import com.example.go4lunch.dao.DaoOnSuccessListener;
import com.example.go4lunch.entity.WorkmateEntity;
import com.example.go4lunch.model.DinerModel;
import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.repo.DinerRepository;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.repo.RepositoryOnSuccessListener;
import com.example.go4lunch.repo.WorkmateRepository;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainActivityViewModelTest {

    MainActivityViewModel viewModel = new MainActivityViewModel();
    DinerRepository dinerRepoMock = mock(DinerRepository.class);
    RepositoryOnSuccessListener<DinerModel> repoOnSuccessListener;

    @Test
    public void getCurrentDinerSnapshot() {
        // THEN
        repoOnSuccessListener = Assert::assertNotNull;

        // GIVEN
        Repositories.setDinerRepository(dinerRepoMock);
        doAnswer(invocation -> {
            repoOnSuccessListener.onSuccess(new DinerModel(
                    "id",
                    "restaurant",
                    new Date(),
                    true,
                    null,
                    "info"
            ));
            return null;
        }).when(dinerRepoMock).getDinerFromWorkmate(any());

        // WHEN
        viewModel.getCurrentDinerSnapshot(any());
    }
}
