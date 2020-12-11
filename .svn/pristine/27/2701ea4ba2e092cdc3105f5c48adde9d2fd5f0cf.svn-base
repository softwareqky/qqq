package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_plan_history_task")
public class PlanHistoryTask implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6856981570662627893L;

    private String id = UUID.randomUUID().toString();

    private PlanHistory planHistory;
    private Plan plan;
    private PlanTask planTask;
    private int taskNum;
    private String pid;
    private int taskLayer;
    private String taskType;
    private String taskName;
    private short isSummary;
    private short isMilestone;
    private short isCritical;
    private int durationDays;
    private Date planStartDate;
    private Date planEndDate;
    private Integer dueProgressDays;
    private String wbs;
    private Integer workload;
    private Double weight;
    private Integer priority;
    private Person leader;
    private Date deadlineDate;
    private Integer constraintType;
    private Date constraintDate;
    private Short isSiteTask;
    private Integer constructionStatus;
    private String siteSegmentId;
    private String participantList;
    private String participantNameList;
    private Date actualStartDate;
    private Date actualEndDate;
    private Integer actualDurationDays;
    private Double progress;
    private String cumulativeProgress;
    private String currentWeekProgress;
    private String description;
    private String nextWeekGoals;
    private int flowStatus;
    private Double progressDays;
    private Short isWarning;
    private Short isBehindPlanStart;
    private Short isBehindPlanEnd;
    private Short isDelay;
    private Integer delayCause;
    private Date progressDate;
    private Short isBehindSchedule;
    private Double score;
    private String progressRemark;
    private String achievement;
    private short isComment;
    private String remark;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

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
    @JoinColumn(name = "plan_history_id", nullable = false)
    public PlanHistory getPlanHistory() {
        return this.planHistory;
    }


    public void setPlanHistory(PlanHistory planHistory) {
        this.planHistory = planHistory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    public Plan getPlan() {
        return this.plan;
    }


    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_task_id", nullable = true)
    public PlanTask getPlanTask() {
        return this.planTask;
    }


    public void setPlanTask(PlanTask planTask) {
        this.planTask = planTask;
    }

    @Column(name = "task_num", nullable = false)
    public int getTaskNum() {
        return this.taskNum;
    }


    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    @Column(name = "pid", nullable = true, length = 36)
    public String getPid() {
        return this.pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "task_layer", nullable = false)
    public int getTaskLayer() {
        return this.taskLayer;
    }


    public void setTaskLayer(int taskLayer) {
        this.taskLayer = taskLayer;
    }

    @Column(name = "task_name", nullable = false, length = 1000)
    public String getTaskName() {
        return this.taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "is_summary", nullable = false)
    public short getIsSummary() {
        return this.isSummary;
    }


    public void setIsSummary(short isSummary) {
        this.isSummary = isSummary;
    }

    @Column(name = "is_milestone", nullable = false)
    public short getIsMilestone() {
        return this.isMilestone;
    }


    public void setIsMilestone(short isMilestone) {
        this.isMilestone = isMilestone;
    }

    @Column(name = "is_critical", nullable = false)
    public short getIsCritical() {
        return this.isCritical;
    }


    public void setIsCritical(short isCritical) {
        this.isCritical = isCritical;
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

    @Column(name = "wbs", nullable = true, length = 50)
    public String getWbs() {
        return this.wbs;
    }


    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    @Column(name = "workload", nullable = true)
    public Integer getWorkload() {
        return this.workload;
    }


    public void setWorkload(Integer workload) {
        this.workload = workload;
    }

    @Column(name = "weight", nullable = true)
    public Double getWeight() {
        return this.weight;
    }


    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "priority", nullable = true)
    public Integer getPriority() {
        return this.priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
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
    @Column(name = "deadline_date", nullable = true, length = 29)
    public Date getDeadlineDate() {
        return this.deadlineDate;
    }


    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    @Column(name = "constraint_type", nullable = true)
    public Integer getConstraintType() {
        return this.constraintType;
    }


    public void setConstraintType(Integer constraintType) {
        this.constraintType = constraintType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "constraint_date", nullable = true, length = 29)
    public Date getConstraintDate() {
        return this.constraintDate;
    }


    public void setConstraintDate(Date constraintDate) {
        this.constraintDate = constraintDate;
    }

    @Column(name = "is_site_task", nullable = true)
    public Short getIsSiteTask() {
        return this.isSiteTask;
    }


    public void setIsSiteTask(Short isSiteTask) {
        this.isSiteTask = isSiteTask;
    }

    @Column(name = "construction_status", nullable = true)
    public Integer getConstructionStatus() {
        return this.constructionStatus;
    }


    public void setConstructionStatus(Integer constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    @Column(name = "site_segment_id", nullable = true, length = 36)
    public String getSiteSegmentId() {
        return this.siteSegmentId;
    }


    public void setSiteSegmentId(String siteSegmentId) {
        this.siteSegmentId = siteSegmentId;
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

    @Column(name = "actual_duration_days", nullable = true)
    public Integer getActualDurationDays() {
        return this.actualDurationDays;
    }


    public void setActualDurationDays(Integer actualDurationDays) {
        this.actualDurationDays = actualDurationDays;
    }

    @Column(name = "progress", nullable = true)
    public Double getProgress() {
        return this.progress;
    }


    public void setProgress(Double progress) {
        this.progress = progress;
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

    @Column(name = "is_delay", nullable = true)
    public Short getIsDelay() {
        return this.isDelay;
    }


    public void setIsDelay(Short isDelay) {
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
    @Column(name = "progress_date", nullable = true, length = 29)
    public Date getProgressDate() {
        return this.progressDate;
    }


    public void setProgressDate(Date progressDate) {
        this.progressDate = progressDate;
    }

    @Column(name = "is_behind_schedule", nullable = true)
    public Short getIsBehindSchedule() {
        return this.isBehindSchedule;
    }


    public void setIsBehindSchedule(Short isBehindSchedule) {
        this.isBehindSchedule = isBehindSchedule;
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

    @Column(name = "is_comment", nullable = false)
    public short getIsComment() {
        return this.isComment;
    }


    public void setIsComment(short isComment) {
        this.isComment = isComment;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
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


    @Column(name = "due_progress_days", nullable = false)
    public Integer getDueProgressDays() {
        return this.dueProgressDays;
    }


    public void setDueProgressDays(Integer dueProgressDays) {
        this.dueProgressDays = dueProgressDays;
    }

    @Column(name = "progress_days", nullable = false)
    public Double getProgressDays() {
        return this.progressDays;
    }


    public void setProgressDays(Double progressDays) {
        this.progressDays = progressDays;
    }

    @Column(name = "is_warning", nullable = false)
    public Short getIsWarning() {
        return this.isWarning;
    }


    public void setIsWarning(Short isWarning) {
        this.isWarning = isWarning;
    }

    @Column(name = "is_behind_plan_start", nullable = false)
    public Short getIsBehindPlanStart() {
        return this.isBehindPlanStart;
    }


    public void setIsBehindPlanStart(Short isBehindPlanStart) {
        this.isBehindPlanStart = isBehindPlanStart;
    }

    @Column(name = "is_behind_plan_end", nullable = false)
    public Short getIsBehindPlanEnd() {
        return this.isBehindPlanEnd;
    }


    public void setIsBehindPlanEnd(Short isBehindPlanEnd) {
        this.isBehindPlanEnd = isBehindPlanEnd;
    }

    @Column(name = "task_type", nullable = true, length = 50)
    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
