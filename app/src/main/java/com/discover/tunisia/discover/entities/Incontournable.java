package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Incontournable implements Serializable {
    private String id;
    private String type;
    private String cover;
    private String detail;
    private int resource;

    public Incontournable(String title, String image, int resource,String description) {
        this.type = title;
        this.cover = image;
        this.resource = resource;
        this.detail = description;
    }

    public String getTitle() {
        return type;
    }

    public void setTitle(String title) {
        this.type = title;
    }

    public String getImage() {
        return cover;
    }

    public void setImage(String image) {
        this.cover = image;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getDescription() {
        return detail;
    }

    public void setDescription(String description) {
        this.detail = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
