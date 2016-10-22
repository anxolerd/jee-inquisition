package edu.anxolerd.jee.lab4.entities;

import edu.anxolerd.jee.lab4.util.GsonExclude;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sin")
public class Sin {

    private long id;
    private String name;
    private String description;
    private Set<Sentence> sentences = new HashSet<>(0);
    @GsonExclude
    private Set<Sinner> sinners = new HashSet<>(0);

    public Sin() {
        super();
    }

    public Sin(long id, String name, String description) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Sin(long id, String name, String description, Set<Sentence> sentences) {
        this(id, name, description);
        this.sentences = sentences;
    }

    public Sin(long id, String name, String description, Set<Sentence> sentences, Set<Sinner> sinners) {
        this(id, name, description, sentences);
        this.sinners = sinners;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", nullable = false, unique = true)
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

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sin")
    public Set<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(Set<Sentence> sentences) {
        this.sentences = sentences;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "sins")
    public Set<Sinner> getSinners() {
        return sinners;
    }

    public void setSinners(Set<Sinner> sinners) {
        this.sinners = sinners;
    }
}
