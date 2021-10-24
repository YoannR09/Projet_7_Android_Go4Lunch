package com.example.go4lunch;

import static com.example.go4lunch.error.ToastError.showError;
import static com.example.go4lunch.ui.ToastError.errorMessage;
import static pub.devrel.easypermissions.RationaleDialogFragment.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.go4lunch.mapper.RestaurantEntityToModel;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
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

public class MainActivity extends AppCompatActivity{

    private final int SPEECH_REQUEST_CODE = 0x1a;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 0xAf;

    DrawerLayout                drawer;
    NavigationView              navigationView;
    ImageButton                 searchButton;
    Toolbar                     inputSearch;
    Toolbar                     topBar;
    ImageButton                 voiceButton;
    ImageButton                 searchDoneButton;
    EditText                    editTextSearch;
    Button                      logout;
    Button                      settings;
    Button                      lunchInfo;
    MapFragment                 mapFragment;
    ListViewFragment            listFragment;
    WorkmatesFragment            workmatesFragment;
    Fragment                    active;
    boolean                     dinerDetailShowed = false;
    boolean                     hasLoggin = false;

    final       FragmentManager         fm          = getSupportFragmentManager();
    private     MainActivityViewModel   viewModel;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    /**
     * This method has called after signed result from firebase auth
     * @param result
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
        active = mapFragment;
        fm.beginTransaction().add(R.id.main_container, workmatesFragment, "WORKMATE").hide(workmatesFragment).commit();
        fm.beginTransaction().add(R.id.main_container, listFragment, "LIST").hide(listFragment).commit();
        fm.beginTransaction().add(R.id.main_container,mapFragment, "MAP").commit();
    }

    /**
     * Here we are instanciate the each component on view
     * Start sign in activity from firebase auth
     * @param savedInstanceState
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
                        new AuthUI.IdpConfig.FacebookBuilder().build());
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
            inputSearch = findViewById(R.id.search_bar);
            voiceButton = findViewById(R.id.voice_button);
            searchDoneButton = findViewById(R.id.search_button_done);
            topBar = findViewById(R.id.topAppBar);
            editTextSearch = findViewById(R.id.input_search);
            settings = findViewById(R.id.drawer_settings);
            lunchInfo = findViewById(R.id.drawer_lunch_info);
            logout = findViewById(R.id.drawer_logout);

            editTextSearch.setOnClickListener(v -> startAutoComplete());

            settings.setOnClickListener(v -> {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            });

            lunchInfo.setOnClickListener(v -> {
                dinerDetailShowed = false;
                Intent intent = new Intent(this, RestaurantDetailsActivity.class);
                viewModel.getCurrentDiner().observe(this, diner -> {
                    if (!dinerDetailShowed) {
                        dinerDetailShowed = true;
                        if (diner != null) {
                            if (diner.isStatus()) {
                                Repositories
                                        .getRestaurantRepository()
                                        .getCurrentRestaurant().observe(this, obs -> {
                                    intent.putExtra(
                                            "data_restaurant",
                                            new RestaurantEntityToModel().map(obs));
                                    startActivityForResult(intent, 234);
                                    onDestroy();
                                });
                                Repositories.getRestaurantRepository()
                                        .getRestaurantNotFoundOnMapById(
                                                diner.getRestaurantId());
                            } else {
                                showToastNoDiner();
                            }
                        } else {
                            showToastNoDiner();
                        }
                    }
                });
                viewModel.loadCurrentDiner();
            });

            logout.setOnClickListener(v -> {
                FirebaseAuth.getInstance().signOut();
                logoutToRefreshMainActivity();
            });

            searchButton.setOnClickListener(v -> {
                inputSearch.setVisibility(View.VISIBLE);
                topBar.setVisibility(View.GONE);
            });

            searchDoneButton.setOnClickListener(v -> {
                PlacesClient placesClient = Places.createClient(this);
                inputSearch.setVisibility(View.GONE);
                AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

                FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                        .setSessionToken(token)
                        .setQuery(editTextSearch.getText().toString())
                        .build();

                placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                    for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                        System.out.println("Here" + prediction.getPlaceId());
                    }
                }).addOnFailureListener((exception) -> {
                    // TODO THROW EXCEPTION
                });
                topBar.setVisibility(View.VISIBLE);
            });

            voiceButton.setOnClickListener(v -> {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            });

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
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.refreshList(
                getLocation().getLatitude(),
                getLocation().getLongitude());
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            assert results != null;
            String spokenText = results.get(0);
            editTextSearch.setText(spokenText);
            // TODO Check if this one was usefull autoCompleteSearch();
        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                mapFragment.mooveCameraWithAutoComplete(Objects.requireNonNull(place.getLatLng()));
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                assert status.getStatusMessage() != null;
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showToastNoDiner() {
        Toast noDiner = new Toast(this);
        noDiner.setText("You have actually not selected resturant");
        noDiner.setDuration(Toast.LENGTH_SHORT);
        noDiner.show();
    }

    /**
     * Start autoComplete Activity
     * Result has catched on OnResult method
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
                fm.beginTransaction().hide(active).show(mapFragment).commit();
                active = mapFragment;

                return true;

            case R.id.view_list:
                fm.beginTransaction().hide(active).show(listFragment).commit();
                active = listFragment;
                return true;

            case R.id.workmates:
                fm.beginTransaction().hide(active).show(workmatesFragment).commit();
                active = workmatesFragment;
                return true;
        }
        return false;
    };

    /**
     * This method was user to display drawer menu
     * @param item
     * @return
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
     * @return
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
     * @return
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