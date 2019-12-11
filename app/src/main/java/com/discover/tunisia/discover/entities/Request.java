package com.discover.tunisia.discover.entities;

import java.io.Serializable;

public class Request implements Serializable {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
