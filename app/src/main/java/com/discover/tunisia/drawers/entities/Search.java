package com.discover.tunisia.drawers.entities;

import com.discover.tunisia.discover.entities.DetailsSejour;
import com.discover.tunisia.discover.entities.Event;
import com.discover.tunisia.discover.entities.Incontournable;
import com.discover.tunisia.discover.entities.Sejour;

import java.io.Serializable;

public class Search implements Serializable {
    private String type;
    private Event event;
    private Sejour sejour;
    private Incontournable incontournable;
    private DetailsSejour monsejourelements;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Sejour getSejour() {
        return sejour;
    }

    public void setSejour(Sejour sejour) {
        this.sejour = sejour;
    }

    public Incontournable getIncontournable() {
        return incontournable;
    }

    public void setIncontournable(Incontournable incontournable) {
        this.incontournable = incontournable;
    }

    public DetailsSejour getDetailsSejour() {
        return monsejourelements;
    }

    public void setDetailsSejour(DetailsSejour detailsSejour) {
        this.monsejourelements = detailsSejour;
    }
}
