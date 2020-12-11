package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;

@Entity
@Table(name = "t_outbidding_info")
public class OutbiddingInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5253170096902925765L;

    private String id = UUID.randomUUID().toString();

    private Person successfulBidder;
    private Date applicantDate;
    private Date dropDate;
    private double bidSecurity;
    private Short isReturn;
    private Person informingPeople;
    private String competitor;
    private String scoreRanking;
    private String causeAnalysis;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public OutbiddingInfo clone() {
        OutbiddingInfo p = null;
        try {
            p = (OutbiddingInfo) super.clone();
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
    @Column(name = "drop_date", nullable = false, length = 29)
    public Date getDropDate() {
        return this.dropDate;
    }


    public void setDropDate(Date dropDate) {
        this.dropDate = dropDate;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "informing_people", nullable = false)
    public Person getInformingPeople() {
        return this.informingPeople;
    }


    public void setInformingPeople(Person informingPeople) {
        this.informingPeople = informingPeople;
    }

    @Column(name = "competitor", nullable = true, length = 36)
    public String getCompetitor() {
        return this.competitor;
    }


    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    @Column(name = "score_ranking", nullable = true, length = 50)
    public String getScoreRanking() {
        return this.scoreRanking;
    }


    public void setScoreRanking(String scoreRanking) {
        this.scoreRanking = scoreRanking;
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

    @Transient
    public List<Archive> getArchivesToDelete() {
        return this.archivesToDelete;
    }


    public void setArchivesToDelete(List<Archive> archivesToDelete) {
        this.archivesToDelete = archivesToDelete;
    }

}
