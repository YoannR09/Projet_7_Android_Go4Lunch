package com.example.go4lunch;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.preference.PreferenceManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.go4lunch.ui.SettingsActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ActivitySettingsTest {

    public ActivitySettingsTest() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("yocorps17@gmail.com", "azerty");
    }
    SettingsActivity activity;

    @Rule
    public ActivityTestRule<SettingsActivity> rule
            = new ActivityTestRule<>(SettingsActivity.class,
            true, false);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        Intent i = new Intent();
        rule.launchActivity(i);
    }

    @Test
    public void initMainActivity() {
        activity = rule.getActivity();
        assertNotNull(activity);
    }

    @Test
    public void checkChangeNotif() {
        SharedPreferences beforesharedPref
                = PreferenceManager.getDefaultSharedPreferences(rule.getActivity());
        boolean before = beforesharedPref.getBoolean("saved_notification", true);
        SwitchMaterial switchElement = rule.getActivity().findViewById(R.id.switch_notif);
        assertEquals(before, switchElement.isChecked());
        onView(withId(R.id.switch_notif)).perform(click());
        assertNotEquals(before, switchElement.isChecked());
        SharedPreferences sharedPref
                = PreferenceManager.getDefaultSharedPreferences(rule.getActivity());
        boolean afterNotif = sharedPref.getBoolean("saved_notification", true);
        assertNotEquals(afterNotif, before);
        onView(withId(R.id.switch_notif)).perform(click());
    }
}
