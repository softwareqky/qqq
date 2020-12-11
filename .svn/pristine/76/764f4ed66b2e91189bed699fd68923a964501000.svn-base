/**
 * 
 */
package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

/**
 * @author wwy
 *
 */
@Entity
@Table(name = "t_budget_estimate_sum")
public class BudgetEstimateSum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768735084291714738L;

	private String id = UUID.randomUUID().toString();

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
	private Integer level;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

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

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "key_performance", nullable = false, length = 1000)
	public String getKeyPerformance() {
		return this.keyPerformance;
	}

	public void setKeyPerformance(String keyPerformance) {
		this.keyPerformance = keyPerformance;
	}

	@Column(name = "brand", nullable = false, length = 1000)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "count", nullable = true, length = 11)
	public Double getCount() {
		return this.count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	@Column(name = "tax_inclusive_price", nullable = true)
	public Double getTaxInclusivePrice() {
		return this.taxInclusivePrice;
	}

	public void setTaxInclusivePrice(Double taxInclusivePrice) {
		this.taxInclusivePrice = taxInclusivePrice;
	}

	@Column(name = "tax_exclusive_price", nullable = true)
	public Double getTaxExcludingPrice() {
		return this.taxExcludingPrice;
	}

	public void setTaxExcludingPrice(Double taxExcludingPrice) {
		this.taxExcludingPrice = taxExcludingPrice;
	}

	@Column(name = "remarks", nullable = true, length = 500)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "version", nullable = true, length = 50)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	@Column(name = "code", nullable = false, length = 36)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "inner_code", nullable = true, length = 36)
	public String getInnerCode() {
		return this.innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	@Column(name = "order_number", nullable = true, length = 36)
	public String getOrderNumber() {
		return this.orderNumber;
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
		return this.measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	@Column(name = "tax_total_price", nullable = true)
	public Double getTaxTotalPrice() {
		return this.taxTotalPrice;
	}

	public void setTaxTotalPrice(Double taxTotalPrice) {
		this.taxTotalPrice = taxTotalPrice;
	}

	@Column(name = "year", nullable = true, length = 4)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "tax_point", nullable = true, length = 36)
	public String getTaxPoint() {
		return this.taxPoint;
	}

	public void setTaxPoint(String taxPoint) {
		this.taxPoint = taxPoint;
	}

    @Column(name = "device_classify", nullable = true)
	public String getDeviceClassify() {
		return this.deviceClassify;
	}

	public void setDeviceClassify(String deviceClassify) {
		this.deviceClassify = deviceClassify;
	}

	@Column(name = "time_interval", nullable = true, length = 36)
	public String getTimeInterval() {
		return this.timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	@Column(name = "person_month", nullable = true, length = 11)
	public Integer getPersonMonth() {
		return this.personMonth;
	}

	public void setPersonMonth(Integer personMonth) {
		this.personMonth = personMonth;
	}

	@Column(name = "person_price", nullable = true)
	public Double getPersonPrice() {
		return this.personPrice;
	}

	public void setPersonPrice(Double personPrice) {
		this.personPrice = personPrice;
	}
	
	@Column(name = "level", nullable = true)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
