package com.example.heitorcolangelo.espressotests.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.BaseActivity;
import com.example.heitorcolangelo.espressotests.ui.widget.ImageAndTextView;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends BaseActivity {

  private static final String TAG = UserDetailsActivity.class.getSimpleName();
  private static final String USER_KEY = TAG + ".user";
  private static final int PHONE_PERMISSION = 100;
  public static final String CLICKED_USER = TAG + ".clickedUser";

  @BindView(R.id.user_details_image) ImageView userImage;
  @BindView(R.id.user_details_name) TextView userName;
  @BindView(R.id.user_details_address) ImageAndTextView userAddress;
  @BindView(R.id.user_details_phone) ImageAndTextView userPhone;
  @BindView(R.id.user_details_email) ImageAndTextView userEmail;

  private UserVO user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_details);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowHomeEnabled(true);
    }

    user = getIntent().getParcelableExtra(CLICKED_USER);
    ButterKnife.bind(this);
    setupViewsInfo();
    setupViewsClick();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home)
      onBackPressed();
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    user = savedInstanceState.getParcelable(USER_KEY);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(USER_KEY, user);
  }

  @Override
  protected void onResume() {
    super.onResume();
    setupViewsInfo();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
  int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == PHONE_PERMISSION)
      callUser();
  }

  private void setupViewsClick() {
    userAddress.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        traceRoute();
      }
    });
    userPhone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        callUser();
      }
    });
    userEmail.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendEmail();
      }
    });
  }

  private void traceRoute() {
    String uri = "google.navigation:q=" + user.location().street();
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    startActivity(intent);
  }

  private void callUser() {
    String uri = "tel:" + user.phone().trim();
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse(uri));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
          != PackageManager.PERMISSION_GRANTED) {
        String[] permission = { Manifest.permission.CALL_PHONE };
        requestPermissions(permission, PHONE_PERMISSION);
        return;
      }
    }
    startActivity(intent);
  }

  private void sendEmail() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_EMAIL, user.email());
    startActivity(intent);
  }

  private void setupViewsInfo() {
    if (user == null)
      return;

    userName.setText(user.fullName());
    userAddress.setupViews(R.drawable.ic_location, user.location().street());
    userPhone.setupViews(R.drawable.ic_phone, user.phone());
    userEmail.setupViews(R.drawable.ic_mail, user.email());

    Picasso.with(this)
        .load(user.picture().large())
        .into(userImage);
  }
}
