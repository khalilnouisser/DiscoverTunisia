package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Incontournable implements Serializable {
    private String title;
    private String image;

    public Incontournable(String title, String image) {
        this.title = title;
        this.image = image;
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
}
