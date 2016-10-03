package edu.anxolerd.inquisition.core.entities;


public enum CaseStatus {
    OPEN (1, "Open"),
    CLOSED (2, "Closed");

    private int db;
    private String repr;

    private CaseStatus(int db, String representation) {
        this.db = db;
        this.repr = representation;
    }

    public static CaseStatus getByPersistentValue(int value) {
        switch (value) {
            case 1: return OPEN;
            case 2: return CLOSED;
            default:
                throw new IllegalArgumentException(String.format("Unexpected value %d", value));
        }
    }

    public String getRepresentation() {
        return this.repr;
    }

    public int getPersistentValue() {
        return this.db;
    }
}
