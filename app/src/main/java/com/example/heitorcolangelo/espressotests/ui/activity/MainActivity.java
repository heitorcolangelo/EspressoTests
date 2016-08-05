package com.example.heitorcolangelo.espressotests.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.adapter.SimpleRecyclerAdapter;
import com.example.heitorcolangelo.espressotests.adapter.UserListAdapter;
import com.example.heitorcolangelo.espressotests.network.UsersApi;
import com.example.heitorcolangelo.espressotests.network.model.ErrorVO;
import com.example.heitorcolangelo.espressotests.network.model.Page;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.BaseActivity;
import com.example.heitorcolangelo.espressotests.ui.widget.UserItemView;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.progress_view) LinearLayout progressView;
  @BindView(R.id.error_view) LinearLayout errorView;

  private static final String TAG = MainActivity.class.getSimpleName();
  private static final String USER_LIST = TAG + ".userList";
  private static final String CURRENT_PAGE = TAG + ".currentPage";
  private int currentPage = 0;
  private UserListAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (adapter == null) {
      showLoading();
      UsersApi.getInstance().getUsers(currentPage);
    } else
      setupRecyclerView();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onUsersApiResponse(Page usersPage) {
    setupAdapter(usersPage.results());
    currentPage++;
  }

  @Override
  protected boolean handleError(ErrorVO error) {
    showError();
    return true;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if(adapter != null)
      outState.putParcelableArrayList(USER_LIST, (ArrayList<? extends Parcelable>) adapter.getItemList());
    outState.putInt(CURRENT_PAGE, currentPage);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if(adapter == null) {
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
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  private void setupAdapter(List<UserVO> userList) {
    showList();
    adapter = new UserListAdapter(new UserListAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(UserVO item) {
        startUserDetailsActivity(item);
      }
    });

    adapter.setViewCreator(new SimpleRecyclerAdapter.ViewCreator<UserVO, UserItemView>() {
      @Override
      public UserItemView createViewInstance(ViewGroup parent, int viewType) {
        return new UserItemView(parent.getContext());
      }
    });
    adapter.addAll(userList);
    setupRecyclerView();
    adapter.notifyDataSetChanged();
  }

  private void showList() {
    progressView.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  private void showLoading() {
    recyclerView.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
    progressView.setVisibility(View.VISIBLE);
  }

  private void showError() {
    recyclerView.setVisibility(View.GONE);
    progressView.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
  }
}
