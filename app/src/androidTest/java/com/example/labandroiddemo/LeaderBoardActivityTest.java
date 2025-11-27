package com.example.labandroiddemo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.database.entities.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LeaderBoardActivityTest {
    @Rule
    public ActivityScenarioRule<LeaderboardActivity> activityRule = new ActivityScenarioRule<>(LeaderboardActivity.class);

    @Before
    public void setUp() {
        BlackJackRepository repo = BlackJackRepository.getRepository((Application) ApplicationProvider.getApplicationContext());

        User u1 = new User("AlphaPlayer", "pass1");
        User u2 = new User("BetaPlayer", "pass2");
        repo.insertUser(u1, u2);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLeaderboardDisplaysUsers() {
        onView(withId(R.id.leaderboardRecyclerView)).check(matches(isDisplayed()));

        onView(withText("Alpha")).check(matches(isDisplayed()));
        onView(withText("Beta")).check(matches(isDisplayed()));
    }
}
