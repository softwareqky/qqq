/**
 * 
 */
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
@Table(name = "t_project_inspect")
public class ProjectInspect implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 2414375056811442690L;

    private String id = UUID.randomUUID().toString();

    private Site site;
    private DataOption inspectType;
    private Project project;
    private String inspectPlan;
    private String inspectArea;
    private String inspectBasis;
    private String inspectContent;

    private Person inspector;
    private Short isImprove;
    private DataOption inspectResult;
    private DataOption verifyResult;
    private Date inspectDate;
    private Date improveReqDate;
    private Date improvePlanDate;
    private Date improveActualDate;
    private String inspectResultContent;
    private String improveReq;
    private String improvePlan;

    private Date verifyDate;
    private String improveResultVerify;
    private String remark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;
    private DataOption inspectStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

//    private Set<Archive> archives = new HashSet<Archive>(0);
//    private List<Archive> archivesToDelete = new ArrayList<>();
    
    private Set<ProjectInspectAttachment> projectInspectAttachments = new HashSet<>(0);
    private List<ProjectInspectAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public ProjectInspect clone() {
        ProjectInspect p = null;
        try {
            p = (ProjectInspect) super.clone();
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
    @JoinColumn(name = "site_id", nullable = false)
    public Site getSite() {
        return this.site;
    }
    
    public void setSite(Site site) {
        this.site = site;
    }

//    @Transient
//    public List<Archive> getArchivesToDelete() {
//        return this.archivesToDelete;
//    }
//
//    public void setArchivesToDelete(List<Archive> archivesToDelete) {
//        this.archivesToDelete = archivesToDelete;
//    }

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "t_project_inspect_attachment",
    // joinColumns = {
    // @JoinColumn(name = "project_inspect_id", nullable = false, updatable = false)},
    // inverseJoinColumns = {
    // @JoinColumn(name = "archive_id", nullable = false, updatable = false)})
    // public Set<Archive> getArchives() {
    // return this.archives;
    // }
    //
    //
    // public void setArchives(Set<Archive> archives) {
    // this.archives = archives;
    // }
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectInspect")
    public Set<ProjectInspectAttachment> getProjectInspectAttachments() {
        return this.projectInspectAttachments;
    }

    
    public void setProjectInspectAttachments(Set<ProjectInspectAttachment> projectInspectAttachments) {
        this.projectInspectAttachments = projectInspectAttachments;
    }

    @Transient
    public List<ProjectInspectAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    
    public void setAttachmentsToDelete(List<ProjectInspectAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspect_type", nullable = false)
    public DataOption getInspectType() {
        return this.inspectType;
    }

    public void setInspectType(DataOption inspectType) {
        this.inspectType = inspectType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "inspect_plan", nullable = true, length = 1024)
    public String getInspectPlan() {
        return this.inspectPlan;
    }


    public void setInspectPlan(String inspectPlan) {
        this.inspectPlan = inspectPlan;
    }

    @Column(name = "inspect_area", nullable = true, length = 1024)
    public String getInspectArea() {
        return this.inspectArea;
    }


    public void setInspectArea(String inspectArea) {
        this.inspectArea = inspectArea;
    }

    @Column(name = "inspect_basis", nullable = true, length = 1024)
    public String getInspectBasis() {
        return this.inspectBasis;
    }


    public void setInspectBasis(String inspectBasis) {
        this.inspectBasis = inspectBasis;
    }

    @Column(name = "inspect_content", nullable = true, length = 1024)
    public String getInspectContent() {
        return this.inspectContent;
    }


    public void setInspectContent(String inspectContent) {
        this.inspectContent = inspectContent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector", nullable = true)
    public Person getInspector() {
        return this.inspector;
    }


    public void setInspector(Person inspector) {
        this.inspector = inspector;
    }

    @Column(name = "is_improve", nullable = true)
    public Short getIsImprove() {
        return this.isImprove;
    }


    public void setIsImprove(Short isImprove) {
        this.isImprove = isImprove;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspect_result", nullable = true)
    public DataOption getInspectResult() {
        return this.inspectResult;
    }


    public void setInspectResult(DataOption inspectResult) {
        this.inspectResult = inspectResult;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verify_result", nullable = true)
    public DataOption getVerifyResult() {
        return this.verifyResult;
    }


    public void setVerifyResult(DataOption verifyResult) {
        this.verifyResult = verifyResult;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inspect_date", nullable = true, length = 29)
    public Date getInspectDate() {
        return this.inspectDate;
    }


    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "improve_req_date", nullable = true, length = 29)
    public Date getImproveReqDate() {
        return this.improveReqDate;
    }


    public void setImproveReqDate(Date improveReqDate) {
        this.improveReqDate = improveReqDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "improve_plan_date", nullable = true, length = 29)
    public Date getImprovePlanDate() {
        return this.improvePlanDate;
    }


    public void setImprovePlanDate(Date improvePlanDate) {
        this.improvePlanDate = improvePlanDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "improve_actual_date", nullable = true, length = 29)
    public Date getImproveActualDate() {
        return this.improveActualDate;
    }


    public void setImproveActualDate(Date improveActualDate) {
        this.improveActualDate = improveActualDate;
    }

    @Column(name = "inspect_result_content", nullable = true, length = 1024)
    public String getInspectResultContent() {
        return this.inspectResultContent;
    }


    public void setInspectResultContent(String inspectResultContent) {
        this.inspectResultContent = inspectResultContent;
    }

    @Column(name = "improve_req", nullable = true, length = 1024)
    public String getImproveReq() {
        return this.improveReq;
    }


    public void setImproveReq(String improveReq) {
        this.improveReq = improveReq;
    }

    @Column(name = "improve_plan", nullable = true, length = 1024)
    public String getImprovePlan() {
        return this.improvePlan;
    }


    public void setImprovePlan(String improvePlan) {
        this.improvePlan = improvePlan;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verify_date", nullable = true, length = 29)
    public Date getVerifyDate() {
        return this.verifyDate;
    }


    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    @Column(name = "improve_result_verify", nullable = true, length = 1024)
    public String getImproveResultVerify() {
        return this.improveResultVerify;
    }


    public void setImproveResultVerify(String improveResultVerify) {
        this.improveResultVerify = improveResultVerify;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspect_status", nullable = true)
    public DataOption getInspectStatus() {
        return this.inspectStatus;
    }

    
    public void setInspectStatus(DataOption inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

}
