package com.starstudio.loser.phrapp.item.treatment;

import org.json.JSONArray;

/**
 * Created by 11024 on 2018/8/2.
 */

public class DoctorItem {
    private String doctorName;
    private String doctorImageId;//图片URL
    private String doctorTitle;//医生职称
    private String profile;//医生简介
    private JSONArray workArray;//医生工作时间和预约情况
    private String dept;//科室
    private String hosp;//医院名

    public DoctorItem(String name,String imageId,String title,String profile,JSONArray workArray,String dept,String hosp){
        doctorName=name;
        doctorImageId=imageId;
        doctorTitle=title;
        this.profile=profile;
        this.workArray=workArray;
        this.dept=dept;
        this.hosp=hosp;
    }

    public String getDoctorName(){
        return doctorName;
    }

    public String getDoctorImagId(){
        return doctorImageId;
    }

    public String getDoctorTitle(){
        return doctorTitle;
    }

    public String getProfile(){
        return profile;
    }

    public JSONArray getWorkArray(){
        return workArray;
    }

    public String getDept(){
        return dept;
    }

    public String getHosp(){
        return hosp;
    }

    public void setDoctorName(String name){
        doctorName=name;
    }

    public void setDoctorImageId(String imageId){
        doctorImageId=imageId;
    }

    public void setProfile(String profile){
        this.profile=profile;
    }

    public void setDoctorTitle(String title){
        doctorTitle=title;
    }

    public void setWorkArray(JSONArray array){
        workArray=array;
    }

    public void setDept(String dept){
        this.dept=dept;
    }

    public void setHosp(String hosp){
        this.hosp=hosp;
    }
}
