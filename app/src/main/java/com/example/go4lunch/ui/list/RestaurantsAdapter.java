package com.example.go4lunch.ui.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.RestaurantDetailsActivity;
import com.example.go4lunch.ui.viewModel.ui.ListFragmentViewModel;
import com.example.go4lunch.ui.viewModel.RestaurantViewModel;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {

    private List<RestaurantViewModel> mData;
    private LifecycleOwner lifecycleRegistry;
    private Context context;

    public RestaurantsAdapter(List<RestaurantViewModel> data, LifecycleOwner lifecycleOwner) {
        this.mData = data;
        lifecycleRegistry = lifecycleOwner;
    }

    public void updateList(List<RestaurantViewModel> viewModelList) {
        mData.clear();
        mData.addAll(viewModelList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.restaurant_item_view;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        context = parent.getContext();
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
    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, LifecycleOwner {

        ListFragmentViewModel viewModel = new ListFragmentViewModel();
        TextView title;
        TextView openStatus;
        TextView description;
        TextView workmateDiner;
        TextView opinion;
        ImageView imageView;
        TextView range;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            openStatus = itemView.findViewById(R.id.restaurant_opening);
            opinion = itemView.findViewById(R.id.restaurant_opinion);
            range = itemView.findViewById(R.id.restaurant_range);
            title = itemView.findViewById(R.id.restaurant_title);
            description = itemView.findViewById(R.id.restaurant_description);
            imageView = itemView.findViewById(R.id.restaurant_picture);
            workmateDiner = itemView.findViewById(R.id.restaurant_workmates_diner);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, RestaurantDetailsActivity.class);
            intent.putExtra("data_restaurant",
                    Repositories.getRestaurantRepository().getRestaurantById(mData.get(this.getPosition()).getId()));
            context.startActivity(intent);
        }

        void bind(RestaurantViewModel restaurantViewModel) {
            title.setText(restaurantViewModel.getName());
            description.setText(restaurantViewModel.getDecription());
            range.setText(restaurantViewModel.getRange());
            viewModel.loadDinersFromRestaurant(restaurantViewModel.getId());
            viewModel.getDinersFromRestaurant().observe(lifecycleRegistry, diners -> {
                if (diners.size() > 0) {
                    workmateDiner.setVisibility(View.VISIBLE);
                    workmateDiner.setText(" (" + diners.size() + ")");
                    workmateDiner.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_emoji_people_24, 0, 0, 0);
                } else {
                    workmateDiner.setVisibility(View.GONE);
                }
            });
            opinion.setText(restaurantViewModel.getOpinion());
            opinion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_star_12, 0, 0, 0);
            openStatus.setText(restaurantViewModel.getOpening());
            if(restaurantViewModel.getOpening().equals("Close")) openStatus.setTextColor(Color.RED);
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(Go4LunchApplication.getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+restaurantViewModel.getPhotoReference()+"&key=AIzaSyD-NY3k75I5IbFh13vcv-kJ3YORhDNETSE").placeholder(circularProgressDrawable).into(imageView);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return null;
        }
    }
}
