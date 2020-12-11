package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * 保存项目成员的表现层DTO。
 */
public class ProjectPersonBean implements ViewBean {

	private String id;
	private String project_;
	private String projectText;
	private String virtualOrg_;
	private String virtualOrgText;
	private String person_;
	private String personText;
	private String projectRole_;
	private String projectRoleText;
	private String remark;
	private String creator_;
	private String creatorText;
	private String cDatetime;
	private String modifier_;
	private String modifierText;
	private String mDatetime;
	private int isOnDuty_;
	private String isOnDutyText;

	@Override
	public String getId() {
		return this.id;
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

	public String getPerson_() {
		return this.person_;
	}

	public void setPerson_(String person_) {
		this.person_ = person_;
	}

	public String getPersonText() {
		return this.personText;
	}

	public void setPersonText(String personText) {
		this.personText = personText;
	}

	public String getProjectRole_() {
		return this.projectRole_;
	}

	public void setProjectRole_(String projectRole_) {
		this.projectRole_ = projectRole_;
	}

	public String getProjectRoleText() {
		return this.projectRoleText;
	}

	public void setProjectRoleText(String projectRoleText) {
		this.projectRoleText = projectRoleText;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVirtualOrg_() {
		return this.virtualOrg_;
	}

	public void setVirtualOrg_(String virtualOrg_) {
		this.virtualOrg_ = virtualOrg_;
	}

	public String getVirtualOrgText() {
		return this.virtualOrgText;
	}

	public void setVirtualOrgText(String virtualOrgText) {
		this.virtualOrgText = virtualOrgText;
	}

	public String getCreator_() {
		return creator_;
	}

	public void setCreator_(String creator_) {
		this.creator_ = creator_;
	}

	public String getCreatorText() {
		return creatorText;
	}

	public void setCreatorText(String creatorText) {
		this.creatorText = creatorText;
	}

	public String getcDatetime() {
		return cDatetime;
	}

	public void setcDatetime(String cDatetime) {
		this.cDatetime = cDatetime;
	}

	public String getModifier_() {
		return modifier_;
	}

	public void setModifier_(String modifier_) {
		this.modifier_ = modifier_;
	}

	public String getModifierText() {
		return modifierText;
	}

	public void setModifierText(String modifierText) {
		this.modifierText = modifierText;
	}

	public String getmDatetime() {
		return mDatetime;
	}

	public void setmDatetime(String mDatetime) {
		this.mDatetime = mDatetime;
	}

	public int getIsOnDuty_() {
		return isOnDuty_;
	}

	public void setIsOnDuty_(int isOnDuty_) {
		this.isOnDuty_ = isOnDuty_;
	}

	public String getIsOnDutyText() {
		return isOnDutyText;
	}

	public void setIsOnDutyText(String isOnDutyText) {
		this.isOnDutyText = isOnDutyText;
	}

}
