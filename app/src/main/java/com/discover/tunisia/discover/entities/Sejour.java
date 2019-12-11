package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Sejour implements Serializable {
    private String id;
    private String name;
    private String thumbnail;
    private String cover;
    private int resource;

    public Sejour(String title, String image, int resource) {
        this.name = title;
        this.thumbnail  = image;
        this.resource = resource;
    }

    public Sejour() {

    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public String getImage() {
        return thumbnail ;
    }

    public void setImage(String image) {
        this.thumbnail  = image;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
