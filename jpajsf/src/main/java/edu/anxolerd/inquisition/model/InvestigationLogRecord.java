package edu.anxolerd.inquisition.model;


import java.io.Serializable;
import java.util.Date;

public class InvestigationLogRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private Inquisitor operator;
    private Date dateCreated;
    private String action;

    public InvestigationLogRecord() {}

    public InvestigationLogRecord(long id, Inquisitor operator, Date dateCreated, String action) {
        this.id = id;
        this.operator = operator;
        this.dateCreated = dateCreated;
        this.action = action;
    }

    public long getId() {
        return id;
    }

    public InvestigationLogRecord setId(long id) {
        this.id = id;
        return this;
    }

    public Inquisitor getOperator() {
        return operator;
    }

    public InvestigationLogRecord setOperator(Inquisitor operator) {
        this.operator = operator;
        return this;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public InvestigationLogRecord setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getAction() {
        return action;
    }

    public InvestigationLogRecord setAction(String action) {
        this.action = action;
        return this;
    }
}
