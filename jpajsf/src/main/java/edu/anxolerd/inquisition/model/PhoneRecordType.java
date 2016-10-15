package edu.anxolerd.inquisition.model;

import java.io.Serializable;


public enum PhoneRecordType implements Serializable, PersistentEnum {
    MAIN(0, "main"),
    MOBILE(1, "mobile"),
    HOME(2, "home"),
    WORK(3, "work"),
    OTHER(4, "other");


    private int persistentValue;
    private String representation;

    PhoneRecordType(int persistentValue, String representation) {
        this.persistentValue = persistentValue;
        this.representation = representation;
    }

    public int getPersistentValue() {
        return persistentValue;
    }

    public PersistentEnum getByPersistentValue(int value) {
        switch (value) {
            case 0: return MAIN;
            case 1: return MOBILE;
            case 2: return HOME;
            case 3: return WORK;
            case 4: return OTHER;
            default: throw new IllegalArgumentException(
                String.format("No type for %d registered", value)
            );
        }
    }

    public String getRepresentation() {
        return representation;
    }
}
