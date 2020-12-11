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
@Table(name = "t_budget_estimate_version")
public class BudgetEstimateVersion implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = -2024381850357632673L;

    private String id = UUID.randomUUID().toString();

	private Project project;
	private Integer year;
	private String version;
	private String name;
	private ProjectGroup group;
	private BudgetEstimateMain budgetMain;
	private String creator;
	private Date cDatetime;
	private String modifier;
	private Date mDatetime;

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

	@Column(name = "version", nullable = false, length = 50)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
	public ProjectGroup getGroup() {
		return this.group;
	}

	public void setGroup(ProjectGroup group) {
		this.group = group;
	}

	@Column(name = "creator", nullable = false, length = 50)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_id", nullable = false)
	public BudgetEstimateMain getBudgetMain() {
		return this.budgetMain;
	}

	public void setBudgetMain(BudgetEstimateMain budgetMain) {
		this.budgetMain = budgetMain;
	}

}
