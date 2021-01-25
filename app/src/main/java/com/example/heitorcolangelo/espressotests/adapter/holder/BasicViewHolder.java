package com.example.heitorcolangelo.espressotests.adapter.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

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
