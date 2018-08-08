package com.starstudio.loser.phrapp.item.treatment;

import android.widget.ImageView;

/**
 * Created by 11024 on 2018/8/1.
 */

public class HospitalItem {
    private String hospName;
    private String hospImagId;//图片URL

    public HospitalItem(String hospName, String hospImagId){
        this.hospName=hospName;
        this.hospImagId=hospImagId;
    }

    public String getHospName(){
        return hospName;
    }

    public void setHospName(String hospName){
        this.hospName=hospName;
    }

    public String getHospImagId(){
        return hospImagId;
    }

    public void setHospImagId(String hospImagId){
        this.hospImagId=hospImagId;
    }

}
