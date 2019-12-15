package com.discover.tunisia.drawers.entities;

import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.photos.entities.Photo;

import java.io.Serializable;

public class GalleryBookMark implements Serializable {

    private Photo gallery;

    public Photo getGallery() {
        return gallery;
    }

    public void setGallery(Photo gallery) {
        this.gallery = gallery;
    }
}
