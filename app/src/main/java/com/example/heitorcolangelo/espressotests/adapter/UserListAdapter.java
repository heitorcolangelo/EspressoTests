package com.example.heitorcolangelo.espressotests.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.widget.UserItemView;
import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private final List<UserVO> userList;

    public UserListAdapter(List<UserVO> userList) {
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new UserItemView(parent.getContext(),userList.get(position));
    }

    public UserVO getUserAtPosition(int position) {
        return (UserVO) getItem(position);
    }
}
