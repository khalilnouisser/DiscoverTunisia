package com.discover.tunisia.photos.entities;

import java.io.Serializable;

public class Photo implements Serializable {

    private String image;

    public Photo(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
