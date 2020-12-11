package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存验收信息表的表现层DTO。
 */
public class AcceptanceCheckBean implements ViewBean {

    private String id;
    private String project_;
    private String projectText;
    private String acceptanceCheckType_;
    private String acceptanceCheckTypeText;
    private String acceptanceCheckKind_;
    private String acceptanceCheckKindText;
    private String checkingUnit;
    private String chechedUnit;
    private String checkedContent;
    private String checkBasis;
    private String checker_;
    private String checkerText;
    /*
     * private String archive_;
     * private String archiveText;
     */
    private Short isImprove;
    private String isImproveText;
    private String acceptanceCheckResult_;
    private String acceptanceCheckResultText;
    private String acceptanceVerifyResult_;
    private String acceptanceVerifyResultText;
    private String checkDate;
    private String improveReqDate;
    private String improvePlanDate;
    private String improveActualDate;
    private String checkResultContent;
    private String improveReq;
    private String improvePlan;
    /*
     * private String improveArchive_;
     * private String improveArchiveText;
     */
    private String verifyDate;
    private String improveResultVerify;
    private String remark;
    private String flowStartDate;
    private String flowEndDate;
    private int flowStatus;
    private String flowStatusText;
    
    private String acceptanceCheckStatus_;
    private String acceptanceCheckStatusText;
    
    private String uploadFileType;

    private List<ArchiveBean> archiveList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archiveReservedList = new ArrayList<>();

    private List<ArchiveBean> improvearchiveList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> improvearchiveReservedList = new ArrayList<>();

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return this.id;
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


    public String getAcceptanceCheckType_() {
        return this.acceptanceCheckType_;
    }


    public void setAcceptanceCheckType_(String acceptanceCheckType_) {
        this.acceptanceCheckType_ = acceptanceCheckType_;
    }


    public String getAcceptanceCheckTypeText() {
        return this.acceptanceCheckTypeText;
    }


    public void setAcceptanceCheckTypeText(String acceptanceCheckTypeText) {
        this.acceptanceCheckTypeText = acceptanceCheckTypeText;
    }


    public String getAcceptanceCheckKind_() {
        return this.acceptanceCheckKind_;
    }


    public void setAcceptanceCheckKind_(String acceptanceCheckKind_) {
        this.acceptanceCheckKind_ = acceptanceCheckKind_;
    }


    public String getAcceptanceCheckKindText() {
        return this.acceptanceCheckKindText;
    }


    public void setAcceptanceCheckKindText(String acceptanceCheckKindText) {
        this.acceptanceCheckKindText = acceptanceCheckKindText;
    }


    public String getCheckingUnit() {
        return this.checkingUnit;
    }


    public void setCheckingUnit(String checkingUnit) {
        this.checkingUnit = checkingUnit;
    }


    public String getChechedUnit() {
        return this.chechedUnit;
    }


    public void setChechedUnit(String chechedUnit) {
        this.chechedUnit = chechedUnit;
    }


    public String getCheckedContent() {
        return this.checkedContent;
    }


    public void setCheckedContent(String checkedContent) {
        this.checkedContent = checkedContent;
    }


    public String getCheckBasis() {
        return this.checkBasis;
    }


    public void setCheckBasis(String checkBasis) {
        this.checkBasis = checkBasis;
    }

    public String getChecker_() {
		return checker_;
	}


	public void setChecker_(String checker_) {
		this.checker_ = checker_;
	}


	public String getCheckerText() {
		return checkerText;
	}


	public void setCheckerText(String checkerText) {
		this.checkerText = checkerText;
	}


	public Short getIsImprove() {
        return this.isImprove;
    }


    public void setIsImprove(Short isImprove) {
        this.isImprove = isImprove;
    }


    public String getIsImproveText() {
        return this.isImproveText;
    }


    public void setIsImproveText(String isImproveText) {
        this.isImproveText = isImproveText;
    }


    public String getAcceptanceCheckResult_() {
        return this.acceptanceCheckResult_;
    }


    public void setAcceptanceCheckResult_(String acceptanceCheckResult_) {
        this.acceptanceCheckResult_ = acceptanceCheckResult_;
    }


    public String getAcceptanceCheckResultText() {
        return this.acceptanceCheckResultText;
    }


    public void setAcceptanceCheckResultText(String acceptanceCheckResultText) {
        this.acceptanceCheckResultText = acceptanceCheckResultText;
    }


    public String getAcceptanceVerifyResult_() {
        return this.acceptanceVerifyResult_;
    }


    public void setAcceptanceVerifyResult_(String acceptanceVerifyResult_) {
        this.acceptanceVerifyResult_ = acceptanceVerifyResult_;
    }


    public String getAcceptanceVerifyResultText() {
        return this.acceptanceVerifyResultText;
    }


    public void setAcceptanceVerifyResultText(String acceptanceVerifyResultText) {
        this.acceptanceVerifyResultText = acceptanceVerifyResultText;
    }


    public String getCheckDate() {
        return this.checkDate;
    }


    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }


    public String getImproveReqDate() {
        return this.improveReqDate;
    }


    public void setImproveReqDate(String improveReqDate) {
        this.improveReqDate = improveReqDate;
    }


    public String getImprovePlanDate() {
        return this.improvePlanDate;
    }


    public void setImprovePlanDate(String improvePlanDate) {
        this.improvePlanDate = improvePlanDate;
    }


    public String getImproveActualDate() {
        return this.improveActualDate;
    }


    public void setImproveActualDate(String improveActualDate) {
        this.improveActualDate = improveActualDate;
    }


    public String getCheckResultContent() {
        return this.checkResultContent;
    }


    public void setCheckResultContent(String checkResultContent) {
        this.checkResultContent = checkResultContent;
    }


    public String getImproveReq() {
        return this.improveReq;
    }


    public void setImproveReq(String improveReq) {
        this.improveReq = improveReq;
    }


    public String getImprovePlan() {
        return this.improvePlan;
    }


    public void setImprovePlan(String improvePlan) {
        this.improvePlan = improvePlan;
    }


    public String getVerifyDate() {
        return this.verifyDate;
    }


    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }


    public String getImproveResultVerify() {
        return this.improveResultVerify;
    }


    public void setImproveResultVerify(String improveResultVerify) {
        this.improveResultVerify = improveResultVerify;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getFlowStartDate() {
        return this.flowStartDate;
    }


    public void setFlowStartDate(String flowStartDate) {
        this.flowStartDate = flowStartDate;
    }


    public String getFlowEndDate() {
        return this.flowEndDate;
    }


    public void setFlowEndDate(String flowEndDate) {
        this.flowEndDate = flowEndDate;
    }


    public int getFlowStatus() {
        return this.flowStatus;
    }


    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }


    public void setId(String id) {
        this.id = id;
    }

    
    public String getFlowStatusText() {
        return this.flowStatusText;
    }

    
    public void setFlowStatusText(String flowStatusText) {
        this.flowStatusText = flowStatusText;
    }

    
    public String getAcceptanceCheckStatus_() {
        return this.acceptanceCheckStatus_;
    }

    
    public void setAcceptanceCheckStatus_(String acceptanceCheckStatus_) {
        this.acceptanceCheckStatus_ = acceptanceCheckStatus_;
    }

    
    public String getAcceptanceCheckStatusText() {
        return this.acceptanceCheckStatusText;
    }

    
    public void setAcceptanceCheckStatusText(String acceptanceCheckStatusText) {
        this.acceptanceCheckStatusText = acceptanceCheckStatusText;
    }

    
    public String getUploadFileType() {
        return this.uploadFileType;
    }

    
    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
    }

    
    public List<ArchiveBean> getArchiveList() {
        return this.archiveList;
    }

    
    public void setArchiveList(List<ArchiveBean> archiveList) {
        this.archiveList = archiveList;
    }

    
    public List<String> getArchiveReservedList() {
        return this.archiveReservedList;
    }

    
    public void setArchiveReservedList(List<String> archiveReservedList) {
        this.archiveReservedList = archiveReservedList;
    }

    
    public List<ArchiveBean> getImprovearchiveList() {
        return this.improvearchiveList;
    }

    
    public void setImprovearchiveList(List<ArchiveBean> improvearchiveList) {
        this.improvearchiveList = improvearchiveList;
    }

    
    public List<String> getImprovearchiveReservedList() {
        return this.improvearchiveReservedList;
    }

    
    public void setImprovearchiveReservedList(List<String> improvearchiveReservedList) {
        this.improvearchiveReservedList = improvearchiveReservedList;
    }

}
