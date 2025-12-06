package com.example.labandroiddemo;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityScenarioRule<SignUp> activityRule = new ActivityScenarioRule<>(SignUp.class);



}