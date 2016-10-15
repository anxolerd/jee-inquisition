package edu.anxolerd.inquisition.model;


import java.io.Serializable;

public class InvestigationNote implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private Inquisitor authorInquisitor;
    private String title;
    private String text;

    public InvestigationNote() {}

    public InvestigationNote(long id, Inquisitor authorInquisitor, String title, String text) {
        this.id = id;
        this.authorInquisitor = authorInquisitor;
        this.title = title;
        this.text = text;
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
}
