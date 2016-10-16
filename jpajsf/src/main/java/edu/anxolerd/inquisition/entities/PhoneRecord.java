package edu.anxolerd.inquisition.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone_record")
public class PhoneRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneRecordType type;

    @ManyToOne(targetEntity = ContactInfo.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_info_id", referencedColumnName = "id")
    private ContactInfo contactInfo;

    public PhoneRecord() {}

    public PhoneRecord(long id, String phoneNumber, PhoneRecordType type, ContactInfo contactInfo) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.contactInfo = contactInfo;
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

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public PhoneRecord setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }
}
