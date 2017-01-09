package com.example.heitorcolangelo.espressotests.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.databinding.ActivityUserDetailsBinding;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.BaseActivity;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends BaseActivity {

  private static final String TAG = UserDetailsActivity.class.getSimpleName();
  private static final String USER_KEY = TAG + ".user";
  private static final int PHONE_PERMISSION_CODE = 100;
  public static final String CLICKED_USER = TAG + ".clickedUser";


  private UserVO user;
  private ActivityUserDetailsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowHomeEnabled(true);
    }

    user = getIntent().getParcelableExtra(CLICKED_USER);
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
    if (requestCode == PHONE_PERMISSION_CODE) {
      if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        callUser();
      } else
        showSnackBar(R.string.permisssion_not_granted, null);
    } else
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private void setupViewsClick() {
    binding.userDetailsAddress.setOnClickListener(v -> traceRoute());
    binding.userDetailsPhone.setOnClickListener(v -> callUser());
    binding.userDetailsEmail.setOnClickListener(v -> sendEmail());
  }

  private void traceRoute() {
    if (noInfoAvailable(user.location().street(), "address")) return;
    String uri = "google.navigation:q=" + user.location().street();
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    startActivity(intent);
  }

  private void callUser() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
      requestPhonePermission();
    } else {
      if (noInfoAvailable(user.phone(), "phone")) return;
      String uri = "tel:" + user.phone().trim();
      Intent intent = new Intent(Intent.ACTION_CALL);
      intent.setData(Uri.parse(uri));
      startActivity(intent);
    }
  }

  private void requestPhonePermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
      showSnackBar(R.string.phone_permission_message, view -> ActivityCompat
          .requestPermissions(
              UserDetailsActivity.this,
              new String[] { Manifest.permission.CALL_PHONE },
              PHONE_PERMISSION_CODE));
    } else {
      ActivityCompat
          .requestPermissions(
              UserDetailsActivity.this,
              new String[] { Manifest.permission.CALL_PHONE },
              PHONE_PERMISSION_CODE);
    }
  }

  private void sendEmail() {
    if (noInfoAvailable(user.email(), "email")) return;
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_EMAIL, user.email());
    startActivity(intent);
  }

  private boolean noInfoAvailable(String info, String infoName) {
    if (TextUtils.isEmpty(info)) {
      new AlertDialog.Builder(this)
          .setTitle(R.string.important)
          .setMessage(getString(R.string.no_info_message, infoName))
          .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
          .show();
      return true;
    } else
      return false;
  }

  private void setupViewsInfo() {
    if (user == null)
      return;

    binding.userDetailsName.setText(user.fullName());
    binding.userDetailsAddress.setupViews(R.drawable.ic_location, user.location().street());
    binding.userDetailsPhone.setupViews(R.drawable.ic_phone, user.phone());
    binding.userDetailsEmail.setupViews(R.drawable.ic_mail, user.email());

    Picasso.with(this)
        .load(user.picture().large())
        .into(binding.userDetailsImage);
  }

  private void showSnackBar(@StringRes int message, View.OnClickListener actionClick) {
    Snackbar snackbar =
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
    if (actionClick != null) {
      snackbar.setAction(R.string.ok, actionClick);
    }
    snackbar.show();
  }
}
