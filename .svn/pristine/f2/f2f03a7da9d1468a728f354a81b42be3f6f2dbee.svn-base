package project.edge.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_notify_subscription")
public class NotifySubscription implements Serializable, Cloneable {

	private static final long serialVersionUID = -2349969317205570441L;
	
	private String id = UUID.randomUUID().toString();
	private String targetId;
	private Integer targetType;
	private String action;
	private String userId;
	private Date applyAfter;
	private Date createAt;
	
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
	
	@Column(name = "target_id", nullable = false, length = 36)
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Column(name = "target_type", nullable = false, length = 20)
	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	@Column(name = "action", nullable = false, length = 20)
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "user_id", nullable = false, length = 36)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = true, length = 29)
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apply_after", nullable = true, length = 29)
	public Date getApplyAfter() {
		return applyAfter;
	}

	public void setApplyAfter(Date applyAfter) {
		this.applyAfter = applyAfter;
	}
	
}
