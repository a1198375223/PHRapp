package com.starstudio.loser.phrapp.item.treatment;

public class EvaluateItem {
    String userName;
    String grade;
    String evaluation;
    String date;
    String imageId;

    public EvaluateItem(String userName,String grade,String evaluation,String date,String imageId){
        this.userName=userName;
        this.grade=grade;
        this.evaluation=evaluation;
        this.date=date;
        this.imageId=imageId;
    }

    public String getUserName() {
        return userName;
    }

    public String getGrade() {
        return grade;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public String getEvaluateDate() {
        return date;
    }

    public String getImageId() {
        return imageId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public void setEvaluateDate(String date) {
        this.date = date;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
