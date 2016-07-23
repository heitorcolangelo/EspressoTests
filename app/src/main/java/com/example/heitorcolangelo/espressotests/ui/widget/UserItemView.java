package com.example.heitorcolangelo.espressotests.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;
import com.example.heitorcolangelo.espressotests.network.model.UserVO;
import com.squareup.picasso.Picasso;

public class UserItemView extends RelativeLayout {

  @BindView(R.id.user_view_image) ImageView userImage;
  @BindView(R.id.user_view_name) TextView userName;
  @BindView(R.id.user_view_phone) TextView userPhone;

  public UserItemView(Context context, UserVO user) {
    this(context);
    setUserData(user);
  }

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

  public void setUserData(@NonNull UserVO user) {
    userName.setText(user.fullName());
    userPhone.setText(user.phone());

    Picasso.with(getContext())
        .load(user.picture().medium())
        .into(userImage);
  }
}
