/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_plan_change")
public class PlanChange implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3164369197045729009L;


    private String id = UUID.randomUUID().toString();

    private Plan plan;
    private Project project;
    private String planName;
    private String planVersion;
    private VirtualOrg virtualOrg;
    private String remark;
    private PlanCalendar planCalendar;
    private Person applicant;
    private String changeReason;
    private String changeRemark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;
    private short isDeleted;
    //private String creator;
    private Person creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = true)
    public VirtualOrg getVirtualOrg() {
        return this.virtualOrg;
    }

    
    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant", nullable = true)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }

    @Column(name = "change_reason", nullable = false, length = 1024)
    public String getChangeReason() {
        return this.changeReason;
    }


    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    @Column(name = "change_remark", nullable = true, length = 1024)
    public String getChangeRemark() {
        return this.changeRemark;
    }


    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
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

//    @Column(name = "creator", nullable = false, length = 50)
//    public String getCreator() {
//        return this.creator;
//    }
//
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
    
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_plan_change_attachment",
            joinColumns = {
                    @JoinColumn(name = "plan_change_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    public Set<Archive> getArchives() {
        return this.archives;
    }


    public void setArchives(Set<Archive> archives) {
        this.archives = archives;
    }

}
