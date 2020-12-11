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
@Table(name = "t_acceptance_check")
public class AcceptanceCheck implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1054640336002815496L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private DataOption acceptanceCheckType;
    private DataOption acceptanceCheckKind;
    private String checkingUnit;
    private String chechedUnit;
    private String checkedContent;
    private String checkBasis;
    private ProjectPerson checker;
    private Short isImprove;
    private DataOption acceptanceCheckResult;
    private DataOption acceptanceVerifyResult;
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
    private DataOption acceptanceCheckStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    
    private Set<AcceptanceCheckAttachment> acceptanceCheckAttachments = new HashSet<>(0);
    private List<AcceptanceCheckAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public AcceptanceCheck clone() {
        AcceptanceCheck p = null;
        try {
            p = (AcceptanceCheck) super.clone();
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
    @JoinColumn(name = "acceptance_check_type", nullable = true)
    public DataOption getAcceptanceCheckType() {
        return this.acceptanceCheckType;
    }


    public void setAcceptanceCheckType(DataOption acceptanceCheckType) {
        this.acceptanceCheckType = acceptanceCheckType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptance_check_kind", nullable = true)
    public DataOption getAcceptanceCheckKind() {
        return this.acceptanceCheckKind;
    }


    public void setAcceptanceCheckKind(DataOption acceptanceCheckKind) {
        this.acceptanceCheckKind = acceptanceCheckKind;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "checker", nullable = true)
    public ProjectPerson getChecker() {
        return this.checker;
    }


    public void setChecker(ProjectPerson checker) {
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
    @JoinColumn(name = "acceptance_check_result", nullable = true)
    public DataOption getAcceptanceCheckResult() {
        return this.acceptanceCheckResult;
    }


    public void setAcceptanceCheckResult(DataOption acceptanceCheckResult) {
        this.acceptanceCheckResult = acceptanceCheckResult;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptance_verify_result", nullable = true)
    public DataOption getAcceptanceVerifyResult() {
        return this.acceptanceVerifyResult;
    }


    public void setAcceptanceVerifyResult(DataOption acceptanceVerifyResult) {
        this.acceptanceVerifyResult = acceptanceVerifyResult;
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

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptance_check_status", nullable = true)
    public DataOption getAcceptanceCheckStatus() {
        return this.acceptanceCheckStatus;
    }

    
    public void setAcceptanceCheckStatus(DataOption acceptanceCheckStatus) {
        this.acceptanceCheckStatus = acceptanceCheckStatus;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acceptanceCheck")
    public Set<AcceptanceCheckAttachment> getAcceptanceCheckAttachments() {
        return this.acceptanceCheckAttachments;
    }

    
    public void setAcceptanceCheckAttachments(
            Set<AcceptanceCheckAttachment> acceptanceCheckAttachments) {
        this.acceptanceCheckAttachments = acceptanceCheckAttachments;
    }

    @Transient
    public List<AcceptanceCheckAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    
    public void setAttachmentsToDelete(List<AcceptanceCheckAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
