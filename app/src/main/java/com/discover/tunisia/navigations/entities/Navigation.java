package com.discover.tunisia.navigations.entities;

public class Navigation {
    private String id;
    private String name;
    private String description;
    private String cover1;
    private String cover2;
    private String cover3;
    private String cover4;
    private String map_picture;
    private String link;



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

    public String getResource_one() {
        return cover1;
    }

    public String getResource_two() {
        return cover2;
    }

    public String getResource_three() {
        return cover3;
    }



    public String getResource_for() {
        return cover4;
    }


    public String getMap() {
        return map_picture;
    }

    public void setMap(String map) {
        this.map_picture = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
