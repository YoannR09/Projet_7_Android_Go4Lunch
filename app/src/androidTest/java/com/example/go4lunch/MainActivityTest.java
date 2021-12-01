package com.example.go4lunch;

import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.MapFragment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public MainActivityTest() {
        Repositories.createRestaurantRepoMock();
        rule.launchActivity(new Intent());
        FirebaseAuth.getInstance().signInWithEmailAndPassword("yocorps17@gmail.com", "azerty");
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
        onView(withId(R.id.view_list)).perform(click());
        onView(withId(R.id.map)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rvRestaurants)).check(matches(isDisplayed()));
        onView(withId(R.id.rvWorkmates)).check(matches(not(isDisplayed())));
    }

    @Test
    public void CswitchToWorkmateFragment() {
        activity = rule.getActivity();
        onView(withId(R.id.workmates)).perform(click());
        onView(withId(R.id.map)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rvRestaurants)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rvWorkmates)).check(matches(isDisplayed()));
    }

    @Test
    public void DreturnToMapFragment() throws InterruptedException {
        activity = rule.getActivity();
        onView(withId(R.id.map_list)).perform(click());
        sleep(100);
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        onView(withId(R.id.rvRestaurants)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rvWorkmates)).check(matches(not(isDisplayed())));
    }

}
