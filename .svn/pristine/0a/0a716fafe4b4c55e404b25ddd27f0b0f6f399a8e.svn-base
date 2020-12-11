package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_project_performance_award")
public class ProjectPerformanceAward implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -398276584886128953L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private ProjectPerson person;
    private DataOption awardAndPunishmentType;
    private Double amount;
    private Date executeTime;
    private String executeBasis;
    private String executeReason;
    private String remark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<ProjectPerformanceAwardAttachment> projectPerformanceAwardAttachments = new HashSet<>(0);
    private List<ProjectPerformanceAwardAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public ProjectPerformanceAward clone() {
        ProjectPerformanceAward p = null;
        try {
            p = (ProjectPerformanceAward) super.clone();
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
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    public ProjectPerson getPerson() {
        return this.person;
    }


    public void setPerson(ProjectPerson person) {
        this.person = person;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "award_and_punishment_type_id", nullable = false)
    public DataOption getAwardAndPunishmentType() {
        return this.awardAndPunishmentType;
    }


    public void setAwardAndPunishmentType(DataOption awardAndPunishmentType) {
        this.awardAndPunishmentType = awardAndPunishmentType;
    }

    @Column(name = "amount", nullable = true)
    public Double getAmount() {
        return this.amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "execute_time", nullable = true, length = 29)
    public Date getExecuteTime() {
        return this.executeTime;
    }


    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    @Column(name = "execute_basis", nullable = true, length = 1024)
    public String getExecuteBasis() {
        return this.executeBasis;
    }


    public void setExecuteBasis(String executeBasis) {
        this.executeBasis = executeBasis;
    }

    @Column(name = "execute_reason", nullable = true, length = 50)
    public String getExecuteReason() {
        return this.executeReason;
    }


    public void setExecuteReason(String executeReason) {
        this.executeReason = executeReason;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "award")
    public Set<ProjectPerformanceAwardAttachment> getProjectPerformanceAwardAttachments() {
        return this.projectPerformanceAwardAttachments;
    }
    
    public void setProjectPerformanceAwardAttachments(
            Set<ProjectPerformanceAwardAttachment> projectPerformanceAwardAttachments) {
        this.projectPerformanceAwardAttachments = projectPerformanceAwardAttachments;
    }

    @Transient
    public List<ProjectPerformanceAwardAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<ProjectPerformanceAwardAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
