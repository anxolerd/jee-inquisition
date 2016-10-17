package edu.anxolerd.inquisition.jpa.entities;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "investigation")
public class Investigation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Inquisitor.class)
    @JoinColumn(name = "assignee_inquisitor_id", nullable = false, referencedColumnName = "id")
    private Inquisitor assigneeInquisitor;
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("now()")
    @Column(name = "date_opened", nullable = false)
    private Date dateOpened;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_closed")
    private Date dateClosed;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "suspect_person_id", nullable = false, referencedColumnName = "id")
    private Person suspectPerson;
    @ManyToMany(targetEntity = Crime.class)
    @JoinTable(name = "m2m_investigation_crime")
    private List<Crime> crimes;

    @OneToMany(targetEntity = InvestigationNote.class, fetch = FetchType.LAZY, mappedBy = "investigation")
    private List<InvestigationNote> notes;
    @OneToMany(targetEntity = InvestigationLogRecord.class, fetch = FetchType.LAZY, mappedBy = "investigation")
    private List<InvestigationLogRecord> logs;

    @Column
    @Enumerated(EnumType.STRING)
    private InvestigationVerdict verdict;
    @Column
    private String sentence;

    public Investigation() {}

    public Investigation(
        long id, Inquisitor assigneeInquisitor, Date dateOpened, Date dateClosed,
        Person suspectPerson, List<Crime> crimes, List<InvestigationNote> notes,
        List<InvestigationLogRecord> logs, InvestigationVerdict verdict, String sentence
    ) {
        this.id = id;
        this.assigneeInquisitor = assigneeInquisitor;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.suspectPerson = suspectPerson;
        this.crimes = crimes;
        this.notes = notes;
        this.logs = logs;
        this.verdict = verdict;
        this.sentence = sentence;
    }

    public long getId() {
        return id;
    }

    public Investigation setId(long id) {
        this.id = id;
        return this;
    }

    public Inquisitor getAssigneeInquisitor() {
        return assigneeInquisitor;
    }

    public Investigation setAssigneeInquisitor(Inquisitor assigneeInquisitor) {
        this.assigneeInquisitor = assigneeInquisitor;
        return this;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public Investigation setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
        return this;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public Investigation setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
        return this;
    }

    public Person getSuspectPerson() {
        return suspectPerson;
    }

    public Investigation setSuspectPerson(Person suspectPerson) {
        this.suspectPerson = suspectPerson;
        return this;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public Investigation setCrimes(List<Crime> crimes) {
        this.crimes = crimes;
        return this;
    }

    public List<InvestigationNote> getNotes() {
        return notes;
    }

    public Investigation setNotes(List<InvestigationNote> notes) {
        this.notes = notes;
        return this;
    }

    public List<InvestigationLogRecord> getLogs() {
        return logs;
    }

    public Investigation setLogs(List<InvestigationLogRecord> logs) {
        this.logs = logs;
        return this;
    }

    public InvestigationVerdict getVerdict() {
        return verdict;
    }

    public Investigation setVerdict(InvestigationVerdict verdict) {
        this.verdict = verdict;
        return this;
    }

    public String getSentence() {
        return sentence;
    }

    public Investigation setSentence(String sentence) {
        this.sentence = sentence;
        return this;
    }
}
