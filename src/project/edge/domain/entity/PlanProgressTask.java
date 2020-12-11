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
@Table(name = "t_plan_progress_task")
public class PlanProgressTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7685664605208449267L;

	private String id = UUID.randomUUID().toString();

	private Plan plan;
	private PlanProgress planProgress;
	private PlanTask planTask;
	private String taskName;
	private int taskNum;
	private String taskType;
	private int durationDays;
	private String wbs;
	private Date planStartDate;
	private Date planEndDate;
	private Double weight;
	private Person leader;
	private Date actualStartDate;
	private Date actualEndDate;
	private Integer actualDurationDays;
	private double lastProgress;
	private double progress;
	private short isDelay;
	private Integer delayCause;
	private Date progressDate;

	private String participantList;
	private String participantNameList;

	private Double score;
	private String progressRemark;
	private String achievement;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

	private String cumulativeProgress;
	private String currentWeekProgress;
	private String description;
	private String nextWeekGoals;
	private int flowStatus;

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
	@JoinColumn(name = "plan_id", nullable = false)
	public Plan getPlan() {
		return this.plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@Column(name = "task_num", nullable = false)
	public int getTaskNum() {
		return this.taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_progress_id", nullable = false)
	public PlanProgress getPlanProgress() {
		return this.planProgress;
	}

	public void setPlanProgress(PlanProgress planProgress) {
		this.planProgress = planProgress;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_task_id", nullable = true)
	public PlanTask getPlanTask() {
		return this.planTask;
	}

	public void setPlanTask(PlanTask planTask) {
		this.planTask = planTask;
	}

	@Column(name = "task_name", nullable = false, length = 1000)
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "duration_days", nullable = false)
	public int getDurationDays() {
		return this.durationDays;
	}

	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_start_date", nullable = false, length = 29)
	public Date getPlanStartDate() {
		return this.planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_end_date", nullable = false, length = 29)
	public Date getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Column(name = "weight", nullable = true)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leader", nullable = true)
	public Person getLeader() {
		return this.leader;
	}

	public void setLeader(Person leader) {
		this.leader = leader;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "actual_start_date", nullable = false, length = 29)
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

	@Column(name = "actual_duration_days", nullable = true)
	public Integer getActualDurationDays() {
		return this.actualDurationDays;
	}

	public void setActualDurationDays(Integer actualDurationDays) {
		this.actualDurationDays = actualDurationDays;
	}

	@Column(name = "progress", nullable = false)
	public double getProgress() {
		return this.progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	@Column(name = "is_delay", nullable = false)
	public short getIsDelay() {
		return this.isDelay;
	}

	public void setIsDelay(short isDelay) {
		this.isDelay = isDelay;
	}

	@Column(name = "delay_cause", nullable = true)
	public Integer getDelayCause() {
		return this.delayCause;
	}

	public void setDelayCause(Integer delayCause) {
		this.delayCause = delayCause;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "progress_date", nullable = false, length = 29)
	public Date getProgressDate() {
		return this.progressDate;
	}

	public void setProgressDate(Date progressDate) {
		this.progressDate = progressDate;
	}

	@Column(name = "score", nullable = true)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "progress_remark", nullable = true, length = 1024)
	public String getProgressRemark() {
		return this.progressRemark;
	}

	public void setProgressRemark(String progressRemark) {
		this.progressRemark = progressRemark;
	}

	@Column(name = "achievement", nullable = true, length = 1024)
	public String getAchievement() {
		return this.achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_plan_progress_task_attachment", joinColumns = {
			@JoinColumn(name = "plan_progress_task_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "archive_id", nullable = false, updatable = false) })
	public Set<Archive> getArchives() {
		return this.archives;
	}

	public void setArchives(Set<Archive> archives) {
		this.archives = archives;
	}

	@Column(name = "cumulative_progress", nullable = true, length = 1024)
	public String getCumulativeProgress() {
		return this.cumulativeProgress;
	}

	public void setCumulativeProgress(String cumulativeProgress) {
		this.cumulativeProgress = cumulativeProgress;
	}

	@Column(name = "current_week_progress", nullable = true, length = 1024)
	public String getCurrentWeekProgress() {
		return this.currentWeekProgress;
	}

	public void setCurrentWeekProgress(String currentWeekProgress) {
		this.currentWeekProgress = currentWeekProgress;
	}

	@Column(name = "description", nullable = true, length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "next_week_goals", nullable = true, length = 1024)
	public String getNextWeekGoals() {
		return this.nextWeekGoals;
	}

	public void setNextWeekGoals(String nextWeekGoals) {
		this.nextWeekGoals = nextWeekGoals;
	}

	@Column(name = "flow_status", nullable = false)
	public int getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	@Column(name = "task_type", nullable = true, length = 50)
	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Column(name = "wbs", nullable = true, length = 50)
	public String getWbs() {
		return this.wbs;
	}

	public void setWbs(String wbs) {
		this.wbs = wbs;
	}

	@Column(name = "participant_list", nullable = true, length = 1024)
	public String getParticipantList() {
		return this.participantList;
	}

	public void setParticipantList(String participantList) {
		this.participantList = participantList;
	}

	@Column(name = "participant_name_list", nullable = true, length = 1024)
	public String getParticipantNameList() {
		return this.participantNameList;
	}

	public void setParticipantNameList(String participantNameList) {
		this.participantNameList = participantNameList;
	}

	@Column(name = "lastProgress", nullable = true)
	public double getLastProgress() {
		return lastProgress;
	}

	public void setLastProgress(double lastProgress) {
		this.lastProgress = lastProgress;
	}
}
