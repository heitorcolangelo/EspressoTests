package com.example.heitorcolangelo.espressotests.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.heitorcolangelo.espressotests.R;

public class ImageAndTextView extends LinearLayout {

  @BindView(R.id.image_and_text_image) ImageView image;
  @BindView(R.id.image_and_text_text) TextView text;

  public ImageAndTextView(Context context) {
    super(context);
    init(context);
  }

  public ImageAndTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public ImageAndTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ImageAndTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    inflate(context, R.layout.view_image_and_text, this);
    ButterKnife.bind(this);
  }

  public void setupViews(int image, String text) {
    this.image.setImageResource(image);
    if(TextUtils.isEmpty(text)) {
      text = getContext().getString(R.string.user_details_no_info);
      this.text.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
    }
    this.text.setText(text);
  }
}
