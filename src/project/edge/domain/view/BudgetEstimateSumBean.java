/**
 * 
 */
package project.edge.domain.view;

import java.math.BigDecimal;
import java.util.Date;

import garage.origin.domain.view.ViewBean;

/**
 * @author wwy 保存总体预算规划信息的表现层DTO。
 */
public class BudgetEstimateSumBean implements ViewBean {

	private String id;
	private String project_;
	private String projectText;
	private String code;
	private String innerCode;
	private String orderNumber;
	private String name;
	private String keyPerformance;
	private String brand;
	private int level;
	private String unit;
	private Double count;
	private Double taxInclusivePrice;
	private Double taxExclusivePrice;
	private Double taxInclusivePriceTotal;
	private String remarks;
	private String version;
	private Integer year;
	private String taxPoint;
	private String deviceClassify;
	private Integer personMonth;
	private Double personPrice;
	private String group_;
	private String groupText;
	private BigDecimal budgetFee;
	private BigDecimal balance;
	private String warning;
	private int feeCount;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject_() {
		return this.project_;
	}
	public void setProject_(String project_) {
		this.project_ = project_;
	}
	public String getProjectText() {
		return this.projectText;
	}
	public void setProjectText(String projectText) {
		this.projectText = projectText;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInnerCode() {
		return this.innerCode;
	}
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	public String getOrderNumber() {
		return this.orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyPerformance() {
		return this.keyPerformance;
	}
	public void setKeyPerformance(String keyPerformance) {
		this.keyPerformance = keyPerformance;
	}
	public String getBrand() {
		return this.brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getUnit() {
		return this.unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getCount() {
		return this.count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	public Double getTaxInclusivePrice() {
		return this.taxInclusivePrice;
	}
	public void setTaxInclusivePrice(Double taxInclusivePrice) {
		this.taxInclusivePrice = taxInclusivePrice;
	}
	public Double getTaxExclusivePrice() {
		return this.taxExclusivePrice;
	}
	public void setTaxExclusivePrice(Double taxExclusivePrice) {
		this.taxExclusivePrice = taxExclusivePrice;
	}
	public Double getTaxInclusivePriceTotal() {
		return this.taxInclusivePriceTotal;
	}
	public void setTaxInclusivePriceTotal(Double taxInclusivePriceTotal) {
		this.taxInclusivePriceTotal = taxInclusivePriceTotal;
	}
	public String getRemarks() {
		return this.remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getVersion() {
		return this.version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getYear() {
		return this.year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getTaxPoint() {
		return this.taxPoint;
	}
	public void setTaxPoint(String taxPoint) {
		this.taxPoint = taxPoint;
	}
	public String getDeviceClassify() {
		return this.deviceClassify;
	}
	public void setDeviceClassify(String deviceClassify) {
		this.deviceClassify = deviceClassify;
	}
	public Integer getPersonMonth() {
		return this.personMonth;
	}
	public void setPersonMonth(Integer personMonth) {
		this.personMonth = personMonth;
	}
	public Double getPersonPrice() {
		return this.personPrice;
	}
	public void setPersonPrice(Double personPrice) {
		this.personPrice = personPrice;
	}
	public String getGroup_() {
		return this.group_;
	}
	public void setGroup_(String group_) {
		this.group_ = group_;
	}
	public String getGroupText() {
		return this.groupText;
	}
	public void setGroupText(String groupText) {
		this.groupText = groupText;
	}
	public String getCreator() {
		return this.creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getcDatetime() {
		return this.cDatetime;
	}
	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}
	public String getModifier() {
		return this.modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getmDatetime() {
		return this.mDatetime;
	}
	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}
	public BigDecimal getBudgetFee() {
		return this.budgetFee;
	}
	public void setBudgetFee(BigDecimal budgetFee) {
		this.budgetFee = budgetFee;
	}
	public BigDecimal getBalance() {
		return this.balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getWarning() {
		return this.warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	public int getFeeCount() {
		return this.feeCount;
	}
	public void setFeeCount(int feeCount) {
		this.feeCount = feeCount;
	}
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
