/**
 * 
 */
package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;


/**
 * @author wwy
 *         预算规划版本表信息的表现层DTO。
 */
public class BudgetEstimateMainBean implements ViewBean {

	private String id;
	private String project_;
	private String projectText;
	private Integer year;
	private Integer isAll;
	private String isAllText;
	private String group_;
	private String groupText;
	private String version;
	private Double budgetTotal;
	private String creator_;
	private String creatorText;
	private String cDatetime;
	private String modifier;
	private String mDatetime;
	
    private String flowStartDate;
	private String flowEndDate;
	private int flowStatus;
	private String flowStatusText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProject_() {
		return project_;
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

	public String getCreator_() {
		return this.creator_;
	}

	public void setCreator_(String creator_) {
		this.creator_ = creator_;
	}

	public String getCreatorText() {
		return this.creatorText;
	}

	public void setCreatorText(String creatorText) {
		this.creatorText = creatorText;
	}

	public String getcDatetime() {
		return this.cDatetime;
	}

	public void setcDatetime(String cDatetime) {
		this.cDatetime = cDatetime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getmDatetime() {
		return mDatetime;
	}

	public void setmDatetime(String mDatetime) {
		this.mDatetime = mDatetime;
	}

	public Integer getIsAll() {
		return this.isAll;
	}

	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}
	
	public String getIsAllText() {
		return this.isAllText;
	}

	public void setIsAllText(String isAllText) {
		this.isAllText = isAllText;
	}

	public String getFlowStartDate() {
		return this.flowStartDate;
	}

	public void setFlowStartDate(String flowStartDate) {
		this.flowStartDate = flowStartDate;
	}

	public String getFlowEndDate() {
		return this.flowEndDate;
	}

	public void setFlowEndDate(String flowEndDate) {
		this.flowEndDate = flowEndDate;
	}

	public int getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowStatusText() {
		return this.flowStatusText;
	}

	public void setFlowStatusText(String flowStatusText) {
		this.flowStatusText = flowStatusText;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Double getBudgetTotal() {
		return this.budgetTotal;
	}

	public void setBudgetTotal(Double budgetTotal) {
		this.budgetTotal = budgetTotal;
	}

}
