package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;


public class FlowableLocalHistoryBean implements ViewBean {

    private String id;

    private String startUserId;
    private String startUserName;

    private String correlateProjectId;
    private String correlateProjectName;

    private String correlateDataId;
    private String correlateDataName;

    private String businessCode;
    private String businessCodeUrl;
    private String businessCodeName;
    private String businessEntity;

    private String flowId;
    private String flowName;
    private String flowStatusText;
    private Date initiateDatetime;
    private String initiateDatetimeText;
    private String participantAuditIdList;
    private String participantAuditNameList;
    private String pendingMatters;
    private String submitDatetimeText;

    private String auditTaskId;
    private Integer auditResult;
    private String auditRemark;
    
    
    public String getBusinessEntity() {
        return this.businessEntity;
    }

    
    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }



    public Integer getAuditResult() {
        return this.auditResult;
    }


    
    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }


    
    public String getAuditRemark() {
        return this.auditRemark;
    }


    
    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }


    public String getAuditTaskId() {
        return this.auditTaskId;
    }


    public void setAuditTaskId(String auditTaskId) {
        this.auditTaskId = auditTaskId;
    }

    public String getBusinessCodeUrl() {
        return this.businessCodeUrl;
    }

    public void setBusinessCodeUrl(String businessCodeUrl) {
        this.businessCodeUrl = businessCodeUrl;
    }


    public String getFlowId() {
        return this.flowId;
    }


    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getStartUserId() {
        return this.startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }



    public String getStartUserName() {
        return this.startUserName;
    }



    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }


    public String getSubmitDatetimeText() {
        return this.submitDatetimeText;
    }


    public void setSubmitDatetimeText(String submitDatetimeText) {
        this.submitDatetimeText = submitDatetimeText;
    }

    public String getPendingMatters() {
        return this.pendingMatters;
    }

    public void setPendingMatters(String pendingMatters) {
        this.pendingMatters = pendingMatters;
    }

    public String getParticipantAuditIdList() {
        return this.participantAuditIdList;
    }



    public void setParticipantAuditIdList(String participantAuditIdList) {
        this.participantAuditIdList = participantAuditIdList;
    }


    public String getCorrelateProjectId() {
        return this.correlateProjectId;
    }


    public void setCorrelateProjectId(String correlateProjectId) {
        this.correlateProjectId = correlateProjectId;
    }



    public String getCorrelateDataId() {
        return this.correlateDataId;
    }



    public void setCorrelateDataId(String correlateDataId) {
        this.correlateDataId = correlateDataId;
    }



    public String getCorrelateDataName() {
        return this.correlateDataName;
    }



    public void setCorrelateDataName(String correlateDataName) {
        this.correlateDataName = correlateDataName;
    }


    @Override
    public String getId() {
        return this.id;
    }


    public String getCorrelateProjectName() {
        return this.correlateProjectName;
    }


    public void setCorrelateProjectName(String correlateProjectName) {
        this.correlateProjectName = correlateProjectName;
    }


    public String getBusinessCode() {
        return this.businessCode;
    }


    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }



    public String getBusinessCodeName() {
        return this.businessCodeName;
    }



    public void setBusinessCodeName(String businessCodeName) {
        this.businessCodeName = businessCodeName;
    }


    public String getFlowName() {
        return this.flowName;
    }


    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowStatusText() {
        return this.flowStatusText;
    }


    public void setFlowStatusText(String flowStatusText) {
        this.flowStatusText = flowStatusText;
    }


    public Date getInitiateDatetime() {
        return this.initiateDatetime;
    }


    public void setInitiateDatetime(Date initiateDatetime) {
        this.initiateDatetime = initiateDatetime;
    }


    public String getInitiateDatetimeText() {
        return this.initiateDatetimeText;
    }


    public void setInitiateDatetimeText(String initiateDatetimeText) {
        this.initiateDatetimeText = initiateDatetimeText;
    }


    public String getParticipantAuditNameList() {
        return this.participantAuditNameList;
    }


    public void setParticipantAuditNameList(String participantAuditNameList) {
        this.participantAuditNameList = participantAuditNameList;
    }


    public void setId(String id) {
        this.id = id;
    }
}
