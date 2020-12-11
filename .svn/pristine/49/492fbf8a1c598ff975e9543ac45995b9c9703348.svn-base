package project.edge.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_plan_progress")
public class PlanProgress implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604582975082688301L;

	private String id = UUID.randomUUID().toString();

	private Plan plan;
	private Project project;
	private Date progressDate;
	private Person applicant;
	private VirtualOrg virtualOrg;
	private String progressName;
	private int dateType;
	private String description;
	private String remark;

	private Date flowStartDate;
	private Date flowEndDate;

	private int reportType;
	private String subTitle;
	private String recipientUnit;
	private Date postDate;
	private String overview;
	private String recipient;
	private String applicantMobile;
	private Double totalAmount;
	private Double investedAmount;
	private String currentProgress;
	private String appearanceProgress;
	private Integer investor;
	private String annualTarget;
	private String completionStatement;

	private int flowStatus;
	private short isDeleted;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

	private Set<PlanProgressAttachment> planProgressAttachments = new HashSet<>(0);
	private List<PlanProgressAttachment> attachmentsToDelete = new ArrayList<>();
	private List<PlanProgressTask> planProgressTasks = new ArrayList<PlanProgressTask>(0);

	@Override
	public PlanProgress clone() {
		PlanProgress p = null;
		try {
			p = (PlanProgress) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return p;
	}

	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", nullable = true)
	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "progress_date", nullable = false, length = 29)
	public Date getProgressDate() {
		return this.progressDate;
	}

	public void setProgressDate(Date progressDate) {
		this.progressDate = progressDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant", nullable = true)
	public Person getApplicant() {
		return this.applicant;
	}

	public void setApplicant(Person applicant) {
		this.applicant = applicant;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "virtual_org_id", nullable = true)
	public VirtualOrg getVirtualOrg() {
		return this.virtualOrg;
	}

	public void setVirtualOrg(VirtualOrg virtualOrg) {
		this.virtualOrg = virtualOrg;
	}

	@Column(name = "progress_name", nullable = false, length = 36)
	public String getProgressName() {
		return this.progressName;
	}

	public void setProgressName(String progressName) {
		this.progressName = progressName;
	}

	@Column(name = "date_type", nullable = false)
	public int getDateType() {
		return this.dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}
	
	@Column(name = "description", nullable = true, length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "remark", nullable = true, length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "flow_start_date", nullable = true, length = 29)
	public Date getFlowStartDate() {
		return this.flowStartDate;
	}

	public void setFlowStartDate(Date flowStartDate) {
		this.flowStartDate = flowStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "flow_end_date", nullable = true, length = 29)
	public Date getFlowEndDate() {
		return this.flowEndDate;
	}

	public void setFlowEndDate(Date flowEndDate) {
		this.flowEndDate = flowEndDate;
	}

	@Column(name = "flow_status", nullable = false)
	public int getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	@Column(name = "is_deleted", nullable = false)
	public short getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(short isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "creator", nullable = false, length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_datetime", nullable = false, length = 29)
	public Date getcDatetime() {
		return this.cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	@Column(name = "modifier", nullable = true, length = 50)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "m_datetime", nullable = false, length = 29)
	public Date getmDatetime() {
		return this.mDatetime;
	}

	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planProgress")
	public List<PlanProgressTask> getPlanProgressTasks() {
		return this.planProgressTasks;
	}

	public void setPlanProgressTasks(List<PlanProgressTask> planProgressTasks) {
		this.planProgressTasks = planProgressTasks;
	}

	@Column(name = "report_type", nullable = false)
	public int getReportType() {
		return this.reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	@Column(name = "sub_title", nullable = true, length = 36)
	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Column(name = "recipient_unit", nullable = true, length = 50)
	public String getRecipientUnit() {
		return this.recipientUnit;
	}

	public void setRecipientUnit(String recipientUnit) {
		this.recipientUnit = recipientUnit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "post_date", nullable = true, length = 29)
	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@Column(name = "overview", nullable = true, length = 1024)
	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Column(name = "recipient", nullable = true, length = 50)
	public String getRecipient() {
		return this.recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Column(name = "applicant_mobile", nullable = true, length = 50)
	public String getApplicantMobile() {
		return this.applicantMobile;
	}

	public void setApplicantMobile(String applicantMobile) {
		this.applicantMobile = applicantMobile;
	}

	@Column(name = "total_amount", nullable = true)
	public Double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "invested_amount", nullable = true)
	public Double getInvestedAmount() {
		return this.investedAmount;
	}

	public void setInvestedAmount(Double investedAmount) {
		this.investedAmount = investedAmount;
	}

	@Column(name = "current_progress", nullable = true, length = 1024)
	public String getCurrentProgress() {
		return this.currentProgress;
	}

	public void setCurrentProgress(String currentProgress) {
		this.currentProgress = currentProgress;
	}

	@Column(name = "appearance_progress", nullable = true, length = 1024)
	public String getAppearanceProgress() {
		return this.appearanceProgress;
	}

	public void setAppearanceProgress(String appearanceProgress) {
		this.appearanceProgress = appearanceProgress;
	}

	@Column(name = "investor", nullable = true)
	public Integer getInvestor() {
		return this.investor;
	}

	public void setInvestor(Integer investor) {
		this.investor = investor;
	}

	@Column(name = "annual_target", nullable = true, length = 1024)
	public String getAnnualTarget() {
		return this.annualTarget;
	}

	public void setAnnualTarget(String annualTarget) {
		this.annualTarget = annualTarget;
	}

	@Column(name = "completion_statement", nullable = true, length = 1024)
	public String getCompletionStatement() {
		return this.completionStatement;
	}

	public void setCompletionStatement(String completionStatement) {
		this.completionStatement = completionStatement;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "planProgress")
	public Set<PlanProgressAttachment> getPlanProgressAttachments() {
		return this.planProgressAttachments;
	}

	public void setPlanProgressAttachments(Set<PlanProgressAttachment> planProgressAttachments) {
		this.planProgressAttachments = planProgressAttachments;
	}

	@Transient
	public List<PlanProgressAttachment> getAttachmentsToDelete() {
		return this.attachmentsToDelete;
	}

	public void setAttachmentsToDelete(List<PlanProgressAttachment> attachmentsToDelete) {
		this.attachmentsToDelete = attachmentsToDelete;
	}

}
