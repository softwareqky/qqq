/**
 * 
 */
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

/**
 * @author wwy
 *
 */
@Entity
@Table(name = "t_capital_receive")
public class CapitalReceive implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768735084291714738L;

	private String id = UUID.randomUUID().toString();

	private CapitalApply capitalApply;
	private Project project;
	private Integer year;
	private String name;
	private Double receiveMoney;
	private Date receiveTime;
	private DataOption source;
	private String remark;
	private Person creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

	@GenericGenerator(name = "generator", strategy = "assigned")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id", nullable = false)
	public CapitalApply getCapitalApply() {
		return this.capitalApply;
	}

	public void setCapitalApply(CapitalApply capitalApply) {
		this.capitalApply = capitalApply;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
	
	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", nullable = false)
	public Person getCreator() {
		return this.creator;
	}

	public void setCreator(Person creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_datetime", nullable = false, length = 29)
	public Date getcDatetime() {
		return this.cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	@Column(name = "modifier", nullable = true, length = 50)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "m_datetime", nullable = false, length = 29)
	public Date getmDatetime() {
		return this.mDatetime;
	}

	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}

	@Column(name = "year", nullable = false, length = 4)
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "receive_money", nullable = false)
	public Double getReceiveMoney() {
		return this.receiveMoney;
	}

	public void setReceiveMoney(Double receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "receive_time", nullable = true, length = 29)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source", nullable = true)
	public DataOption getSource() {
		return this.source;
	}

	public void setSource(DataOption source) {
		this.source = source;
	}

	@Column(name = "remark", nullable = true)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
