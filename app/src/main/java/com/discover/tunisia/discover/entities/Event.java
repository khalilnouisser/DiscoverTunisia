package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Event implements Serializable {

    private String id;
    private String name;
    private String description;
    private String thumbnail ;
    private String date;

    public Event(String title, String description, String image, String date) {
        this.name = title;
        this.description = description;
        this.thumbnail  = image;
        this.date = date;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return thumbnail ;
    }

    public void setImage(String image) {
        this.thumbnail  = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
