package com.example.heitorcolangelo.espressotests.network.type;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class FullName implements Parcelable {

  @Expose private String first;
  @Expose private String last;
  @Expose private String title;

  public String first() {
    return first.substring(0, 1).toUpperCase() + first.substring(1);
  }

  public String last() {
    return last.substring(0, 1).toUpperCase() + last.substring(1);
  }

  public String title() {
    return title.substring(0, 1).toUpperCase() + title.substring(1);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.first);
    dest.writeString(this.last);
    dest.writeString(this.title);
  }

  public FullName() {
  }

  protected FullName(Parcel in) {
    this.first = in.readString();
    this.last = in.readString();
    this.title = in.readString();
  }

  public static final Creator<FullName> CREATOR = new Creator<FullName>() {
    @Override
    public FullName createFromParcel(Parcel source) {
      return new FullName(source);
    }

    @Override
    public FullName[] newArray(int size) {
      return new FullName[size];
    }
  };
}
