package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存评审表的表现层DTO。
 */
public class ReviewBean implements ViewBean {

    private String id;

    private String project_;
    private String projectText;
    private String reviewType_;
    private String reviewTypeText;
    private String reviewingUnit;
    private String reviewedUnit;
    private String reviewContent;
    private String reviewBasis;
    private String reviewer_;
    private String reviewerText;
    private List<String> expert_ = new ArrayList<>();
    private String expertText;
    /*
     * private String archive_;
     * private String archiveText;
     */
    private Short isImprove;
    private String isImproveText;
    private String reviewResult_;
    private String reviewResultText;
    private String verifyResult_;
    private String verifyResultText;
    private String reviewDate;
    private String improveReqDate;
    private String improvePlanDate;
    private String improveActualDate;
    private String reviewResultContent;
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
    
    private String reviewStatus_;
    private String reviewStatusText;
    
    
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


    public String getReviewType_() {
        return this.reviewType_;
    }


    public void setReviewType_(String reviewType_) {
        this.reviewType_ = reviewType_;
    }


    public String getReviewTypeText() {
        return this.reviewTypeText;
    }


    public void setReviewTypeText(String reviewTypeText) {
        this.reviewTypeText = reviewTypeText;
    }


    public String getReviewingUnit() {
        return this.reviewingUnit;
    }


    public void setReviewingUnit(String reviewingUnit) {
        this.reviewingUnit = reviewingUnit;
    }


    public String getReviewedUnit() {
        return this.reviewedUnit;
    }


    public void setReviewedUnit(String reviewedUnit) {
        this.reviewedUnit = reviewedUnit;
    }


    public String getReviewContent() {
        return this.reviewContent;
    }


    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }


    public String getReviewBasis() {
        return this.reviewBasis;
    }


    public void setReviewBasis(String reviewBasis) {
        this.reviewBasis = reviewBasis;
    }

    public String getReviewer_() {
		return reviewer_;
	}


	public void setReviewer_(String reviewer_) {
		this.reviewer_ = reviewer_;
	}


	public String getReviewerText() {
		return reviewerText;
	}


	public void setReviewerText(String reviewerText) {
		this.reviewerText = reviewerText;
	}


	public List<String> getExpert_() {
        return this.expert_;
    }


    public void setExpert_(List<String> expert_) {
        this.expert_ = expert_;
    }


    public String getExpertText() {
        return this.expertText;
    }


    public void setExpertText(String expertText) {
        this.expertText = expertText;
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


    public String getReviewResult_() {
        return this.reviewResult_;
    }


    public void setReviewResult_(String reviewResult_) {
        this.reviewResult_ = reviewResult_;
    }


    public String getReviewResultText() {
        return this.reviewResultText;
    }


    public void setReviewResultText(String reviewResultText) {
        this.reviewResultText = reviewResultText;
    }


    public String getVerifyResult_() {
        return this.verifyResult_;
    }


    public void setVerifyResult_(String verifyResult_) {
        this.verifyResult_ = verifyResult_;
    }


    public String getVerifyResultText() {
        return this.verifyResultText;
    }


    public void setVerifyResultText(String verifyResultText) {
        this.verifyResultText = verifyResultText;
    }


    public String getReviewDate() {
        return this.reviewDate;
    }


    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
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


    public String getReviewResultContent() {
        return this.reviewResultContent;
    }


    public void setReviewResultContent(String reviewResultContent) {
        this.reviewResultContent = reviewResultContent;
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

    public String getReviewStatus_() {
        return this.reviewStatus_;
    }

    public void setReviewStatus_(String reviewStatus_) {
        this.reviewStatus_ = reviewStatus_;
    }

    public String getReviewStatusText() {
        return this.reviewStatusText;
    }

    public void setReviewStatusText(String reviewStatusText) {
        this.reviewStatusText = reviewStatusText;
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
