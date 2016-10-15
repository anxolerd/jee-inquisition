package edu.anxolerd.inquisition.model;


import java.io.Serializable;

public class EmailRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String email;
    private EmailRecordType type;

    public EmailRecord() {}

    public EmailRecord(long id, String email, EmailRecordType type) {
        this.id = id;
        this.email = email;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public EmailRecord setId(long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EmailRecord setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmailRecordType getType() {
        return type;
    }

    public EmailRecord setType(EmailRecordType type) {
        this.type = type;
        return this;
    }
}
