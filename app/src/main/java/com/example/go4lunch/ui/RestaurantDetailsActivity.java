package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.ui.viewModel.RestaurantDetailsActivityViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantDetailsActivity extends AppCompatActivity {

    ImageView picture;
    CollapsingToolbarLayout ctl;
    Toolbar toolbar;
    TextView description;
    FloatingActionButton dinerButton;

    RestaurantDetailsActivityViewModel viewModel = new RestaurantDetailsActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        toolbar = findViewById(R.id.toolbar);
        description = findViewById(R.id.detail_description);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        dinerButton = findViewById(R.id.floating_action_button_diner);

        picture = findViewById(R.id.picture);

        RestaurantModel restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("data_restaurant");
        viewModel.definePicture(restaurantModel.getId());
        getSupportActionBar().setTitle(restaurantModel.getName());
        getSupportActionBar().setSubtitle(restaurantModel.getAddress());
        description.setText(restaurantModel.getAddress());
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(Go4LunchApplication.getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+restaurantModel.getPhotoReference()+"&key=AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE").placeholder(circularProgressDrawable).into(picture);


        dinerButton.setOnClickListener(v -> {
            DinerEntity dinerEntity = new DinerEntity();
            dinerEntity.setRestaurantId(restaurantModel.getId());
            viewModel.createDiner(dinerEntity);
        });
    }
}