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
@Table(name = "t_budget_final")
public class BudgetFinal implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = -2024381850357632673L;

    private String id = UUID.randomUUID().toString();

	private Project project;
	private String code;
	private String orderNumber;
	private String name;
	private VirtualOrg group;
	private String measurementUnit;
	private Double count;
	private Double taxInclusivePrice;
	private Double taxTotalPrice;
	private Integer year;
	private Double reimburseAmount;
	private Integer reimburseCount;
	private Double balance;
    private String versionId;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	
	private String auditStatus;
	private Date auditDatetime;

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
	
	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "reimburse_amount", nullable = true)
	public Double getReimburseAmount() {
		return this.reimburseAmount;
	}

	public void setReimburseAmount(Double reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	@Column(name = "reimburse_count", nullable = true)
	public Integer getReimburseCount() {
		return this.reimburseCount;
	}

	public void setReimburseCount(Integer reimburseCount) {
		this.reimburseCount = reimburseCount;
	}

	@Column(name = "balance", nullable = true)
	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Column(name = "version_id", nullable = true, length = 36)
    public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	
}
