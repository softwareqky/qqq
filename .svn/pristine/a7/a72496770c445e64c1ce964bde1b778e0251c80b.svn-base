package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_preset_task")
public class PresetTask implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6097324294627009937L;

    private String id = UUID.randomUUID().toString();

    private int taskNum;
    private Integer preTaskNum;
    private String taskName;
    private String taskType;
    private short isSiteTask;
    private int projectGroup;
    private int startDateDayOffset;
    private int endDateDayOffset;
    private int durationDays;
    private Integer constructionStatus;
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

    @Column(name = "task_num", nullable = false)
    public int getTaskNum() {
        return this.taskNum;
    }


    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    @Column(name = "pre_task_num", nullable = true)
    public Integer getPreTaskNum() {
        return this.preTaskNum;
    }


    public void setPreTaskNum(Integer preTaskNum) {
        this.preTaskNum = preTaskNum;
    }

    @Column(name = "task_name", nullable = false, length = 1000)
    public String getTaskName() {
        return this.taskName;
    }


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "is_site_task", nullable = false)
    public short getIsSiteTask() {
        return this.isSiteTask;
    }


    public void setIsSiteTask(short isSiteTask) {
        this.isSiteTask = isSiteTask;
    }

    @Column(name = "project_group", nullable = false)
    public int getProjectGroup() {
        return this.projectGroup;
    }


    public void setProjectGroup(int projectGroup) {
        this.projectGroup = projectGroup;
    }

    @Column(name = "start_date_day_offset", nullable = false)
    public int getStartDateDayOffset() {
        return this.startDateDayOffset;
    }


    public void setStartDateDayOffset(int startDateDayOffset) {
        this.startDateDayOffset = startDateDayOffset;
    }

    @Column(name = "end_date_day_offset", nullable = false)
    public int getEndDateDayOffset() {
        return this.endDateDayOffset;
    }


    public void setEndDateDayOffset(int endDateDayOffset) {
        this.endDateDayOffset = endDateDayOffset;
    }

    @Column(name = "duration_days", nullable = false)
    public int getDurationDays() {
        return this.durationDays;
    }


    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    @Column(name = "construction_status", nullable = true)
    public Integer getConstructionStatus() {
        return this.constructionStatus;
    }


    public void setConstructionStatus(Integer constructionStatus) {
        this.constructionStatus = constructionStatus;
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

    @Column(name = "task_type", nullable = true, length = 50)
    public String getTaskType() {
        return this.taskType;
    }

    
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }


}
