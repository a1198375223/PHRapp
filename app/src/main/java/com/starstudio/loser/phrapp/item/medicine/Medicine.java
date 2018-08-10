package com.starstudio.loser.phrapp.item.medicine;

import com.avos.avoscloud.AVObject;

import java.text.SimpleDateFormat;

public class Medicine {
    String name;
    String money;
    String factory;
    String content;
    String doctor;
    String id;
    String date;

    public Medicine(AVObject avObject){
        doctor = avObject.getAVUser("doctor").getUsername();
        name = avObject.getString("name");
        money = avObject.getString("money");
        content = avObject.getString("content");
        factory = avObject.getString("factory");
        id = avObject.getObjectId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.format(avObject.getUpdatedAt());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setFactory(String factory) {
        this.factory = factory;
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

    public String getName() {
        return name;
    }

    public String getMoney() {
        return money;
    }

    public String getFactory() {
        return factory;
    }

    public String getContent() {
        return content;
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
}

