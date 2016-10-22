package edu.anxolerd.jee.lab4.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "sentence")
public class Sentence {
    private long id;
    private String description;
    private Sin sin;
    private Imp imp;

    public Sentence() {
        super();
    }

    public Sentence(long id, String description) {
        this();
        this.id = id;
        this.description = description;
    }

    public Sentence(long id, String description, Sin sin) {
        this(id, description);
        this.sin = sin;
    }

    public Sentence(long id, String description, Sin sin, Imp imp) {
        this(id, description, sin);
        this.imp = imp;
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

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sin_id")
    public Sin getSin() {
        return sin;
    }

    public void setSin(Sin sin) {
        this.sin = sin;
    }

    @OneToOne
    @JoinColumn(name = "imp_id")
    public Imp getImp() {
        return imp;
    }

    public void setImp(Imp imp) {
        this.imp = imp;
    }
}
