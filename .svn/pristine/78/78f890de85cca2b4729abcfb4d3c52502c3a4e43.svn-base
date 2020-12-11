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

@Entity
@Table(name = "t_purchase_order")
public class PurchaseOrder implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 3914787818241614691L;

    private String id = UUID.randomUUID().toString();

    private String purchaseOrderNo;
    private Project project;
    private Person applicant;
    private String applicantContact;
    private String purchaseReason;
    private String receivableCompany;
    private DataOption paymentType;
    private Double totalAmount;
    private int flowStatus;
    private DataOption purchaseType;
    private DataOption purchaseKind;
    private Person auditApplicant;
    private int dataSource;
    private String extId;
    private String purchaseName;
    private VirtualOrg virtualOrg;
    private Person inputApplicant;
    private Date recordTime;
    private Date paymentTime;
    private Double paymentRate;
    private Double paymentAmount;
    private String remark;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<PurchaseOrderAttachment> purchaseOrderAttachments = new HashSet<>(0);
    private List<PurchaseOrderAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public PurchaseOrder clone() {
        PurchaseOrder p = null;
        try {
            p = (PurchaseOrder) super.clone();
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

    @Column(name = "purchase_order_no", nullable = false, length = 36)
    public String getPurchaseOrderNo() {
        return this.purchaseOrderNo;
    }


    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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
    @JoinColumn(name = "applicant", nullable = false)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }

    @Column(name = "applicant_contact", nullable = true, length = 100)
    public String getApplicantContact() {
        return this.applicantContact;
    }


    public void setApplicantContact(String applicantContact) {
        this.applicantContact = applicantContact;
    }

    @Column(name = "purchase_reason", nullable = true)
    public String getPurchaseReason() {
        return this.purchaseReason;
    }


    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    @Column(name = "receivable_company", nullable = true, length = 50)
    public String getReceivableCompany() {
        return this.receivableCompany;
    }


    public void setReceivableCompany(String receivableCompany) {
        this.receivableCompany = receivableCompany;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type", nullable = true)
    public DataOption getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(DataOption paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "total_amount", nullable = true)
    public Double getTotalAmount() {
        return this.totalAmount;
    }


    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Column(name = "flow_status", nullable = false)
    public int getFlowStatus() {
        return this.flowStatus;
    }


    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_type", nullable = true)
    public DataOption getPurchaseType() {
        return this.purchaseType;
    }


    public void setPurchaseType(DataOption purchaseType) {
        this.purchaseType = purchaseType;
    }
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_kind", nullable = true)
    public DataOption getPurchaseKind() {
        return this.purchaseKind;
    }


    public void setPurchaseKind(DataOption purchaseKind) {
        this.purchaseKind = purchaseKind;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_applicant", nullable = true)
    public Person getAuditApplicant() {
        return this.auditApplicant;
    }


    public void setAuditApplicant(Person auditApplicant) {
        this.auditApplicant = auditApplicant;
    }

    @Column(name = "data_source", nullable = true)
    public int getDataSource() {
        return this.dataSource;
    }


    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }

    
    @Column(name = "ext_id", nullable = true, length = 36)
    public String getExtId() {
        return this.extId;
    }


    public void setExtId(String extId) {
        this.extId = extId;
    }

    ;
    @Column(name = "purchase_name", nullable = true, length = 500)
    public String getPurchaseName() {
        return this.purchaseName;
    }


    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = true)
    public VirtualOrg getVirtualOrg() {
        return this.virtualOrg;
    }

    
    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "input_applicant", nullable = true)
    public Person getInputApplicant() {
        return this.inputApplicant;
    }


    public void setInputApplicant(Person inputApplicant) {
        this.inputApplicant = inputApplicant;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "record_time", nullable = true, length = 29)
    public Date getRecordTime() {
        return this.recordTime;
    }


    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_time", nullable = true, length = 29)
    public Date getPaymentTime() {
        return this.paymentTime;
    }


    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Column(name = "payment_rate", nullable = true)
    public Double getPaymentRate() {
        return this.paymentRate;
    }


    public void setPaymentRate(Double paymentRate) {
        this.paymentRate = paymentRate;
    }

    @Column(name = "payment_amount", nullable = true)
    public Double getPaymentAmount() {
        return this.paymentAmount;
    }


    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    
    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
    public Set<PurchaseOrderAttachment> getPurchaseOrderAttachments() {
        return purchaseOrderAttachments;
    }

    public void setPurchaseOrderAttachments(Set<PurchaseOrderAttachment> purchaseOrderAttachments) {
        this.purchaseOrderAttachments = purchaseOrderAttachments;
    }

    @Transient
    public List<PurchaseOrderAttachment> getAttachmentsToDelete() {
        return attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<PurchaseOrderAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

}
