package edu.anxolerd.inquisition.model;

import java.io.Serializable;


public class PhoneRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String phoneNumber;
    private PhoneRecordType type;

    public PhoneRecord() {}

    public PhoneRecord(long id, String phoneNumber, PhoneRecordType type) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public PhoneRecord setId(long id) {
        this.id = id;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneRecord setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public PhoneRecordType getType() {
        return type;
    }

    public PhoneRecord setType(PhoneRecordType type) {
        this.type = type;
        return this;
    }
}
