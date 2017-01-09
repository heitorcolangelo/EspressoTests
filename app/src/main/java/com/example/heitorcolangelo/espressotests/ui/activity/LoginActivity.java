package com.example.heitorcolangelo.espressotests.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.databinding.ActivityLoginBinding;
import com.example.heitorcolangelo.espressotests.ui.BaseActivity;

public class LoginActivity extends BaseActivity {

  private ActivityLoginBinding bind;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bind = DataBindingUtil.setContentView(this, R.layout.activity_login);

    bind.loginButton.setOnClickListener(v -> {
      if (validateFields())
        doLogin();
      else
        showErrorDialog();
    });
  }

  private boolean validateFields() {
    int totalFields = 2;
    int validFields = 0;

    validFields += isNotEmpty(bind.loginUsername) ? 1 : 0;
    validFields += isNotEmpty(bind.loginPassword) ? 1 : 0;
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
        .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
        .show();
  }
}
