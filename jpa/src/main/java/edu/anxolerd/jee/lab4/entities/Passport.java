package edu.anxolerd.jee.lab4.entities;


import edu.anxolerd.jee.lab4.util.GsonExclude;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "passport")
public class Passport {
    private long id;
    private String no;
    @GsonExclude
    private Sinner sinner;

    public Passport() {
        super();
    }

    public Passport(long id, String no) {
        this();
        this.id = id;
        this.no = no;
    }

    public Passport(long id, String no, Sinner sinner) {
        this(id, no);
        this.sinner = sinner;
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

    @Column(name = "no", nullable = false)
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @OneToOne(mappedBy = "passport")
    public Sinner getSinner() {
        return sinner;
    }

    public void setSinner(Sinner sinner) {
        this.sinner = sinner;
    }
}
