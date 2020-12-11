package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000 保存实际进度的表现层DTO。
 */
public class PlanProgressBean implements ViewBean {

	private String id;

	private String plan_;
	private String planText;
	private String project_;
	private String projectText;
	private String progressDate;
	private String progressName;
	private int dateType;
	private String dateTypeText;
	private String applicant_;
	private String applicantText;
	private String virtualOrg_;
	private String virtualOrgText;

	private String description;
	private String remark;
	private String flowStartDate;
	private String flowEndDate;
	private int flowStatus;
	private String flowStatusText;

	private int reportType;
	private String reportTypeText;
	private String subTitle;
	private String recipientUnit;
	private String postDate;
	private String overview;
	private String recipient;
	private String applicantMobile;
	private Double totalAmount;
	private Double investedAmount;
	private String currentProgress;
	private String appearanceProgress;
	private Integer investor;
	private String investorText;
	private String annualTarget;
	private String completionStatement;
	
	private String personInCharge_;
	private String personInChargeText;

	private List<PlanProgressTaskBean> planProgressTaskBeans = new ArrayList<PlanProgressTaskBean>();

	private List<ArchiveBean> archivesList = new ArrayList<>();

	/**
	 * 修改时，保留的档案文件id列表
	 */
	private List<String> archivesReservedList = new ArrayList<>();

	@Override
	public String getId() {
		return this.id;
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

	public String getProgressDate() {
		return this.progressDate;
	}

	public void setProgressDate(String progressDate) {
		this.progressDate = progressDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getProgressName() {
		return this.progressName;
	}

	public void setProgressName(String progressName) {
		this.progressName = progressName;
	}

	public int getDateType() {
		return this.dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public String getDateTypeText() {
		return this.dateTypeText;
	}

	public void setDateTypeText(String dateTypeText) {
		this.dateTypeText = dateTypeText;
	}

	public List<PlanProgressTaskBean> getPlanProgressTaskBeans() {
		return this.planProgressTaskBeans;
	}

	public void setPlanProgressTaskBeans(List<PlanProgressTaskBean> planProgressTaskBeans) {
		this.planProgressTaskBeans = planProgressTaskBeans;
	}

	public int getReportType() {
		return this.reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String getReportTypeText() {
		return this.reportTypeText;
	}

	public void setReportTypeText(String reportTypeText) {
		this.reportTypeText = reportTypeText;
	}

	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getRecipientUnit() {
		return this.recipientUnit;
	}

	public void setRecipientUnit(String recipientUnit) {
		this.recipientUnit = recipientUnit;
	}

	public String getPostDate() {
		return this.postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getApplicantMobile() {
		return this.applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	public Double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getInvestedAmount() {
		return this.investedAmount;
	}

	public void setInvestedAmount(Double investedAmount) {
		this.investedAmount = investedAmount;
	}

	public String getCurrentProgress() {
		return this.currentProgress;
	}

	public void setCurrentProgress(String currentProgress) {
		this.currentProgress = currentProgress;
	}

	public String getAppearanceProgress() {
		return this.appearanceProgress;
	}

	public void setAppearanceProgress(String appearanceProgress) {
		this.appearanceProgress = appearanceProgress;
	}

	public Integer getInvestor() {
		return this.investor;
	}

	public void setInvestor(Integer investor) {
		this.investor = investor;
	}

	public String getInvestorText() {
		return this.investorText;
	}

	public void setInvestorText(String investorText) {
		this.investorText = investorText;
	}

	public String getAnnualTarget() {
		return this.annualTarget;
	}

	public void setAnnualTarget(String annualTarget) {
		this.annualTarget = annualTarget;
	}

	public String getCompletionStatement() {
		return this.completionStatement;
	}

	public void setCompletionStatement(String completionStatement) {
		this.completionStatement = completionStatement;
	}

	public List<ArchiveBean> getArchivesList() {
		return this.archivesList;
	}

	public void setArchivesList(List<ArchiveBean> archiveList) {
		this.archivesList = archiveList;
	}

	public List<String> getArchivesReservedList() {
		return this.archivesReservedList;
	}

	public void setArchivesReservedList(List<String> archiveIdReservedList) {
		this.archivesReservedList = archiveIdReservedList;
	}

	public String getVirtualOrg_() {
		return virtualOrg_;
	}

	public void setVirtualOrg_(String virtualOrg_) {
		this.virtualOrg_ = virtualOrg_;
	}

	public String getVirtualOrgText() {
		return virtualOrgText;
	}

	public void setVirtualOrgText(String virtualOrgText) {
		this.virtualOrgText = virtualOrgText;
	}

	public String getPersonInCharge_() {
		return this.personInCharge_;
	}

	public void setPersonInCharge_(String personInCharge_) {
		this.personInCharge_ = personInCharge_;
	}

	public String getPersonInChargeText() {
		return this.personInChargeText;
	}

	public void setPersonInChargeText(String personInChargeText) {
		this.personInChargeText = personInChargeText;
	}
	
}
