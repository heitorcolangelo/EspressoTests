package com.example.heitorcolangelo.espressotests.matcher;

import android.support.annotation.ColorInt;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TextColorMatcher {

  private TextColorMatcher(){}

  public static Matcher<View> withTextColor(@ColorInt final int color) {
    return new BoundedMatcher<View, TextView>(TextView.class) {
      int targetTextColor = 0;
      @Override
      public void describeTo(Description description) {
        description.appendText("hasTextColor: ").appendValue(Integer.toHexString(color));
        description.appendText(" actualTextColor: ")
            .appendValue(Integer.toHexString(targetTextColor));
      }

      @Override
      protected boolean matchesSafely(TextView item) {
        if(targetTextColor == 0)
          targetTextColor = item.getCurrentTextColor();
        return targetTextColor == color;
      }
    };
  }
}
