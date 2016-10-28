package com.example.testjsui;

/**
 * Created by Administrator on 2016/5/18.
 */
public class Contacts {
    int id;
    int phone;
    String name;

    public Contacts(int id,String name, int phone ) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
