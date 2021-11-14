package com.example.go4lunch.ui.list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Go4LunchApplication;
import com.example.go4lunch.R;
import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.repo.Repositories;

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
        // TODO Remove to DAO !

        mData.addAll(viewModelList);
        Collections.sort(mData,
                (s1, s2) ->
                        (s1.isHasDiner() ? 1 : 0) - (s2.isHasDiner() ? 1 : 0));
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

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder  {

        TextView title;
        ImageView imageView;

        WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.workmates_detail_title);
            imageView = itemView.findViewById(R.id.workmates_detail_picture);
        }


        void bind(WorkmateModel workmateModel) {
            Repositories.getDinerRepository().getDinerFromWorkmateId(
                    workmateModel.getId(),
                    data -> {
                        if(checkDiner(data)) {
                            title.setText(
                                    workmateModel.getUsername() +
                                            " is eating ( "
                                            + data.getInfo()
                                            + " )");
                        } else {
                            title.setTextColor(Color.LTGRAY);
                            title.setText(title.getContext().getString(
                                    R.string.workmate_no_diner, workmateModel.getUsername()));
                        }
                    });
            CircularProgressDrawable circularProgressDrawable
                    = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            String url = workmateModel.getPictureUrl() == null
                    ? "https://eu.ui-avatars.com/api/?name="+workmateModel.getUsername() :
                    workmateModel.getPictureUrl();
            Glide.with(Go4LunchApplication.getContext())
                    .load(url)
                    .placeholder(circularProgressDrawable).into(imageView);
        }
    }
}
