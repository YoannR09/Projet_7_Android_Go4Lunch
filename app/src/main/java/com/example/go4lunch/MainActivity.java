package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.ListViewFragment;
import com.example.go4lunch.ui.MapFragment;
import com.example.go4lunch.ui.WorkmatesFragment;
import com.example.go4lunch.ui.viewModel.ui.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static com.example.go4lunch.Go4LunchApplication.getContext;

public class MainActivity extends AppCompatActivity{

    private final int SPEECH_REQUEST_CODE = 0x1a;

    DrawerLayout drawer;
    NavigationView navigationView;
    ImageButton searchButton;
    Toolbar inputSearch;
    Toolbar topBar;
    ImageButton voiceButton;
    ImageButton searchDoneButton;

    final Fragment fragment1 = new MapFragment();
    final Fragment fragment2 = new ListViewFragment();
    final Fragment fragment3 = new WorkmatesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    private static final int RC_SIGN_IN = 123;

    private MainActivityViewModel viewModel = new MainActivityViewModel();

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> onSignInResult(result)
    );


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            viewModel.createUser();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            ImageView drawerPicture = findViewById(R.id.drawer_picture);
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(getContext()).load(user.getPhotoUrl()).placeholder(circularProgressDrawable).into(drawerPicture);
            TextView username = findViewById(R.id.drawer_username);
            username.setText(user.getDisplayName());
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.topAppBar));

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        searchButton = findViewById(R.id.search_button);
        inputSearch = findViewById(R.id.search_bar);
        voiceButton = findViewById(R.id.voice_button);
        searchDoneButton = findViewById(R.id.search_button_done);
        topBar = findViewById(R.id.topAppBar);

        searchButton.setOnClickListener(v -> {
            inputSearch.setVisibility(View.VISIBLE);
            topBar.setVisibility(View.GONE);
        });

        searchDoneButton.setOnClickListener(v -> {
            inputSearch.setVisibility(View.GONE);
            topBar.setVisibility(View.VISIBLE);
        });

        voiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, 0,R.string.com_facebook_loginview_cancel_action);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            System.out.println("Here text : " + spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.map_list:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;

            case R.id.view_list:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;

            case R.id.workmates:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
        }
        return false;
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == 16908332) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    public MainActivityViewModel getViewModel() {
        return viewModel;
    }
}