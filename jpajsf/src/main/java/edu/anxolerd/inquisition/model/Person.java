package edu.anxolerd.inquisition.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String document;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "death_date")
    private Date deathDate;

    @OneToMany(targetEntity = PersonNote.class, fetch = FetchType.LAZY)
    private List<PersonNote> notes;

    @OneToOne(targetEntity = ContactInfo.class)
    @JoinColumn(name = "contact_info_id", nullable = false)
    private ContactInfo contactInfo;

    @ManyToMany(targetEntity = Interest.class, fetch = FetchType.LAZY)
    @JoinTable(name = "m2m_person_interest")
    private List<Interest> interests;

    public Person() {}

    public Person(
        long id, String firstName, String middleName, String lastName,
        String document, Date birthDate, Date deathDate,
        List<PersonNote> notes, ContactInfo contactInfo,
        List<Interest> interests
    ) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.document = document;
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

    public String getDocument() {
        return document;
    }

    public Person setDocument(String document) {
        this.document = document;
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
