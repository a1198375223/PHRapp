package com.starstudio.loser.phrapp.item.management;


import com.avos.avoscloud.AVObject;

public class Record {
    private String date;
    private String hospitalName;
    private String type;
    private String content;
    private String recordId;

//    public Record(String date,String content,String hospitalName,String type){
//        this.date = date;
//        this.content = content;
//        this.hospitalName = hospitalName;
//        this.type = type;
//    }

    public Record(AVObject avObject){
        date = avObject.getString("date");
        content = avObject.getString("content");
        hospitalName = avObject.getString("hospitalName");
        type = avObject.getString("type");
        recordId = avObject.getObjectId();
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getHospitalName() { return hospitalName; }

    public String getType() { return type; }

    public String getRecordId() { return recordId; }
}
