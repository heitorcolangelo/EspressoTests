package com.example.heitorcolangelo.espressotests.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.ui.BaseActivity;

public class LoginActivity extends BaseActivity {

  @BindView(R.id.login_button) Button loginButton;
  @BindView(R.id.login_username) EditText username;
  @BindView(R.id.login_password) EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (validateFields())
          doLogin();
        else
          showErrorDialog();
      }
    });
  }

  private boolean validateFields() {
    int totalFields = 2;
    int validFields = 0;

    validFields += isNotEmpty(username) ? 1 : 0;
    validFields += isNotEmpty(password) ? 1 : 0;
    return validFields == totalFields;
  }

  private boolean isNotEmpty(EditText field) {
    return !TextUtils.isEmpty(field.getText());
  }

  private void doLogin() {
    startActivity(new Intent(this, MainActivity.class));
  }

  private void showErrorDialog() {
    new AlertDialog.Builder(this)
        .setTitle(R.string.important)
        .setMessage(R.string.validation_message)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        })
        .show();
  }
}
