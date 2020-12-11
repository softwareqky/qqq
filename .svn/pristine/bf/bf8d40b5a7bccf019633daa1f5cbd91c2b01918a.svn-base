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
@Table(name = "t_bidding_basic_info")
public class BiddingBasicInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 714888962135523796L;

    private String id = UUID.randomUUID().toString();

    private Person applicant;
    private Date applicantDate;
    private Person biddingLeader;
    private int biddingDocumentType;
    private int tenderDocumentType;
    private short isAutoBidding;
    private String biddingSupport;
    private Person projectLeader;
    private String competitor;
    private Date openingDate;
    private Double priceCeiling;
    private String priceUnit;
    private Double quotedPrice;
    private String bidEvaluationMethod;
    private Double referencePrice;
    private Date bidQaDate;
    private Double bidSecurity;
    private Integer paymentMethod;
    private Date siteInvestigationDate;
    private Person reader;
    private Double advanceAmount;
    private Integer advancePeriod;
    private Double profitMargin;
    private Person bidApplicant;
    private short bidResult;
    private Double winningAmount;
    private String siteFeeContent;
    private String tenderDocumentContent;
    private String biddingStrategy;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<Archive> archives = new HashSet<Archive>(0);

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public BiddingBasicInfo clone() {
        BiddingBasicInfo p = null;
        try {
            p = (BiddingBasicInfo) super.clone();
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
    @JoinColumn(name = "applicant", nullable = false)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applicant_date", nullable = false, length = 29)
    public Date getApplicantDate() {
        return this.applicantDate;
    }


    public void setApplicantDate(Date applicantDate) {
        this.applicantDate = applicantDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidding_leader", nullable = false)
    public Person getBiddingLeader() {
        return this.biddingLeader;
    }


    public void setBiddingLeader(Person biddingLeader) {
        this.biddingLeader = biddingLeader;
    }

    @Column(name = "bidding_document_type", nullable = false)
    public int getBiddingDocumentType() {
        return this.biddingDocumentType;
    }


    public void setBiddingDocumentType(int biddingDocumentType) {
        this.biddingDocumentType = biddingDocumentType;
    }

    @Column(name = "tender_document_type", nullable = false)
    public int getTenderDocumentType() {
        return this.tenderDocumentType;
    }


    public void setTenderDocumentType(int tenderDocumentType) {
        this.tenderDocumentType = tenderDocumentType;
    }

    @Column(name = "is_auto_bidding", nullable = false)
    public short getIsAutoBidding() {
        return this.isAutoBidding;
    }


    public void setIsAutoBidding(short isAutoBidding) {
        this.isAutoBidding = isAutoBidding;
    }

    @Column(name = "bidding_support", nullable = false, length = 36)
    public String getBiddingSupport() {
        return this.biddingSupport;
    }


    public void setBiddingSupport(String biddingSupport) {
        this.biddingSupport = biddingSupport;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_leader", nullable = false)
    public Person getProjectLeader() {
        return this.projectLeader;
    }


    public void setProjectLeader(Person projectLeader) {
        this.projectLeader = projectLeader;
    }

    @Column(name = "competitor", nullable = true, length = 36)
    public String getCompetitor() {
        return this.competitor;
    }


    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "opening_date", nullable = false, length = 29)
    public Date getOpeningDate() {
        return this.openingDate;
    }


    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    @Column(name = "price_ceiling", nullable = true)
    public Double getPriceCeiling() {
        return this.priceCeiling;
    }


    public void setPriceCeiling(Double priceCeiling) {
        this.priceCeiling = priceCeiling;
    }

    @Column(name = "price_unit", nullable = true, length = 36)
    public String getPriceUnit() {
        return this.priceUnit;
    }


    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    @Column(name = "quoted_price", nullable = true)
    public Double getQuotedPrice() {
        return this.quotedPrice;
    }


    public void setQuotedPrice(Double quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    @Column(name = "bid_evaluation_method", nullable = true, length = 1024)
    public String getBidEvaluationMethod() {
        return this.bidEvaluationMethod;
    }


    public void setBidEvaluationMethod(String bidEvaluationMethod) {
        this.bidEvaluationMethod = bidEvaluationMethod;
    }

    @Column(name = "reference_price", nullable = true)
    public Double getReferencePrice() {
        return this.referencePrice;
    }


    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bid_qa_date", nullable = true, length = 29)
    public Date getBidQaDate() {
        return this.bidQaDate;
    }


    public void setBidQaDate(Date bidQaDate) {
        this.bidQaDate = bidQaDate;
    }

    @Column(name = "bid_security", nullable = true)
    public Double getBidSecurity() {
        return this.bidSecurity;
    }


    public void setBidSecurity(Double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }

    @Column(name = "payment_method", nullable = true)
    public Integer getPaymentMethod() {
        return this.paymentMethod;
    }


    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "site_investigation_date", nullable = true, length = 29)
    public Date getSiteInvestigationDate() {
        return this.siteInvestigationDate;
    }


    public void setSiteInvestigationDate(Date siteInvestigationDate) {
        this.siteInvestigationDate = siteInvestigationDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader", nullable = true)
    public Person getReader() {
        return this.reader;
    }


    public void setReader(Person reader) {
        this.reader = reader;
    }

    @Column(name = "advance_amount", nullable = true)
    public Double getAdvanceAmount() {
        return this.advanceAmount;
    }


    public void setAdvanceAmount(Double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    @Column(name = "advance_period", nullable = true)
    public Integer getAdvancePeriod() {
        return this.advancePeriod;
    }


    public void setAdvancePeriod(Integer advancePeriod) {
        this.advancePeriod = advancePeriod;
    }

    @Column(name = "profit_margin", nullable = true)
    public Double getProfitMargin() {
        return this.profitMargin;
    }


    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_applicant", nullable = true)
    public Person getBidApplicant() {
        return this.bidApplicant;
    }


    public void setBidApplicant(Person bidApplicant) {
        this.bidApplicant = bidApplicant;
    }

    @Column(name = "bid_result", nullable = false)
    public short getBidResult() {
        return this.bidResult;
    }


    public void setBidResult(short bidResult) {
        this.bidResult = bidResult;
    }

    @Column(name = "winning_amount", nullable = true)
    public Double getWinningAmount() {
        return this.winningAmount;
    }


    public void setWinningAmount(Double winningAmount) {
        this.winningAmount = winningAmount;
    }

    @Column(name = "site_fee_content", nullable = true, length = 1024)
    public String getSiteFeeContent() {
        return this.siteFeeContent;
    }


    public void setSiteFeeContent(String siteFeeContent) {
        this.siteFeeContent = siteFeeContent;
    }

    @Column(name = "tender_document_content", nullable = true, length = 1024)
    public String getTenderDocumentContent() {
        return this.tenderDocumentContent;
    }


    public void setTenderDocumentContent(String tenderDocumentContent) {
        this.tenderDocumentContent = tenderDocumentContent;
    }

    @Column(name = "bidding_strategy", nullable = true, length = 1024)
    public String getBiddingStrategy() {
        return this.biddingStrategy;
    }


    public void setBiddingStrategy(String biddingStrategy) {
        this.biddingStrategy = biddingStrategy;
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
    @JoinTable(name = "t_bidding_basic_info_attachment",
            joinColumns = {@JoinColumn(name = "bidding_basic_info_id", nullable = false,
                    updatable = false)},
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
