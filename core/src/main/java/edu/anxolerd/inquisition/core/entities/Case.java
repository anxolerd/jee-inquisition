package edu.anxolerd.inquisition.core.entities;


import java.util.Date;
import java.util.UUID;

public class Case {
    private UUID id;
    private UUID suspectPersonId;
    private Date dateOpened;

    private CaseStatus status;
    private String accuses;

    private Date dateClosed;
    private CaseVerdict verdict;
    private String sentence;

    public Case() { }

    public Case(UUID id, UUID suspectPersonId, Date dateOpened, CaseStatus status, String accuses, Date dateClosed, CaseVerdict verdict, String sentence) {
        this.id = id;
        this.suspectPersonId = suspectPersonId;
        this.dateOpened = dateOpened;
        this.status = status;
        this.accuses = accuses;
        this.dateClosed = dateClosed;
        this.verdict = verdict;
        this.sentence = sentence;
    }

    public UUID getId() {
        return id;
    }

    public Case setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getSuspectPersonId() {
        return suspectPersonId;
    }

    public Case setSuspectPersonId(UUID suspectPersonId) {
        this.suspectPersonId = suspectPersonId;
        return this;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public Case setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
        return this;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public Case setStatus(CaseStatus status) {
        this.status = status;
        return this;
    }

    public String getAccuses() {
        return accuses;
    }

    public Case setAccuses(String accuses) {
        this.accuses = accuses;
        return this;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public Case setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
        return this;
    }

    public CaseVerdict getVerdict() {
        return verdict;
    }

    public Case setVerdict(CaseVerdict verdict) {
        this.verdict = verdict;
        return this;
    }

    public String getSentence() {
        return sentence;
    }

    public Case setSentence(String sentence) {
        this.sentence = sentence;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Case aCase = (Case) o;

        if (getId() != null ? !getId().equals(aCase.getId()) : aCase.getId() != null) return false;
        if (getSuspectPersonId() != null ? !getSuspectPersonId().equals(aCase.getSuspectPersonId()) : aCase.getSuspectPersonId() != null)
            return false;
        if (getDateOpened() != null ? !getDateOpened().equals(aCase.getDateOpened()) : aCase.getDateOpened() != null)
            return false;
        if (getStatus() != aCase.getStatus()) return false;
        if (getAccuses() != null ? !getAccuses().equals(aCase.getAccuses()) : aCase.getAccuses() != null) return false;
        if (getDateClosed() != null ? !getDateClosed().equals(aCase.getDateClosed()) : aCase.getDateClosed() != null)
            return false;
        if (getVerdict() != aCase.getVerdict()) return false;
        return getSentence() != null ? getSentence().equals(aCase.getSentence()) : aCase.getSentence() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSuspectPersonId() != null ? getSuspectPersonId().hashCode() : 0);
        result = 31 * result + (getDateOpened() != null ? getDateOpened().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getAccuses() != null ? getAccuses().hashCode() : 0);
        result = 31 * result + (getDateClosed() != null ? getDateClosed().hashCode() : 0);
        result = 31 * result + (getVerdict() != null ? getVerdict().hashCode() : 0);
        result = 31 * result + (getSentence() != null ? getSentence().hashCode() : 0);
        return result;
    }
}
