package com.example.heitorcolangelo.espressotests.adapter;

import android.view.View;
import com.example.heitorcolangelo.espressotests.adapter.holder.BasicViewHolder;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.example.heitorcolangelo.espressotests.ui.widget.UserItemView;

public class UserListAdapter extends SimpleRecyclerAdapter<UserVO, UserItemView> {

  private OnItemClickListener listener;

  public UserListAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(BasicViewHolder viewHolder, int position) {
    super.onBindViewHolder(viewHolder, position);
    final UserVO user = itemList.get(position);
    ((UserItemView) viewHolder.getBinder()).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (listener != null)
          listener.onItemClick(user);
      }
    });
  }

  public interface OnItemClickListener {
    void onItemClick(UserVO item);
  }
}
