package com.example.go4lunch;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.MapFragment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public MainActivityTest() {
        Repositories.createRestaurantRepoMock();
        rule.launchActivity(new Intent());
        FirebaseAuth.getInstance().signInWithEmailAndPassword("mytestmail@outlook.com", "azerty");
    }

    private static final double DELTA = 1e-15;

    MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class,
            true, true);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void initMainActivity() {
        activity = rule.getActivity();
        assertNotNull(activity);
    }

    @Test
    public void AstartMapFragment() {
        activity = rule.getActivity();
        assertEquals(activity.active.getClass(), MapFragment.class);
        //onView(ViewMatchers.withText("Se connecter avec une adresse e-mail")).perform(click());
        //onView(ViewMatchers.withText("AUCUN DES COMPTES CI-DESSUS")).perform(click());
    }

    @Test
    public void BswitchToListFragment() {
        activity = rule.getActivity();
        onView(ViewMatchers.withId(R.id.view_list)).perform(click());
        assertTrue(Objects.requireNonNull(activity.fm.findFragmentByTag("LIST")).isVisible());
    }

    @Test
    public void CswitchToWorkmateFragment() {
        activity = rule.getActivity();
        onView(ViewMatchers.withId(R.id.workmates)).perform(click());
        assertTrue(Objects.requireNonNull(activity.fm.findFragmentByTag("WORKMATE")).isVisible());
    }

    @Test
    public void DreturnToMapFragment() {
        activity = rule.getActivity();
        onView(ViewMatchers.withId(R.id.map_list)).perform(click());
        assertTrue(Objects.requireNonNull(activity.fm.findFragmentByTag("MAP")).isVisible());
    }

}
