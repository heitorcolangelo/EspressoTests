package com.example.heitorcolangelo.espressotests.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.adapter.UserListAdapter;
import com.example.heitorcolangelo.espressotests.network.UsersApi;
import com.example.heitorcolangelo.espressotests.network.model.Page;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String USER_LIST = TAG + ".userList";
    private static final String CURRENT_PAGE = TAG + ".currentPage";

    @BindView(R.id.grid_view) GridView grid;
    @BindView(R.id.progress_view) LinearLayout progressView;

    private int currentPage = 0;
    private List<UserVO> userList;
    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupGridView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userList == null) {
            showLoading();
            UsersApi.getInstance().getUsers(currentPage);
        } else
            setupAdapter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUsersApiResponse(Page usersPage) {
        userList = usersPage.results();
        setupAdapter();
        currentPage++;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(USER_LIST, (ArrayList<? extends Parcelable>) userList);
        outState.putInt(CURRENT_PAGE, currentPage);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userList = savedInstanceState.getParcelableArrayList(USER_LIST);
        currentPage = savedInstanceState.getInt(CURRENT_PAGE);
    }

    private void setupGridView() {
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter == null)
                    return;
                startUserDetailsActivity(position);
            }
        });
    }

    private void startUserDetailsActivity(int position) {
        UserVO clickedUser = adapter.getUserAtPosition(position);
        Intent userDetailsIntent = new Intent(this, UserDetailsActivity.class);
        userDetailsIntent.putExtra(UserDetailsActivity.CLICKED_USER, clickedUser);
        startActivity(userDetailsIntent);
    }

    private void setupAdapter() {
        showList();
        adapter = new UserListAdapter(userList);
        if (grid.getAdapter() == null)
            grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showList() {
        progressView.setVisibility(View.GONE);
        grid.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        grid.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

}
