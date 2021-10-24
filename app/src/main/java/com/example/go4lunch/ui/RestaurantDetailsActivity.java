package com.example.go4lunch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.entity.DinerEntity;
import com.example.go4lunch.entity.LikeEntity;
import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.ui.list.WorkMatesDetailAdapter;
import com.example.go4lunch.ui.viewModel.ui.RestaurantDetailsActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.go4lunch.Go4LunchApplication.getContext;
import static com.example.go4lunch.error.ToastError.showError;
import static com.example.go4lunch.util.Util.checkDiner;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private final static int RESULT_CODE = 0x0123;

    ImageView picture;
    WorkMatesDetailAdapter adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView description;
    TextView ratioDetail;
    FloatingActionButton dinerButton;
    boolean status;
    boolean valueChanged = false;
    boolean isLiked = false;
    Button phoneButton;
    Button webSiteButton;
    Button likeButton;
    RestaurantDetailsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(this).get(RestaurantDetailsActivityViewModel.class);
            setContentView(R.layout.activity_restaurant_details);

            toolbar = findViewById(R.id.toolbar);
            description = findViewById(R.id.detail_description);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            toolbar.setTitleTextColor(0xFFFFFFFF);
            dinerButton = findViewById(R.id.floating_action_button_diner);
            webSiteButton = findViewById(R.id.detail_website);
            phoneButton = findViewById(R.id.detail_phone);
            picture = findViewById(R.id.picture);
            ratioDetail = findViewById(R.id.detail_ratio);
            likeButton = findViewById(R.id.detail_like);
            RestaurantModel restaurantModel = (RestaurantModel) getIntent()
                    .getSerializableExtra("data_restaurant");
            assert restaurantModel != null;
            getSupportActionBar().setTitle(restaurantModel.getName());
            getSupportActionBar().setSubtitle(restaurantModel.getAddress());
            description.setText(restaurantModel.getAddress());
            ratioDetail.setText(restaurantModel.getOpinion() + "/5");
            viewModel.loadDinerFromWorkmate();
            viewModel.getCurrentDiner().observe(this, diner -> {
                if (checkDiner(diner) && diner.getRestaurantId().equals(restaurantModel.getId())) {
                    status = true;
                    dinerButton.setImageResource(R.drawable.ic_baseline_clear_24);
                } else {
                    status = false;
                    dinerButton.setImageResource(R.drawable.ic_baseline_local_dining_24);
                }
            });
            viewModel.getCurrentLike().observe(this, like -> {
                if(like != null) {
                    isLiked = like.isStatus();
                    String likeText = like.isStatus()
                            ? getString(R.string.dislike_button)
                            : getString(R.string.like_button);
                    likeButton.setText(likeText);
                } else {
                    isLiked = false;
                    likeButton.setText(getString(R.string.like_button));
                }
            });
            viewModel.loadLikeFromRestaurant(restaurantModel.getId());
            likeButton.setOnClickListener(v-> {
                isLiked = !isLiked;
                String likeText = isLiked
                        ? getString(R.string.dislike_button)
                        : getString(R.string.like_button);
                likeButton.setText(likeText);
                LikeEntity newLike = new LikeEntity(
                        restaurantModel.getId(),
                        isLiked
                );
                viewModel.createLike(newLike);
            });
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + restaurantModel.getPhotoReference() + "&key=AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE").placeholder(circularProgressDrawable).into(picture);

            dinerButton.setOnClickListener(v -> {
                DinerEntity dinerEntity = new DinerEntity();
                dinerEntity.setRestaurantId(restaurantModel.getId());
                status = !status;
                if (status) {
                    dinerButton.setImageResource(R.drawable.ic_baseline_clear_24);
                } else {
                    dinerButton.setImageResource(R.drawable.ic_baseline_local_dining_24);
                }
                dinerEntity.setInfo(restaurantModel.getName());
                dinerEntity.setStatus(status);
                viewModel.createDiner(dinerEntity);
                valueChanged = true;
            });

            webSiteButton.setOnClickListener(v -> {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurantModel.getWebSite()));
                startActivity(browse);
            });

            phoneButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts(
                                "tel",
                                restaurantModel.getPhoneNumber(),
                                null));
                startActivity(intent);
            });

            recyclerView = findViewById(R.id.rvWorkmatesDetail);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new WorkMatesDetailAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);
            viewModel.loadDinersFromRestaurant(restaurantModel.getId());
            viewModel.getCurrentDiners().observe(this, data
                    -> adapter.updateList(data));
        } catch (Exception e) {
            showError(getString(R.string.error_main));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}