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
@Table(name = "t_purchase_order_detail")
public class PurchaseOrderDetail implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -2149249463623777781L;

    private String id = UUID.randomUUID().toString();

    private PurchaseOrder purchaseOrder;
    private Project project;
    private String parentBudgetname;
    private String materialName;
    private BudgetEstimateSum budgetEstimateSum;
    private String specificationType;
    private int purchaseQuantity;
    private String measurementUnit;
    private double unitPrice;
    private double sumMoney;
    private Double paymentNum;
    private Double paymentAmount;
    private Double totalPaymentNum;
    private Double totalPaymentAmount;
    private String usageInfo;
    private DataOption purchaseKind;
    private Province province;
    private City city;
    private Site site;
    private Date reserveTime;
    private Person applicant;    
    private String remark;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    private int flowStatus;

    private Set<PurchaseOrderDetailAttachment> purchaseOrderDetailAttachments = new HashSet<>(0);
    private List<PurchaseOrderDetailAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public PurchaseOrderDetail clone() {
        PurchaseOrderDetail p = null;
        try {
            p = (PurchaseOrderDetail) super.clone();
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
    @JoinColumn(name = "purchase_order_id", nullable = false)
    public PurchaseOrder getPurchaseOrder() {
        return this.purchaseOrder;
    }


    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }
    
    @Column(name = "parent_budgetname", nullable = true, length = 50)
    public String getParentBudgetname() {
        return this.parentBudgetname;
    }


    public void setParentBudgetname(String parentBudgetname) {
        this.parentBudgetname = parentBudgetname;
    }

    
    @Column(name = "material_name", nullable = true, length = 50)
    public String getMaterialName() {
        return this.materialName;
    }


    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Column(name = "specification_type", nullable = true, length = 50)
    public String getSpecificationType() {
        return this.specificationType;
    }


    public void setSpecificationType(String specificationType) {
        this.specificationType = specificationType;
    }

    @Column(name = "measurement_unit", nullable = false, length = 50)
    public String getMeasurementUnit() {
        return this.measurementUnit;
    }


    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    @Column(name = "purchase_quantity", nullable = false)
    public int getPurchaseQuantity() {
        return this.purchaseQuantity;
    }


    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    @Column(name = "unit_price", nullable = false)
    public double getUnitPrice() {
        return this.unitPrice;
    }


    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "usage_info", nullable = false)
    public String getUsageInfo() {
        return this.usageInfo;
    }


    public void setUsageInfo(String usageInfo) {
        this.usageInfo = usageInfo;
    }

    @Column(name = "sum_money", nullable = false)
    public double getSumMoney() {
        return this.sumMoney;
    }


    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    @Column(name = "payment_num", nullable = true)
    public Double getPaymentNum() {
        return this.paymentNum;
    }


    public void setPaymentNum(Double paymentNum) {
        this.paymentNum = paymentNum;
    }

    @Column(name = "payment_amount", nullable = true)
    public Double getPaymentAmount() {
        return this.paymentAmount;
    }


    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Column(name = "total_payment_num", nullable = true)
    public Double getTotalPaymentNum() {
        return this.totalPaymentNum;
    }


    public void setTotalPaymentNum(Double totalPaymentNum) {
        this.totalPaymentNum = totalPaymentNum;
    }

    @Column(name = "total_payment_amount", nullable = true)
    public Double getTotalPaymentAmount() {
        return this.totalPaymentAmount;
    }


    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
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
    @JoinColumn(name = "applicant", nullable = true)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reserve_time", nullable = true, length = 29)
    public Date getReserveTime() {
        return this.reserveTime;
    }


    public void setReserveTime(Date reserveTime) {
        this.reserveTime = reserveTime;
    }


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = true)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = true)
    public Province getProvince() {
        return this.province;
    }


    public void setProvince(Province province) {
        this.province = province;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = true)
    public City getCity() {
        return this.city;
    }


    public void setCity(City city) {
        this.city = city;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrderDetail")
    public Set<PurchaseOrderDetailAttachment> getPurchaseOrderDetailAttachments() {
        return this.purchaseOrderDetailAttachments;
    }

    
    public void setPurchaseOrderDetailAttachments(
            Set<PurchaseOrderDetailAttachment> purchaseOrderDetailAttachments) {
        this.purchaseOrderDetailAttachments = purchaseOrderDetailAttachments;
    }

    @Transient
    public List<PurchaseOrderDetailAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    
    public void setAttachmentsToDelete(List<PurchaseOrderDetailAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }
    
    @Column(name = "flow_status", nullable = false)
    public int getFlowStatus() {
        return this.flowStatus;
    }


    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "budget_estimate_sum", nullable = true)
	public BudgetEstimateSum getBudgetEstimateSum() {
		return budgetEstimateSum;
	}

	public void setBudgetEstimateSum(BudgetEstimateSum budgetEstimateSum) {
		this.budgetEstimateSum = budgetEstimateSum;
	}

}
