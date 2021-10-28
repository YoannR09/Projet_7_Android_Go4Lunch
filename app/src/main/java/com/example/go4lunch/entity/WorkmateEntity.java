package com.example.go4lunch.entity;

public class WorkmateEntity {

    private String id;
    private String username;
    private String urlPicture;
    private String mail;

    public WorkmateEntity () {}

    public WorkmateEntity(String id, String username, String urlPicture, String mail) {
        this.id = id;
        this.username = username;
        this.urlPicture = urlPicture;
        this.mail = mail;
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
