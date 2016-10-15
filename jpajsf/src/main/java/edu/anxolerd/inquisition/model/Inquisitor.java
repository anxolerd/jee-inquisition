package edu.anxolerd.inquisition.model;


import java.io.Serializable;

public class Inquisitor implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private Person person;
    private String codename;

    public Inquisitor() {}

    public Inquisitor(long id, Person person, String codename) {
        this.id = id;
        this.person = person;
        this.codename = codename;
    }

    public long getId() {
        return id;
    }

    public Inquisitor setId(long id) {
        this.id = id;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public Inquisitor setPerson(Person person) {
        this.person = person;
        return this;
    }

    public String getCodename() {
        return codename;
    }

    public Inquisitor setCodename(String codename) {
        this.codename = codename;
        return this;
    }
}
