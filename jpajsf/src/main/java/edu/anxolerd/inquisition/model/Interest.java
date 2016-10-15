package edu.anxolerd.inquisition.model;

import java.io.Serializable;


public class Interest implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private String description;
    private int sinRate;

    public Interest() {}

    public Interest(long id, String title, String description, int sinRate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sinRate = sinRate;
    }

    public long getId() {
        return id;
    }

    public Interest setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Interest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Interest setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getSinRate() {
        return sinRate;
    }

    public Interest setSinRate(int sinRate) {
        this.sinRate = sinRate;
        return this;
    }
}
