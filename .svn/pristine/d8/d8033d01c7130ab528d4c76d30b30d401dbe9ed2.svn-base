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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "t_project_person_change")
public class ProjectPersonChange implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 8978503501975025191L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String changeReason;
    private String changeRemark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<Archive> archives = new HashSet<Archive>(0);
    private List<Archive> archivesToDelete = new ArrayList<>();


    @Override
    public ProjectPersonChange clone() {
        ProjectPersonChange p = null;
        try {
            p = (ProjectPersonChange) super.clone();
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
    @JoinTable(name = "t_project_person_change_attachment",
            joinColumns = {@JoinColumn(name = "project_person_change_id", nullable = false,
                    updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    public Set<Archive> getArchives() {
        return this.archives;
    }


    public void setArchives(Set<Archive> archives) {
        this.archives = archives;
    }

    @Transient
    public List<Archive> getArchivesToDelete() {
        return this.archivesToDelete;
    }

    public void setArchivesToDelete(List<Archive> archivesToDelete) {
        this.archivesToDelete = archivesToDelete;
    }
}
