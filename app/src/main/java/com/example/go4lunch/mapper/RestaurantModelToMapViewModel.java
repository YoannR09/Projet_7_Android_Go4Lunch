package com.example.go4lunch.mapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.model.RestaurantModel;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModelToMapViewModel {

    public MarkerOptions map(RestaurantModel restaurantModel) {
        LatLng restaurantPosition = new LatLng(restaurantModel.getLatitude(), restaurantModel.getLongitude());
        return new MarkerOptions()
                .position(restaurantPosition)
                .icon(bitmapDescriptorFromVector(Go4LunchApplication.getContext(),
                        R.drawable.ic_baseline_restaurant_24))
                .title(restaurantModel.getId());
    }

    public List<MarkerOptions> maps(List<RestaurantModel> restaurantModels) {
        List<MarkerOptions> markerOptions = new ArrayList<>();
        for(RestaurantModel r: restaurantModels) {
            markerOptions.add(map(r));
        }
        return markerOptions;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
