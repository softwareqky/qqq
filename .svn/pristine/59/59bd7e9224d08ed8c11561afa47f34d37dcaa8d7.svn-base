package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_tendering_plan")
public class TenderingPlan implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 5138028359730931375L;

    private String id = UUID.randomUUID().toString();

    //private PurchaseOrder purchaseOrder;
    private Project project;
    private DataOption tenderingType;
    private String tenderingNo;
    private String tenderingName;
    private Person tenderingLeader;
    private Dept tenderingDept;
    private VirtualOrg virtualOrg;
    private String tenderingMethod;
    private String evaluatingMethods;
    private String approvalNumber;
    private Integer tenderingStatus;
    private Integer priceType;
    private String priceUnit;
    private Double estimatedPrice;
    private String tenderScope;
    private Date planStartTime;
    private Date planEndTime;
    private Date tenderOpenTime;
    private Date tenderOpenEndTime;
    private Date tenderAssessmentStartTime;
    private Date tenderAssessmentEndTime;
    private String remark;
    private Person applicant;
    private Date recordTime;
    private Date flowStartDate;
    private Date flowEndDate;
    private Integer flowStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<TenderingPlanAttachment> archives = new HashSet<>(0);
    private List<TenderingPlanAttachment> archivesToDelete = new ArrayList<>();
    
    private Set<TenderingPurchase> purchases = new HashSet<>(0);

    @Override
    public TenderingPlan clone() {
        TenderingPlan p = null;
        try {
            p = (TenderingPlan) super.clone();
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "purchase_order_id", nullable = true)
//    public PurchaseOrder getPurchaseOrder() {
//        return this.purchaseOrder;
//    }
//
//
//    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
//        this.purchaseOrder = purchaseOrder;
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tendering_type", nullable = false)
    public DataOption getTenderingType() {
        return this.tenderingType;
    }


    public void setTenderingType(DataOption tenderingType) {
        this.tenderingType = tenderingType;
    }

    @Column(name = "tendering_no", nullable = false, length = 36)
    public String getTenderingNo() {
        return this.tenderingNo;
    }


    public void setTenderingNo(String tenderingNo) {
        this.tenderingNo = tenderingNo;
    }

    @Column(name = "tendering_name", nullable = true, length = 36)
    public String getTenderingName() {
        return this.tenderingName;
    }


    public void setTenderingName(String tenderingName) {
        this.tenderingName = tenderingName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tendering_leader", nullable = true)
    public Person getTenderingLeader() {
        return this.tenderingLeader;
    }


    public void setTenderingLeader(Person tenderingLeader) {
        this.tenderingLeader = tenderingLeader;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tendering_dept", nullable = true)
    public Dept getTenderingDept() {
        return this.tenderingDept;
    }


    public void setTenderingDept(Dept tenderingDept) {
        this.tenderingDept = tenderingDept;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = false)
    public VirtualOrg getVirtualOrg() {
        return this.virtualOrg;
    }


    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
    }

    @Column(name = "tendering_method", nullable = true, length = 36)
    public String getTenderingMethod() {
        return this.tenderingMethod;
    }


    public void setTenderingMethod(String tenderingMethod) {
        this.tenderingMethod = tenderingMethod;
    }

    @Column(name = "evaluating_methods", nullable = true, length = 36)
    public String getEvaluatingMethods() {
        return this.evaluatingMethods;
    }


    public void setEvaluatingMethods(String evaluatingMethods) {
        this.evaluatingMethods = evaluatingMethods;
    }

    @Column(name = "approval_number", nullable = true, length = 36)
    public String getApprovalNumber() {
        return this.approvalNumber;
    }


    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    @Column(name = "tendering_status", nullable = true)
    public Integer getTenderingStatus() {
        return this.tenderingStatus;
    }


    public void setTenderingStatus(Integer tenderingStatus) {
        this.tenderingStatus = tenderingStatus;
    }

    @Column(name = "price_type", nullable = true)
    public Integer getPriceType() {
        return this.priceType;
    }


    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    @Column(name = "price_unit", nullable = true, length = 36)
    public String getPriceUnit() {
        return this.priceUnit;
    }


    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    @Column(name = "estimated_price", nullable = true)
    public Double getEstimatedPrice() {
        return this.estimatedPrice;
    }


    public void setEstimatedPrice(Double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    @Column(name = "tender_scope", nullable = true, length = 500)
    public String getTenderScope() {
        return this.tenderScope;
    }


    public void setTenderScope(String tenderScope) {
        this.tenderScope = tenderScope;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "plan_start_time", nullable = true, length = 29)
    public Date getPlanStartTime() {
        return this.planStartTime;
    }


    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "plan_end_time", nullable = true, length = 29)
    public Date getPlanEndTime() {
        return this.planEndTime;
    }


    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tender_open_time", nullable = true, length = 29)
    public Date getTenderOpenTime() {
        return this.tenderOpenTime;
    }


    public void setTenderOpenTime(Date tenderOpenTime) {
        this.tenderOpenTime = tenderOpenTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tender_open_end_time", nullable = true, length = 29)
    public Date getTenderOpenEndTime() {
        return this.tenderOpenEndTime;
    }


    public void setTenderOpenEndTime(Date tenderOpenEndTime) {
        this.tenderOpenEndTime = tenderOpenEndTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tender_assessment_start_time", nullable = true, length = 29)
    public Date getTenderAssessmentStartTime() {
        return this.tenderAssessmentStartTime;
    }


    public void setTenderAssessmentStartTime(Date tenderAssessmentStartTime) {
        this.tenderAssessmentStartTime = tenderAssessmentStartTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tender_assessment_end_time", nullable = true, length = 29)
    public Date getTenderAssessmentEndTime() {
        return this.tenderAssessmentEndTime;
    }


    public void setTenderAssessmentEndTime(Date tenderAssessmentEndTime) {
        this.tenderAssessmentEndTime = tenderAssessmentEndTime;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant", nullable = true)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "record_time", nullable = false, length = 29)
    public Date getRecordTime() {
        return this.recordTime;
    }


    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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

    @Column(name = "flow_status", nullable = true)
    public Integer getFlowStatus() {
        return this.flowStatus;
    }


    public void setFlowStatus(Integer flowStatus) {
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


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenderingPlan")
    public Set<TenderingPlanAttachment> getArchives() {
 		return this.archives;
 	}

 	public void setArchives(Set<TenderingPlanAttachment> archives) {
 		this.archives = archives;
 	}
 	
    @Transient
	public List<TenderingPlanAttachment> getArchivesToDelete() {
		return this.archivesToDelete;
	}

	public void setArchivesToDelete(List<TenderingPlanAttachment> archivesToDelete) {
		this.archivesToDelete = archivesToDelete;
	}



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenderingPlan")
    public Set<TenderingPurchase> getPurchases() {
 		return this.purchases;
 	}
	
 	public void setPurchases(Set<TenderingPurchase> purchases) {
 		this.purchases = purchases;
 	}
}
