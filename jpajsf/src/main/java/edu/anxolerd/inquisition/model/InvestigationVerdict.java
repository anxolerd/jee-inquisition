package edu.anxolerd.inquisition.model;

import java.io.Serializable;


public enum InvestigationVerdict implements Serializable, PersistentEnum {
    GUILTY(0, "guilty"),
    INNOCENT(1, "innocent");

    private int persistentValue;
    private String representation;

    InvestigationVerdict(int persistentValue, String representation) {
        this.persistentValue = persistentValue;
        this.representation = representation;
    }

    public int getPersistentValue() {
        return persistentValue;
    }

    public PersistentEnum getByPersistentValue(int value) {
        switch (value) {
            case 0:
                return GUILTY;
            case 1:
                return INNOCENT;
            default:
                throw new IllegalArgumentException(
                    String.format("No type for %d registered", value)
                );
        }
    }

    public String getRepresentation() {
        return representation;
    }
}
