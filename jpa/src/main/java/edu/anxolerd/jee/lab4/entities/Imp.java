package edu.anxolerd.jee.lab4.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "imp")
public class Imp {
    private long id;
    private String name;

    public Imp() {
        super();
    }

    public Imp(long id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
