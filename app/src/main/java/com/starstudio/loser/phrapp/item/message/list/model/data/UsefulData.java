package com.starstudio.loser.phrapp.item.message.list.model.data;

/*
    create by:loser
    date:2018/7/26 13:46
*/

public class UsefulData {
    private String url;
    private String image1;
    private String author;
    private String image2;
    private String title;
    private String image3;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    @Override
    public String toString() {
        return "UsefulData{" +
                "url='" + url + '\'' +
                ", image1='" + image1 + '\'' +
                ", author='" + author + '\'' +
                ", image2='" + image2 + '\'' +
                ", title='" + title + '\'' +
                ", image3='" + image3 + '\'' +
                '}';
    }
}
