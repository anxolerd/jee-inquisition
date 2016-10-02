package edu.anxolerd.inquisition.core.entities;


import java.util.Date;
import java.util.UUID;

public class Person {
    private UUID id;

    private String firstName;
    private String middleName;
    private String lastName;

    private Date birthDate;
    private Date deathDate;

    public Person() {
    }

    public Person(UUID id, String firstName, String middleName, String lastName, Date birthDate, Date deathDate) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public UUID getId() {
        return id;
    }

    public Person setId(UUID id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) return false;
        if (!getFirstName().equals(person.getFirstName())) return false;
        if (getMiddleName() != null ? !getMiddleName().equals(person.getMiddleName()) : person.getMiddleName() != null)
            return false;
        if (!getLastName().equals(person.getLastName())) return false;
        if (!getBirthDate().equals(person.getBirthDate())) return false;
        return getDeathDate() != null ? getDeathDate().equals(person.getDeathDate()) : person.getDeathDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + (getMiddleName() != null ? getMiddleName().hashCode() : 0);
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getBirthDate().hashCode();
        result = 31 * result + (getDeathDate() != null ? getDeathDate().hashCode() : 0);
        return result;
    }
}
