package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


public class BiddingBasicInfoBean implements ViewBean {

    private String id;
    private String applicant_;
    private String applicantText;
    private String applicantDate;
    private String biddingLeader_;
    private String biddingLeaderText;
    private int biddingDocumentType;
    private int tenderDocumentType;
    private short isAutoBidding;
    private String isAutoBiddingText;
    private String biddingSupport;
    private String projectLeader_;
    private String projectLeaderText;
    private String competitor;
    private String openingDate;
    private Double priceCeiling;
    private String priceUnit;
    private Double quotedPrice;
    private String bidEvaluationMethod;
    private Double referencePrice;
    private String bidQaDate;
    private Double bidSecurity;
    private Integer paymentMethod;
    private String siteInvestigationDate;
    private String reader_;
    private String readerText;
    private Double advanceAmount;
    private Integer advancePeriod;
    private Double profitMargin;
    private String bidApplicant_;
    private String bidApplicantText;
    private short bidResult;
    private String bidResultText;
    private Double winningAmount;
    private String siteFeeContent;
    private String tenderDocumentContent;
    private String biddingStrategy;

    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }


    public String getApplicant_() {
        return this.applicant_;
    }


    public void setApplicant_(String applicant_) {
        this.applicant_ = applicant_;
    }


    public String getApplicantText() {
        return this.applicantText;
    }


    public void setApplicantText(String applicantText) {
        this.applicantText = applicantText;
    }


    public String getApplicantDate() {
        return this.applicantDate;
    }


    public void setApplicantDate(String applicantDate) {
        this.applicantDate = applicantDate;
    }


    public String getBiddingLeader_() {
        return this.biddingLeader_;
    }


    public void setBiddingLeader_(String biddingLeader_) {
        this.biddingLeader_ = biddingLeader_;
    }


    public String getBiddingLeaderText() {
        return this.biddingLeaderText;
    }


    public void setBiddingLeaderText(String biddingLeaderText) {
        this.biddingLeaderText = biddingLeaderText;
    }


    public int getBiddingDocumentType() {
        return this.biddingDocumentType;
    }


    public void setBiddingDocumentType(int biddingDocumentType) {
        this.biddingDocumentType = biddingDocumentType;
    }


    public int getTenderDocumentType() {
        return this.tenderDocumentType;
    }


    public void setTenderDocumentType(int tenderDocumentType) {
        this.tenderDocumentType = tenderDocumentType;
    }


    public short getIsAutoBidding() {
        return this.isAutoBidding;
    }


    public void setIsAutoBidding(short isAutoBidding) {
        this.isAutoBidding = isAutoBidding;
    }


    public String getIsAutoBiddingText() {
        return this.isAutoBiddingText;
    }


    public void setIsAutoBiddingText(String isAutoBiddingText) {
        this.isAutoBiddingText = isAutoBiddingText;
    }


    public String getBiddingSupport() {
        return this.biddingSupport;
    }


    public void setBiddingSupport(String biddingSupport) {
        this.biddingSupport = biddingSupport;
    }


    public String getProjectLeader_() {
        return this.projectLeader_;
    }


    public void setProjectLeader_(String projectLeader_) {
        this.projectLeader_ = projectLeader_;
    }


    public String getProjectLeaderText() {
        return this.projectLeaderText;
    }


    public void setProjectLeaderText(String projectLeaderText) {
        this.projectLeaderText = projectLeaderText;
    }


    public String getCompetitor() {
        return this.competitor;
    }


    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }


    public String getOpeningDate() {
        return this.openingDate;
    }


    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }


    public Double getPriceCeiling() {
        return this.priceCeiling;
    }


    public void setPriceCeiling(Double priceCeiling) {
        this.priceCeiling = priceCeiling;
    }


    public String getPriceUnit() {
        return this.priceUnit;
    }


    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }


    public Double getQuotedPrice() {
        return this.quotedPrice;
    }


    public void setQuotedPrice(Double quotedPrice) {
        this.quotedPrice = quotedPrice;
    }


    public String getBidEvaluationMethod() {
        return this.bidEvaluationMethod;
    }


    public void setBidEvaluationMethod(String bidEvaluationMethod) {
        this.bidEvaluationMethod = bidEvaluationMethod;
    }


    public Double getReferencePrice() {
        return this.referencePrice;
    }


    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }


    public String getBidQaDate() {
        return this.bidQaDate;
    }


    public void setBidQaDate(String bidQaDate) {
        this.bidQaDate = bidQaDate;
    }


    public Double getBidSecurity() {
        return this.bidSecurity;
    }


    public void setBidSecurity(Double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }


    public Integer getPaymentMethod() {
        return this.paymentMethod;
    }


    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getSiteInvestigationDate() {
        return this.siteInvestigationDate;
    }


    public void setSiteInvestigationDate(String siteInvestigationDate) {
        this.siteInvestigationDate = siteInvestigationDate;
    }


    public String getReader_() {
        return this.reader_;
    }


    public void setReader_(String reader_) {
        this.reader_ = reader_;
    }


    public String getReaderText() {
        return this.readerText;
    }


    public void setReaderText(String readerText) {
        this.readerText = readerText;
    }


    public Double getAdvanceAmount() {
        return this.advanceAmount;
    }


    public void setAdvanceAmount(Double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }


    public Integer getAdvancePeriod() {
        return this.advancePeriod;
    }


    public void setAdvancePeriod(Integer advancePeriod) {
        this.advancePeriod = advancePeriod;
    }


    public Double getProfitMargin() {
        return this.profitMargin;
    }


    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }


    public String getBidApplicant_() {
        return this.bidApplicant_;
    }


    public void setBidApplicant_(String bidApplicant_) {
        this.bidApplicant_ = bidApplicant_;
    }


    public String getBidApplicantText() {
        return this.bidApplicantText;
    }


    public void setBidApplicantText(String bidApplicantText) {
        this.bidApplicantText = bidApplicantText;
    }


    public short getBidResult() {
        return this.bidResult;
    }


    public void setBidResult(short bidResult) {
        this.bidResult = bidResult;
    }


    public String getBidResultText() {
        return this.bidResultText;
    }


    public void setBidResultText(String bidResultText) {
        this.bidResultText = bidResultText;
    }


    public Double getWinningAmount() {
        return this.winningAmount;
    }


    public void setWinningAmount(Double winningAmount) {
        this.winningAmount = winningAmount;
    }


    public String getSiteFeeContent() {
        return this.siteFeeContent;
    }


    public void setSiteFeeContent(String siteFeeContent) {
        this.siteFeeContent = siteFeeContent;
    }


    public String getTenderDocumentContent() {
        return this.tenderDocumentContent;
    }


    public void setTenderDocumentContent(String tenderDocumentContent) {
        this.tenderDocumentContent = tenderDocumentContent;
    }


    public String getBiddingStrategy() {
        return this.biddingStrategy;
    }


    public void setBiddingStrategy(String biddingStrategy) {
        this.biddingStrategy = biddingStrategy;
    }


    public void setId(String id) {
        this.id = id;
    }

    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }


    public void setArchivesList(List<ArchiveBean> archivesList) {
        this.archivesList = archivesList;
    }


    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }


    public void setArchivesReservedList(List<String> archivesReservedList) {
        this.archivesReservedList = archivesReservedList;
    }

}
