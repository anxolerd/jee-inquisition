package edu.anxolerd.inquisition.model;


import java.io.Serializable;
import java.util.List;


public class ContactInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private Address homeAddress;
    private Address workAddress;
    private List<Address> otherAddresses;

    private Person person;

    private List<EmailRecord> emails;
    private List<PhoneRecord> phones;

    public ContactInfo() {}

    public ContactInfo(
        long id, Address homeAddress, Address workAddress, List<Address> otherAddresses,
        Person person, List<EmailRecord> emails, List<PhoneRecord> phones
    ) {
        this.id = id;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
        this.otherAddresses = otherAddresses;
        this.person = person;
        this.emails = emails;
        this.phones = phones;
    }

    public long getId() {
        return id;
    }

    public ContactInfo setId(long id) {
        this.id = id;
        return this;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public ContactInfo setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
        return this;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public ContactInfo setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
        return this;
    }

    public List<Address> getOtherAddresses() {
        return otherAddresses;
    }

    public ContactInfo setOtherAddresses(List<Address> otherAddresses) {
        this.otherAddresses = otherAddresses;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public ContactInfo setPerson(Person person) {
        this.person = person;
        return this;
    }

    public List<EmailRecord> getEmails() {
        return emails;
    }

    public ContactInfo setEmails(List<EmailRecord> emails) {
        this.emails = emails;
        return this;
    }

    public List<PhoneRecord> getPhones() {
        return phones;
    }

    public ContactInfo setPhones(List<PhoneRecord> phones) {
        this.phones = phones;
        return this;
    }
}
