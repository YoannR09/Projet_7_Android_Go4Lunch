package com.example.go4lunch.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.ui.list.WorkMatesDetailAdapter;
import com.example.go4lunch.ui.viewModel.DinerViewModel;
import com.example.go4lunch.ui.viewModel.ui.RestaurantDetailsActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.go4lunch.Go4LunchApplication.getContext;

public class RestaurantDetailsActivity extends AppCompatActivity {

    ImageView picture;
    WorkMatesDetailAdapter adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView description;
    FloatingActionButton dinerButton;
    List<DinerViewModel> dinerViewModelList = new ArrayList<>();
    boolean status;

    RestaurantDetailsActivityViewModel viewModel = new RestaurantDetailsActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        toolbar = findViewById(R.id.toolbar);
        description = findViewById(R.id.detail_description);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        dinerButton = findViewById(R.id.floating_action_button_diner);

        picture = findViewById(R.id.picture);

        RestaurantModel restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("data_restaurant");
        assert restaurantModel != null;
        getSupportActionBar().setTitle(restaurantModel.getName());
        getSupportActionBar().setSubtitle(restaurantModel.getAddress());
        description.setText(restaurantModel.getAddress());

        viewModel.loadDinerFromWorkmate();
        viewModel.getCurrentDiner().observe(this, diner -> {
            if(diner.isStatus() && diner.getRestaurantId().equals(restaurantModel.getId())) {
                status = true;
                dinerButton.setImageResource(R.drawable.ic_baseline_check_circle_24_green);
            } else {
                status = false;
                dinerButton.setImageResource(R.drawable.ic_baseline_check_circle_24);
            }
        });
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+restaurantModel.getPhotoReference()+"&key=AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE").placeholder(circularProgressDrawable).into(picture);


        dinerButton.setOnClickListener(v -> {
            DinerEntity dinerEntity = new DinerEntity();
            dinerEntity.setRestaurantId(restaurantModel.getId());
            status = !status;
            if (status) {
                dinerButton.setImageResource(R.drawable.ic_baseline_check_circle_24_green);
            } else {
                dinerButton.setImageResource(R.drawable.ic_baseline_check_circle_24);
            }
            dinerEntity.setStatus(status);
            viewModel.createDiner(dinerEntity);
        });

        recyclerView = findViewById(R.id.rvWorkmatesDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkMatesDetailAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        viewModel.loadDinersFromRestaurant(restaurantModel.getId());
        viewModel.getCurrentDiners().observe(this, data
                -> adapter.updateList(data));
    }
}