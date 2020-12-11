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
public class OutbiddingInfoBean implements ViewBean {

    private String id;
    private String successfulBidder_;
    private String successfulBidderText;
    private String applicantDate;
    private String dropDate;
    private double bidSecurity;
    private Short isReturn;
    private String isReturnText;
    private String informingPeople_;
    private String informingPeopleText;
    private String competitor;
    private String scoreRanking;
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


    public String getDropDate() {
        return this.dropDate;
    }


    public void setDropDate(String dropDate) {
        this.dropDate = dropDate;
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


    public String getCompetitor() {
        return this.competitor;
    }


    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }


    public String getScoreRanking() {
        return this.scoreRanking;
    }


    public void setScoreRanking(String scoreRanking) {
        this.scoreRanking = scoreRanking;
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
