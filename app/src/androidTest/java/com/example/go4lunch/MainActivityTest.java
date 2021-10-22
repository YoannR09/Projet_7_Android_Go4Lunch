package com.example.go4lunch;

import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class,
            true, true);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void start() {
        activity = rule.getActivity();
        rule.launchActivity(new Intent());
    }

    @Test
    public void initMainActivity() {
        assertNotNull(activity);
    }

    @Test
    public void startAuthPage() {
        onView(ViewMatchers.withText("test")).perform(click());
    }

}
