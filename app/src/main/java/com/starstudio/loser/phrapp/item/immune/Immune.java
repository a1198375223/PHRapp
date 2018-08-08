package com.starstudio.loser.phrapp.item.immune;

import android.util.Log;

import com.avos.avoscloud.AVObject;

import java.text.SimpleDateFormat;

public class Immune {
    String name;
    String type;
    Number times;
    String content;
    String age;
    String doctor;
    String id;
    String date;

    public Immune(AVObject avObject){
        doctor = avObject.getAVUser("doctor").getUsername();
        name = avObject.getString("name");
        type = avObject.getString("type");
        times = avObject.getNumber("times");
        content = avObject.getString("content");
        age = avObject.getString("age");
        id = avObject.getObjectId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.format(avObject.getUpdatedAt());
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Number getTimes() {
        return times;
    }

    public String getContent() {
        return content;
    }

    public String getAge() {
        return age;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimes(Number times) {
        this.times = times;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
