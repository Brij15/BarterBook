package com.example.barterbooksapp;

import java.util.Date;

public class BookPostDataModel {
    private String title;
    private Integer image;
    private String author;
    private String condition;
    private String location;
    private Double price;
    private String category;
    private Date timePosted;
    private String userDetails;

    public BookPostDataModel() {
    }
//Without Category
    public BookPostDataModel(String title, Integer image, String author, String condition, String location, Double price) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.condition = condition;
        this.location = location;
        this.price = price;
    }
//With Category
    public BookPostDataModel(String title, Integer image, String author, String condition, String location, Double price, String category) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.condition = condition;
        this.location = location;
        this.price = price;
        this.category = category;
    }

    public BookPostDataModel(String title, Integer image, String author, String condition, String location, Double price, String category, Date timePosted, String userDetails) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.condition = condition;
        this.location = location;
        this.price = price;
        this.category = category;
        this.timePosted = timePosted;
        this.userDetails = userDetails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Date timePosted) {
        this.timePosted = timePosted;
    }

    @Override
    public String toString() {
        return "BookPostDataModel{" +
                "title='" + title + '\'' +
                ", image=" + image +
                ", author='" + author + '\'' +
                ", condition='" + condition + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", timePosted=" + timePosted +
                '}';
    }
}
