package com.example.go4lunch.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.ui.list.WorkmatesAdapter;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.example.go4lunch.ui.viewModel.ui.WorkmatesFragmentViewModel;

import java.util.ArrayList;

public class WorkmatesFragment extends Fragment {


    WorkmatesAdapter adapter;
    RecyclerView recyclerView;
    WorkmatesFragmentViewModel viewModel;


    public WorkmatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workmates, container, false);

        viewModel = new ViewModelProvider(this).get(WorkmatesFragmentViewModel.class);
        recyclerView = view.findViewById(R.id.rvWorkmates);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new WorkmatesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        viewModel.loadWormatesList();
        viewModel.getWorkmatesList().observe(getActivity(), workmates
                -> adapter.updateList(workmates));
        return view;
    }
}