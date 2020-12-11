package project.edge.domain.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import garage.origin.domain.view.ViewBean;

public class PurchaseOrderBean implements ViewBean {

    private String id;

    private String purchaseOrderNo;
    private String project_;
    private String projectText;
    private String projectNum;
    private String applicant_;
    private String applicantText;
    private String applicantContact;
    private String purchaseReason;
    private String receivableCompany;
    private String paymentType_;
    private String paymentTypeText;
    private Double totalAmount;
    private int flowStatus;
    private String flowStatusText;
    private String purchaseType_;
    private String purchaseTypeText;
    private String purchaseKind_;
    private String purchaseKindText;
    private String auditApplicant_;
    private String auditApplicantText;
    private String extId;
    private String purchaseName;
    private String virtualOrg_;
    private String virtualOrgText;
    private String inputApplicant_;
    private String inputApplicantText;
    private String recordTime;
    private String remark;
    private String cDatetime;
    
    private String paymentTime;
    private String paymentRate;
    private Double paymentAmount;
    private String totalPaymentRate;
    private Double totalPaymentAmount;
    
    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseOrderNo() {
        return this.purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getProjectNum() {
        return this.projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
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

    public String getApplicantContact() {
        return this.applicantContact;
    }

    public void setApplicantContact(String applicantContact) {
        this.applicantContact = applicantContact;
    }

    public String getPurchaseReason() {
        return this.purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    public String getReceivableCompany() {
        return this.receivableCompany;
    }

    public void setReceivableCompany(String receivableCompany) {
        this.receivableCompany = receivableCompany;
    }

    public String getPaymentType_() {
        return this.paymentType_;
    }

    public void setPaymentType_(String paymentType_) {
        this.paymentType_ = paymentType_;
    }

    public String getPaymentTypeText() {
        return this.paymentTypeText;
    }

    public void setPaymentTypeText(String paymentTypeText) {
        this.paymentTypeText = paymentTypeText;
    }
    
    public Double getTotalAmount() {
        return this.totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public int getFlowStatus() {
    	return this.flowStatus;
    }
    
    public void setFlowStatus(int flowStatus) {
    	this.flowStatus = flowStatus;
    }
    
    public String getFlowStatusText() {
    	return this.flowStatusText;
    }
    
    public void setFlowStatusText(String flowStatusText) {
    	this.flowStatusText = flowStatusText;
    }
    
    public String getPurchaseType_() {
    	return this.purchaseType_;
    }
    
    public void setPurchaseType_(String purchaseType_) {
    	this.purchaseType_ = purchaseType_;
    }
    
    public String getPurchaseTypeText() {
    	return this.purchaseTypeText;
    }
    
    public void setPurchaseTypeText(String purchaseTypeText) {
    	this.purchaseTypeText = purchaseTypeText;
    }

    
    public String getPurchaseKind_() {
        return this.purchaseKind_;
    }


    public void setPurchaseKind_(String purchaseKind_) {
        this.purchaseKind_ = purchaseKind_;
    }

    public String getAuditApplicant_() {
        return this.auditApplicant_;
    }

    public void setAuditApplicant_(String auditApplicant_) {
        this.auditApplicant_ = auditApplicant_;
    }

    public String getAuditApplicantText() {
        return this.auditApplicantText;
    }

    public void setAuditApplicantText(String auditApplicantText) {
        this.auditApplicantText = auditApplicantText;
    }
    
    public String getPurchaseKindText() {
        return this.purchaseKindText;
    }

    public void setPurchaseKindText(String purchaseKindText) {
        this.purchaseKindText = purchaseKindText;
    }
    
    public String getExtId() {
    	return this.extId;
    }
    
    public void setExtId(String extId) {
    	this.extId = extId;
    }
    
    public String getPurchaseName() {
    	return this.purchaseName;
    }
    
    public void setPurchaseName(String purchaseName) {
    	this.purchaseName = purchaseName;
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
    
    public String getInputApplicant_() {
    	return this.inputApplicant_;
    }
    
    public void setInputApplicant_(String inputApplicant_) {
    	this.inputApplicant_ = inputApplicant_;
    }
    
    public String getInputApplicantText() {
    	return this.inputApplicantText;
    }
    
    public void setInputApplicantText(String inputApplicantText) {
    	this.inputApplicantText = inputApplicantText;
    }
	
	public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
    
    public String getRemark() {
    	return this.remark;
    }
    
    public void setRemark(String remark) {
    	this.remark = remark;
    }

    public String getcDatetime() {
        return cDatetime;
    }

    public void setcDatetime(String cDatetime) {
        this.cDatetime = cDatetime;
    }

    public List<ArchiveBean> getArchivesList() {
        return archivesList;
    }

    public void setArchivesList(List<ArchiveBean> archivesList) {
        this.archivesList = archivesList;
    }

    public List<String> getArchivesReservedList() {
        return archivesReservedList;
    }

    public void setArchivesReservedList(List<String> archivesReservedList) {
        this.archivesReservedList = archivesReservedList;
    }
    
    public String getPaymentTime() {
        return this.paymentTime;
    }


    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentRate() {
        return this.paymentRate;
    }

    public void setPaymentRate(String paymentRate) {
        this.paymentRate = paymentRate;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getTotalPaymentRate() {
        return this.totalPaymentRate;
    }

    public void setTotalPaymentRate(String totalPaymentRate) {
        this.totalPaymentRate = totalPaymentRate;
    }

    public Double getTotalPaymentAmount() {
        return this.totalPaymentAmount;
    }

    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

}
