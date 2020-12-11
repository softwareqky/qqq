package project.edge.domain.view;

import java.math.BigDecimal;
import java.util.Date;
import garage.origin.domain.view.ViewBean;


public class BudgetFeeBean implements ViewBean {

    private String id;
    private String mainfee_;
    private String mainfeeText;
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
    private String approvalTime;
    private String creator;
    private String cDatetime;
    private String modifier;
    private Date mDatetime;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMainfee_() {
        return this.mainfee_;
    }



    public void setMainfee_(String mainfee_) {
        this.mainfee_ = mainfee_;
    }



    public String getMainfeeText() {
        return this.mainfeeText;
    }



    public void setMainfeeText(String mainfeeText) {
        this.mainfeeText = mainfeeText;
    }
    
    public String getBh() {
        return bh;
    }
    
    public void setBh(String bh) {
        this.bh = bh;
    }
    
    public String getZymc() {
        return zymc;
    }
    
    public void setZymc(String zymc) {
        this.zymc = zymc;
    }
    
    public String getDw() {
        return dw;
    }
    
    public void setDw(String dw) {
        this.dw = dw;
    }
    
    public Integer getSl() {
        return sl;
    }
    
    public void setSl(Integer sl) {
        this.sl = sl;
    }
    
    public BigDecimal getDjy() {
        return djy;
    }
    
    public void setDjy(BigDecimal djy) {
        this.djy = djy;
    }
    
    public BigDecimal getBxjey() {
        return bxjey;
    }
    
    public void setBxjey(BigDecimal bxjey) {
        this.bxjey = bxjey;
    }
    
    public String getZdmc() {
        return zdmc;
    }
    
    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }
    
    public String getFyxm() {
        return fyxm;
    }
    
    public void setFyxm(String fyxm) {
        this.fyxm = fyxm;
    }
    
    public String getBz() {
        return bz;
    }
    
    public void setBz(String bz) {
        this.bz = bz;
    }
    
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
	
	public String getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public String getcDatetime() {
		return this.cDatetime;
	}

	public void setcDatetime(String cDatetime) {
		this.cDatetime = cDatetime;
	}

	public String getModifier() {
        return modifier;
    }
    
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    
    public Date getmDatetime() {
        return mDatetime;
    }
    
    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }
} 
