package edu.anxolerd.inquisition.model;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "investigation_record")
public class InvestigationLogRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(targetEntity = Inquisitor.class)
    @JoinColumn(name = "operator_id", nullable = false)
    private Inquisitor operator;

    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("now()")
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;
    @Column(nullable = false)
    private String action;
    @ManyToOne(targetEntity = Investigation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "investigation_id", nullable = false)
    private Investigation investigation;

    public InvestigationLogRecord() {}

    public InvestigationLogRecord(long id, Inquisitor operator, Date dateCreated, String action, Investigation investigation) {
        this.id = id;
        this.operator = operator;
        this.dateCreated = dateCreated;
        this.action = action;
        this.investigation = investigation;
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

    public Investigation getInvestigation() {
        return investigation;
    }

    public InvestigationLogRecord setInvestigation(Investigation investigation) {
        this.investigation = investigation;
        return this;
    }
}
