package com.example.go4lunch.ui.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.RestaurantDetailsActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.go4lunch.util.Util.checkDiner;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {

    private List<WorkmateModel> mData;

    public WorkmatesAdapter(List<WorkmateModel> data) {
        this.mData = data;
    }

    public void updateList(List<WorkmateModel> viewModelList) {
        mData.clear();
        mData.addAll(viewModelList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.workmates_detail_item_view;
    }

    @NonNull
    @Override
    public WorkmatesAdapter.WorkmatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new WorkmatesAdapter.WorkmatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkmatesAdapter.WorkmatesViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder  {

        final TextView title;
        final ImageView imageView;
        final LinearLayout card;
        final View view;

        WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.title = itemView.findViewById(R.id.workmates_detail_title);
            this.imageView = itemView.findViewById(R.id.workmates_detail_picture);
            this.card = itemView.findViewById(R.id.card_workmate);
        }


        void bind(WorkmateModel workmateModel) {
            this.title.setTextColor(Color.LTGRAY);
            this.title.setText(title.getContext().getString(
                    R.string.workmate_no_diner, workmateModel.getUsername()));
            Repositories.getDinerRepository().getDinerFromWorkmateId(
                    workmateModel.getId(),
                    data -> {
                        if (data == null) {
                            this.title.setText(title.getContext().getString(
                                    R.string.workmate_no_diner, workmateModel.getUsername()));
                        } else {
                            if (checkDiner(data)) {
                                this.card.setOnClickListener(v -> {
                                    Intent intent = new Intent(this.view.getContext(), RestaurantDetailsActivity.class);
                                    Repositories.getRestaurantRepository()
                                            .getRestaurantNotFoundOnMapById(data.getRestaurantId(),
                                                    r -> {
                                                        intent.putExtra(
                                                                "data_restaurant",
                                                                r);
                                                        ((Activity) this.view.getContext()).startActivityForResult(intent, 234);
                                                    });
                                });
                                this.title.setText(
                                        workmateModel.getUsername() +
                                                title.getContext().getString(R.string.is_eating) + " ( "
                                                + data.getInfo()
                                                + " )");
                                this.title.setTextColor(Color.BLACK);
                            } else {
                                this.title.setTextColor(Color.LTGRAY);
                                this.title.setText(title.getContext().getString(
                                        R.string.workmate_no_diner, workmateModel.getUsername()));
                            }
                        }
                    });
            CircularProgressDrawable circularProgressDrawable
                    = new CircularProgressDrawable(this.view.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            String url = workmateModel.getPictureUrl() == null
                    ? "https://eu.ui-avatars.com/api/?name=" + workmateModel.getUsername() :
                    workmateModel.getPictureUrl();
            try {
                Glide.with(Go4LunchApplication.getContext())
                        .load(url)
                        .placeholder(circularProgressDrawable).into(this.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
