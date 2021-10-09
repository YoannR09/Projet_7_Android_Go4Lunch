package com.example.go4lunch.ui.viewModel.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.example.go4lunch.ui.viewModel.ui.MapFragmentViewModel;

public class MapFragmentViewModelFactory implements ViewModelProvider.Factory {
    private MainActivityViewModel mainActivityViewModel;


    public MapFragmentViewModelFactory(MainActivityViewModel mainActivityViewModel) {
        this.mainActivityViewModel = mainActivityViewModel;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MapFragmentViewModel(mainActivityViewModel);
    }
}
