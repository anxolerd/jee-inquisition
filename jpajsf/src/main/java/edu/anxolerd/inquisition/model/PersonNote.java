package edu.anxolerd.inquisition.model;

import java.io.Serializable;


public class PersonNote implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private Inquisitor authorInquisitor;
    private Person person;
    private String text;

    public PersonNote() {}

    public PersonNote(long id, Inquisitor authorInquisitor, Person person, String text) {
        this.id = id;
        this.authorInquisitor = authorInquisitor;
        this.person = person;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public PersonNote setId(long id) {
        this.id = id;
        return this;
    }

    public Inquisitor getAuthorInquisitor() {
        return authorInquisitor;
    }

    public PersonNote setAuthorInquisitor(Inquisitor authorInquisitor) {
        this.authorInquisitor = authorInquisitor;
        return this;
    }

    public Person getPerson() {
        return person;
    }

    public PersonNote setPerson(Person person) {
        this.person = person;
        return this;
    }

    public String getText() {
        return text;
    }

    public PersonNote setText(String text) {
        this.text = text;
        return this;
    }
}
