package com.example.heitorcolangelo.espressotests.network.type;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class Location implements Parcelable {
  @Expose private String street;
  @Expose private String city;
  @Expose private String state;
  @Expose private String postcode;

  public String street() {
    return street;
  }

  public String city() {
    return city;
  }

  public String state() {
    return state;
  }

  public String postcode() {
    return postcode;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.street);
    dest.writeString(this.city);
    dest.writeString(this.state);
    dest.writeString(this.postcode);
  }

  public Location() {
  }

  protected Location(Parcel in) {
    this.street = in.readString();
    this.city = in.readString();
    this.state = in.readString();
    this.postcode = in.readString();
  }

  public static final Creator<Location> CREATOR = new Creator<Location>() {
    @Override
    public Location createFromParcel(Parcel source) {
      return new Location(source);
    }

    @Override
    public Location[] newArray(int size) {
      return new Location[size];
    }
  };
}
