package edu.anxolerd.inquisition.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "interest")
public class Interest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @Column(name = "sin_rate", nullable = false)
    private int sinRate;

    @ManyToMany(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinTable(name = "m2m_person_interest")
    private List<Person> persons;

    public Interest() {}

    public Interest(long id, String title, String description, int sinRate, List<Person> persons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sinRate = sinRate;
        this.persons = persons;
    }

    public long getId() {
        return id;
    }

    public Interest setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Interest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Interest setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getSinRate() {
        return sinRate;
    }

    public Interest setSinRate(int sinRate) {
        this.sinRate = sinRate;
        return this;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Interest setPersons(List<Person> persons) {
        this.persons = persons;
        return this;
    }
}
