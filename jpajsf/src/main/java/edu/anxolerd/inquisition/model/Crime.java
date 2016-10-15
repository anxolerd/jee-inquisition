package edu.anxolerd.inquisition.model;


import java.io.Serializable;
import java.util.Date;

public class Crime implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private String description;

    private Date existsSince;
    private Date existsUntil;

    public Crime() {}

    public Crime(long id, String title, String description, Date existsSince, Date existsUntil) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.existsSince = existsSince;
        this.existsUntil = existsUntil;
    }

    public long getId() {
        return id;
    }

    public Crime setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Crime setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Crime setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getExistsSince() {
        return existsSince;
    }

    public Crime setExistsSince(Date existsSince) {
        this.existsSince = existsSince;
        return this;
    }

    public Date getExistsUntil() {
        return existsUntil;
    }

    public Crime setExistsUntil(Date existsUntil) {
        this.existsUntil = existsUntil;
        return this;
    }
}
