package edu.anxolerd.inquisition.core.entities;


public enum CaseVerdict {
    INNOCENT (1, "Innocent"),
    GUILTY (2, "Guilty");

    private int db;
    private String repr;

    private CaseVerdict(int db, String representation) {
        this.db = db;
        this.repr = representation;
    }

    public static CaseVerdict getByPersistentValue(int value) {
        switch (value) {
            case 1: return INNOCENT;
            case 2: return GUILTY;
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
