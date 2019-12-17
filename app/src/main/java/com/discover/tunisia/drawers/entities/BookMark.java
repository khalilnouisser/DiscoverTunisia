package com.discover.tunisia.drawers.entities;

import com.discover.tunisia.discover.entities.Event;

import java.io.Serializable;

public class BookMark implements Serializable {
    private String id;
    private String type;
    private String video_link;
    private String image;
    private Event event;
    private boolean is_clicked_favoris;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isIs_clicked_favoris() {
        return is_clicked_favoris;
    }

    public void setIs_clicked_favoris(boolean is_clicked_favoris) {
        this.is_clicked_favoris = is_clicked_favoris;
    }
}
