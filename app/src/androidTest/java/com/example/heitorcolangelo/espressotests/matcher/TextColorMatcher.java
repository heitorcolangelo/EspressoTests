package com.example.heitorcolangelo.espressotests.matcher;

import android.support.annotation.ColorInt;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TextColorMatcher {

  private TextColorMatcher() {
  }

  public static Matcher<View> withTextColor(@ColorInt final int expectedColor) {
    return new BoundedMatcher<View, TextView>(TextView.class) {
      int currentColor = 0;

      @Override
      public void describeTo(Description description) {
        description.appendText("expected TextColor: ")
            .appendValue(Integer.toHexString(expectedColor));
        description.appendText(" current TextColor: ")
            .appendValue(Integer.toHexString(currentColor));
      }

      @Override
      protected boolean matchesSafely(TextView item) {
        if (currentColor == 0)
          currentColor = item.getCurrentTextColor();
        return currentColor == expectedColor;
      }
    };
  }
}
