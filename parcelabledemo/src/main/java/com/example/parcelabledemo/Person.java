package com.example.parcelabledemo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Person implements Parcelable {
    public HashMap<String, String> map = new HashMap<String, String>();
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.map);
        dest.writeString(this.name);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.map = (HashMap<String, String>) in.readSerializable();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
