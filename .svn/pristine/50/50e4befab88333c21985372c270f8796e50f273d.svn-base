/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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


/**
 * @author wwy
 *
 */
@Entity
@Table(name = "t_budget_estimate_main")
public class BudgetEstimateMain implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = -2024381850357632673L;

    private String id = UUID.randomUUID().toString();

	private Project project;
	private VirtualOrg group;
	private Integer year;
	private Integer isAll;
	private String version;
	private Double budgetTotal;
	private Person creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;
	private String remarks;
	
    private Date flowStartDate;
	private Date flowEndDate;
	private int flowStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "flow_start_date", nullable = true, length = 29)
	public Date getFlowStartDate() {
		return this.flowStartDate;
	}

	public void setFlowStartDate(Date flowStartDate) {
		this.flowStartDate = flowStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "flow_end_date", nullable = true, length = 29)
	public Date getFlowEndDate() {
		return this.flowEndDate;
	}

	public void setFlowEndDate(Date flowEndDate) {
		this.flowEndDate = flowEndDate;
	}

	@Column(name = "flow_status", nullable = false)
	public int getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

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
	@JoinColumn(name = "project_id", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
	public VirtualOrg getGroup() {
		return this.group;
	}

	public void setGroup(VirtualOrg group) {
		this.group = group;
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
		return cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	@Column(name = "modifier", nullable = true, length = 50)
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "m_datetime", nullable = false, length = 29)
	public Date getmDatetime() {
		return mDatetime;
	}

	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}


	@Column(name = "year", nullable = false, length = 4)
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	
	@Column(name = "is_all", nullable = true, length = 4)
	public Integer getIsAll() {
		return this.isAll;
	}

	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}

	@Column(name = "version", nullable = true, length = 36)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "budget_total", nullable = true)
	public Double getBudgetTotal() {
		return this.budgetTotal;
	}

	public void setBudgetTotal(Double budgetTotal) {
		this.budgetTotal = budgetTotal;
	}

	@Column(name = "remarks", nullable = true, length = 500)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
