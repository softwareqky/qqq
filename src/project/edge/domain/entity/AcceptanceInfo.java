package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "t_acceptance_info")
public class AcceptanceInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7220897694103509974L;

    private String id = UUID.randomUUID().toString();

    private Person successfulBidder;
    private Date applicantDate;
    private Date winningDate;
    private double winningAmount;
    private double bidSecurity;
    private Short isReturn;
    private String implementationDepartment;
    private Person projectLeader;
    private Short isSigned;
    private String signingBody;
    private Date estimatedSigningTime;
    private Short isBidWinningNotice;
    private Person informingPeople;
    private Integer contractingMode;
    private String contractScope;
    private String causeAnalysis;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<Archive> archives = new HashSet<Archive>(0);

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public AcceptanceInfo clone() {
        AcceptanceInfo p = null;
        try {
            p = (AcceptanceInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "successful_bidder", nullable = false)
    public Person getSuccessfulBidder() {
        return this.successfulBidder;
    }


    public void setSuccessfulBidder(Person successfulBidder) {
        this.successfulBidder = successfulBidder;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applicant_date", nullable = false, length = 29)
    public Date getApplicantDate() {
        return this.applicantDate;
    }


    public void setApplicantDate(Date applicantDate) {
        this.applicantDate = applicantDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "winning_date", nullable = false, length = 29)
    public Date getWinningDate() {
        return this.winningDate;
    }


    public void setWinningDate(Date winningDate) {
        this.winningDate = winningDate;
    }

    @Column(name = "winning_amount", nullable = false)
    public double getWinningAmount() {
        return this.winningAmount;
    }


    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }

    @Column(name = "bid_security", nullable = false)
    public double getBidSecurity() {
        return this.bidSecurity;
    }


    public void setBidSecurity(double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }

    @Column(name = "is_return", nullable = true)
    public Short getIsReturn() {
        return this.isReturn;
    }


    public void setIsReturn(Short isReturn) {
        this.isReturn = isReturn;
    }

    @Column(name = "implementation_department", nullable = true, length = 36)
    public String getImplementationDepartment() {
        return this.implementationDepartment;
    }


    public void setImplementationDepartment(String implementationDepartment) {
        this.implementationDepartment = implementationDepartment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_leader", nullable = true)
    public Person getProjectLeader() {
        return this.projectLeader;
    }


    public void setProjectLeader(Person projectLeader) {
        this.projectLeader = projectLeader;
    }

    @Column(name = "is_signed", nullable = true)
    public Short getIsSigned() {
        return this.isSigned;
    }


    public void setIsSigned(Short isSigned) {
        this.isSigned = isSigned;
    }

    @Column(name = "signing_body", nullable = true, length = 50)
    public String getSigningBody() {
        return this.signingBody;
    }


    public void setSigningBody(String signingBody) {
        this.signingBody = signingBody;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "estimated_signing_time", nullable = true, length = 29)
    public Date getEstimatedSigningTime() {
        return this.estimatedSigningTime;
    }


    public void setEstimatedSigningTime(Date estimatedSigningTime) {
        this.estimatedSigningTime = estimatedSigningTime;
    }

    @Column(name = "is_bid_winning_notice", nullable = true)
    public Short getIsBidWinningNotice() {
        return this.isBidWinningNotice;
    }


    public void setIsBidWinningNotice(Short isBidWinningNotice) {
        this.isBidWinningNotice = isBidWinningNotice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "informing_people", nullable = true)
    public Person getInformingPeople() {
        return this.informingPeople;
    }


    public void setInformingPeople(Person informingPeople) {
        this.informingPeople = informingPeople;
    }

    @Column(name = "contracting_mode", nullable = true)
    public Integer getContractingMode() {
        return this.contractingMode;
    }


    public void setContractingMode(Integer contractingMode) {
        this.contractingMode = contractingMode;
    }

    @Column(name = "contract_scope", nullable = true, length = 50)
    public String getContractScope() {
        return this.contractScope;
    }


    public void setContractScope(String contractScope) {
        this.contractScope = contractScope;
    }

    @Column(name = "cause_analysis", nullable = true, length = 1024)
    public String getCauseAnalysis() {
        return this.causeAnalysis;
    }


    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_acceptance_info_attachment",
            joinColumns = {
                    @JoinColumn(name = "acceptance_info_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    public Set<Archive> getArchives() {
        return this.archives;
    }


    public void setArchives(Set<Archive> archives) {
        this.archives = archives;
    }

    @Transient
    public List<Archive> getArchivesToDelete() {
        return this.archivesToDelete;
    }


    public void setArchivesToDelete(List<Archive> archivesToDelete) {
        this.archivesToDelete = archivesToDelete;
    }

}
