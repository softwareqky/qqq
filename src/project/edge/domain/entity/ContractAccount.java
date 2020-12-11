package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_contract_account")
public class ContractAccount implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4319847475549774000L;

    private String id = UUID.randomUUID().toString();

    private String contractNo;
    private String contractName;
    private double finalContractAmount;
    private Date signingTime;
    private Project project;
    private Double accumulatedCollectionAmount;
    private Double cumulativeInvoiced;
    private Double accumulatedInvoiceNotIssued;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;


    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "contract_no", nullable = false, length = 36)
    public String getContractNo() {
        return this.contractNo;
    }


    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Column(name = "contract_name", nullable = false, length = 50)
    public String getContractName() {
        return this.contractName;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Column(name = "final_contract_amount", nullable = false)
    public double getFinalContractAmount() {
        return this.finalContractAmount;
    }


    public void setFinalContractAmount(double finalContractAmount) {
        this.finalContractAmount = finalContractAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signing_time", nullable = false, length = 29)
    public Date getSigningTime() {
        return this.signingTime;
    }


    public void setSigningTime(Date signingTime) {
        this.signingTime = signingTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "accumulated_collection_amount", nullable = true)
    public Double getAccumulatedCollectionAmount() {
        return this.accumulatedCollectionAmount;
    }


    public void setAccumulatedCollectionAmount(Double accumulatedCollectionAmount) {
        this.accumulatedCollectionAmount = accumulatedCollectionAmount;
    }

    @Column(name = "cumulative_invoiced", nullable = true)
    public Double getCumulativeInvoiced() {
        return this.cumulativeInvoiced;
    }


    public void setCumulativeInvoiced(Double cumulativeInvoiced) {
        this.cumulativeInvoiced = cumulativeInvoiced;
    }

    @Column(name = "accumulated_invoice_not_issued", nullable = true)
    public Double getAccumulatedInvoiceNotIssued() {
        return this.accumulatedInvoiceNotIssued;
    }


    public void setAccumulatedInvoiceNotIssued(Double accumulatedInvoiceNotIssued) {
        this.accumulatedInvoiceNotIssued = accumulatedInvoiceNotIssued;
    }


    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }


    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = true, length = 50)
    public String getModifier() {
        return this.modifier;
    }


    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }


}
