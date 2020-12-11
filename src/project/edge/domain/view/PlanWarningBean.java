package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * @author angel_000
 *         保存进度预警表的表现层DTO。
 */
public class PlanWarningBean implements ViewBean {

    private String id;

    private String project_;
    private String projectText;
    private String site_;
    private String siteText;
    private String segment_;
    private String segmentText;
    private String planName;
    private String planVersion;
    private String remark;
    private String planCalendar_;
    private String planCalendarText;
    private String planStartDate;
    private String planEndDate;
    private Integer durationDays;
    private String actualStartDate;
    private String actualEndDate;
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
    private String isCompleteText;
    private String flowStartDate;
    private String flowEndDate;
    private int flowStatus;
    private String versionDatetime;

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

    public String getPlanName() {
        return this.planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanVersion() {
        return this.planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanCalendar_() {
        return this.planCalendar_;
    }

    public void setPlanCalendar_(String planCalendar_) {
        this.planCalendar_ = planCalendar_;
    }

    public String getPlanCalendarText() {
        return this.planCalendarText;
    }

    public void setPlanCalendarText(String planCalendarText) {
        this.planCalendarText = planCalendarText;
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

    public Integer getDurationDays() {
        return this.durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
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

    public Integer getSumTaskDurationDays() {
        return this.sumTaskDurationDays;
    }

    public void setSumTaskDurationDays(Integer sumTaskDurationDays) {
        this.sumTaskDurationDays = sumTaskDurationDays;
    }

    public Double getSumTaskProgressDays() {
        return this.sumTaskProgressDays;
    }

    public void setSumTaskProgressDays(Double sumTaskProgressDays) {
        this.sumTaskProgressDays = sumTaskProgressDays;
    }

    public Double getProgress() {
        return this.progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Integer getTotalTaskCount() {
        return this.totalTaskCount;
    }

    public void setTotalTaskCount(Integer totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public Integer getTotalFinishTaskCount() {
        return this.totalFinishTaskCount;
    }

    public void setTotalFinishTaskCount(Integer totalFinishTaskCount) {
        this.totalFinishTaskCount = totalFinishTaskCount;
    }

    public Integer getTotalWarningTaskCount() {
        return this.totalWarningTaskCount;
    }

    public void setTotalWarningTaskCount(Integer totalWarningTaskCount) {
        this.totalWarningTaskCount = totalWarningTaskCount;
    }

    public Integer getTotalDelayTaskCount() {
        return this.totalDelayTaskCount;
    }

    public void setTotalDelayTaskCount(Integer totalDelayTaskCount) {
        this.totalDelayTaskCount = totalDelayTaskCount;
    }

    public Integer getReqDelayTaskCount() {
        return this.reqDelayTaskCount;
    }

    public void setReqDelayTaskCount(Integer reqDelayTaskCount) {
        this.reqDelayTaskCount = reqDelayTaskCount;
    }

    public Integer getSolutionDelayTaskCount() {
        return this.solutionDelayTaskCount;
    }

    public void setSolutionDelayTaskCount(Integer solutionDelayTaskCount) {
        this.solutionDelayTaskCount = solutionDelayTaskCount;
    }

    public Integer getHrDelayTaskCount() {
        return this.hrDelayTaskCount;
    }

    public void setHrDelayTaskCount(Integer hrDelayTaskCount) {
        this.hrDelayTaskCount = hrDelayTaskCount;
    }

    public Integer getBudgetDelayTaskCount() {
        return this.budgetDelayTaskCount;
    }

    public void setBudgetDelayTaskCount(Integer budgetDelayTaskCount) {
        this.budgetDelayTaskCount = budgetDelayTaskCount;
    }

    public Short getIsComplete() {
        return this.isComplete;
    }

    public void setIsComplete(Short isComplete) {
        this.isComplete = isComplete;
    }

    public String getIsCompleteText() {
        return this.isCompleteText;
    }

    public void setIsCompleteText(String isCompleteText) {
        this.isCompleteText = isCompleteText;
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

    public String getVersionDatetime() {
        return this.versionDatetime;
    }

    public void setVersionDatetime(String versionDatetime) {
        this.versionDatetime = versionDatetime;
    }

    public void setId(String id) {
        this.id = id;
    }

}
