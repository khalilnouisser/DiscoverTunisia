package com.discover.tunisia.incontournables.entities;

import java.io.Serializable;
import java.sql.SQLTransactionRollbackException;

public class DetailsIncontournable implements Serializable {
    private String id;
    private String titre;
    private String content;
    private String type_incontournable;

    public DetailsIncontournable(String title, String description) {
        this.titre = title;
        this.content = description;
    }

    public String getTitle() {
        return titre;
    }

    public void setTitle(String title) {
        this.titre = title;
    }

    public String getDescription() {
        return content;
    }

    public void setDescription(String description) {
        this.content = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_incontournable() {
        return type_incontournable;
    }

    public void setType_incontournable(String type_incontournable) {
        this.type_incontournable = type_incontournable;
    }
}
