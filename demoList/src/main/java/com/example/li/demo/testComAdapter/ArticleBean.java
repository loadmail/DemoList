package com.example.li.demo.testComAdapter;

/**
 * Created by lizhichao on 16/6/11.
 */
public class ArticleBean {
   String title , phone,content,time;

    public ArticleBean(String title, String content, String time, String phone) {
        this.title = title;
        this.phone = phone;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
