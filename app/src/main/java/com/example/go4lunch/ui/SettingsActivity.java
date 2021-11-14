package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.go4lunch.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

import static android.provider.Settings.System.getString;
import static com.example.go4lunch.error.ToastError.showError;
import static java.lang.Boolean.getBoolean;

public class SettingsActivity extends AppCompatActivity {

    SwitchMaterial switchNotif;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = findViewById(R.id.toolbar_settings);
        switchNotif = findViewById(R.id.switch_notif);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.settings);
        SharedPreferences sharedPref
                = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notif = sharedPref.getBoolean("saved_notification", true);
        switchNotif.setChecked(notif);
        switchNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeNotificationStatus(isChecked);
        });
    }

    public void changeNotificationStatus(boolean status) {
        try {
            SharedPreferences sharedPref
                    = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("saved_notification", status);
            editor.apply();
        } catch (Exception e) {
          showError(getString(R.string.error_config));  
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}