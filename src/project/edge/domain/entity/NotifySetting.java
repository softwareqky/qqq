package project.edge.domain.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_notify_setting")
public class NotifySetting implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4652727502978675842L;

	private String id = UUID.randomUUID().toString();
	
	private Integer notifyTargetType;
	
	private Integer daysInAdvance;
	
	private Integer notifyWay;
	
	private String comment;

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

	@Column(name = "notify_target_type", nullable = true)
	public int getNotifyTargetType() {
		return notifyTargetType;
	}

	public void setNotifyTargetType(int notifyTargetType) {
		this.notifyTargetType = notifyTargetType;
	}

	@Column(name = "days_in_advance", nullable = true)
	public int getDaysInAdvance() {
		return daysInAdvance;
	}

	public void setDaysInAdvance(int daysInAdvance) {
		this.daysInAdvance = daysInAdvance;
	}

	@Column(name = "notify_way", nullable = true)
	public int getNotifyWay() {
		return notifyWay;
	}

	public void setNotifyWay(int notifyWay) {
		this.notifyWay = notifyWay;
	}

	@Column(name = "comment", nullable = true)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
