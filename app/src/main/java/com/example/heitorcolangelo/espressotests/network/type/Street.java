package com.example.heitorcolangelo.espressotests.network.type;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Street implements Parcelable {
    @Expose
    private String name;
    @Expose
    private int number;

    public String name() {
        return name;
    }

    public int number() {
        return number;
    }

    public String numberAndName() {
        return number + ", " + name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.number);
    }

    public Street() {
    }

    protected Street(Parcel in) {
        name = in.readString();
        number = in.readInt();
    }

    public static final Creator<Street> CREATOR = new Creator<Street>() {
        @Override
        public Street createFromParcel(Parcel in) {
            return new Street(in);
        }

        @Override
        public Street[] newArray(int size) {
            return new Street[size];
        }
    };

}
