package com.example.go4lunch.mapper;

import com.example.go4lunch.model.Like;
import com.example.go4lunch.ui.viewModel.LikeViewModel;

public class LikeModelToLikeViewModel {

    public LikeViewModel map(Like like) {
        return new LikeViewModel(
                like.getId(),
                like.getWorkmateId(),
                like.getRestaurantId(),
                like.isStatus());
    }
}
