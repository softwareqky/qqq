/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author wwy
 *
 */
@Entity
@Table(name = "t_budget_estimate")
public class BudgetEstimate implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = -2024381850357632673L;

    private String id = UUID.randomUUID().toString();

    private BudgetEstimateMain budgetEstimateMain;
	private Project project;
	private String code;
	private String innerCode;
	private String orderNumber;
	private String name;
	private String keyPerformance;
	private String brand;
	private VirtualOrg group;
	private String measurementUnit;
	private Double count;
	private Double taxInclusivePrice;
	private Double taxExcludingPrice;
	private Double taxTotalPrice;
	private String remarks;
	private Integer year;
	private String taxPoint;
	private String deviceClassify;
	private String timeInterval;
	private Integer personMonth;
	private Double personPrice;
	private String version;
	private String planRemark;
	private Integer level;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	
	private String auditStatus;
	private Date auditDatetime;
	
	private Double paymentAmount;
	private Double paymentCount;
	private String paymentPercent;
	private Double payAmountTotal;
	
	private DataOption budgetType;

	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_estimate_main_id", nullable = false)
	public BudgetEstimateMain getBudgetEstimateMain() {
		return this.budgetEstimateMain;
	}

	public void setBudgetEstimateMain(BudgetEstimateMain budgetEstimateMain) {
		this.budgetEstimateMain = budgetEstimateMain;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "key_performance", nullable = true, length = 1000)
	public String getKeyPerformance() {
		return this.keyPerformance;
	}

	public void setKeyPerformance(String keyPerformance) {
		this.keyPerformance = keyPerformance;
	}

	@Column(name = "brand", nullable = true, length = 1000)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "count", nullable = true, length = 11)
	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	@Column(name = "tax_inclusive_price", nullable = true)
	public Double getTaxInclusivePrice() {
		return taxInclusivePrice;
	}

	public void setTaxInclusivePrice(Double taxInclusivePrice) {
		this.taxInclusivePrice = taxInclusivePrice;
	}

	@Column(name = "tax_exclusive_price", nullable = true)
	public Double getTaxExcludingPrice() {
		return taxExcludingPrice;
	}

	public void setTaxExcludingPrice(Double taxExcludingPrice) {
		this.taxExcludingPrice = taxExcludingPrice;
	}

	@Column(name = "remarks", nullable = true, length = 500)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "version", nullable = true, length = 50)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "creator", nullable = false, length = 50)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_datetime", nullable = false, length = 29)
	public Date getcDatetime() {
		return cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	@Column(name = "modifier", nullable = true, length = 50)
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "m_datetime", nullable = false, length = 29)
	public Date getmDatetime() {
		return mDatetime;
	}

	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}

	@Column(name = "code", nullable = false, length = 36)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "inner_code", nullable = true, length = 36)
	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	@Column(name = "order_number", nullable = true, length = 36)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
	public VirtualOrg getGroup() {
		return this.group;
	}

	public void setGroup(VirtualOrg group) {
		this.group = group;
	}

	@Column(name = "measurement_unit", nullable = true, length = 50)
	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	@Column(name = "tax_total_price", nullable = true)
	public Double getTaxTotalPrice() {
		return taxTotalPrice;
	}

	public void setTaxTotalPrice(Double taxTotalPrice) {
		this.taxTotalPrice = taxTotalPrice;
	}

	@Column(name = "year", nullable = true, length = 4)
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "tax_point", nullable = true, length = 36)
	public String getTaxPoint() {
		return taxPoint;
	}

	public void setTaxPoint(String taxPoint) {
		this.taxPoint = taxPoint;
	}

    @Column(name = "device_classify", nullable = true)
	public String getDeviceClassify() {
		return deviceClassify;
	}

	public void setDeviceClassify(String deviceClassify) {
		this.deviceClassify = deviceClassify;
	}

	@Column(name = "time_interval", nullable = true, length = 36)
	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	@Column(name = "person_month", nullable = true, length = 11)
	public Integer getPersonMonth() {
		return personMonth;
	}

	public void setPersonMonth(Integer personMonth) {
		this.personMonth = personMonth;
	}

	@Column(name = "person_price", nullable = true)
	public Double getPersonPrice() {
		return personPrice;
	}

	public void setPersonPrice(Double personPrice) {
		this.personPrice = personPrice;
	}
	
	@Column(name = "audit_status", nullable = true)
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "audit_datetime", nullable = true)
	public Date getAuditDatetime() {
		return auditDatetime;
	}

	public void setAuditDatetime(Date auditDatetime) {
		this.auditDatetime = auditDatetime;
	}

	@Column(name = "plan_remark", nullable = true)
	public String getPlanRemark() {
		return this.planRemark;
	}

	public void setPlanRemark(String planRemark) {
		this.planRemark = planRemark;
	}

	@Column(name = "level", nullable = true)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "payment_amount", nullable = true)
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Column(name = "payment_percent", nullable = true, length = 10)
	public String getPaymentPercent() {
		return paymentPercent;
	}

	public void setPaymentPercent(String paymentPercent) {
		this.paymentPercent = paymentPercent;
	}

	@Column(name = "payment_count", nullable = true)
	public Double getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Double paymentCount) {
		this.paymentCount = paymentCount;
	}

	@Column(name = "pay_amount_total", nullable = true)
	public Double getPayAmountTotal() {
		return this.payAmountTotal;
	}

	public void setPayAmountTotal(Double payAmountTotal) {
		this.payAmountTotal = payAmountTotal;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_type", nullable = true)
	public DataOption getBudgetType() {
		return this.budgetType;
	}

	public void setBudgetType(DataOption budgetType) {
		this.budgetType = budgetType;
	}
	
}
