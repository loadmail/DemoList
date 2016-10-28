package com.example.parcelabledemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/24.
 */
public class User implements Parcelable {
    String name ;
    int id;
    House house;


    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeParcelable(this.house, flags);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.house = in.readParcelable(House.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
