package com.example.barterbooksapp.utlity;

import java.util.Date;
import java.util.List;

public class BookPostDataModel {
    private String title;
    private Integer image;
    private String author;
    private String condition;
    private String location;
    private Double price;
    private String category;
    private Boolean isBarter;
    private String details;
    private Date timePosted;
    private List<Integer> imagesList;

//  User details
    private String userID;
    private String userName;
    private String userEmail;
    private String userDetails;

    public BookPostDataModel() {
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

    public BookPostDataModel(String title, String author, String condition, String location, Double price, String category, Date timePosted, String userID, List<Integer> imagesList, String userDetails) {
        this.title = title;
        this.author = author;
        this.condition = condition;
        this.location = location;
        this.price = price;
        this.category = category;
        this.timePosted = timePosted;
        this.userID = userID;
        this.imagesList = imagesList;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Integer> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Integer> imagesList) {
        this.imagesList = imagesList;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public Boolean getBarter() {
        return isBarter;
    }

    public void setBarter(Boolean barter) {
        isBarter = barter;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
                ", isBarter=" + isBarter +
                ", details='" + details + '\'' +
                ", timePosted=" + timePosted +
                ", imagesList=" + imagesList +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userDetails='" + userDetails + '\'' +
                '}';
    }
}
