package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.model.WorkmateModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.ListViewFragment;
import com.example.go4lunch.ui.MapFragment;
import com.example.go4lunch.ui.RestaurantDetailsActivity;
import com.example.go4lunch.ui.SettingsActivity;
import com.example.go4lunch.ui.WorkmatesFragment;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.go4lunch.error.ToastError.showError;
import static com.example.go4lunch.ui.ToastError.errorMessage;
import static com.example.go4lunch.util.Util.checkDiner;
import static pub.devrel.easypermissions.RationaleDialogFragment.TAG;

public class MainActivity extends AppCompatActivity{

    private final static int AUTOCOMPLETE_REQUEST_CODE = 0xAf;

    DrawerLayout                drawer;
    NavigationView              navigationView;
    ImageButton                 searchButton;
    Toolbar                     topBar;
    Button                      logout;
    Button                      settings;
    Button                      lunchInfo;
    MapFragment                 mapFragment;
    ListViewFragment            listFragment;
    WorkmatesFragment            workmatesFragment;
    Fragment                    active;

    final       FragmentManager         fm          = getSupportFragmentManager();
    private     MainActivityViewModel   viewModel;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    /**
     * This method has called after signed result from firebase auth
     * @param result: FirebaseAuthUIAuthenticationResult
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK) {
            initSignInInfo();
        } else {
            errorMessage(getString(R.string.error_login));
            logoutToRefreshMainActivity();
        }
    }

    private void initSignInInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ImageView drawerPicture = findViewById(R.id.drawer_picture);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        assert user != null;
        String url = user.getPhotoUrl() == null
                ? "https://eu.ui-avatars.com/api/?name="+ user.getDisplayName() :
                String.valueOf(user.getPhotoUrl());
        Glide.with(Go4LunchApplication.getContext())
                .load(url)
                .placeholder(circularProgressDrawable).into(drawerPicture);
        TextView username = findViewById(R.id.drawer_username);
        username.setText(user.getDisplayName());
        Repositories.getWorkmateRepository().getWorkmatesList(data -> {
            boolean userCanBeSaved = true;
            for( WorkmateModel u : data) {
                if(u.getId().equals(user.getUid())) {
                    userCanBeSaved = false;
                }
            }
            if(userCanBeSaved) {
                viewModel.createUser();
            }
        });
    }

    public void defineTabByIndex(int index) {
        switch (index) {
            case 1:
                if(searchButton!= null)  {searchButton.setVisibility(View.VISIBLE);}
                fm.beginTransaction().hide(active).show(listFragment).commit();
                active = listFragment;
                break;
            case 2:
                fm.beginTransaction().hide(active).show(workmatesFragment).commit();
                active = workmatesFragment;
                if(searchButton!= null)  {searchButton.setVisibility(View.GONE);}
                break;
            default:
                if(searchButton!= null)  {searchButton.setVisibility(View.VISIBLE);}
                fm.beginTransaction().hide(active).show(mapFragment).commit();
                active = mapFragment;
                break;
        }
    }

    /**
     * Init the fragment list with new instances
     * Get current location
     */
    private void initFragment() {
        if(viewModel.getCurrentPosition() == null) {
            viewModel.setLocation(getLocation());
        }
        mapFragment = new MapFragment();
        listFragment = new ListViewFragment();
        workmatesFragment = new WorkmatesFragment();
        for (Fragment fragment : fm.getFragments()) {
            fm.beginTransaction().remove(fragment).commit();
        }
        fm.beginTransaction().add(R.id.main_container, workmatesFragment, "WORKMATE").hide(workmatesFragment).commit();
        fm.beginTransaction().add(R.id.main_container, listFragment, "MAP").hide(listFragment).commit();
        fm.beginTransaction().add(R.id.main_container,mapFragment, "LIST").hide(mapFragment).commit();
        int index = 99;
        if(viewModel.getCurrentTab().getValue() != null) {
            index = viewModel.getCurrentTab().getValue();
        }
        if(active == null) {
            active = mapFragment;
        }
        defineTabByIndex(index);
        viewModel.getCurrentTab().observe(this, this::defineTabByIndex);
    }

    /**
     * Here we are instanciate the each component on view
     * Start sign in activity from firebase auth
     * @param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
            viewModel.setLocation(getLocation());
            initFragment();
            setContentView(R.layout.activity_main);
            setSupportActionBar(findViewById(R.id.topAppBar));
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.FacebookBuilder().build(),
                        new AuthUI.IdpConfig.TwitterBuilder().build());
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build();
                signInLauncher.launch(signInIntent);
            } else {
                initSignInInfo();
            }

            drawer = findViewById(R.id.drawer);
            navigationView = findViewById(R.id.navigation_view);
            searchButton = findViewById(R.id.search_button);
            topBar = findViewById(R.id.topAppBar);
            settings = findViewById(R.id.drawer_settings);
            lunchInfo = findViewById(R.id.drawer_lunch_info);
            logout = findViewById(R.id.drawer_logout);

            settings.setOnClickListener(v -> {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            });

            lunchInfo.setOnClickListener(v -> {
                Intent intent = new Intent(this, RestaurantDetailsActivity.class);
                viewModel.getCurrentDinerSnapshot(data -> {
                    if (data != null) {
                        if (checkDiner(data)) {
                            Repositories.getRestaurantRepository()
                                    .getRestaurantNotFoundOnMapById(data.getRestaurantId(),
                                            r -> {
                                                intent.putExtra(
                                                        "data_restaurant",
                                                        r);
                                                startActivityForResult(intent, 234);
                                            });
                        } else {
                            showToastNoDiner();
                        }
                    } else {
                        showToastNoDiner();
                    }
                });
            });

            logout.setOnClickListener(v -> {
                FirebaseAuth.getInstance().signOut();
                logoutToRefreshMainActivity();
            });

            searchButton.setOnClickListener(v -> startAutoComplete());

            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, 0, R.string.com_facebook_loginview_cancel_action);
            drawer.addDrawerListener(drawerToggle);
            drawerToggle.syncState();

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }catch (Exception e) {
            showError(getString(R.string.error_main));
            e.printStackTrace();
        }
    }

    /**
     * This activuty result has used to catch microphone input search
     * @param requestCode: int
     * @param resultCode: int
     * @param data: Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    if(viewModel.getCurrentTab().getValue() == null) {
                        mapFragment.mooveCameraWithAutoComplete(Objects.requireNonNull(place.getLatLng()));
                        return;
                    }
                    switch (viewModel.getCurrentTab().getValue()) {
                        case 0:
                            mapFragment.mooveCameraWithAutoComplete(Objects.requireNonNull(place.getLatLng()));
                            break;
                        case 1:
                            viewModel.refreshList(
                                    Objects.requireNonNull(place.getLatLng()).latitude,
                                    Objects.requireNonNull(place.getLatLng()).longitude);
                            break;
                    }

                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    assert status.getStatusMessage() != null;
                    Log.i(TAG, status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    Log.i(TAG, "User canceled");
                    // The user canceled the operation.
                } else {
                    viewModel.refreshList(
                            getLocation().getLatitude(),
                            getLocation().getLongitude());

                    if(workmatesFragment != null) {
                        workmatesFragment.refreshList();
                    }
                }
                return;
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToastNoDiner() {
        Toast noDiner = new Toast(this);
        noDiner.setText(getString(R.string.no_diner));
        noDiner.setDuration(Toast.LENGTH_SHORT);
        noDiner.show();
    }

    /**
     * Start autoComplete Activity
     * Result has catches on OnResult method
     */
    public void startAutoComplete() {
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    /**
     * This var was used to define fragment navigation on tab
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.map_list:
                viewModel.setCurrentTab(0);
                return true;

            case R.id.view_list:
                viewModel.setCurrentTab(1);
                return true;

            case R.id.workmates:
                viewModel.setCurrentTab(2);
                return true;
        }
        return false;
    };

    /**
     * This method was user to display drawer menu
     * @param item: MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == 16908332) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    /**
     * Return the current isntance of viewModel
     * @return MainActivityViewModel
     */
    public MainActivityViewModel getViewModel() {
        return viewModel;
    }

    /**
     * This method return to Freibase auth activity
     * And lohout if current firebase session has active
     */
    public void logoutToRefreshMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Return the current location from device
     * @return Location
     */
    public Location getLocation() {
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission") Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}