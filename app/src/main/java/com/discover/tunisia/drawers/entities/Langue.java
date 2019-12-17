package com.discover.tunisia.drawers.entities;

import java.util.Locale;

public class Langue {

    private String name;
    private boolean isSelected;
    private Locale locale;

    public Langue(String name, boolean isSelected, Locale locale) {
        this.name = name;
        this.isSelected = isSelected;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
