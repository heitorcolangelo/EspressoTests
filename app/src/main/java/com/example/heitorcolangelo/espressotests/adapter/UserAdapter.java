package com.example.heitorcolangelo.espressotests.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.databinding.ViewUserItemBinding;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.squareup.picasso.Picasso;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

  public interface UserClickListener {
    void onUserClick(UserVO user);
  }

  private List<UserVO> userList;
  private UserClickListener listener;

  public UserAdapter(UserClickListener listener) {
    this.listener = listener;
  }

  @Override
  public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewUserItemBinding binding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.view_user_item,
            parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
    UserVO userVO = userList.get(position);
    holder.bindUser(userVO);
    holder.binding.userViewContainer.setOnClickListener(v -> listener.onUserClick(userVO));
    Picasso.with(holder.binding.userViewImage.getContext())
        .load(userVO.picture().medium())
        .into(holder.binding.userViewImage);
  }

  @Override
  public int getItemCount() {
    return userList.size();
  }

  public void setUserList(List<UserVO> userList) {
    this.userList = userList;
  }

  public List<UserVO> getUserList() {
    return userList;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private ViewUserItemBinding binding;

    public ViewHolder(ViewUserItemBinding itemBinding) {
      super(itemBinding.getRoot());
      binding = itemBinding;
    }

    public void bindUser(UserVO user) {
      binding.setUser(user);
    }
  }
}
