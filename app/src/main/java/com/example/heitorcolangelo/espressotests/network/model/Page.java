package com.example.heitorcolangelo.espressotests.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.List;

public class Page implements Parcelable {
  @Expose private List<UserVO> results;

  public List<UserVO> results() {
    return results;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(this.results);
  }

  public Page() {
  }

  protected Page(Parcel in) {
    this.results = in.createTypedArrayList(UserVO.CREATOR);
  }

  public static final Creator<Page> CREATOR = new Creator<Page>() {
    @Override
    public Page createFromParcel(Parcel source) {
      return new Page(source);
    }

    @Override
    public Page[] newArray(int size) {
      return new Page[size];
    }
  };
}
