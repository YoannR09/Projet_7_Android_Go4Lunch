package com.example.go4lunch;

import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.go4lunch.model.RestaurantModel;
import com.example.go4lunch.repo.Repositories;
import com.example.go4lunch.ui.RestaurantDetailsActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class RestaurantDetailActivityTest {

    public RestaurantDetailActivityTest() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("yocorps17@gmail.com", "azerty");
    }

    RestaurantDetailsActivity activity;

    @Rule
    public ActivityTestRule<RestaurantDetailsActivity> rule
            = new ActivityTestRule<>(RestaurantDetailsActivity.class,
            true, false);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        Repositories.createRestaurantRepoMock();
        Intent i = new Intent();
        RestaurantModel restaurantModel
                = new RestaurantModel(
                "id",
                "name",
                34,
                33,
                "Adresse",
                null,
                4,
                "photo",
                "false",
                false);
        i.putExtra("data_restaurant", restaurantModel);
        rule.launchActivity(i);
    }

    @Test
    public void initMainActivity() {
        activity = rule.getActivity();
        assertNotNull(activity);
    }

    @Test
    public void detailsInfo() {
        activity = rule.getActivity();
        ViewInteraction opinion = onView(withId(R.id.detail_ratio));
        opinion.check(matches(withText("4.0/5")));
        onView(withId(R.id.appBarLayout)).check(matches(hasDescendant(withText("name"))));
    }
}
