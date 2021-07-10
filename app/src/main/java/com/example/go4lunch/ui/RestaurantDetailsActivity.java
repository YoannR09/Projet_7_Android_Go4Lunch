package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.ui.viewModel.RestaurantDetailsActivityViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class RestaurantDetailsActivity extends AppCompatActivity {

    ImageView picture;
    CollapsingToolbarLayout ctl;
    Toolbar toolbar;
    TextView title;
    TextView description;
    LinearLayout progressIndicator;

    RestaurantDetailsActivityViewModel viewModel = new RestaurantDetailsActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        title = findViewById(R.id.title);
        description = findViewById(R.id.descritption);
        picture = findViewById(R.id.picture);
        progressIndicator = findViewById(R.id.picture_progress);


        RestaurantModel restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("data_restaurant");
        viewModel.definePicture(restaurantModel.getId());
        viewModel.getCurrentPicture().observe(this, observe -> {
            picture.setImageBitmap(observe);
            picture.setVisibility(View.VISIBLE);
            progressIndicator.setVisibility(View.GONE);
        });
        title.setText(restaurantModel.getName());
        description.setText(restaurantModel.getAddress());
    }
}