package com.example.heitorcolangelo.espressotests.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.adapter.holder.ViewBinder;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.squareup.picasso.Picasso;

public class UserItemView extends RelativeLayout implements ViewBinder<UserVO> {

  @BindView(R.id.user_view_image) ImageView userImage;
  @BindView(R.id.user_view_name) TextView userName;

  public UserItemView(Context context) {
    super(context);
    init(context);
  }

  public UserItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public UserItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public UserItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    inflate(context, R.layout.view_user_item, this);
    ButterKnife.bind(this);
  }

  @Override
  public void bind(UserVO payload) {
    userName.setText(payload.fullName());

    Picasso.with(getContext())
        .load(payload.picture().medium())
        .into(userImage);
  }
}
