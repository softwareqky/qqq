package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class PlanChangeBean implements ViewBean {

    private String id;

    private String plan_;
    private String planText;
    private String project_;
    private String projectText;
    private String planName;
    private String planVersion;
    private String virtualOrg_;
    private String virtualOrgText;
    private String creator_;
    private String creatorText;
    private String cDatetime;
    private String remark;
    private String remarkHtmlEscaped;
    private String applicantName;

    private String planCalendarId_;
    private String planCalendarIdText;
    private String applicant_;
    private String applicantText;
    private String changeReason;
    private String changeReasonHtmlEscaped;
    private String changeRemark;
    private String flowStartDate;
    private String flowEndDate;
    private Integer flowStatus;
    private String flowStatusText;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_() {
        return this.plan_;
    }

    public void setPlan_(String plan_) {
        this.plan_ = plan_;
    }

    public String getPlanText() {
        return this.planText;
    }

    public void setPlanText(String planText) {
        this.planText = planText;
    }

    public String getProject_() {
        return this.project_;
    }

    public void setProject_(String project_) {
        this.project_ = project_;
    }

    public String getProjectText() {
        return this.projectText;
    }

    public void setProjectText(String projectText) {
        this.projectText = projectText;
    }

    public String getPlanName() {
        return this.planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanVersion() {
        return this.planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanCalendarId_() {
        return this.planCalendarId_;
    }

    public void setPlanCalendarId_(String planCalendarId_) {
        this.planCalendarId_ = planCalendarId_;
    }

    public String getPlanCalendarIdText() {
        return this.planCalendarIdText;
    }

    public void setPlanCalendarIdText(String planCalendarIdText) {
        this.planCalendarIdText = planCalendarIdText;
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

    public String getChangeReason() {
        return this.changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getChangeRemark() {
        return this.changeRemark;
    }

    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    public String getFlowStartDate() {
        return flowStartDate;
    }

    public void setFlowStartDate(String flowStartDate) {
        this.flowStartDate = flowStartDate;
    }

    public String getFlowEndDate() {
        return flowEndDate;
    }

    public void setFlowEndDate(String flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowStatusText() {
        return flowStatusText;
    }

    public void setFlowStatusText(String flowStatusText) {
        this.flowStatusText = flowStatusText;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getRemarkHtmlEscaped() {
        return this.remarkHtmlEscaped;
    }

    public void setRemarkHtmlEscaped(String remarkHtmlEscaped) {
        this.remarkHtmlEscaped = remarkHtmlEscaped;
    }

    public String getChangeReasonHtmlEscaped() {
        return this.changeReasonHtmlEscaped;
    }

    public void setChangeReasonHtmlEscaped(String changeReasonHtmlEscaped) {
        this.changeReasonHtmlEscaped = changeReasonHtmlEscaped;
    }
    
    public String getVirtualOrg_() {
        return this.virtualOrg_;
    }

    public void setVirtualOrg_(String virtualOrg_) {
        this.virtualOrg_ = virtualOrg_;
    }

    public String getVirtualOrgText() {
        return this.virtualOrgText;
    }

    public void setVirtualOrgText(String virtualOrgText) {
        this.virtualOrgText = virtualOrgText;
    }
    
//    public String getCreator() {
//        return this.creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
    
    public String getCreator_() {
        return this.creator_;
    }

    
    public void setCreator_(String creator_) {
        this.creator_ = creator_;
    }

    
    public String getCreatorText() {
        return this.creatorText;
    }

    
    public void setCreatorText(String creatorText) {
        this.creatorText = creatorText;
    }
    
    public String getcDatetime() {
        return this.cDatetime;
    }

    public void setcDatetime(String cDatetime) {
        this.cDatetime = cDatetime;
    }

}
