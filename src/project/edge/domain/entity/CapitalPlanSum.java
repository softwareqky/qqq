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
@Table(name = "t_capital_plan_sum")
public class CapitalPlanSum implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3011384914774774810L;

    private String id = UUID.randomUUID().toString();
    
    private Project project;

    private VirtualOrg group;

    private String target;

    private Double capitalPlan;

    private int year;

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
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    public VirtualOrg getGroup() {
		return this.group;
	}

	public void setGroup(VirtualOrg group) {
		this.group = group;
	}

    @Column(name = "target", nullable = false, length = 500)
    public String getTarget() {
        return target;
    }

	public void setTarget(String target) {
        this.target = target;
    }

    @Column(name = "capital_plan", nullable = false)
    public Double getCapitalPlan() {
        return capitalPlan;
    }

    public void setCapitalPlan(Double capitalPlan) {
        this.capitalPlan = capitalPlan;
    }

    @Column(name = "year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

}
