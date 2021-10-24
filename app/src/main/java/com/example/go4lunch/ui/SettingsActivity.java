package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.example.go4lunch.R;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import static android.provider.Settings.System.getString;
import static com.example.go4lunch.error.ToastError.showError;
import static java.lang.Boolean.getBoolean;

public class SettingsActivity extends AppCompatActivity {

    SwitchMaterial switchNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchNotif = findViewById(R.id.switch_notif);

        switchNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeNotificationStatus(isChecked);
        });
    }

    public void changeNotificationStatus(boolean status) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("saved_notification", status);
            editor.apply();
        } catch (Exception e) {
          showError(getString(R.string.error_config));  
        }
    }
}