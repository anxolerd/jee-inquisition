package edu.anxolerd.inquisition.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email_record")
public class EmailRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmailRecordType type;

    @ManyToOne(targetEntity = ContactInfo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_info_id", referencedColumnName = "id", nullable = false)
    private ContactInfo contactInfo;

    public EmailRecord() {}

    public EmailRecord(long id, String email, EmailRecordType type, ContactInfo contactInfo) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.contactInfo = contactInfo;
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

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public EmailRecord setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }
}
