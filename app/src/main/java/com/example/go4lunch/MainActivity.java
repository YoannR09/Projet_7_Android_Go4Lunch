package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.go4lunch.ui.ListViewFragment;
import com.example.go4lunch.ui.MapFragment;
import com.example.go4lunch.ui.WorkmatesFragment;
import com.example.go4lunch.ui.viewModel.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    DrawerLayout drawer;
    NavigationView navigationView;

    final Fragment fragment1 = new MapFragment();
    final Fragment fragment2 = new ListViewFragment();
    final Fragment fragment3 = new WorkmatesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    private static final int RC_SIGN_IN = 123;

    private MainActivityViewModel viewModel = new MainActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        //.setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(), //EMAIL
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(), //GOOGLE
                                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())) // FACEBOOK
                        .setIsSmartLockEnabled(false, true)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .setLogo(R.drawable.ic_baseline_local_pizza_24)
                        .build(),
                RC_SIGN_IN);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.topAppBar));

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);


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
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.createUser();
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