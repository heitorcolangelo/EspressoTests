package com.example.heitorcolangelo.espressotests.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class ErrorVO implements Parcelable{

  @Expose String error;

  public String getError() {
    return error;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.error);
  }

  public ErrorVO() {
  }

  protected ErrorVO(Parcel in) {
    this.error = in.readString();
  }

  public static final Creator<ErrorVO> CREATOR = new Creator<ErrorVO>() {
    @Override
    public ErrorVO createFromParcel(Parcel source) {
      return new ErrorVO(source);
    }

    @Override
    public ErrorVO[] newArray(int size) {
      return new ErrorVO[size];
    }
  };
}
