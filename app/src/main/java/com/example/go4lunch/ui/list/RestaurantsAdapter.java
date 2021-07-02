package com.example.go4lunch.ui.list;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.entity.RestaurantEntity;
import com.example.go4lunch.viewModel.RestaurantViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Collections;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {

    private List<RestaurantViewModel> mData;


    public RestaurantsAdapter(List<RestaurantViewModel> data) {
        this.mData = data;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.restaurant_item_view;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RestaurantViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView description;
        ImageView imageView;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.restaurant_title);
            description = itemView.findViewById(R.id.restaurant_description);
            imageView = itemView.findViewById(R.id.restaurant_picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
        void bind(RestaurantViewModel restaurantViewModel) {
            myTextView.setText(restaurantViewModel.getDecription());
            PlacesClient placesClient = Places.createClient(Go4LunchApplication.getContext());
            final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);
            final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(restaurantViewModel.getId(), fields);
            placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
                final Place place = response.getPlace();
                final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                if (metadata == null || metadata.isEmpty()) {
                    return;
                }
                for(PhotoMetadata photoMetadata: metadata) {
                }

                final String attributions = metadata.get(0).getAttributions();
                final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(metadata.get(0))
                        .setMaxWidth(500)
                        .setMaxHeight(300)
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
                    imageView.setImageBitmap(bitmap);
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;;
                        final int statusCode = apiException.getStatusCode();
                    }
                });
            });
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
