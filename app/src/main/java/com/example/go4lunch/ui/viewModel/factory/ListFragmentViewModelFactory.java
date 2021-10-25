package com.example.go4lunch.ui.viewModel.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.ui.viewModel.ui.ListFragmentViewModel;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.example.go4lunch.ui.viewModel.ui.MapFragmentViewModel;

public class ListFragmentViewModelFactory implements ViewModelProvider.Factory  {

    private MainActivityViewModel mainActivityViewModel;

    public ListFragmentViewModelFactory(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ListFragmentViewModel(mainActivityViewModel);
    }
}
