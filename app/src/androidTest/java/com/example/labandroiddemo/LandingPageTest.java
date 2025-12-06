package com.example.labandroiddemo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LandingPageTest {

    @Rule
    public ActivityScenarioRule<LandingPage> activityScenarioRule = new ActivityScenarioRule<>(LandingPage.class);

    @Test
    public void testLanding(){
        onView(withId(R.id.play)).perform(click());

    }
}
