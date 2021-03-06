package com.example.go4lunch.ui;

import static com.example.go4lunch.ui.ToastError.showError;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.go4lunch.MainActivity;
import com.example.go4lunch.R;
import com.example.go4lunch.ui.list.WorkmatesAdapter;
import com.example.go4lunch.ui.viewModel.ui.WorkmatesFragmentViewModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class WorkmatesFragment extends Fragment {


    WorkmatesAdapter adapter;
    RecyclerView recyclerView;
    WorkmatesFragmentViewModel viewModel;


    public WorkmatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_workmates, container, false);
            final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
            pullToRefresh.setOnRefreshListener(() -> {
                viewModel.loadWorkmatesList();
                pullToRefresh.setRefreshing(false);
            });
            viewModel = new ViewModelProvider(this).get(WorkmatesFragmentViewModel.class);
            recyclerView = view.findViewById(R.id.rvWorkmates);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new WorkmatesAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);
            viewModel.getWorkmatesList().observe(getViewLifecycleOwner(), workmates
                    -> adapter.updateList(workmates));
            viewModel.loadWorkmatesList();
            return view;
        } catch (Exception e) {
            showError(getString(R.string.error_main));
            return inflater.inflate(R.layout.fragment_workmates, container, false);
        }
    }

    public void refreshList() {
        viewModel.loadWorkmatesList();
    }
}