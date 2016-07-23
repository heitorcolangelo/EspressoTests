package com.example.heitorcolangelo.espressotests.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.example.heitorcolangelo.espressotests.network.type.FullName;
import com.example.heitorcolangelo.espressotests.network.type.Location;
import com.example.heitorcolangelo.espressotests.network.type.Picture;
import com.google.gson.annotations.Expose;

public class UserVO implements Parcelable {

  @Expose private FullName name;
  @Expose private String phone;
  @Expose private String email;
  @Expose private Location location;
  @Expose private Picture picture;

  public Picture picture() {
    return picture;
  }

  public Location location() {
    return location;
  }

  public String email() {
    return email;
  }

  public String phone() {
    return phone;
  }

  public String fullName() {
    return TextUtils.concat(name.first(), " ", name.last()).toString();
  }

  public String fullNameWithTitle() {
    return TextUtils.concat(name.title(), ". ", name.first(), " ", name.last()).toString();
  }

  public String firstName() {
    return name.first();
  }

  public String lastName() {
    return name.last();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.name, flags);
    dest.writeString(this.phone);
    dest.writeString(this.email);
    dest.writeParcelable(this.location, flags);
    dest.writeParcelable(this.picture, flags);
  }

  public UserVO() {
  }

  protected UserVO(Parcel in) {
    this.name = in.readParcelable(FullName.class.getClassLoader());
    this.phone = in.readString();
    this.email = in.readString();
    this.location = in.readParcelable(Location.class.getClassLoader());
    this.picture = in.readParcelable(Picture.class.getClassLoader());
  }

  public static final Creator<UserVO> CREATOR = new Creator<UserVO>() {
    @Override
    public UserVO createFromParcel(Parcel source) {
      return new UserVO(source);
    }

    @Override
    public UserVO[] newArray(int size) {
      return new UserVO[size];
    }
  };
}
