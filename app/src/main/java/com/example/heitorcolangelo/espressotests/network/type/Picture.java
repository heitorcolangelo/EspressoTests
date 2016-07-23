package com.example.heitorcolangelo.espressotests.network.type;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class Picture implements Parcelable {

  @Expose private String large;
  @Expose private String medium;
  @Expose private String thumbnail;

  public String thumbnail() {
    return thumbnail;
  }

  public String medium() {
    return medium;
  }

  public String large() {
    return large;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.thumbnail);
    dest.writeString(this.medium);
    dest.writeString(this.large);
  }

  protected Picture(Parcel in) {
    this.thumbnail = in.readString();
    this.medium = in.readString();
    this.large = in.readString();
  }

  public static final Creator<Picture> CREATOR = new Creator<Picture>() {
    @Override
    public Picture createFromParcel(Parcel source) {
      return new Picture(source);
    }

    @Override
    public Picture[] newArray(int size) {
      return new Picture[size];
    }
  };
}
