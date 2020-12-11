package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

public class NotifySettingBean implements ViewBean {

	private String id;
	
	private Integer notifyTargetType;
	private String notifyTargetTypeText;
	
	private Integer daysInAdvance;
	
	private Integer notifyWay;
	private String notifyWayText;
	
	private String comment;

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNotifyTargetType() {
		return notifyTargetType;
	}

	public void setNotifyTargetType(Integer notifyTargetType) {
		this.notifyTargetType = notifyTargetType;
	}

	public Integer getDaysInAdvance() {
		return daysInAdvance;
	}

	public void setDaysInAdvance(Integer daysInAdvance) {
		this.daysInAdvance = daysInAdvance;
	}

	public Integer getNotifyWay() {
		return notifyWay;
	}

	public void setNotifyWay(Integer notifyWay) {
		this.notifyWay = notifyWay;
	}

	public String getNotifyTargetTypeText() {
		return notifyTargetTypeText;
	}

	public void setNotifyTargetTypeText(String notifyTargetTypeText) {
		this.notifyTargetTypeText = notifyTargetTypeText;
	}

	public String getNotifyWayText() {
		return notifyWayText;
	}

	public void setNotifyWayText(String notifyWayText) {
		this.notifyWayText = notifyWayText;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
