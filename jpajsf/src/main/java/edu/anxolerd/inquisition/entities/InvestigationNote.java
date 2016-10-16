package edu.anxolerd.inquisition.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "investigation_note")
public class InvestigationNote implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(targetEntity = Inquisitor.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_inquisitor_id",  nullable = false)
    private Inquisitor authorInquisitor;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @ManyToOne(targetEntity = Investigation.class)
    @JoinColumn(name = "investigation_id", nullable = false)
    private Investigation investigation;

    public InvestigationNote() {}

    public InvestigationNote(long id, Inquisitor authorInquisitor, String title, String text, Investigation investigation) {
        this.id = id;
        this.authorInquisitor = authorInquisitor;
        this.title = title;
        this.text = text;
        this.investigation = investigation;
    }

    public long getId() {
        return id;
    }

    public InvestigationNote setId(long id) {
        this.id = id;
        return this;
    }

    public Inquisitor getAuthorInquisitor() {
        return authorInquisitor;
    }

    public InvestigationNote setAuthorInquisitor(Inquisitor authorInquisitor) {
        this.authorInquisitor = authorInquisitor;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InvestigationNote setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public InvestigationNote setText(String text) {
        this.text = text;
        return this;
    }

    public Investigation getInvestigation() {
        return investigation;
    }

    public InvestigationNote setInvestigation(Investigation investigation) {
        this.investigation = investigation;
        return this;
    }
}
