package com.discover.tunisia.photos.entities;

import java.io.Serializable;

public class Photo implements Serializable {

    private String id;
    private String type;
    private String image;
    private String video_link;
    private boolean is_clicked_favoris;

    public Photo(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public boolean isIs_clicked_favoris() {
        return is_clicked_favoris;
    }

    public void setIs_clicked_favoris(boolean is_clicked_favoris) {
        this.is_clicked_favoris = is_clicked_favoris;
    }
}
