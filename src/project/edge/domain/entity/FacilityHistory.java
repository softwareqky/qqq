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

@Entity
@Table(name = "t_facility_history")
public class FacilityHistory implements Serializable {

	
	private static final long serialVersionUID = 3925323537185596896L;

	private String id = UUID.randomUUID().toString();
	
	private Person createBy;
	private Date createAt;
	private Integer createType;
	private String content;
	private String targetId;
	private Integer targetType;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", nullable = true)
	public Person getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(Person createBy) {
		this.createBy = createBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = false, length = 29)
	public Date getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	@Column(name = "create_type", nullable = false)
	public Integer getCreateType() {
		return createType;
	}
	
	public void setCreateType(Integer createType) {
		this.createType = createType;
	}

	@Column(name = "content", nullable = true)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "target_id", nullable = false, length = 36)
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Column(name = "target_type", nullable = false)
	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	
	
}
