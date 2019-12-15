package com.discover.tunisia.drawers.entities;

import com.discover.tunisia.discover.entities.Event;

import java.io.Serializable;

public class EventBookMark implements Serializable {

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
