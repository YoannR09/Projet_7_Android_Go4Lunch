package com.example.go4lunch.ui.list;

import android.content.Context;
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
import com.example.go4lunch.ui.viewModel.DinerViewModel;

import java.util.List;

public class WorkMatesDetailAdapter extends RecyclerView.Adapter<WorkMatesDetailAdapter.WorkmatesDetailViewHolder> {

    private List<DinerViewModel> mData;

    private Context context;

    public WorkMatesDetailAdapter(List<DinerViewModel> data) {
        this.mData = data;
    }

    public void updateList(List<DinerViewModel> viewModelList) {
        mData.clear();
        mData.addAll(viewModelList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.workmates_detail_item_view;
    }

    @Override
    public WorkMatesDetailAdapter.WorkmatesDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        context = parent.getContext();
        return new WorkmatesDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkMatesDetailAdapter.WorkmatesDetailViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class WorkmatesDetailViewHolder extends RecyclerView.ViewHolder  {

        TextView title;
        ImageView imageView;

        WorkmatesDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.workmates_detail_title);
            imageView = itemView.findViewById(R.id.workmates_detail_picture);
        }


        void bind(DinerViewModel dinerViewModel) {
            title.setText(dinerViewModel.getWorkmate());
            CircularProgressDrawable circularProgressDrawable
                    = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            String url = dinerViewModel.getWorkmatePictureUrl() == null
                    ? "https://eu.ui-avatars.com/api/?name="+dinerViewModel.getWorkmate() :
                    dinerViewModel.getWorkmatePictureUrl();
            Glide.with(Go4LunchApplication.getContext())
                    .load(url)
                    .placeholder(circularProgressDrawable).into(imageView);
        }
    }
}
