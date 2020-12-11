package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_budget_mainfee")
public class BudgetMainfee implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1232810593223234341L;

    private String id = UUID.randomUUID().toString();
    private String mainid;
    private Person requestPerson;
    private VirtualOrg virtualOrg;
    private String workflowName;
    private String approvalId;
    private Person approver;
    private Date approvalTime;
    private Date requestTime;
    private BigDecimal payRatio;
    private BigDecimal payAmount;
    private String payType;
    private String purchaseNo;
    private String stockNo;
    private String creator;
    private Date cDatetime;
    private int dataSource;

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

    @Column(name = "mainid", nullable = true, length = 100)
    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_person", nullable = true)
    public Person getRequestPerson() {
        return requestPerson;
    }

    public void setRequestPerson(Person requestPerson) {
        this.requestPerson = requestPerson;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = true)
    public VirtualOrg getVirtualOrg() {
        return virtualOrg;
    }

    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
    }

    @Column(name = "workflow_name", nullable = true, length = 100)
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
	
	@Column(name = "approval_id", nullable = true, length = 255)
	public String getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver", nullable = true)
	public Person getApprover() {
		return this.approver;
	}

	public void setApprover(Person approver) {
		this.approver = approver;
	}

	@Column(name = "approval_time", nullable = true, length = 255)
	public Date getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time", nullable = true)
    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    @Column(name = "pay_type", nullable = true, length = 100)
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Column(name = "purchase_no", nullable = true, length = 100)
    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }


    @Column(name = "stock_no", nullable = true, length = 100)
    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
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
    
    @Column(name = "pay_ratio", nullable = true, length = 17)
    public BigDecimal getPayRatio() {
        return payRatio;
    }

    public void setPayRatio(BigDecimal payRatio) {
        this.payRatio = payRatio;
    }
    
    @Column(name = "pay_amount", nullable = true, length = 17)
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    @Column(name = "data_source", nullable = true)
    public int getDataSource() {
        return this.dataSource;
    }


    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }
}
