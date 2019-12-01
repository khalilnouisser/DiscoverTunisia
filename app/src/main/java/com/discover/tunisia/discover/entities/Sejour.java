package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Sejour implements Serializable {
    private String title;
    private String image;
    private int resource;

    public Sejour(String title, String image, int resource) {
        this.title = title;
        this.image = image;
        this.resource = resource;
    }

    public Sejour() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
