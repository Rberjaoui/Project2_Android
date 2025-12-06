package com.example.labandroiddemo;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUp> activityRule = new ActivityScenarioRule<>(SignUp.class);

    @Test
    public void testSignUpUsers(){
        onView(withId(R.id.nameInput)).perform(typeText("Sasha"));
        onView(withId(R.id.emailInput)).perform(typeText("sbraus@yahoo.com"));
        onView(withId(R.id.passowrdInput)).perform(typeText("potatoes"));
        onView(withId(R.id.signupButt)).perform(click());
    }


    /**
     *
     */

    @Test
    public void  testWrongSignUp(){
        onView(withId(R.id.nameInput)).perform(typeText(" "));
        onView(withId(R.id.emailInput)).perform(typeText("sbraus@yahoo.com"));
        onView(withId(R.id.passowrdInput)).perform(typeText("pie"));
        onView(withId(R.id.signupButt)).perform(click());

    }
    /**
     * Tests if input is a valid email format
     */
    @Test
    public void validEmail(){
        assertTrue(SignUp.isValidEmail("glep@gmail.com"));
    }
}