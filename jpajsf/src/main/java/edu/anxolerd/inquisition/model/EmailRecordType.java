package edu.anxolerd.inquisition.model;

import java.io.Serializable;


public enum EmailRecordType implements Serializable, PersistentEnum {
    MAIN(0, "main"),
    HOME(1, "home"),
    WORK(2, "work"),
    OTHER(3, "other");


    private int persistentValue;
    private String representation;

    EmailRecordType(int persistentValue, String representation) {
        this.persistentValue = persistentValue;
        this.representation = representation;
    }

    public int getPersistentValue() {
        return persistentValue;
    }

    public PersistentEnum getByPersistentValue(int value) {
        switch (value) {
            case 0: return MAIN;
            case 1: return HOME;
            case 2: return WORK;
            case 3: return OTHER;
            default: throw new IllegalArgumentException(
                String.format("No type for %d registered", value)
            );
        }
    }

    public String getRepresentation() {
        return representation;
    }
}
