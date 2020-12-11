/**
 * 
 */
package project.edge.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_plan")
public class Plan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6488321397914844590L;

	private String id = UUID.randomUUID().toString();

	private Project project;
	private Site site;
	private Segment segment;
	private String planName;
	private String planVersion;
	private VirtualOrg virtualOrg;
	private String remark;
	private PlanCalendar planCalendar;
	private String planYear;
	private Integer combineType;
	private Date planStartDate;
	private Date planEndDate;
	private Integer durationDays;
	private Date actualStartDate;
	private Date actualEndDate;
	private Integer sumTaskDurationDays;
	private Double sumTaskProgressDays;
	private Double progress;
	private Integer totalTaskCount;
	private Integer totalFinishTaskCount;
	private Integer totalWarningTaskCount;
	private Integer totalDelayTaskCount;
	private Integer reqDelayTaskCount;
	private Integer solutionDelayTaskCount;
	private Integer hrDelayTaskCount;
	private Integer budgetDelayTaskCount;
	private Short isComplete;
	private Date flowStartDate;
	private Date flowEndDate;
	private int flowStatus;
	private Date versionDatetime;
	private short isDeleted;
	// private String creator;
	private Person creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	private Integer isYear;
	private Person personInCharge;
	private Integer status;

	private Set<Archive> archives = new HashSet<Archive>(0);

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
	@JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = true)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "segment_id", nullable = true)
	public Segment getSegment() {
		return this.segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	@Column(name = "plan_name", nullable = false, length = 150)
	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@Column(name = "plan_version", nullable = true, length = 50)
	public String getPlanVersion() {
		return this.planVersion;
	}

	public void setPlanVersion(String planVersion) {
		this.planVersion = planVersion;
	}

	@Column(name = "remark", nullable = true, length = 1024)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_calendar_id", nullable = true)
	public PlanCalendar getPlanCalendar() {
		return this.planCalendar;
	}

	public void setPlanCalendar(PlanCalendar planCalendar) {
		this.planCalendar = planCalendar;
	}

	@Column(name = "plan_year", nullable = true, length = 36)
	public String getPlanYear() {
		return this.planYear;
	}

	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}

	@Column(name = "combine_type", nullable = true)
	public Integer getCombineType() {
		return this.combineType;
	}

	public void setCombineType(Integer combineType) {
		this.combineType = combineType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_start_date", nullable = true, length = 29)
	public Date getPlanStartDate() {
		return this.planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_end_date", nullable = true, length = 29)
	public Date getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Column(name = "duration_days", nullable = true)
	public Integer getDurationDays() {
		return this.durationDays;
	}

	public void setDurationDays(Integer durationDays) {
		this.durationDays = durationDays;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_start_date", nullable = true, length = 29)
	public Date getActualStartDate() {
		return this.actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_end_date", nullable = true, length = 29)
	public Date getActualEndDate() {
		return this.actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	@Column(name = "progress", nullable = true)
	public Double getProgress() {
		return this.progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	@Column(name = "total_task_count", nullable = true)
	public Integer getTotalTaskCount() {
		return this.totalTaskCount;
	}

	public void setTotalTaskCount(Integer totalTaskCount) {
		this.totalTaskCount = totalTaskCount;
	}
	
	@Column(name = "status", nullable = true)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "total_delay_task_count", nullable = true)
	public Integer getTotalDelayTaskCount() {
		return this.totalDelayTaskCount;
	}

	public void setTotalDelayTaskCount(Integer totalDelayTaskCount) {
		this.totalDelayTaskCount = totalDelayTaskCount;
	}

	@Column(name = "req_delay_task_count", nullable = true)
	public Integer getReqDelayTaskCount() {
		return this.reqDelayTaskCount;
	}

	public void setReqDelayTaskCount(Integer reqDelayTaskCount) {
		this.reqDelayTaskCount = reqDelayTaskCount;
	}

	@Column(name = "solution_delay_task_count", nullable = true)
	public Integer getSolutionDelayTaskCount() {
		return this.solutionDelayTaskCount;
	}

	public void setSolutionDelayTaskCount(Integer solutionDelayTaskCount) {
		this.solutionDelayTaskCount = solutionDelayTaskCount;
	}

	@Column(name = "hr_delay_task_count", nullable = true)
	public Integer getHrDelayTaskCount() {
		return this.hrDelayTaskCount;
	}

	public void setHrDelayTaskCount(Integer hrDelayTaskCount) {
		this.hrDelayTaskCount = hrDelayTaskCount;
	}

	@Column(name = "budget_delay_task_count", nullable = true)
	public Integer getBudgetDelayTaskCount() {
		return this.budgetDelayTaskCount;
	}

	public void setBudgetDelayTaskCount(Integer budgetDelayTaskCount) {
		this.budgetDelayTaskCount = budgetDelayTaskCount;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "version_datetime", nullable = true, length = 29)
	public Date getVersionDatetime() {
		return this.versionDatetime;
	}

	public void setVersionDatetime(Date versionDatetime) {
		this.versionDatetime = versionDatetime;
	}

	@Column(name = "is_deleted", nullable = false)
	public short getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(short isDeleted) {
		this.isDeleted = isDeleted;
	}

	// @Column(name = "creator", nullable = false, length = 50)
	// public String getCreator() {
	// return this.creator;
	// }
	//
	//
	// public void setCreator(String creator) {
	// this.creator = creator;
	// }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator", nullable = false)
	public Person getCreator() {
		return this.creator;
	}

	public void setCreator(Person creator) {
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

	@Column(name = "sum_task_duration_days", nullable = true)
	public Integer getSumTaskDurationDays() {
		return this.sumTaskDurationDays;
	}

	public void setSumTaskDurationDays(Integer sumTaskDurationDays) {
		this.sumTaskDurationDays = sumTaskDurationDays;
	}

	@Column(name = "sum_task_progress_days", nullable = true)
	public Double getSumTaskProgressDays() {
		return this.sumTaskProgressDays;
	}

	public void setSumTaskProgressDays(Double sumTaskProgressDays) {
		this.sumTaskProgressDays = sumTaskProgressDays;
	}

	@Column(name = "total_warning_task_count", nullable = true)
	public Integer getTotalWarningTaskCount() {
		return this.totalWarningTaskCount;
	}

	public void setTotalWarningTaskCount(Integer totalWarningTaskCount) {
		this.totalWarningTaskCount = totalWarningTaskCount;
	}

	@Column(name = "total_finish_task_count", nullable = true)
	public Integer getTotalFinishTaskCount() {
		return this.totalFinishTaskCount;
	}

	public void setTotalFinishTaskCount(Integer totalFinishTaskCount) {
		this.totalFinishTaskCount = totalFinishTaskCount;
	}

	@Column(name = "is_complete", nullable = true)
	public Short getIsComplete() {
		return this.isComplete;
	}

	public void setIsComplete(Short isComplete) {
		this.isComplete = isComplete;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "virtual_org_id", nullable = true)
	public VirtualOrg getVirtualOrg() {
		return this.virtualOrg;
	}

	public void setVirtualOrg(VirtualOrg virtualOrg) {
		this.virtualOrg = virtualOrg;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_plan_attachment", joinColumns = {
			@JoinColumn(name = "plan_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "archive_id", nullable = false, updatable = false) })
	public Set<Archive> getArchives() {
		return this.archives;
	}

	public void setArchives(Set<Archive> archives) {
		this.archives = archives;
	}

	@Column(name = "is_year", nullable = true)
	public Integer getIsYear() {
		return isYear;
	}

	public void setIsYear(Integer isYear) {
		this.isYear = isYear;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_in_charge", nullable = true)
	public Person getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(Person personInCharge) {
		this.personInCharge = personInCharge;
	}

}
