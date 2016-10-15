package edu.anxolerd.inquisition.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String documentId;

    private Date birthDate;
    private Date deathDate;

    private List<PersonNote> notes;
    private ContactInfo contactInfo;

    private List<Interest> interests;

    public Person() {}

    public Person(
        long id, String firstName, String middleName, String lastName,
        String documentId, Date birthDate, Date deathDate,
        List<PersonNote> notes, ContactInfo contactInfo,
        List<Interest> interests
    ) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.documentId = documentId;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.notes = notes;
        this.contactInfo = contactInfo;
        this.interests = interests;
    }

    public long getId() {
        return id;
    }

    public Person setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Person setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Person setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Person setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public Person setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
        return this;
    }

    public List<PersonNote> getNotes() {
        return notes;
    }

    public Person setNotes(List<PersonNote> notes) {
        this.notes = notes;
        return this;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public Person setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public Person setInterests(List<Interest> interests) {
        this.interests = interests;
        return this;
    }
}
