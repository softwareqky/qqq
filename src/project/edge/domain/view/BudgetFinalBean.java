/**
 * 
 */
package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;


/**
 * @author wwy
 *         保存资金计划构成表信息的表现层DTO。
 */
public class BudgetFinalBean implements ViewBean {

	private String id;
	
	private String project_;
	private String projectText;
	private String code;
	private String orderNumber;
	private String name;
	private String unit;
	private Double count;
	private Double taxInclusivePrice;
	private Double taxInclusivePriceTotal;
	private Integer year;
	private String group_;
	private String groupText;
	private Double reimburseAmount;
	private Integer reimburseCount;
	private Double balance;
	private String versionId;
	
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

	public String getId() {
		return id;
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
		return projectText;
	}
	
	public void setProjectText(String projectText) {
		this.projectText = projectText;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Double getTaxInclusivePrice() {
		return taxInclusivePrice;
	}

	public void setTaxInclusivePrice(Double taxInclusivePrice) {
		this.taxInclusivePrice = taxInclusivePrice;
	}

	public Double getTaxInclusivePriceTotal() {
		return taxInclusivePriceTotal;
	}

	public void setTaxInclusivePriceTotal(Double taxInclusivePriceTotal) {
		this.taxInclusivePriceTotal = taxInclusivePriceTotal;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getcDatetime() {
		return cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getmDatetime() {
		return mDatetime;
	}

	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getGroup_() {
		return this.group_;
	}

	public void setGroup_(String group_) {
		this.group_ = group_;
	}

	public String getGroupText() {
		return groupText;
	}

	public void setGroupText(String groupText) {
		this.groupText = groupText;
	}

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

	public Double getReimburseAmount() {
		return this.reimburseAmount;
	}

	public void setReimburseAmount(Double reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	public Integer getReimburseCount() {
		return this.reimburseCount;
	}

	public void setReimburseCount(Integer reimburseCount) {
		this.reimburseCount = reimburseCount;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
    public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
}
