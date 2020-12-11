package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         招标计划详细的表现层DTO。
 */
public class TenderingPlanBean implements ViewBean {

    private String id;
    private String purchaseOrder_;
    private String purchaseOrderText;
    private String project_;
    private String projectText;
    private String tenderingType_;
    private String tenderingTypeText;
    private String tenderingNo;
    private String tenderingName;
    private String tenderingLeader_;
    private String tenderingLeaderText;
    private String tenderingDept_;
    private String tenderingDeptText;
    private String virtualOrg_;
    private String virtualOrgText;
    private String tenderingMethod;
    private String evaluatingMethods;
    private String approvalNumber;
    private Integer tenderingStatus_;
    private String tenderingStatusText;
    private Integer priceType_;
    private String priceTypeText;
    private String priceUnit;
    private Double estimatedPrice;
    private String tenderScope;
    private String planStartTime;
    private String planEndTime;
    private String tenderOpenTime;
    private String tenderOpenEndTime;
    private String tenderAssessmentStartTime;
    private String tenderAssessmentEndTime;
    private String remark;
    private String applicant_;
    private String applicantText;
    private String recordTime;
    private String flowStartDate;
    private String flowEndDate;
    private Integer flowStatus;
    private String flowStatusText;

    private List<ArchiveBean> archiveDraftFirstList = new ArrayList<>();
    private List<ArchiveBean> archiveDraftFinalList = new ArrayList<>();
    private List<ArchiveBean> archiveTechnicalDocList = new ArrayList<>();
    private List<ArchiveBean> archiveAnnouncementList = new ArrayList<>();
    private List<ArchiveBean> archiveTenderWinList = new ArrayList<>();
    
    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archiveDraftFirstReservedList = new ArrayList<>();
    private List<String> archiveDraftFinalReservedList = new ArrayList<>();
    private List<String> archiveTechnicalDocReservedList = new ArrayList<>();
    private List<String> archiveAnnouncementReservedList = new ArrayList<>();
    private List<String> archiveTenderWinReservedList = new ArrayList<>();

    public String getPurchaseOrder_() {
        return this.purchaseOrder_;
    }

    public void setPurchaseOrder_(String purchaseOrder_) {
        this.purchaseOrder_ = purchaseOrder_;
    }

    public String getPurchaseOrderText() {
        return this.purchaseOrderText;
    }

    public void setPurchaseOrderText(String purchaseOrderText) {
        this.purchaseOrderText = purchaseOrderText;
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

    public String getTenderingType_() {
        return this.tenderingType_;
    }

    public void setTenderingType_(String tenderingType_) {
        this.tenderingType_ = tenderingType_;
    }

    public String getTenderingTypeText() {
        return this.tenderingTypeText;
    }

    public void setTenderingTypeText(String tenderingTypeText) {
        this.tenderingTypeText = tenderingTypeText;
    }

    public String getTenderingNo() {
        return this.tenderingNo;
    }

    public void setTenderingNo(String tenderingNo) {
        this.tenderingNo = tenderingNo;
    }

    public String getTenderingName() {
        return this.tenderingName;
    }

    public void setTenderingName(String tenderingName) {
        this.tenderingName = tenderingName;
    }

    public String getTenderingLeader_() {
        return this.tenderingLeader_;
    }

    public void setTenderingLeader_(String tenderingLeader_) {
        this.tenderingLeader_ = tenderingLeader_;
    }

    public String getTenderingLeaderText() {
        return this.tenderingLeaderText;
    }

    public void setTenderingLeaderText(String tenderingLeaderText) {
        this.tenderingLeaderText = tenderingLeaderText;
    }

    public String getTenderingDept_() {
        return this.tenderingDept_;
    }

    public void setTenderingDept_(String tenderingDept_) {
        this.tenderingDept_ = tenderingDept_;
    }

    public String getTenderingDeptText() {
        return this.tenderingDeptText;
    }

    public void setTenderingDeptText(String tenderingDeptText) {
        this.tenderingDeptText = tenderingDeptText;
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

    public String getTenderingMethod() {
        return this.tenderingMethod;
    }

    public void setTenderingMethod(String tenderingMethod) {
        this.tenderingMethod = tenderingMethod;
    }

    public String getEvaluatingMethods() {
        return this.evaluatingMethods;
    }

    public void setEvaluatingMethods(String evaluatingMethods) {
        this.evaluatingMethods = evaluatingMethods;
    }

    public String getApprovalNumber() {
        return this.approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public String getPriceUnit() {
        return this.priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getEstimatedPrice() {
        return this.estimatedPrice;
    }

    public void setEstimatedPrice(Double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getTenderScope() {
        return this.tenderScope;
    }

    public void setTenderScope(String tenderScope) {
        this.tenderScope = tenderScope;
    }

    public String getPlanStartTime() {
        return this.planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return this.planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getTenderOpenTime() {
        return this.tenderOpenTime;
    }

    public void setTenderOpenTime(String tenderOpenTime) {
        this.tenderOpenTime = tenderOpenTime;
    }

    public String getTenderOpenEndTime() {
        return this.tenderOpenEndTime;
    }

    public void setTenderOpenEndTime(String tenderOpenEndTime) {
        this.tenderOpenEndTime = tenderOpenEndTime;
    }

    public String getTenderAssessmentStartTime() {
        return this.tenderAssessmentStartTime;
    }

    public void setTenderAssessmentStartTime(String tenderAssessmentStartTime) {
        this.tenderAssessmentStartTime = tenderAssessmentStartTime;
    }

    public String getTenderAssessmentEndTime() {
        return this.tenderAssessmentEndTime;
    }

    public void setTenderAssessmentEndTime(String tenderAssessmentEndTime) {
        this.tenderAssessmentEndTime = tenderAssessmentEndTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
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
        return this.flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowStatusText() {
        return this.flowStatusText;
    }

    public void setFlowStatusText(String flowStatusText) {
        this.flowStatusText = flowStatusText;
    }
    
    public List<ArchiveBean> getArchiveDraftFirstList() {
		return this.archiveDraftFirstList;
	}

	public void setArchiveDraftFirstList(List<ArchiveBean> archiveDraftFirstList) {
		this.archiveDraftFirstList = archiveDraftFirstList;
	}
    
    public List<ArchiveBean> getArchiveDraftFinalList() {
		return this.archiveDraftFinalList;
	}

	public void setArchiveDraftFinalList(List<ArchiveBean> archiveDraftFinalList) {
		this.archiveDraftFinalList = archiveDraftFinalList;
	}
    
    public List<ArchiveBean> getArchiveTechnicalDocList() {
		return this.archiveTechnicalDocList;
	}

	public void setArchiveTechnicalDocList(List<ArchiveBean> archiveTechnicalDocList) {
		this.archiveTechnicalDocList = archiveTechnicalDocList;
	}
    
    public List<ArchiveBean> getArchiveAnnouncementList() {
		return this.archiveAnnouncementList;
	}

	public void setArchiveAnnouncementList(List<ArchiveBean> archiveAnnouncementList) {
		this.archiveAnnouncementList = archiveAnnouncementList;
	}
    
    public List<ArchiveBean> getArchiveTenderWinList() {
		return this.archiveTenderWinList;
	}

	public void setArchiveTenderWinList(List<ArchiveBean> archiveTenderWinList) {
		this.archiveTenderWinList = archiveTenderWinList;
	}
	
	public List<String> getArchiveDraftFirstReservedList() {
		return this.archiveDraftFirstReservedList;
	}

	public void setArchiveDraftFirstReservedList(List<String> archiveDraftFirstReservedList) {
		this.archiveDraftFirstReservedList = archiveDraftFirstReservedList;
	}
	
	public List<String> getArchiveDraftFinalReservedList() {
		return this.archiveDraftFinalReservedList;
	}

	public void setArchiveDraftFinalReservedList(List<String> archiveDraftFinalReservedList) {
		this.archiveDraftFinalReservedList = archiveDraftFinalReservedList;
	}
	
	public List<String> getArchiveTechnicalDocReservedList() {
		return this.archiveTechnicalDocReservedList;
	}

	public void setArchiveTechnicalDocReservedList(List<String> archiveTechnicalDocReservedList) {
		this.archiveTechnicalDocReservedList = archiveTechnicalDocReservedList;
	}
	
	public List<String> getArchiveAnnouncementReservedList() {
		return this.archiveAnnouncementReservedList;
	}

	public void setArchiveAnnouncementReservedList(List<String> archiveAnnouncementReservedList) {
		this.archiveAnnouncementReservedList = archiveAnnouncementReservedList;
	}
	
	public List<String> getArchiveTenderWinReservedList() {
		return this.archiveTenderWinReservedList;
	}

	public void setArchiveTenderWinReservedList(List<String> archiveTenderWinReservedList) {
		this.archiveTenderWinReservedList = archiveTenderWinReservedList;
	}


	@Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTenderingStatus_() {
        return tenderingStatus_;
    }

    public void setTenderingStatus_(Integer tenderingStatus_) {
        this.tenderingStatus_ = tenderingStatus_;
    }

    public String getTenderingStatusText() {
        return tenderingStatusText;
    }

    public void setTenderingStatusText(String tenderingStatusText) {
        this.tenderingStatusText = tenderingStatusText;
    }

    public Integer getPriceType_() {
        return priceType_;
    }

    public void setPriceType_(Integer priceType_) {
        this.priceType_ = priceType_;
    }

    public String getPriceTypeText() {
        return priceTypeText;
    }

    public void setPriceTypeText(String priceTypeText) {
        this.priceTypeText = priceTypeText;
    }

}
