package com.example.go4lunch.ui;

import static com.example.go4lunch.error.ToastError.showError;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.MainActivity;
import com.example.go4lunch.R;
import com.example.go4lunch.ui.list.RestaurantsAdapter;
import com.example.go4lunch.ui.viewModel.ui.ListFragmentViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListViewFragment extends Fragment {

    RestaurantsAdapter adapter;
    RecyclerView recyclerView;
    ListFragmentViewModel viewModel;

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_list_view, container, false);
            viewModel = new ListFragmentViewModel(((MainActivity) getActivity()).getViewModel());
            recyclerView = view.findViewById(R.id.rvRestaurants);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new RestaurantsAdapter(new ArrayList<>(), this, this);
            recyclerView.setAdapter(adapter);

            viewModel.getCurrentRestaurants().observe(getActivity(), restaurants
                    -> adapter.updateList(restaurants));
            return view;
        } catch (Exception e) {
            showError(getString(R.string.error_main));
            return inflater.inflate(R.layout.fragment_list_view, container, false);
        }
    }
}