package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name = "t_budget_fee")
public class BudgetFee implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4222810593423237340L;

    private String id = UUID.randomUUID().toString();
    private BudgetMainfee mainfee;
    private String bh;
    private String zymc;
    private String dw;
    private Integer sl;
    private BigDecimal djy;
    private BigDecimal bxjey;
    private String zdmc;
    private String fyxm;
    private String bz;
    private Integer mainid;
    private String lkr;
    private String gzz;
    private String approvalId;
    private String approver;
    private Date approvalTime;
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
    @JoinColumn(name = "mainfee_id", nullable = true)
    public BudgetMainfee getMainfee() {
        return mainfee;
    }

    public void setMainfee(BudgetMainfee mainfee) {
        this.mainfee = mainfee;
    }
    
    @Column(name = "bh", nullable = true, length = 1000)
    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    @Column(name = "zymc", nullable = true, length = 100)
    public String getZymc() {
        return zymc;
    }

    public void setZymc(String zymc) {
        this.zymc = zymc;
    }

    @Column(name = "dw", nullable = true, length = 100)
    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    @Column(name = "sl", nullable = true, length = 4)
    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    @Column(name = "djy", nullable = true, length = 17)
    public BigDecimal getDjy() {
        return djy;
    }

    public void setDjy(BigDecimal djy) {
        this.djy = djy;
    }

    @Column(name = "bxjey", nullable = true, length = 17)
    public BigDecimal getBxjey() {
        return bxjey;
    }

    public void setBxjey(BigDecimal bxjey) {
        this.bxjey = bxjey;
    }

    @Column(name = "zdmc", nullable = true, length = 1000)
    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    @Column(name = "fyxm", nullable = true, length = 1000)
    public String getFyxm() {
        return fyxm;
    }

    public void setFyxm(String fyxm) {
        this.fyxm = fyxm;
    }

    @Column(name = "bz", nullable = true, length = 100)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Column(name = "mainid", nullable = true, length = 4)
    public Integer getMainid() {
        return mainid;
    }

    public void setMainid(Integer mainid) {
        this.mainid = mainid;
    }
    
    public String getLkr() {
		return this.lkr;
	}

	public void setLkr(String lkr) {
		this.lkr = lkr;
	}

	public String getGzz() {
		return this.gzz;
	}

	public void setGzz(String gzz) {
		this.gzz = gzz;
	}
	
	@Column(name = "approval_id", nullable = true, length = 255)
	public String getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	@Column(name = "approver", nullable = true, length = 255)
	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Column(name = "approval_time", nullable = true, length = 255)
	public Date getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Column(name = "creator", nullable = true, length = 50)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = true)
    public Date getcDatetime() {
        return cDatetime;
    }

    public void setcDatetime(Date cdatetime) {
        this.cDatetime = cdatetime;
    }

    @Column(name = "modifier", nullable = true, length = 50)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = true)
    public Date getmDatetime() {
        return mDatetime;
    }

    public void setmDatetime(Date mdatetime) {
        this.mDatetime = mdatetime;
    }
}
