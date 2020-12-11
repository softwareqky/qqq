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
@Table(name = "t_project_check")
public class ProjectCheck implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -6834798312591002616L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private DataOption checkType;
    private DataOption checkKind;
    private String checkingUnit;
    private String chechedUnit;
    private String checkedContent;
    private String checkBasis;
    private String checker;
    private Short isImprove;
    private String notification;
    private String additionalExplanation;
    private DataOption checkResult;
    private DataOption verifyResult;
    private Date checkDate;
    private Date improveReqDate;
    private Date improvePlanDate;
    private Date improveActualDate;
    private String checkResultContent;
    private String improveReq;
    private String improvePlan;
    private Date verifyDate;
    private String improveResultVerify;
    private String remark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;
    private DataOption checkStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    // private Set<Archive> archives = new HashSet<Archive>(0);
    // private List<Archive> archivesToDelete = new ArrayList<>();
    private Set<ProjectCheckAttachment> projectCheckAttachments = new HashSet<>(0);
    private List<ProjectCheckAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public ProjectCheck clone() {
        ProjectCheck p = null;
        try {
            p = (ProjectCheck) super.clone();
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_kind", nullable = false)
    public DataOption getCheckKind() {
        return this.checkKind;
    }


    public void setCheckKind(DataOption checkKind) {
        this.checkKind = checkKind;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_type", nullable = false)
    public DataOption getCheckType() {
        return this.checkType;
    }


    public void setCheckType(DataOption checkType) {
        this.checkType = checkType;
    }

    @Column(name = "checking_unit", nullable = true, length = 50)
    public String getCheckingUnit() {
        return this.checkingUnit;
    }


    public void setCheckingUnit(String checkingUnit) {
        this.checkingUnit = checkingUnit;
    }

    @Column(name = "cheched_unit", nullable = true, length = 50)
    public String getChechedUnit() {
        return this.chechedUnit;
    }


    public void setChechedUnit(String chechedUnit) {
        this.chechedUnit = chechedUnit;
    }

    @Column(name = "checked_content", nullable = true, length = 1024)
    public String getCheckedContent() {
        return this.checkedContent;
    }


    public void setCheckedContent(String checkedContent) {
        this.checkedContent = checkedContent;
    }

    @Column(name = "check_basis", nullable = true, length = 1024)
    public String getCheckBasis() {
        return this.checkBasis;
    }


    public void setCheckBasis(String checkBasis) {
        this.checkBasis = checkBasis;
    }

    @Column(name = "checker", nullable = true, length = 50)
    public String getChecker() {
        return this.checker;
    }


    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Column(name = "is_improve", nullable = true)
    public Short getIsImprove() {
        return this.isImprove;
    }


    public void setIsImprove(Short isImprove) {
        this.isImprove = isImprove;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_result", nullable = true)
    public DataOption getCheckResult() {
        return this.checkResult;
    }


    public void setCheckResult(DataOption checkResult) {
        this.checkResult = checkResult;
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
    @Column(name = "check_date", nullable = true, length = 29)
    public Date getCheckDate() {
        return this.checkDate;
    }


    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
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

    @Column(name = "check_result_content", nullable = true, length = 1024)
    public String getCheckResultContent() {
        return this.checkResultContent;
    }


    public void setCheckResultContent(String checkResultContent) {
        this.checkResultContent = checkResultContent;
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

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "t_project_person_change_attachment",
    // joinColumns = {@JoinColumn(name = "project_person_change_id", nullable = false,
    // updatable = false)},
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
    //
    // @Transient
    // public List<Archive> getArchivesToDelete() {
    // return this.archivesToDelete;
    // }
    //
    // public void setArchivesToDelete(List<Archive> archivesToDelete) {
    // this.archivesToDelete = archivesToDelete;
    // }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectCheck")
    public Set<ProjectCheckAttachment> getProjectCheckAttachments() {
        return this.projectCheckAttachments;
    }


    public void setProjectCheckAttachments(Set<ProjectCheckAttachment> projectCheckAttachments) {
        this.projectCheckAttachments = projectCheckAttachments;
    }

    @Transient
    public List<ProjectCheckAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }


    public void setAttachmentsToDelete(List<ProjectCheckAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }


    @Column(name = "notification", nullable = true, length = 1024)
    public String getNotification() {
        return this.notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Column(name = "additional_explanation", nullable = true, length = 1024)
    public String getAdditionalExplanation() {
        return this.additionalExplanation;
    }


    public void setAdditionalExplanation(String additionalExplanation) {
        this.additionalExplanation = additionalExplanation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_status", nullable = true)
    public DataOption getCheckStatus() {
        return this.checkStatus;
    }


    public void setCheckStatus(DataOption checkStatus) {
        this.checkStatus = checkStatus;
    }

}
