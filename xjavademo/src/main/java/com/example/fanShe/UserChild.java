package com.example.fanShe;

/**
 * Created by ly on 2016/10/26 16:16.
 */

public class UserChild extends User {
    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String dress;

    public UserChild(String name, int id) {
        super(name, id);
    }
}
