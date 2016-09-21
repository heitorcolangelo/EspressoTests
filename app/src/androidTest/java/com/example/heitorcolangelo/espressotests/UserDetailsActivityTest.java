package com.example.heitorcolangelo.espressotests;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.example.heitorcolangelo.espressotests.mocks.Mocks;
import com.example.heitorcolangelo.espressotests.network.UsersApi;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.activity.UserDetailsActivity;
import com.example.heitorcolangelo.espressotests.utils.PermissionUtils;
import com.linkedin.android.testbutler.TestButler;
import java.io.IOException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.heitorcolangelo.espressotests.matcher.TextColorMatcher.withTextColor;
import static com.googlecode.eyesfree.utils.LogUtils.TAG;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class UserDetailsActivityTest {
  @Rule
  public ActivityTestRule<UserDetailsActivity> mActivityRule =
      new ActivityTestRule<>(UserDetailsActivity.class, false, false);

  @Test
  public void checkCommonViews() {
    mActivityRule.launchActivity(createIntent(false));
    onView(withId(R.id.user_details_image)).check(matches(isDisplayed()));
    onView(withId(R.id.user_details_name)).check(matches(isDisplayed()));
    onView(allOf(
        withId(R.id.image_and_text_image),
        hasSibling(withText("9446 pockrus page rd"))))
        .check(matches(isDisplayed()));
    onView(allOf(
        withId(R.id.image_and_text_image),
        hasSibling(withText("eddie.dunn@example.com"))))
        .check(matches(isDisplayed()));
    onView(allOf(
        withId(R.id.image_and_text_image),
        hasSibling(withText("(961)-878-5210"))))
        .check(matches(isDisplayed()));
  }

  @Test
  public void whenEmailIsMissing_shouldDisplay_noInfoMessage() {
    mActivityRule.launchActivity(createIntent(true));
    onView(withId(R.id.user_details_image)).check(matches(isDisplayed()));
    onView(withId(R.id.user_details_name)).check(matches(isDisplayed()));
    onView(allOf(
        withId(R.id.image_and_text_image),
        hasSibling(withText("No info available.")))
    ).check(matches(isDisplayed()));

    onView(allOf(
        withText("No info available."),
        withTextColor(ContextCompat.getColor(mActivityRule.getActivity(), R.color.red)))
    ).check(matches(isDisplayed()));
  }

  @Before
  public void beforeTest(){
    String packageName = InstrumentationRegistry.getTargetContext().getPackageName();
    try {
      TestButler.revokePermission(packageName, Manifest.permission.CALL_PHONE);
    }catch (Exception e){
      Log.e(TAG, "beforeTest: ");
    }
  }

  @Test
  public void clickOnPhone_shouldStartPhoneIntent() throws IOException {
    mActivityRule.launchActivity(createIntent(false));
    Intents.init();
    intending(hasAction(Intent.ACTION_CALL))
        .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, new Intent()));
    onView(withId(R.id.user_details_phone)).perform(scrollTo(), click());
    PermissionUtils.allowPermissionsIfNeeded(Manifest.permission.CALL_PHONE);
    intended(hasAction(Intent.ACTION_CALL));
    Intents.release();
  }

  private Intent createIntent(boolean missingInfo) {
    return new Intent().putExtra(UserDetailsActivity.CLICKED_USER, getMockedUser(missingInfo));
  }

  private UserVO getMockedUser(boolean missingInfo) {
    final String mock = missingInfo ? Mocks.USER_MISSING_INFO : Mocks.USER;
    return UsersApi.GSON.fromJson(mock, UserVO.class);
  }
}
