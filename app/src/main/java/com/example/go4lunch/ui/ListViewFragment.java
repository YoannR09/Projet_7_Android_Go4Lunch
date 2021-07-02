package com.example.go4lunch.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.ui.list.RestaurantsAdapter;
import com.example.go4lunch.viewModel.MainActivityViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListViewFragment extends Fragment {

    RestaurantsAdapter adapter;
    RecyclerView recyclerView;
    MainActivityViewModel viewModel = new MainActivityViewModel();

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = view.findViewById(R.id.rvRestaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel.getCurrentRestaurants().observe(getActivity(), restaurants -> {
            adapter = new RestaurantsAdapter(restaurants);
            recyclerView.setAdapter(adapter);
        } );
        return view;
    }
}