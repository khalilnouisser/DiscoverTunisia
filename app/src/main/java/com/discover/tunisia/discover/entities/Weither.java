package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Weither implements Serializable {
    private Location location;
    private Current current;
    private Request request;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
