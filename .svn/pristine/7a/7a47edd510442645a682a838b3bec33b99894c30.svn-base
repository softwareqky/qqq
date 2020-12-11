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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "t_review")
public class Review implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8736228984379186829L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private DataOption reviewType;
    private String reviewingUnit;
    private String reviewedUnit;
    private String reviewContent;
    private String reviewBasis;
    private ProjectPerson reviewer;
    private Short isImprove;
    private DataOption reviewResult;
    private DataOption verifyResult;
    private Date reviewDate;
    private Date improveReqDate;
    private Date improvePlanDate;
    private Date improveActualDate;
    private String reviewResultContent;
    private String improveReq;
    private String improvePlan;
    private Date verifyDate;
    private String improveResultVerify;
    private String remark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;
    private DataOption reviewStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<ReviewAttachment> reviewAttachments = new HashSet<>(0);
    private List<ReviewAttachment> attachmentsToDelete = new ArrayList<>();
    
    private Set<Expert> experts = new HashSet<Expert>(0);

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
    @JoinColumn(name = "review_type", nullable = false)
    public DataOption getReviewType() {
        return this.reviewType;
    }


    public void setReviewType(DataOption reviewType) {
        this.reviewType = reviewType;
    }

    @Column(name = "reviewing_unit", nullable = true, length = 50)
    public String getReviewingUnit() {
        return this.reviewingUnit;
    }


    public void setReviewingUnit(String reviewingUnit) {
        this.reviewingUnit = reviewingUnit;
    }

    @Column(name = "reviewed_unit", nullable = true, length = 50)
    public String getReviewedUnit() {
        return this.reviewedUnit;
    }


    public void setReviewedUnit(String reviewedUnit) {
        this.reviewedUnit = reviewedUnit;
    }

    @Column(name = "review_content", nullable = true, length = 1024)
    public String getReviewContent() {
        return this.reviewContent;
    }


    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    @Column(name = "review_basis", nullable = true, length = 1024)
    public String getReviewBasis() {
        return this.reviewBasis;
    }


    public void setReviewBasis(String reviewBasis) {
        this.reviewBasis = reviewBasis;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer", nullable = true)
    public ProjectPerson getReviewer() {
        return this.reviewer;
    }


    public void setReviewer(ProjectPerson reviewer) {
        this.reviewer = reviewer;
    }

    @Column(name = "is_improve", nullable = true)
    public Short getIsImprove() {
        return this.isImprove;
    }


    public void setIsImprove(Short isImprove) {
        this.isImprove = isImprove;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_result", nullable = true)
    public DataOption getReviewResult() {
        return this.reviewResult;
    }


    public void setReviewResult(DataOption reviewResult) {
        this.reviewResult = reviewResult;
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
    @Column(name = "review_date", nullable = true, length = 29)
    public Date getReviewDate() {
        return this.reviewDate;
    }


    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
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

    @Column(name = "review_result_content", nullable = true, length = 1024)
    public String getReviewResultContent() {
        return this.reviewResultContent;
    }


    public void setReviewResultContent(String reviewResultContent) {
        this.reviewResultContent = reviewResultContent;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_review_expert",
            joinColumns = {@JoinColumn(name = "review_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "expert_id", nullable = false, updatable = false)})
    public Set<Expert> getExperts() {
        return this.experts;
    }


    public void setExperts(Set<Expert> experts) {
        this.experts = experts;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_status", nullable = true)
    public DataOption getReviewStatus() {
        return this.reviewStatus;
    }

    
    public void setReviewStatus(DataOption reviewStatus) {
        this.reviewStatus = reviewStatus;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review")
    public Set<ReviewAttachment> getReviewAttachments() {
        return this.reviewAttachments;
    }


    public void setReviewAttachments(Set<ReviewAttachment> reviewAttachments) {
        this.reviewAttachments = reviewAttachments;
    }

    @Transient
    public List<ReviewAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }


    public void setAttachmentsToDelete(List<ReviewAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }
}
