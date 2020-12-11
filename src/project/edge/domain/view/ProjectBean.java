/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


/**
 * 项目信息。
 *
 */
public class ProjectBean implements ViewBean {

    private String id;


    private String projectSource;

    private String site_;
    private String siteText;
    private String segment_;
    private String segmentText;
    private String projectKind_;
    private String projectKindText;
    private String applicant_;
    private String applicantText;
    private String applicantName;
    private String applicantDept;
    private String applicantPost;
    private String applicantMobile;
    private String constructor_;
    private String constructorText;
    private String constructorName;
    private String constructorContact;
    private String constructorContactMobile;
    private String constructorAddress;
    private String constructorCode;
    private String constructorProjectDept_;
    private String constructorProjectDeptText;
    private String constructorIndustry;
    private String constructorProjectNum;
    private String projectNum;
    private String governmentProjectNum;
    private String projectName;
    private List<String> projectGroup_;
    private String projectGroupText;
    private List<String> projectRole_;
    private String projectRoleText;
    private String projectStartDate;
    private String projectEndDate;
    private String province_;
    private String provinceText;
    private String city_;
    private String cityText;
    private String projectSet_;
    private String projectSetText;
    private String projectAddress;
    private String projectDuration;
    private String effortEstimate;
    private Double projectCost;
    private String currency_;
    private String currencyText;
    private Double exchangeRate;
    private Double projectCostRmb;
    private Double estimateProfitRate;
    private Double estimateFieldCostRate;
    private Short isSolution;
    private String isSolutionText;
    private String projectStatus_;
    private String projectStatusText;
    private String projectStage_;
    private String projectStageText;
    private String projectType_;
    private String projectTypeText;
    private Double professionalCost;
    private String qualityGrade_;
    private String qualityGradeText;
    private String projectCategory_;
    private String projectCategoryText;
    private String projectAuditStatus;
    private String biddingAgent;
    private String projectTracker_;
    private String projectTrackerText;
    private String projectTrackerName;
    private String finalClient_;
    private String finalClientText;
    private String implementProjectDept_;
    private String implementProjectDeptText;
    private String operator_;
    private String operatorText;
    private String operatorName;
    private String poNum;
    private String signSubject_;
    private String signSubjectText;
    private String region_;
    private String regionText;
    private String projectDept_;
    private String projectDeptText;
    private String companyCode;
    private String contractUnit_;
    private String contractUnitText;
    private String qualificationNum;
    private String professionType_;
    private String professionTypeText;
    private String pjmReq;
    private String techLeaderReq;
    private String projectDesc;
    private String infoSource;
    private String otherReq;
    private String remark;
    private String changedContent;
    private String qualificationReq_;
    private String qualificationReqText;
    private String qualificationGrade;
    private String contractMethod_;
    private String contractMethodText;
    private String paymentMethod_;
    private String paymentMethodText;
    private String biddingMethod_;
    private String biddingMethodText;
    private String settlementMethod_;
    private String settlementMethodText;
    private String budgetMethod_;
    private String budgetMethodText;
    private String importance_;
    private String importanceText;
    private String projectManager_;
    private String projectManagerText;
    private String projectManagerName;
    private String projectManagerMobile;
    private String mainLeader_;
    private String mainLeaderText;
    private String mainLeaderName;
    private String mainLeaderMobile;
    private String businessManager_;
    private String businessManagerText;
    private String businessManagerName;
    private String projectClass_;
    private String projectClassText;
    private String projectClassGrade;
    private String projectSize;
    private String projectSizeGrade;
    private String fundSource;
    private String fundImplementStatus;
    private String buildingArea;
    private String floorArea;
    private String undergroundArea;
    private String reader_;
    private String readerText;
    private String readerName;
    private String greenArea;
    private String parkArea;
    private Double bidDeposit;
    private Double contractDeposit;
    private Double materialDeposit;
    private String otherDesc;
    private Double managementDeposit;
    private Double migrantWorkerSalaryDeposit;
    private Double managementFeeRate;
    private Double performanceDeposit;
    private Double otherDeposit;
    private Double taxRate;
    private Double costFeeRate;
    private Double qualityDepositRate;
    private Double checkedDepositDeductRate;
    private Double riskDepositDeductRate;
    private Double safeProductionFee;
    private Double bidSecurity;
    private String investor;
    private String investorLeader;
    private String investorContact;
    private String designer;
    private String designerLeader;
    private String designerContact;
    private String supervisor;
    private String supervisorLeader;
    private String supervisorContact;
    private String builder;
    private String builderLeader;
    private String builderContact;
    private String contractor;
    private String contractorLeader;
    private String contractorContact;
    private String associates;
    private String referee;
    private String subCompany;
    private short isChecked;
    private String isCheckedText;
    private short isReviewed;
    private String isReviewedText;
    private String flowProjectStartDate;
    private String flowProjectEndDate;
    private String flowMemberStartDate;
    private String flowMemberEndDate;
    private int flowStatusProject;
    private String flowStatusProjectText;
    private int flowStatusMember;
    private String flowStatusMemberText;
    private String projectVersionDatetime;
    private String projectPersonVersionDatetime;
    private String cDatetime;
    private String creator_;
    private String creatorText;
    private String modifier_;
    private String modifierText;

    private String specialCategory;
    private String competentDepartment;
    private Integer undertakeProperty;
    private String undertakePropertyText;
    private String undertakeUnit;
    private String participateUnit;
    private String graphicProgress;
    
    private String proposalArchiveRemark; // 项目建议书说明
    private String feasibilityArchiveRemark; // 可研文档说明
    private String preliminaryDesignArchiveRemark; // 项目初设文件说明

    /**
     * 用来区分，附件是在什么画面上传的。具体有普通修改画面、项目建议文件上传画面、项目可研文件上传画面，以及项目初设文件画面。
     * 
     * 前端在提交时，需要提供相应的值来指明。
     */
    private String uploadFileType;

    private List<ArchiveBean> archiveList = new ArrayList<>();
    private List<String> archiveReservedList = new ArrayList<>(); // 修改时，保留的档案文件id列表

    private List<ArchiveBean> proposalArchiveList = new ArrayList<>();
    private List<String> proposalArchiveReservedList = new ArrayList<>();

    private List<ArchiveBean> feasibilityArchiveList = new ArrayList<>();
    private List<String> feasibilityArchiveReservedList = new ArrayList<>();

    private List<ArchiveBean> eiaArchiveList = new ArrayList<>();
    private List<String> eiaArchiveReservedList = new ArrayList<>();

    private List<ArchiveBean> energyAssessmentArchiveList = new ArrayList<>();
    private List<String> energyAssessmentArchiveReservedList = new ArrayList<>();

    private List<ArchiveBean> landArchiveList = new ArrayList<>();
    private List<String> landArchiveReservedList = new ArrayList<>();

    private List<ArchiveBean> preliminaryDesignArchiveList = new ArrayList<>();
    private List<String> preliminaryDesignArchiveReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectSource() {
        return this.projectSource;
    }


    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public String getSite_() {
        return this.site_;
    }

    public void setSite_(String site_) {
        this.site_ = site_;
    }

    public String getSiteText() {
        return this.siteText;
    }

    public void setSiteText(String siteText) {
        this.siteText = siteText;
    }

    public String getSegment_() {
        return this.segment_;
    }

    public void setSegment_(String segment_) {
        this.segment_ = segment_;
    }

    public String getSegmentText() {
        return this.segmentText;
    }

    public void setSegmentText(String segmentText) {
        this.segmentText = segmentText;
    }


    public String getProjectKind_() {
        return this.projectKind_;
    }


    public void setProjectKind_(String projectKind_) {
        this.projectKind_ = projectKind_;
    }


    public String getProjectKindText() {
        return this.projectKindText;
    }


    public void setProjectKindText(String projectKindText) {
        this.projectKindText = projectKindText;
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

    public String getApplicantName() {
        return this.applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantDept() {
        return this.applicantDept;
    }

    public void setApplicantDept(String applicantDept) {
        this.applicantDept = applicantDept;
    }

    public String getApplicantPost() {
        return this.applicantPost;
    }

    public void setApplicantPost(String applicantPost) {
        this.applicantPost = applicantPost;
    }

    public String getApplicantMobile() {
        return this.applicantMobile;
    }

    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    public String getConstructor_() {
        return this.constructor_;
    }

    public void setConstructor_(String constructor_) {
        this.constructor_ = constructor_;
    }

    public String getConstructorText() {
        return this.constructorText;
    }

    public void setConstructorText(String constructorText) {
        this.constructorText = constructorText;
    }

    public String getConstructorName() {
        return this.constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }

    public String getConstructorContact() {
        return this.constructorContact;
    }

    public void setConstructorContact(String constructorContact) {
        this.constructorContact = constructorContact;
    }

    public String getConstructorContactMobile() {
        return this.constructorContactMobile;
    }

    public void setConstructorContactMobile(String constructorContactMobile) {
        this.constructorContactMobile = constructorContactMobile;
    }

    public String getConstructorAddress() {
        return this.constructorAddress;
    }

    public void setConstructorAddress(String constructorAddress) {
        this.constructorAddress = constructorAddress;
    }

    public String getConstructorCode() {
        return this.constructorCode;
    }

    public void setConstructorCode(String constructorCode) {
        this.constructorCode = constructorCode;
    }

    public String getConstructorProjectDept_() {
        return this.constructorProjectDept_;
    }

    public void setConstructorProjectDept_(String constructorProjectDept_) {
        this.constructorProjectDept_ = constructorProjectDept_;
    }

    public String getConstructorProjectDeptText() {
        return this.constructorProjectDeptText;
    }

    public void setConstructorProjectDeptText(String constructorProjectDeptText) {
        this.constructorProjectDeptText = constructorProjectDeptText;
    }

    public String getConstructorIndustry() {
        return this.constructorIndustry;
    }

    public void setConstructorIndustry(String constructorIndustry) {
        this.constructorIndustry = constructorIndustry;
    }

    public String getConstructorProjectNum() {
        return this.constructorProjectNum;
    }

    public void setConstructorProjectNum(String constructorProjectNum) {
        this.constructorProjectNum = constructorProjectNum;
    }

    public String getProjectNum() {
        return this.projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }


    public String getGovernmentProjectNum() {
        return this.governmentProjectNum;
    }


    public void setGovernmentProjectNum(String governmentProjectNum) {
        this.governmentProjectNum = governmentProjectNum;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public List<String> getProjectGroup_() {
        return this.projectGroup_;
    }


    public void setProjectGroup_(List<String> projectGroup_) {
        this.projectGroup_ = projectGroup_;
    }

    public String getProjectGroupText() {
        return this.projectGroupText;
    }

    public void setProjectGroupText(String projectGroupText) {
        this.projectGroupText = projectGroupText;
    }


    public List<String> getProjectRole_() {
        return this.projectRole_;
    }


    public void setProjectRole_(List<String> projectRole_) {
        this.projectRole_ = projectRole_;
    }

    public String getProjectRoleText() {
        return this.projectRoleText;
    }

    public void setProjectRoleText(String projectRoleText) {
        this.projectRoleText = projectRoleText;
    }

    public String getProjectStartDate() {
        return this.projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return this.projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getProvince_() {
        return this.province_;
    }


    public void setProvince_(String province_) {
        this.province_ = province_;
    }


    public String getProvinceText() {
        return this.provinceText;
    }


    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }


    public String getCity_() {
        return this.city_;
    }


    public void setCity_(String city_) {
        this.city_ = city_;
    }


    public String getCityText() {
        return this.cityText;
    }


    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getProjectAddress() {
        return this.projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectDuration() {
        return this.projectDuration;
    }

    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    public String getEffortEstimate() {
        return this.effortEstimate;
    }

    public void setEffortEstimate(String effortEstimate) {
        this.effortEstimate = effortEstimate;
    }

    public Double getProjectCost() {
        return this.projectCost;
    }

    public void setProjectCost(Double projectCost) {
        this.projectCost = projectCost;
    }

    public String getCurrency_() {
        return this.currency_;
    }

    public void setCurrency_(String currency_) {
        this.currency_ = currency_;
    }

    public String getCurrencyText() {
        return this.currencyText;
    }

    public void setCurrencyText(String currencyText) {
        this.currencyText = currencyText;
    }

    public Double getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getProjectCostRmb() {
        return this.projectCostRmb;
    }

    public void setProjectCostRmb(Double projectCostRmb) {
        this.projectCostRmb = projectCostRmb;
    }

    public Double getEstimateProfitRate() {
        return this.estimateProfitRate;
    }

    public void setEstimateProfitRate(Double estimateProfitRate) {
        this.estimateProfitRate = estimateProfitRate;
    }

    public Double getEstimateFieldCostRate() {
        return this.estimateFieldCostRate;
    }

    public void setEstimateFieldCostRate(Double estimateFieldCostRate) {
        this.estimateFieldCostRate = estimateFieldCostRate;
    }

    public Short getIsSolution() {
        return this.isSolution;
    }

    public void setIsSolution(Short isSolution) {
        this.isSolution = isSolution;
    }

    public String getIsSolutionText() {
        return this.isSolutionText;
    }

    public void setIsSolutionText(String isSolutionText) {
        this.isSolutionText = isSolutionText;
    }
    
    public String getProjectStage_() {
		return projectStage_;
	}

	public void setProjectStage_(String projectStage_) {
		this.projectStage_ = projectStage_;
	}

	public String getProjectStageText() {
		return projectStageText;
	}

	public void setProjectStageText(String projectStageText) {
		this.projectStageText = projectStageText;
	}

	public String getProjectStatus_() {
        return this.projectStatus_;
    }

    public void setProjectStatus_(String projectStatus_) {
        this.projectStatus_ = projectStatus_;
    }

    public String getProjectStatusText() {
        return this.projectStatusText;
    }

    public void setProjectStatusText(String projectStatusText) {
        this.projectStatusText = projectStatusText;
    }

    public String getProjectType_() {
        return this.projectType_;
    }

    public void setProjectType_(String projectType_) {
        this.projectType_ = projectType_;
    }

    public String getProjectTypeText() {
        return this.projectTypeText;
    }

    public void setProjectTypeText(String projectTypeText) {
        this.projectTypeText = projectTypeText;
    }

    public Double getProfessionalCost() {
        return this.professionalCost;
    }

    public void setProfessionalCost(Double professionalCost) {
        this.professionalCost = professionalCost;
    }

    public String getQualityGrade_() {
        return this.qualityGrade_;
    }

    public void setQualityGrade_(String qualityGrade_) {
        this.qualityGrade_ = qualityGrade_;
    }

    public String getQualityGradeText() {
        return this.qualityGradeText;
    }

    public void setQualityGradeText(String qualityGradeText) {
        this.qualityGradeText = qualityGradeText;
    }

    public String getProjectCategory_() {
        return this.projectCategory_;
    }

    public void setProjectCategory_(String projectCategory_) {
        this.projectCategory_ = projectCategory_;
    }

    public String getProjectCategoryText() {
        return this.projectCategoryText;
    }

    public void setProjectCategoryText(String projectCategoryText) {
        this.projectCategoryText = projectCategoryText;
    }

    public String getProjectAuditStatus() {
        return this.projectAuditStatus;
    }

    public void setProjectAuditStatus(String projectAuditStatus) {
        this.projectAuditStatus = projectAuditStatus;
    }

    public String getBiddingAgent() {
        return this.biddingAgent;
    }

    public void setBiddingAgent(String biddingAgent) {
        this.biddingAgent = biddingAgent;
    }

    public String getProjectTracker_() {
        return this.projectTracker_;
    }

    public void setProjectTracker_(String projectTracker_) {
        this.projectTracker_ = projectTracker_;
    }

    public String getProjectTrackerText() {
        return this.projectTrackerText;
    }

    public void setProjectTrackerText(String projectTrackerText) {
        this.projectTrackerText = projectTrackerText;
    }

    public String getProjectTrackerName() {
        return this.projectTrackerName;
    }

    public void setProjectTrackerName(String projectTrackerName) {
        this.projectTrackerName = projectTrackerName;
    }

    public String getFinalClient_() {
        return this.finalClient_;
    }

    public void setFinalClient_(String finalClient_) {
        this.finalClient_ = finalClient_;
    }

    public String getFinalClientText() {
        return this.finalClientText;
    }

    public void setFinalClientText(String finalClientText) {
        this.finalClientText = finalClientText;
    }

    public String getImplementProjectDept_() {
        return this.implementProjectDept_;
    }

    public void setImplementProjectDept_(String implementProjectDept_) {
        this.implementProjectDept_ = implementProjectDept_;
    }

    public String getImplementProjectDeptText() {
        return this.implementProjectDeptText;
    }

    public void setImplementProjectDeptText(String implementProjectDeptText) {
        this.implementProjectDeptText = implementProjectDeptText;
    }

    public String getOperator_() {
        return this.operator_;
    }

    public void setOperator_(String operator_) {
        this.operator_ = operator_;
    }

    public String getOperatorText() {
        return this.operatorText;
    }

    public void setOperatorText(String operatorText) {
        this.operatorText = operatorText;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getPoNum() {
        return this.poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public String getSignSubject_() {
        return this.signSubject_;
    }

    public void setSignSubject_(String signSubject_) {
        this.signSubject_ = signSubject_;
    }

    public String getSignSubjectText() {
        return this.signSubjectText;
    }

    public void setSignSubjectText(String signSubjectText) {
        this.signSubjectText = signSubjectText;
    }

    public String getRegion_() {
        return this.region_;
    }

    public void setRegion_(String region_) {
        this.region_ = region_;
    }

    public String getRegionText() {
        return this.regionText;
    }

    public void setRegionText(String regionText) {
        this.regionText = regionText;
    }

    public String getProjectDept_() {
        return this.projectDept_;
    }

    public void setProjectDept_(String projectDept_) {
        this.projectDept_ = projectDept_;
    }

    public String getProjectDeptText() {
        return this.projectDeptText;
    }

    public void setProjectDeptText(String projectDeptText) {
        this.projectDeptText = projectDeptText;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getContractUnit_() {
        return this.contractUnit_;
    }

    public void setContractUnit_(String contractUnit_) {
        this.contractUnit_ = contractUnit_;
    }

    public String getContractUnitText() {
        return this.contractUnitText;
    }

    public void setContractUnitText(String contractUnitText) {
        this.contractUnitText = contractUnitText;
    }

    public String getQualificationNum() {
        return this.qualificationNum;
    }

    public void setQualificationNum(String qualificationNum) {
        this.qualificationNum = qualificationNum;
    }

    public String getProfessionType_() {
        return this.professionType_;
    }

    public void setProfessionType_(String professionType_) {
        this.professionType_ = professionType_;
    }

    public String getProfessionTypeText() {
        return this.professionTypeText;
    }

    public void setProfessionTypeText(String professionTypeText) {
        this.professionTypeText = professionTypeText;
    }

    public String getPjmReq() {
        return this.pjmReq;
    }

    public void setPjmReq(String pjmReq) {
        this.pjmReq = pjmReq;
    }

    public String getTechLeaderReq() {
        return this.techLeaderReq;
    }

    public void setTechLeaderReq(String techLeaderReq) {
        this.techLeaderReq = techLeaderReq;
    }

    public String getProjectDesc() {
        return this.projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getInfoSource() {
        return this.infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    public String getOtherReq() {
        return this.otherReq;
    }

    public void setOtherReq(String otherReq) {
        this.otherReq = otherReq;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChangedContent() {
        return this.changedContent;
    }

    public void setChangedContent(String changedContent) {
        this.changedContent = changedContent;
    }

    public String getQualificationReq_() {
        return this.qualificationReq_;
    }

    public void setQualificationReq_(String qualificationReq_) {
        this.qualificationReq_ = qualificationReq_;
    }

    public String getQualificationReqText() {
        return this.qualificationReqText;
    }

    public void setQualificationReqText(String qualificationReqText) {
        this.qualificationReqText = qualificationReqText;
    }

    public String getQualificationGrade() {
        return this.qualificationGrade;
    }

    public void setQualificationGrade(String qualificationGrade) {
        this.qualificationGrade = qualificationGrade;
    }

    public String getContractMethod_() {
        return this.contractMethod_;
    }

    public void setContractMethod_(String contractMethod_) {
        this.contractMethod_ = contractMethod_;
    }

    public String getContractMethodText() {
        return this.contractMethodText;
    }

    public void setContractMethodText(String contractMethodText) {
        this.contractMethodText = contractMethodText;
    }

    public String getPaymentMethod_() {
        return this.paymentMethod_;
    }

    public void setPaymentMethod_(String paymentMethod_) {
        this.paymentMethod_ = paymentMethod_;
    }

    public String getPaymentMethodText() {
        return this.paymentMethodText;
    }

    public void setPaymentMethodText(String paymentMethodText) {
        this.paymentMethodText = paymentMethodText;
    }

    public String getBiddingMethod_() {
        return this.biddingMethod_;
    }

    public void setBiddingMethod_(String biddingMethod_) {
        this.biddingMethod_ = biddingMethod_;
    }

    public String getBiddingMethodText() {
        return this.biddingMethodText;
    }

    public void setBiddingMethodText(String biddingMethodText) {
        this.biddingMethodText = biddingMethodText;
    }

    public String getSettlementMethod_() {
        return this.settlementMethod_;
    }

    public void setSettlementMethod_(String settlementMethod_) {
        this.settlementMethod_ = settlementMethod_;
    }

    public String getSettlementMethodText() {
        return this.settlementMethodText;
    }

    public void setSettlementMethodText(String settlementMethodText) {
        this.settlementMethodText = settlementMethodText;
    }

    public String getBudgetMethod_() {
        return this.budgetMethod_;
    }

    public void setBudgetMethod_(String budgetMethod_) {
        this.budgetMethod_ = budgetMethod_;
    }

    public String getBudgetMethodText() {
        return this.budgetMethodText;
    }

    public void setBudgetMethodText(String budgetMethodText) {
        this.budgetMethodText = budgetMethodText;
    }

    public String getImportance_() {
        return this.importance_;
    }

    public void setImportance_(String importance_) {
        this.importance_ = importance_;
    }

    public String getImportanceText() {
        return this.importanceText;
    }

    public void setImportanceText(String importanceText) {
        this.importanceText = importanceText;
    }

    public String getProjectManager_() {
        return this.projectManager_;
    }

    public void setProjectManager_(String projectManager_) {
        this.projectManager_ = projectManager_;
    }

    public String getProjectManagerText() {
        return this.projectManagerText;
    }

    public void setProjectManagerText(String projectManagerText) {
        this.projectManagerText = projectManagerText;
    }

    public String getProjectManagerName() {
        return this.projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    public String getProjectManagerMobile() {
        return this.projectManagerMobile;
    }

    public void setProjectManagerMobile(String projectManagerMobile) {
        this.projectManagerMobile = projectManagerMobile;
    }

    public String getMainLeader_() {
        return this.mainLeader_;
    }

    public void setMainLeader_(String mainLeader_) {
        this.mainLeader_ = mainLeader_;
    }

    public String getMainLeaderText() {
        return this.mainLeaderText;
    }

    public void setMainLeaderText(String mainLeaderText) {
        this.mainLeaderText = mainLeaderText;
    }

    public String getMainLeaderName() {
        return this.mainLeaderName;
    }

    public void setMainLeaderName(String mainLeaderName) {
        this.mainLeaderName = mainLeaderName;
    }

    public String getMainLeaderMobile() {
        return this.mainLeaderMobile;
    }

    public void setMainLeaderMobile(String mainLeaderMobile) {
        this.mainLeaderMobile = mainLeaderMobile;
    }

    public String getBusinessManager_() {
        return this.businessManager_;
    }

    public void setBusinessManager_(String businessManager_) {
        this.businessManager_ = businessManager_;
    }

    public String getBusinessManagerText() {
        return this.businessManagerText;
    }

    public void setBusinessManagerText(String businessManagerText) {
        this.businessManagerText = businessManagerText;
    }

    public String getBusinessManagerName() {
        return this.businessManagerName;
    }

    public void setBusinessManagerName(String businessManagerName) {
        this.businessManagerName = businessManagerName;
    }

    public String getProjectClass_() {
        return this.projectClass_;
    }

    public void setProjectClass_(String projectClass_) {
        this.projectClass_ = projectClass_;
    }

    public String getProjectClassText() {
        return this.projectClassText;
    }

    public void setProjectClassText(String projectClassText) {
        this.projectClassText = projectClassText;
    }

    public String getProjectClassGrade() {
        return this.projectClassGrade;
    }

    public void setProjectClassGrade(String projectClassGrade) {
        this.projectClassGrade = projectClassGrade;
    }

    public String getProjectSize() {
        return this.projectSize;
    }

    public void setProjectSize(String projectSize) {
        this.projectSize = projectSize;
    }

    public String getProjectSizeGrade() {
        return this.projectSizeGrade;
    }

    public void setProjectSizeGrade(String projectSizeGrade) {
        this.projectSizeGrade = projectSizeGrade;
    }

    public String getFundSource() {
        return this.fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    public String getFundImplementStatus() {
        return this.fundImplementStatus;
    }

    public void setFundImplementStatus(String fundImplementStatus) {
        this.fundImplementStatus = fundImplementStatus;
    }

    public String getBuildingArea() {
        return this.buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getFloorArea() {
        return this.floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getUndergroundArea() {
        return this.undergroundArea;
    }

    public void setUndergroundArea(String undergroundArea) {
        this.undergroundArea = undergroundArea;
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

    public String getReaderName() {
        return this.readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getGreenArea() {
        return this.greenArea;
    }

    public void setGreenArea(String greenArea) {
        this.greenArea = greenArea;
    }

    public String getParkArea() {
        return this.parkArea;
    }

    public void setParkArea(String parkArea) {
        this.parkArea = parkArea;
    }

    public Double getBidDeposit() {
        return this.bidDeposit;
    }

    public void setBidDeposit(Double bidDeposit) {
        this.bidDeposit = bidDeposit;
    }

    public Double getContractDeposit() {
        return this.contractDeposit;
    }

    public void setContractDeposit(Double contractDeposit) {
        this.contractDeposit = contractDeposit;
    }

    public Double getMaterialDeposit() {
        return this.materialDeposit;
    }

    public void setMaterialDeposit(Double materialDeposit) {
        this.materialDeposit = materialDeposit;
    }

    public String getOtherDesc() {
        return this.otherDesc;
    }

    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }

    public Double getManagementDeposit() {
        return this.managementDeposit;
    }

    public void setManagementDeposit(Double managementDeposit) {
        this.managementDeposit = managementDeposit;
    }

    public Double getMigrantWorkerSalaryDeposit() {
        return this.migrantWorkerSalaryDeposit;
    }

    public void setMigrantWorkerSalaryDeposit(Double migrantWorkerSalaryDeposit) {
        this.migrantWorkerSalaryDeposit = migrantWorkerSalaryDeposit;
    }

    public Double getManagementFeeRate() {
        return this.managementFeeRate;
    }

    public void setManagementFeeRate(Double managementFeeRate) {
        this.managementFeeRate = managementFeeRate;
    }

    public Double getPerformanceDeposit() {
        return this.performanceDeposit;
    }

    public void setPerformanceDeposit(Double performanceDeposit) {
        this.performanceDeposit = performanceDeposit;
    }

    public Double getOtherDeposit() {
        return this.otherDeposit;
    }

    public void setOtherDeposit(Double otherDeposit) {
        this.otherDeposit = otherDeposit;
    }

    public Double getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getCostFeeRate() {
        return this.costFeeRate;
    }

    public void setCostFeeRate(Double costFeeRate) {
        this.costFeeRate = costFeeRate;
    }

    public Double getQualityDepositRate() {
        return this.qualityDepositRate;
    }

    public void setQualityDepositRate(Double qualityDepositRate) {
        this.qualityDepositRate = qualityDepositRate;
    }

    public Double getCheckedDepositDeductRate() {
        return this.checkedDepositDeductRate;
    }

    public void setCheckedDepositDeductRate(Double checkedDepositDeductRate) {
        this.checkedDepositDeductRate = checkedDepositDeductRate;
    }

    public Double getRiskDepositDeductRate() {
        return this.riskDepositDeductRate;
    }

    public void setRiskDepositDeductRate(Double riskDepositDeductRate) {
        this.riskDepositDeductRate = riskDepositDeductRate;
    }

    public Double getSafeProductionFee() {
        return this.safeProductionFee;
    }

    public void setSafeProductionFee(Double safeProductionFee) {
        this.safeProductionFee = safeProductionFee;
    }

    public Double getBidSecurity() {
        return this.bidSecurity;
    }

    public void setBidSecurity(Double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }

    public String getInvestor() {
        return this.investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public String getInvestorLeader() {
        return this.investorLeader;
    }

    public void setInvestorLeader(String investorLeader) {
        this.investorLeader = investorLeader;
    }

    public String getInvestorContact() {
        return this.investorContact;
    }

    public void setInvestorContact(String investorContact) {
        this.investorContact = investorContact;
    }

    public String getDesigner() {
        return this.designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getDesignerLeader() {
        return this.designerLeader;
    }

    public void setDesignerLeader(String designerLeader) {
        this.designerLeader = designerLeader;
    }

    public String getDesignerContact() {
        return this.designerContact;
    }

    public void setDesignerContact(String designerContact) {
        this.designerContact = designerContact;
    }

    public String getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisorLeader() {
        return this.supervisorLeader;
    }

    public void setSupervisorLeader(String supervisorLeader) {
        this.supervisorLeader = supervisorLeader;
    }

    public String getSupervisorContact() {
        return this.supervisorContact;
    }

    public void setSupervisorContact(String supervisorContact) {
        this.supervisorContact = supervisorContact;
    }

    public String getBuilder() {
        return this.builder;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public String getBuilderLeader() {
        return this.builderLeader;
    }

    public void setBuilderLeader(String builderLeader) {
        this.builderLeader = builderLeader;
    }

    public String getBuilderContact() {
        return this.builderContact;
    }

    public void setBuilderContact(String builderContact) {
        this.builderContact = builderContact;
    }

    public String getContractor() {
        return this.contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getContractorLeader() {
        return this.contractorLeader;
    }

    public void setContractorLeader(String contractorLeader) {
        this.contractorLeader = contractorLeader;
    }

    public String getContractorContact() {
        return this.contractorContact;
    }

    public void setContractorContact(String contractorContact) {
        this.contractorContact = contractorContact;
    }

    public String getAssociates() {
        return this.associates;
    }

    public void setAssociates(String associates) {
        this.associates = associates;
    }

    public String getReferee() {
        return this.referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getSubCompany() {
        return this.subCompany;
    }

    public void setSubCompany(String subCompany) {
        this.subCompany = subCompany;
    }

    public short getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(short isChecked) {
        this.isChecked = isChecked;
    }

    public String getIsCheckedText() {
        return this.isCheckedText;
    }

    public void setIsCheckedText(String isCheckedText) {
        this.isCheckedText = isCheckedText;
    }

    public short getIsReviewed() {
        return this.isReviewed;
    }

    public void setIsReviewed(short isReviewed) {
        this.isReviewed = isReviewed;
    }

    public String getIsReviewedText() {
        return this.isReviewedText;
    }

    public void setIsReviewedText(String isReviewedText) {
        this.isReviewedText = isReviewedText;
    }

    public String getFlowProjectStartDate() {
        return this.flowProjectStartDate;
    }

    public void setFlowProjectStartDate(String flowProjectStartDate) {
        this.flowProjectStartDate = flowProjectStartDate;
    }

    public String getFlowProjectEndDate() {
        return this.flowProjectEndDate;
    }

    public void setFlowProjectEndDate(String flowProjectEndDate) {
        this.flowProjectEndDate = flowProjectEndDate;
    }

    public String getFlowMemberStartDate() {
        return this.flowMemberStartDate;
    }

    public void setFlowMemberStartDate(String flowMemberStartDate) {
        this.flowMemberStartDate = flowMemberStartDate;
    }

    public String getFlowMemberEndDate() {
        return this.flowMemberEndDate;
    }

    public void setFlowMemberEndDate(String flowMemberEndDate) {
        this.flowMemberEndDate = flowMemberEndDate;
    }

    public int getFlowStatusProject() {
        return this.flowStatusProject;
    }

    public void setFlowStatusProject(int flowStatusProject) {
        this.flowStatusProject = flowStatusProject;
    }

    public int getFlowStatusMember() {
        return this.flowStatusMember;
    }

    public void setFlowStatusMember(int flowStatusMember) {
        this.flowStatusMember = flowStatusMember;
    }

    public String getProjectVersionDatetime() {
        return this.projectVersionDatetime;
    }

    public void setProjectVersionDatetime(String projectVersionDatetime) {
        this.projectVersionDatetime = projectVersionDatetime;
    }

    public String getProjectPersonVersionDatetime() {
        return this.projectPersonVersionDatetime;
    }

    public void setProjectPersonVersionDatetime(String projectPersonVersionDatetime) {
        this.projectPersonVersionDatetime = projectPersonVersionDatetime;
    }

    public String getFlowStatusProjectText() {
        return this.flowStatusProjectText;
    }

    public void setFlowStatusProjectText(String flowStatusProjectText) {
        this.flowStatusProjectText = flowStatusProjectText;
    }

    public String getFlowStatusMemberText() {
        return this.flowStatusMemberText;
    }

    public void setFlowStatusMemberText(String flowStatusMemberText) {
        this.flowStatusMemberText = flowStatusMemberText;
    }

    public String getProjectSet_() {
        return this.projectSet_;
    }

    public void setProjectSet_(String projectSet_) {
        this.projectSet_ = projectSet_;
    }

    public String getProjectSetText() {
        return projectSetText;
    }

    public void setProjectSetText(String projectSetText) {
        this.projectSetText = projectSetText;
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

    public List<ArchiveBean> getProposalArchiveList() {
        return this.proposalArchiveList;
    }

    public void setProposalArchiveList(List<ArchiveBean> proposalArchiveList) {
        this.proposalArchiveList = proposalArchiveList;
    }

    public List<String> getProposalArchiveReservedList() {
        return this.proposalArchiveReservedList;
    }

    public void setProposalArchiveReservedList(List<String> proposalArchiveReservedList) {
        this.proposalArchiveReservedList = proposalArchiveReservedList;
    }

    public List<ArchiveBean> getFeasibilityArchiveList() {
        return this.feasibilityArchiveList;
    }

    public void setFeasibilityArchiveList(List<ArchiveBean> feasibilityArchiveList) {
        this.feasibilityArchiveList = feasibilityArchiveList;
    }

    public List<String> getFeasibilityArchiveReservedList() {
        return this.feasibilityArchiveReservedList;
    }

    public void setFeasibilityArchiveReservedList(List<String> feasibilityArchiveReservedList) {
        this.feasibilityArchiveReservedList = feasibilityArchiveReservedList;
    }

    public List<ArchiveBean> getEiaArchiveList() {
        return this.eiaArchiveList;
    }

    public void setEiaArchiveList(List<ArchiveBean> eiaArchiveList) {
        this.eiaArchiveList = eiaArchiveList;
    }

    public List<String> getEiaArchiveReservedList() {
        return this.eiaArchiveReservedList;
    }

    public void setEiaArchiveReservedList(List<String> eiaArchiveReservedList) {
        this.eiaArchiveReservedList = eiaArchiveReservedList;
    }

    public List<ArchiveBean> getEnergyAssessmentArchiveList() {
        return this.energyAssessmentArchiveList;
    }

    public void setEnergyAssessmentArchiveList(List<ArchiveBean> energyAssessmentArchiveList) {
        this.energyAssessmentArchiveList = energyAssessmentArchiveList;
    }

    public List<String> getEnergyAssessmentArchiveReservedList() {
        return this.energyAssessmentArchiveReservedList;
    }

    public void setEnergyAssessmentArchiveReservedList(
            List<String> energyAssessmentArchiveReservedList) {
        this.energyAssessmentArchiveReservedList = energyAssessmentArchiveReservedList;
    }

    public List<ArchiveBean> getLandArchiveList() {
        return this.landArchiveList;
    }

    public void setLandArchiveList(List<ArchiveBean> landArchiveList) {
        this.landArchiveList = landArchiveList;
    }

    public List<String> getLandArchiveReservedList() {
        return this.landArchiveReservedList;
    }

    public void setLandArchiveReservedList(List<String> landArchiveReservedList) {
        this.landArchiveReservedList = landArchiveReservedList;
    }

    public List<ArchiveBean> getPreliminaryDesignArchiveList() {
        return this.preliminaryDesignArchiveList;
    }

    public void setPreliminaryDesignArchiveList(List<ArchiveBean> preliminaryDesignArchiveList) {
        this.preliminaryDesignArchiveList = preliminaryDesignArchiveList;
    }

    public List<String> getPreliminaryDesignArchiveReservedList() {
        return this.preliminaryDesignArchiveReservedList;
    }

    public void setPreliminaryDesignArchiveReservedList(
            List<String> preliminaryDesignArchiveReservedList) {
        this.preliminaryDesignArchiveReservedList = preliminaryDesignArchiveReservedList;
    }

    public String getUploadFileType() {
        return this.uploadFileType;
    }

    public void setUploadFileType(String uploadFileType) {
        this.uploadFileType = uploadFileType;
    }

    public String getcDatetime() {
        return this.cDatetime;
    }

    public void setcDatetime(String cDatetime) {
        this.cDatetime = cDatetime;
    }

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

    public String getModifier_() {
        return this.modifier_;
    }

    public void setModifier_(String modifier_) {
        this.modifier_ = modifier_;
    }

    public String getModifierText() {
        return this.modifierText;
    }

    public void setModifierText(String modifierText) {
        this.modifierText = modifierText;
    }


    public String getSpecialCategory() {
        return this.specialCategory;
    }


    public void setSpecialCategory(String specialCategory) {
        this.specialCategory = specialCategory;
    }


    public String getCompetentDepartment() {
        return this.competentDepartment;
    }


    public void setCompetentDepartment(String competentDepartment) {
        this.competentDepartment = competentDepartment;
    }


    public Integer getUndertakeProperty() {
        return this.undertakeProperty;
    }


    public void setUndertakeProperty(Integer undertakeProperty) {
        this.undertakeProperty = undertakeProperty;
    }


    public String getUndertakePropertyText() {
        return this.undertakePropertyText;
    }


    public void setUndertakePropertyText(String undertakePropertyText) {
        this.undertakePropertyText = undertakePropertyText;
    }


    public String getUndertakeUnit() {
        return this.undertakeUnit;
    }


    public void setUndertakeUnit(String undertakeUnit) {
        this.undertakeUnit = undertakeUnit;
    }


    public String getParticipateUnit() {
        return this.participateUnit;
    }


    public void setParticipateUnit(String participateUnit) {
        this.participateUnit = participateUnit;
    }

	public String getGraphicProgress() {
		return graphicProgress;
	}

	public void setGraphicProgress(String graphicProgress) {
		this.graphicProgress = graphicProgress;
	}
	
	public String getProposalArchiveRemark() {
		return proposalArchiveRemark;
	}
	
	public void setProposalArchiveRemark(String proposalArchiveRemark) {
		this.proposalArchiveRemark = proposalArchiveRemark;
	}
	
	public String getFeasibilityArchiveRemark() {
		return feasibilityArchiveRemark;
	}
	
	public void setFeasibilityArchiveRemark(String feasibilityArchiveRemark) {
		this.feasibilityArchiveRemark = feasibilityArchiveRemark;
	}
	
	public String getPreliminaryDesignArchiveRemark() {
		return preliminaryDesignArchiveRemark;
	}
	
	public void setPreliminaryDesignArchiveRemark(String preliminaryDesignArchiveRemark) {
		this.preliminaryDesignArchiveRemark = preliminaryDesignArchiveRemark;
	}

}
