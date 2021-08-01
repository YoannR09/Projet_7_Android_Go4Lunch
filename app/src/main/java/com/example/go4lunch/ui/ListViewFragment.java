package com.example.go4lunch.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.MainActivity;
import com.example.go4lunch.R;
import com.example.go4lunch.ui.list.RestaurantsAdapter;
import com.example.go4lunch.ui.viewModel.ListFragmentViewModel;
import com.example.go4lunch.ui.viewModel.MapFragmentViewModel;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        // TODO
        /*
        val spannable = SpannableString(“Text styling”)
spannable.setSpan(
     ForegroundColorSpan(Color.PINK),
     0, 4,
     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
myTextView.text = spannable
         */
        viewModel = new ListFragmentViewModel(((MainActivity) getActivity()).getViewModel());
        recyclerView = view.findViewById(R.id.rvRestaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RestaurantsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        viewModel.getCurrentRestaurants().observe(getActivity(), restaurants
                -> adapter.updateList(restaurants));
        return view;
    }
}