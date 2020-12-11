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
@Table(name = "t_virtual_org")
public class VirtualOrg implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4189769937287604576L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private String virtualOrgName;
    private Person leader;
    private String shortName;
    private String orgCode;
    private VirtualOrg pid;
    private int level;
    private int orgOrder;
    private short isDeleted;
    private int syncStatus;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    private int isEchartsShow;
    private int isBudgetMerge;
    private int isGetAllBudget;

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
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "virtual_org_name", nullable = false, length = 50)
    public String getVirtualOrgName() {
        return this.virtualOrgName;
    }

    public void setVirtualOrgName(String virtualOrgName) {
        this.virtualOrgName = virtualOrgName;
    }

    @Column(name = "short_name", nullable = true, length = 25)
    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "org_code", nullable = true, length = 25)
    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid", nullable = true)
    public VirtualOrg getPid() {
        return this.pid;
    }

    public void setPid(VirtualOrg pid) {
        this.pid = pid;
    }

    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "org_order", nullable = false)
    public int getOrgOrder() {
        return this.orgOrder;
    }

    public void setOrgOrder(int orgOrder) {
        this.orgOrder = orgOrder;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Column(name = "sync_status", nullable = false)
    public int getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
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
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

    @Column(name = "is_echarts_show", nullable = true)
	public int getIsEchartsShow() {
		return isEchartsShow;
	}

	public void setIsEchartsShow(int isEchartsShow) {
		this.isEchartsShow = isEchartsShow;
	}
	
    @Column(name = "is_budget_merge", nullable = true)
    public int getIsBudgetMerge() {
		return this.isBudgetMerge;
	}

	public void setIsBudgetMerge(int isBudgetMerge) {
		this.isBudgetMerge = isBudgetMerge;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader", nullable = true)
    public Person getLeader() {
        return this.leader;
    }

	public void setLeader(Person leader) {
        this.leader = leader;
    }

	@Column(name = "is_get_all_budget", nullable = true)
	public int getIsGetAllBudget() {
		return this.isGetAllBudget;
	}

	public void setIsGetAllBudget(int isGetAllBudget) {
		this.isGetAllBudget = isGetAllBudget;
	}
}
