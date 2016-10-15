package edu.anxolerd.inquisition.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Investigation implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private Inquisitor assigneeInquisitor;
    private Date dateOpened;
    private Date dateClosed;

    private Person suspectPerson;
    private List<Crime> crimes;

    private List<InvestigationNote> notes;
    private List<InvestigationLogRecord> logs;

    private InvestigationVerdict verdict;
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
