package com.example.go4lunch.ui.viewModel.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.repo.Repositories;

import java.util.List;

public class WorkmatesFragmentViewModel extends ViewModel {

    MutableLiveData<List<WorkmateModel>> workmates = new MutableLiveData<>();


    public WorkmatesFragmentViewModel() {
    }

    public void loadWormatesList() {
        Repositories.getWorkmateRepository().getWorkmatesList(data -> {
            workmates.setValue(data);
        });
    }

    public LiveData<List<WorkmateModel>> getWorkmatesList() {
        return workmates;
    }


}
