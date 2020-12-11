/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class AcceptanceInfoBean implements ViewBean {

    private String id;
    private String successfulBidder_;
    private String successfulBidderText;
    private String applicantDate;
    private String winningDate;
    private double winningAmount;
    private double bidSecurity;
    private Short isReturn;
    private String isReturnText;
    private String implementationDepartment;
    private String projectLeader_;
    private String projectLeaderText;
    private Short isSigned;
    private String isSignedText;
    private String signingBody;
    private String estimatedSigningTime;
    private Short isBidWinningNotice;
    private String isBidWinningNoticeText;
    private String informingPeople_;
    private String informingPeopleText;
    private Integer contractingMode;
    private String contractScope;
    private String causeAnalysis;

    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }


    public String getSuccessfulBidder_() {
        return this.successfulBidder_;
    }


    public void setSuccessfulBidder_(String successfulBidder_) {
        this.successfulBidder_ = successfulBidder_;
    }


    public String getSuccessfulBidderText() {
        return this.successfulBidderText;
    }


    public void setSuccessfulBidderText(String successfulBidderText) {
        this.successfulBidderText = successfulBidderText;
    }


    public String getApplicantDate() {
        return this.applicantDate;
    }


    public void setApplicantDate(String applicantDate) {
        this.applicantDate = applicantDate;
    }


    public String getWinningDate() {
        return this.winningDate;
    }


    public void setWinningDate(String winningDate) {
        this.winningDate = winningDate;
    }


    public double getWinningAmount() {
        return this.winningAmount;
    }


    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }


    public double getBidSecurity() {
        return this.bidSecurity;
    }


    public void setBidSecurity(double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }


    public Short getIsReturn() {
        return this.isReturn;
    }


    public void setIsReturn(Short isReturn) {
        this.isReturn = isReturn;
    }


    public String getIsReturnText() {
        return this.isReturnText;
    }


    public void setIsReturnText(String isReturnText) {
        this.isReturnText = isReturnText;
    }


    public String getImplementationDepartment() {
        return this.implementationDepartment;
    }


    public void setImplementationDepartment(String implementationDepartment) {
        this.implementationDepartment = implementationDepartment;
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


    public Short getIsSigned() {
        return this.isSigned;
    }


    public void setIsSigned(Short isSigned) {
        this.isSigned = isSigned;
    }


    public String getIsSignedText() {
        return this.isSignedText;
    }


    public void setIsSignedText(String isSignedText) {
        this.isSignedText = isSignedText;
    }


    public String getSigningBody() {
        return this.signingBody;
    }


    public void setSigningBody(String signingBody) {
        this.signingBody = signingBody;
    }


    public String getEstimatedSigningTime() {
        return this.estimatedSigningTime;
    }


    public void setEstimatedSigningTime(String estimatedSigningTime) {
        this.estimatedSigningTime = estimatedSigningTime;
    }


    public Short getIsBidWinningNotice() {
        return this.isBidWinningNotice;
    }


    public void setIsBidWinningNotice(Short isBidWinningNotice) {
        this.isBidWinningNotice = isBidWinningNotice;
    }


    public String getIsBidWinningNoticeText() {
        return this.isBidWinningNoticeText;
    }


    public void setIsBidWinningNoticeText(String isBidWinningNoticeText) {
        this.isBidWinningNoticeText = isBidWinningNoticeText;
    }


    public String getInformingPeople_() {
        return this.informingPeople_;
    }


    public void setInformingPeople_(String informingPeople_) {
        this.informingPeople_ = informingPeople_;
    }


    public String getInformingPeopleText() {
        return this.informingPeopleText;
    }


    public void setInformingPeopleText(String informingPeopleText) {
        this.informingPeopleText = informingPeopleText;
    }


    public Integer getContractingMode() {
        return this.contractingMode;
    }


    public void setContractingMode(Integer contractingMode) {
        this.contractingMode = contractingMode;
    }


    public String getContractScope() {
        return this.contractScope;
    }


    public void setContractScope(String contractScope) {
        this.contractScope = contractScope;
    }


    public String getCauseAnalysis() {
        return this.causeAnalysis;
    }


    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
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
