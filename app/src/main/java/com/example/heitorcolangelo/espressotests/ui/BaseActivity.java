package com.example.heitorcolangelo.espressotests.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onDestroy() {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onRequestError(Throwable error) {
    if (handleError(error))
      return;
    error.printStackTrace();
  }

  /**
   * If you wish to handle the error in a particular way, override this method in your activity
   * and return true.
   */
  protected boolean handleError(Throwable error) {
    return false;
  }
}
