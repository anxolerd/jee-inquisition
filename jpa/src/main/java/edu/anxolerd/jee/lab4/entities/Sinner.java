package edu.anxolerd.jee.lab4.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sinner")
public class Sinner {
    private long id;
    private String name;
    private Passport passport;
    private Set<Sin> sins = new HashSet<>(0);

    public Sinner() {
        super();
    }

    public Sinner(long id, String name, Passport passport) {
        this();
        this.id = id;
        this.name = name;
        this.passport = passport;
    }

    public Sinner(long id, String name, Passport passport, Set<Sin> sins) {
        this(id, name, passport);
        this.sins = sins;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "m2m_sinners_sins",
        joinColumns = @JoinColumn(name = "sinner_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "sin_id", referencedColumnName = "id")
    )
    public Set<Sin> getSins() {
        return sins;
    }

    public void setSins(Set<Sin> sins) {
        this.sins = sins;
    }
}
