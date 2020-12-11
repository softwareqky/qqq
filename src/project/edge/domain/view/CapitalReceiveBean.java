/**
 * 
 */
package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;

/**
 * @author wwy 保存资金下达的表现层DTO。
 */
public class CapitalReceiveBean implements ViewBean {

	private String id;
    private String capitalApply_;
    private String capitalApplyText;
    private String project_;
	private Integer year;
	private String name;
	private Double receiveMoney;
	private String receiveTime;
    private String source_;
    private String sourceText;
	private String remark;
    private String creator_;
    private String creatorText;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCapitalApply_() {
		return this.capitalApply_;
	}
	public void setCapitalApply_(String capitalApply_) {
		this.capitalApply_ = capitalApply_;
	}
	public String getCapitalApplyText() {
		return this.capitalApplyText;
	}
	public void setCapitalApplyText(String capitalApplyText) {
		this.capitalApplyText = capitalApplyText;
	}
    public String getProject_() {
        return this.project_;
    }
    public void setProject_(String project_) {
        this.project_ = project_;
    }
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getYear() {
		return this.year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Double getReceiveMoney() {
		return this.receiveMoney;
	}
	public void setReceiveMoney(Double receiveMoney) {
		this.receiveMoney = receiveMoney;
	}
	public String getReceiveTime() {
		return this.receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getSource_() {
		return this.source_;
	}
	public void setSource_(String source_) {
		this.source_ = source_;
	}
	public String getSourceText() {
		return this.sourceText;
	}
	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}
