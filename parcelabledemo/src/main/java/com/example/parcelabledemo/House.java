package com.example.parcelabledemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/24.
 */
public class House implements Parcelable {
    String price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.price);
    }

    public House() {
    }

    protected House(Parcel in) {
        this.price = in.readString();
    }

    public static final Parcelable.Creator<House> CREATOR = new Parcelable.Creator<House>() {
        @Override
        public House createFromParcel(Parcel source) {
            return new House(source);
        }

        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };
}
