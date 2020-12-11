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
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_tendering_plan_relatedunit")
public class TenderingPlanRelatedunit implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8084370642187605532L;

    private String id = UUID.randomUUID().toString();
    private TenderingPlan tenderingPlan;
    private RelatedUnit relatedUnit;
    private Project project;
    private Integer tenderingResult;
    private Date tenderWinTime;
    private String creator;
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
    @JoinColumn(name = "tendering_plan_id", nullable = false)
	public TenderingPlan getTenderingPlan() {
		return this.tenderingPlan;
	}

	public void setTenderingPlan(TenderingPlan tenderingPlan) {
		this.tenderingPlan = tenderingPlan;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_unit_id", nullable = false)
    public RelatedUnit getRelatedUnit() {
        return this.relatedUnit;
    }

	public void setRelatedUnit(RelatedUnit relatedUnit) {
        this.relatedUnit = relatedUnit;
    }

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }
    
	@Column(name = "tendering_result", nullable = true)
	public Integer getTenderingResult() {
		return tenderingResult;
	}

	public void setTenderingResult(Integer tenderingResult) {
		this.tenderingResult = tenderingResult;
	}


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tender_win_time", nullable = true, length = 29)
    public Date getTenderWinTime() {
        return this.tenderWinTime;
    }


    public void setTenderWinTime(Date tenderWinTime) {
        this.tenderWinTime = tenderWinTime;
    }
	
    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
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
    @Column(name = "m_datetime", nullable = true, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }
}
