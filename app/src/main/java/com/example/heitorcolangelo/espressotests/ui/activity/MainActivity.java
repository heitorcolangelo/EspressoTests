package com.example.heitorcolangelo.espressotests.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.adapter.UserAdapter;
import com.example.heitorcolangelo.espressotests.databinding.ActivityMainBinding;
import com.example.heitorcolangelo.espressotests.network.UsersApi;
import com.example.heitorcolangelo.espressotests.network.model.Page;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private static final String USER_LIST = TAG + ".userList";
  private static final String CURRENT_PAGE = TAG + ".currentPage";
  private int currentPage = 0;
  private UserAdapter adapter;
  private ActivityMainBinding binding;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (adapter == null) {
      showLoading();
      UsersApi.getInstance()
          .getUsers(currentPage)
          .subscribe(this::onUsersApiResponse, this::showError);
    } else
      setupRecyclerView();
  }

  public void onUsersApiResponse(Page usersPage) {
    setupAdapter(usersPage.results());
    currentPage++;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (adapter != null)
      outState.putParcelableArrayList(USER_LIST,
          (ArrayList<? extends Parcelable>) adapter.getUserList());
    outState.putInt(CURRENT_PAGE, currentPage);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (adapter == null) {
      List<UserVO> userList = savedInstanceState.getParcelableArrayList(USER_LIST);
      setupAdapter(userList);
    }
    currentPage = savedInstanceState.getInt(CURRENT_PAGE);
  }

  private void startUserDetailsActivity(UserVO clickedUser) {
    Intent userDetailsIntent = new Intent(this, UserDetailsActivity.class);
    userDetailsIntent.putExtra(UserDetailsActivity.CLICKED_USER, clickedUser);
    startActivity(userDetailsIntent);
  }

  private void setupRecyclerView() {
    binding.recyclerView.setHasFixedSize(true);
    binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.recyclerView.setAdapter(adapter);
  }

  private void setupAdapter(List<UserVO> userList) {
    showList();
    adapter = new UserAdapter(this::startUserDetailsActivity);
    adapter.setUserList(userList);
    setupRecyclerView();
    adapter.notifyDataSetChanged();
  }

  private void showList() {
    binding.progressView.setVisibility(View.GONE);
    binding.errorView.setVisibility(View.GONE);
    binding.recyclerView.setVisibility(View.VISIBLE);
  }

  private void showLoading() {
    binding.recyclerView.setVisibility(View.GONE);
    binding.errorView.setVisibility(View.GONE);
    binding.progressView.setVisibility(View.VISIBLE);
  }

  private void showError(Throwable throwable) {
    binding.recyclerView.setVisibility(View.GONE);
    binding.progressView.setVisibility(View.GONE);
    binding.errorView.setVisibility(View.VISIBLE);
  }
}
