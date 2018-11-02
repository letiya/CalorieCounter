package com.letiyaha.android.caloriecounter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class UserHintTest {

    @Rule
    public ActivityTestRule<UserActivity> mActivityTestRule = new ActivityTestRule<>(UserActivity.class);

    @Test
    public void TestHints() {
        String usernameHint = "Enter User Name";
        onView(withId(R.id.et_username)).check(matches(withHint(usernameHint)));

        String dobHint = "Enter D.O.B (YYYY/MM/DD)";
        onView(withId(R.id.et_dob)).check(matches(withHint(dobHint)));

        String heightHint = "Enter User Height (cm)";
        onView(withId(R.id.et_height)).check(matches(withHint(heightHint)));

        String weightHint = "Enter User Weight (kg)";
        onView(withId(R.id.et_weight)).check(matches(withHint(weightHint)));
    }
}
