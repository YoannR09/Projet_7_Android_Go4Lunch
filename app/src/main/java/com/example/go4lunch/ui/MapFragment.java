package com.example.go4lunch.ui;

import static com.example.go4lunch.error.ToastError.showError;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.R;
import com.example.go4lunch.mapper.RestaurantEntityToModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.viewModel.factory.MapFragmentViewModelFactory;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.example.go4lunch.ui.viewModel.ui.MapFragmentViewModel;
import com.example.go4lunch.usecase.RestaurantUseCase;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements LocationListener {

    private GoogleMap mMap;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    MapFragmentViewModel viewModel;
    Location location;

    public MapFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            viewModel = new ViewModelProvider(
                    this,
                    new MapFragmentViewModelFactory(
                            new ViewModelProvider(
                                    getActivity()).get(MainActivityViewModel.class)))
                    .get(MapFragmentViewModel.class);
            viewModel = new ViewModelProvider(this).get(MapFragmentViewModel.class);
            location = viewModel.getLocation();
            viewModel.getRestaurantMarkersList().observe(getActivity(), restaurants -> {
                if (mMap != null) {
                    mMap.clear();
                    for (MarkerOptions m : restaurants) {
                        mMap.addMarker(m);
                    }
                }
            });
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(callback);
            }
        }catch (Exception e) {
            showError(getString(R.string.error_main));
        }
    }

    /**
     * On start, check the current application's permission
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestLocationPermission();
    }

    /**
     * View model return markers list for each restaurants find
     * @return inflate
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    public void mooveCameraWithAutoComplete(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(
                        latLng.latitude,
                        latLng.longitude)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        viewModel.refreshList(
                latLng.latitude,
                latLng.longitude);
    }

    /**
     * On map ready, the current positon has define
     * Action listener added on icon's marker
     */
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            try {
                mMap = googleMap;
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                if (location != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                }
                mMap.setOnCameraIdleListener(
                        () -> viewModel.refreshList(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude));
                mMap.setOnMarkerClickListener(marker -> {
                    Intent intent = new Intent(getContext(), RestaurantDetailsActivity.class);
                    RestaurantUseCase useCase = new RestaurantUseCase();
                    useCase.getRestaurantUseCase().observe(
                            getActivity(),
                            rest -> {
                                intent.putExtra(
                                        "data_restaurant",
                                        new RestaurantEntityToModel().map(rest));
                                startActivityForResult(intent, 234);
                                onDestroy();
                            });
                    useCase.loadRestaurantUseCase(marker.getTitle());
                    Repositories.getRestaurantRepository().getRestaurantNotFoundOnMapById(marker.getTitle());
                    return true;
                });
            } catch (Exception e) {
                showError(getString(R.string.error_map));
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0x0123) {
            assert data != null;
            boolean result = data.getBooleanExtra("valueChanged", false);
            if(result) {
                viewModel.refreshList(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        // TODO Add while ask permission
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(getActivity(), perms)) {

        }
        else {
            EasyPermissions.requestPermissions(
                    this,
                    "Please grant the location permission",
                    REQUEST_LOCATION_PERMISSION,
                    perms);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        LatLng currentPos = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
    }
}