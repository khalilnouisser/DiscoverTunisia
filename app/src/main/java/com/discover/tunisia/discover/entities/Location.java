package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private String localtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocaltime() {
        try {
            return localtime.substring(0,10);
        } catch (Exception e) {
            return "";
        }
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }
}
