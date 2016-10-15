package edu.anxolerd.inquisition.model;


public interface PersistentEnum {
    int getPersistentValue();
    PersistentEnum getByPersistentValue(int value);

    String getRepresentation();
}
