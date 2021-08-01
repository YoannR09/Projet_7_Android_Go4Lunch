package com.example.go4lunch.entity;

public class WorkmateEntity {

    private String id;
    private String username;
    private String urlPicture;

    public WorkmateEntity(String id, String username, String urlPicture) {
        this.id = id;
        this.username = username;
        this.urlPicture = urlPicture;
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
}
