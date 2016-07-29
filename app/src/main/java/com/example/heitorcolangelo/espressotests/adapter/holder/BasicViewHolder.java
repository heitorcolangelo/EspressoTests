package com.example.heitorcolangelo.espressotests.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public final class BasicViewHolder<T, V extends View & ViewBinder<T>>
    extends RecyclerView.ViewHolder {

  private final ViewBinder<T> binder;

  public BasicViewHolder(V view) {
    super(view);
    this.binder = view;
  }

  public ViewBinder<T> getBinder() {
    return binder;
  }
}
