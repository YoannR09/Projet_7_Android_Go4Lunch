package com.example.go4lunch.entity;

import com.google.firebase.database.Exclude;

public class WorkmateEntity {

    private String id;
    private String username;
    private String urlPicture;
    private String mail;

    @Exclude
    private boolean hasDiner;

    public WorkmateEntity () {}

    public WorkmateEntity(String id, String username, String urlPicture, String mail) {
        this.id = id;
        this.username = username;
        this.urlPicture = urlPicture;
        this.mail = mail;
    }

    public boolean hasDiner() {
        return hasDiner;
    }

    public void setHasDiner(boolean hasDiner) {
        this.hasDiner = hasDiner;
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

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
