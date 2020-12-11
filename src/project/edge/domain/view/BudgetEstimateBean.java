/**
 * 
 */
package project.edge.domain.view;

import java.math.BigDecimal;
import java.util.Date;

import garage.origin.domain.view.ViewBean;


/**
 * @author wwy
 *         保存资金计划构成表信息的表现层DTO。
 */
public class BudgetEstimateBean implements ViewBean {

	private String id;
    private String budgetEstimateMain_;
    private String budgetEstimateMainText;
	private String project_;
	private String projectText;
	private String code;
	private String innerCode;
	private String orderNumber;
	private String name;
	private String keyPerformance;
	private String brand;
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
	private int level;
	
	private String capitalGroup;
	private String task2019;
	private String task2020;
	private String task2021;
	private String task2022;
	private String task2023;
	private BigDecimal budget2019;
	private BigDecimal budget2020;
	private BigDecimal budget2021;
	private BigDecimal budget2022;
	private BigDecimal budget2023;
	
	private Double capital2019;
	private Double capital2020;
	private Double capital2021;
	private Double capital2022;
	private Double capital2023;
	
	private String remark2019;
	private String remark2020;
	private String remark2021;
	private String remark2022;
	private String remark2023;
	private String oneLevelClassification;
	private String twoLevelClassification;
	private String threeLevelClassification;
	
	private String planRemark;
	private BigDecimal remainCount;
	private BigDecimal remainMoney;
	
	private Double count2019;
	private Double count2020;
	private Double count2021;
	private Double count2022;
	private Double count2023;
	private Double priceTotal2019;
	private Double priceTotal2020;
	private Double priceTotal2021;
	private Double priceTotal2022;
	private Double priceTotal2023;
	
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

	private Double paymentAmount;
	private String paymentPercent;
	private Double paymentCount;
	private String lastToThisCount;
	private String lastToThisPercent;
	private String lastToThisAmount;
	private String nextYearCount;
	private String nextYearAmount;
	private String nextYearPercent;
	private Double payAmountTotal;
	
    private String budgetType_;
    private String budgetTypeText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getBudgetEstimateMain_() {
		return this.budgetEstimateMain_;
	}

	public void setBudgetEstimateMain_(String budgetEstimateMain_) {
		this.budgetEstimateMain_ = budgetEstimateMain_;
	}

	public String getBudgetEstimateMainText() {
		return this.budgetEstimateMainText;
	}

	public void setBudgetEstimateMainText(String budgetEstimateMainText) {
		this.budgetEstimateMainText = budgetEstimateMainText;
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

	public Double getTaxExclusivePrice() {
		return taxExclusivePrice;
	}

	public void setTaxExclusivePrice(Double taxExclusivePrice) {
		this.taxExclusivePrice = taxExclusivePrice;
	}

	public String getName() {
		return name;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
	public String getTaxPoint() {
		return taxPoint;
	}

	public void setTaxPoint(String taxPoint) {
		this.taxPoint = taxPoint;
	}

	public String getDeviceClassify() {
		return deviceClassify;
	}

	public void setDeviceClassify(String deviceClassify) {
		this.deviceClassify = deviceClassify;
	}

	public Integer getPersonMonth() {
		return personMonth;
	}

	public void setPersonMonth(Integer personMonth) {
		this.personMonth = personMonth;
	}

	public Double getPersonPrice() {
		return personPrice;
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

	public String getCapitalGroup() {
		return this.capitalGroup;
	}

	public void setCapitalGroup(String capitalGroup) {
		this.capitalGroup = capitalGroup;
	}

	public String getTask2019() {
		return this.task2019;
	}

	public void setTask2019(String task2019) {
		this.task2019 = task2019;
	}

	public String getTask2020() {
		return this.task2020;
	}

	public void setTask2020(String task2020) {
		this.task2020 = task2020;
	}

	public String getTask2021() {
		return this.task2021;
	}

	public void setTask2021(String task2021) {
		this.task2021 = task2021;
	}

	public String getTask2022() {
		return this.task2022;
	}

	public void setTask2022(String task2022) {
		this.task2022 = task2022;
	}

	public String getTask2023() {
		return this.task2023;
	}

	public void setTask2023(String task2023) {
		this.task2023 = task2023;
	}

	public BigDecimal getBudget2019() {
		return this.budget2019;
	}

	public void setBudget2019(BigDecimal budget2019) {
		this.budget2019 = budget2019;
	}

	public BigDecimal getBudget2020() {
		return this.budget2020;
	}

	public void setBudget2020(BigDecimal budget2020) {
		this.budget2020 = budget2020;
	}

	public BigDecimal getBudget2021() {
		return this.budget2021;
	}

	public void setBudget2021(BigDecimal budget2021) {
		this.budget2021 = budget2021;
	}

	public BigDecimal getBudget2022() {
		return this.budget2022;
	}

	public void setBudget2022(BigDecimal budget2022) {
		this.budget2022 = budget2022;
	}

	public BigDecimal getBudget2023() {
		return this.budget2023;
	}

	public void setBudget2023(BigDecimal budget2023) {
		this.budget2023 = budget2023;
	}

	public String getRemark2019() {
		return this.remark2019;
	}

	public void setRemark2019(String remark2019) {
		this.remark2019 = remark2019;
	}

	public String getRemark2020() {
		return this.remark2020;
	}

	public void setRemark2020(String remark2020) {
		this.remark2020 = remark2020;
	}

	public String getRemark2021() {
		return this.remark2021;
	}

	public void setRemark2021(String remark2021) {
		this.remark2021 = remark2021;
	}

	public String getRemark2022() {
		return this.remark2022;
	}

	public void setRemark2022(String remark2022) {
		this.remark2022 = remark2022;
	}

	public String getRemark2023() {
		return this.remark2023;
	}

	public void setRemark2023(String remark2023) {
		this.remark2023 = remark2023;
	}

	public String getOneLevelClassification() {
		return this.oneLevelClassification;
	}

	public void setOneLevelClassification(String oneLevelClassification) {
		this.oneLevelClassification = oneLevelClassification;
	}

	public String getTwoLevelClassification() {
		return this.twoLevelClassification;
	}

	public void setTwoLevelClassification(String twoLevelClassification) {
		this.twoLevelClassification = twoLevelClassification;
	}

	public String getThreeLevelClassification() {
		return this.threeLevelClassification;
	}

	public void setThreeLevelClassification(String threeLevelClassification) {
		this.threeLevelClassification = threeLevelClassification;
	}

	public Double getCapital2019() {
		return this.capital2019;
	}

	public void setCapital2019(Double capital2019) {
		this.capital2019 = capital2019;
	}

	public Double getCapital2020() {
		return this.capital2020;
	}

	public void setCapital2020(Double capital2020) {
		this.capital2020 = capital2020;
	}

	public Double getCapital2021() {
		return this.capital2021;
	}

	public void setCapital2021(Double capital2021) {
		this.capital2021 = capital2021;
	}

	public Double getCapital2022() {
		return this.capital2022;
	}

	public void setCapital2022(Double capital2022) {
		this.capital2022 = capital2022;
	}

	public Double getCapital2023() {
		return this.capital2023;
	}

	public void setCapital2023(Double capital2023) {
		this.capital2023 = capital2023;
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

	public String getPlanRemark() {
		return this.planRemark;
	}

	public void setPlanRemark(String planRemark) {
		this.planRemark = planRemark;
	}

	public BigDecimal getRemainCount() {
		return this.remainCount;
	}

	public void setRemainCount(BigDecimal remainCount) {
		this.remainCount = remainCount;
	}

	public BigDecimal getRemainMoney() {
		return this.remainMoney;
	}

	public void setRemainMoney(BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}

	public Double getCount2019() {
		return this.count2019;
	}

	public void setCount2019(Double count2019) {
		this.count2019 = count2019;
	}

	public Double getCount2020() {
		return this.count2020;
	}

	public void setCount2020(Double count2020) {
		this.count2020 = count2020;
	}

	public Double getCount2021() {
		return this.count2021;
	}

	public void setCount2021(Double count2021) {
		this.count2021 = count2021;
	}

	public Double getCount2022() {
		return this.count2022;
	}

	public void setCount2022(Double count2022) {
		this.count2022 = count2022;
	}

	public Double getCount2023() {
		return this.count2023;
	}

	public void setCount2023(Double count2023) {
		this.count2023 = count2023;
	}

	public Double getPriceTotal2019() {
		return this.priceTotal2019;
	}

	public void setPriceTotal2019(Double priceTotal2019) {
		this.priceTotal2019 = priceTotal2019;
	}

	public Double getPriceTotal2020() {
		return this.priceTotal2020;
	}

	public void setPriceTotal2020(Double priceTotal2020) {
		this.priceTotal2020 = priceTotal2020;
	}

	public Double getPriceTotal2021() {
		return this.priceTotal2021;
	}

	public void setPriceTotal2021(Double priceTotal2021) {
		this.priceTotal2021 = priceTotal2021;
	}

	public Double getPriceTotal2022() {
		return this.priceTotal2022;
	}

	public void setPriceTotal2022(Double priceTotal2022) {
		this.priceTotal2022 = priceTotal2022;
	}

	public Double getPriceTotal2023() {
		return this.priceTotal2023;
	}

	public void setPriceTotal2023(Double priceTotal2023) {
		this.priceTotal2023 = priceTotal2023;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPaymentPercent() {
		return paymentPercent;
	}

	public void setPaymentPercent(String paymentPercent) {
		this.paymentPercent = paymentPercent;
	}

	public String getLastToThisCount() {
		return lastToThisCount;
	}

	public void setLastToThisCount(String lastToThisCount) {
		this.lastToThisCount = lastToThisCount;
	}

	public String getLastToThisPercent() {
		return lastToThisPercent;
	}

	public void setLastToThisPercent(String lastToThisPercent) {
		this.lastToThisPercent = lastToThisPercent;
	}

	public String getLastToThisAmount() {
		return lastToThisAmount;
	}

	public void setLastToThisAmount(String lastToThisAmount) {
		this.lastToThisAmount = lastToThisAmount;
	}

	public String getNextYearCount() {
		return nextYearCount;
	}

	public void setNextYearCount(String nextYearCount) {
		this.nextYearCount = nextYearCount;
	}

	public String getNextYearAmount() {
		return nextYearAmount;
	}

	public void setNextYearAmount(String nextYearAmount) {
		this.nextYearAmount = nextYearAmount;
	}

	public String getNextYearPercent() {
		return nextYearPercent;
	}

	public void setNextYearPercent(String nextYearPercent) {
		this.nextYearPercent = nextYearPercent;
	}

	public Double getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Double paymentCount) {
		this.paymentCount = paymentCount;
	}

	public Double getPayAmountTotal() {
		return this.payAmountTotal;
	}

	public void setPayAmountTotal(Double payAmountTotal) {
		this.payAmountTotal = payAmountTotal;
	}

	public String getBudgetType_() {
		return this.budgetType_;
	}

	public void setBudgetType_(String budgetType_) {
		this.budgetType_ = budgetType_;
	}

	public String getBudgetTypeText() {
		return this.budgetTypeText;
	}

	public void setBudgetTypeText(String budgetTypeText) {
		this.budgetTypeText = budgetTypeText;
	}

}
