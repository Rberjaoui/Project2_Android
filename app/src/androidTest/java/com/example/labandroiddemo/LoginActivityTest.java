package com.example.labandroiddemo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Application;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.labandroiddemo.database.BlackJackRepository;
import com.example.labandroiddemo.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Intents.init();

        Context context = ApplicationProvider.getApplicationContext();
        BlackJackRepository repo = BlackJackRepository.getRepository((Application) context);

        User u = new User("testuser1", "testuser1");
        repo.insertUser(u);

        User a = new User("admin2", "admin2");
        a.setAdmin(true);
        repo.insertUser(a);

        try {
            Thread.sleep(1000); // Wait 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testRegularUserLogin() {
        onView(withId(R.id.userNameLogIn)).perform(replaceText("testuser1"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(replaceText("testuser1"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        intended(hasComponent(LandingPage.class.getName()));
    }

    @Test
    public void testAdminUserLogin() {
        onView(withId(R.id.userNameLogIn)).perform(replaceText("admin2"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogIn)).perform(replaceText("admin2"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        intended(hasComponent(LandingPage.class.getName()));
    }
}
