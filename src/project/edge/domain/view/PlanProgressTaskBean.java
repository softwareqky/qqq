package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000 保存实际进度明细信息的表现层DTO。
 */
public class PlanProgressTaskBean implements ViewBean {

	private String id;
	private String plan_;
	private String planText;
	private int taskNum;
	private String planProgress_;
	private String planProgressText;
	private String planTask_;
	private String planTaskText;
	private String taskName;
	private int durationDays;
	private String planStartDate;
	private String planEndDate;
	private Double weight;
	private String leader_;
	private String leaderText;
	private String actualStartDate;
	private String actualEndDate;
	private Integer actualDurationDays;
	private Double lastProgress;
	private Double progress;
	private Integer delayCause;
	private String progressDate;
	private Short isBehindSchedule;
	private String isBehindScheduleText;
	private Double score;
	private String progressRemark;
	private String achievement;
	private String cumulativeProgress;
	private String currentWeekProgress;
	private String description;
	private String nextWeekGoals;
	private int flowStatus;
	private String flowStatusText;
	private String issueType;
	private PlanTask planTask;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getTaskNum() {
		return this.taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public String getPlanProgress_() {
		return this.planProgress_;
	}

	public void setPlanProgress_(String planProgress_) {
		this.planProgress_ = planProgress_;
	}

	public String getPlanProgressText() {
		return this.planProgressText;
	}

	public void setPlanProgressText(String planProgressText) {
		this.planProgressText = planProgressText;
	}

	public String getPlanTask_() {
		return this.planTask_;
	}

	public void setPlanTask_(String planTask_) {
		this.planTask_ = planTask_;
	}

	public String getPlanTaskText() {
		return this.planTaskText;
	}

	public void setPlanTaskText(String planTaskText) {
		this.planTaskText = planTaskText;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getDurationDays() {
		return this.durationDays;
	}

	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	}

	public String getPlanStartDate() {
		return this.planStartDate;
	}

	public void setPlanStartDate(String planStartDate) {
		this.planStartDate = planStartDate;
	}

	public String getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getLeader_() {
		return this.leader_;
	}

	public void setLeader_(String leader_) {
		this.leader_ = leader_;
	}

	public String getLeaderText() {
		return this.leaderText;
	}

	public void setLeaderText(String leaderText) {
		this.leaderText = leaderText;
	}

	public String getActualStartDate() {
		return this.actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualEndDate() {
		return this.actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public Integer getActualDurationDays() {
		return this.actualDurationDays;
	}

	public void setActualDurationDays(Integer actualDurationDays) {
		this.actualDurationDays = actualDurationDays;
	}

	public Double getProgress() {
		return this.progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public Integer getDelayCause() {
		return this.delayCause;
	}

	public void setDelayCause(Integer delayCause) {
		this.delayCause = delayCause;
	}

	public String getProgressDate() {
		return this.progressDate;
	}

	public void setProgressDate(String progressDate) {
		this.progressDate = progressDate;
	}

	public Short getIsBehindSchedule() {
		return this.isBehindSchedule;
	}

	public void setIsBehindSchedule(Short isBehindSchedule) {
		this.isBehindSchedule = isBehindSchedule;
	}

	public String getIsBehindScheduleText() {
		return this.isBehindScheduleText;
	}

	public void setIsBehindScheduleText(String isBehindScheduleText) {
		this.isBehindScheduleText = isBehindScheduleText;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getProgressRemark() {
		return this.progressRemark;
	}

	public void setProgressRemark(String progressRemark) {
		this.progressRemark = progressRemark;
	}

	public String getAchievement() {
		return this.achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getCumulativeProgress() {
		return this.cumulativeProgress;
	}

	public void setCumulativeProgress(String cumulativeProgress) {
		this.cumulativeProgress = cumulativeProgress;
	}

	public String getCurrentWeekProgress() {
		return this.currentWeekProgress;
	}

	public void setCurrentWeekProgress(String currentWeekProgress) {
		this.currentWeekProgress = currentWeekProgress;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNextWeekGoals() {
		return this.nextWeekGoals;
	}

	public void setNextWeekGoals(String nextWeekGoals) {
		this.nextWeekGoals = nextWeekGoals;
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

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public Double getLastProgress() {
		return lastProgress;
	}

	public void setLastProgress(Double lastProgress) {
		this.lastProgress = lastProgress;
	}

	public PlanTask getPlanTask() {
		return planTask;
	}

	public void setPlanTask(PlanTask planTask) {
		this.planTask = planTask;
	}

}
