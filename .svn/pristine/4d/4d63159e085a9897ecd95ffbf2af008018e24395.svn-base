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
@Table(name = "t_capital_plan")
public class CapitalPlan implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7661677146943576397L;

    private String id = UUID.randomUUID().toString();
    
    private Project project;

    private String firstClassify;

    private String secondClassify;

    private String thirdClassify;

    private Double capitalPlan;

    private String remarks;

    private VirtualOrg group;

    private int year;
    
    private String versionId;

    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
	private String auditStatus;
	private Date auditDatetime;

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
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "first_classify", nullable = false, length = 50)
	public String getFirstClassify() {
		return this.firstClassify;
	}

	public void setFirstClassify(String firstClassify) {
		this.firstClassify = firstClassify;
	}

	@Column(name = "second_classify", nullable = false, length = 50)
	public String getSecondClassify() {
		return this.secondClassify;
	}

	public void setSecondClassify(String secondClassify) {
		this.secondClassify = secondClassify;
	}

	@Column(name = "third_classify", nullable = false, length = 50)
	public String getThirdClassify() {
		return this.thirdClassify;
	}

	public void setThirdClassify(String thirdClassify) {
		this.thirdClassify = thirdClassify;
	}

	@Column(name = "capital_plan", nullable = true)
    public Double getCapitalPlan() {
        return capitalPlan;
    }

    public void setCapitalPlan(Double capitalPlan) {
        this.capitalPlan = capitalPlan;
    }

    @Column(name = "remarks", nullable = true, length = 500)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
    public VirtualOrg getGroup() {
		return this.group;
	}

	public void setGroup(VirtualOrg group) {
		this.group = group;
	}

    @Column(name = "year", nullable = false)
    public int getYear() {
        return year;
    }

	public void setYear(int year) {
        this.year = year;
    }
	
	@Column(name = "version_id", nullable = true, length = 36)
    public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
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
    
	@Column(name = "audit_status", nullable = true)
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "audit_datetime", nullable = true)
	public Date getAuditDatetime() {
		return auditDatetime;
	}

	public void setAuditDatetime(Date auditDatetime) {
		this.auditDatetime = auditDatetime;
	}
}
