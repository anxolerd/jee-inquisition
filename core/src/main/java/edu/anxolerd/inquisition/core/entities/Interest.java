package edu.anxolerd.inquisition.core.entities;

import java.util.UUID;


public class Interest {
    private UUID id;
    private String title;
    private String description;
    private int sinRate;

    public Interest() {
    }

    public Interest(UUID id, String title, String description, int sinRate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sinRate = sinRate;
    }

    public UUID getId() {
        return id;
    }

    public Interest setId(UUID id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interest interest = (Interest) o;

        if (getSinRate() != interest.getSinRate()) return false;
        if (getId() != null ? !getId().equals(interest.getId()) : interest.getId() != null) return false;
        if (!getTitle().equals(interest.getTitle())) return false;
        return getDescription() != null ? getDescription().equals(interest.getDescription()) : interest.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getSinRate();
        return result;
    }
}
