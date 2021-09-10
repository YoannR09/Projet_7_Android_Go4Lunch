package com.example.go4lunch.model;

public class WorkmateModel {

    private String id;
    private String username;
    private String mail;
    private String pictureUrl;

    public WorkmateModel(String id, String username, String mail, String pictureUrl) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.pictureUrl = pictureUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
