package com.example.heitorcolangelo.espressotests;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.heitorcolangelo.espressotests.ui.activity.LoginActivity;
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

  private static final int BOTH_FIELDS_ID = -1;
  @Rule
  public ActivityScenarioRule<LoginActivity>
      mActivityRule = new ActivityScenarioRule<>(LoginActivity.class);

  @Test
  public void whenActivityIsLaunched_shouldDisplayInitialState() {
    onView(withId(R.id.login_image)).check(matches(isDisplayed()));
    onView(withId(R.id.login_username)).check(matches(isDisplayed()));
    onView(withId(R.id.login_password)).check(matches(isDisplayed()));
    onView(withId(R.id.login_button)).check(matches(isDisplayed()));
  }

  @Test
  public void whenPasswordIsEmpty_andClickOnLoginButton_shouldDisplayDialog() {
    testEmptyFieldState(R.id.login_username);
  }

  @Test
  public void whenUserNameIsEmpty_andClickOnLoginButton_shouldDisplayDialog() {
    testEmptyFieldState(R.id.login_password);
  }

  @Test
  public void whenBothFieldsAreEmpty_andClickOnLoginButton_shouldDisplayDialog() {
    testEmptyFieldState(BOTH_FIELDS_ID);
  }

  @Test
  public void whenBothFieldsAreFilled_andClickOnLoginButton_shouldOpenMainActivity() {
    Intents.init();
    onView(withId(R.id.login_username)).perform(typeText("defaultText"), closeSoftKeyboard());
    onView(withId(R.id.login_password)).perform(typeText("defaultText"), closeSoftKeyboard());
    Matcher<Intent> matcher = hasComponent(MainActivity.class.getName());

    ActivityResult result = new ActivityResult(Activity.RESULT_OK, null);

    intending(matcher).respondWith(result);

    onView(withId(R.id.login_button)).perform(click());
    intended(matcher);
    Intents.release();
  }


  private void testEmptyFieldState(int notEmptyFieldId) {
    if (notEmptyFieldId != BOTH_FIELDS_ID) {
      onView(withId(notEmptyFieldId)).perform(typeText("defaultText"), closeSoftKeyboard());
    }

    onView(withId(R.id.login_button)).perform(click());
    onView(withText(R.string.validation_message)).check(matches(isDisplayed()));
    onView(withText(R.string.ok)).perform(click());
  }
}
