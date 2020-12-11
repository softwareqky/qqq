package project.edge.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_payment_contract")
public class PaymentContract implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1383449930987963212L;

    private String id = UUID.randomUUID().toString();

    private PurchaseOrder purchaseOrder;
    private Project project;
    private VirtualOrg virtualOrg;
    private String contractName;
    private String serialNumber;
    private String contractYear;
    private Integer contractCount;
    private double contractAmount;
    private Short isTemporaryPricing;
    private Double amountExcludingTax;
    private ContractCategory contractKind;
    private String contractAttribute;
    private Date contractTime;
    private String ontractAddress;
    private Date startTime;
    private Date endTime;
    private String partyA;
    private String partyB;
    private Person signingPeople;
    private String partyBContact;
    private String partyAContactInfo;
    private String partyBContactInfo;
    private String briefIntroduction;
    private DataOption sealType;
    private Short isIncludePendingData;
    private String virtualContract;
    private String contractReturnInfo;
    private String countersignatureReturn;
    private String mainProvisions;
    private String remark;
    private Person entryPerson;
    private Date entryTime;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    private Integer dataSource;
    private String extId;
    private Date flowStartDate;
	private Date flowEndDate;
	private int flowStatus;

    //private List<Archive> archivesToDelete = new ArrayList<>();
    private Set<PaymentContractAttachment> paymentContractAttachments = new HashSet<>(0);
    private List<PaymentContractAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public PaymentContract clone() {
        PaymentContract p = null;
        try {
            p = (PaymentContract) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }


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
    @JoinColumn(name = "purchase_order_id", nullable = true)
    public PurchaseOrder getPurchaseOrder() {
        return this.purchaseOrder;
    }


    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_org_id", nullable = true)
    public VirtualOrg getVirtualOrg() {
        return this.virtualOrg;
    }


    public void setVirtualOrg(VirtualOrg virtualOrg) {
        this.virtualOrg = virtualOrg;
    }

    @Column(name = "contract_name", nullable = false, length = 50)
    public String getContractName() {
        return this.contractName;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Column(name = "serial_number", nullable = false, length = 36)
    public String getSerialNumber() {
        return this.serialNumber;
    }


    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "contract_year", nullable = true, length = 36)
    public String getContractYear() {
        return this.contractYear;
    }


    public void setContractYear(String contractYear) {
        this.contractYear = contractYear;
    }

    @Column(name = "contract_count", nullable = true)
    public Integer getContractCount() {
        return this.contractCount;
    }


    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    @Column(name = "contract_amount", nullable = false)
    public double getContractAmount() {
        return this.contractAmount;
    }


    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    @Column(name = "is_temporary_pricing", nullable = true)
    public Short getIsTemporaryPricing() {
        return this.isTemporaryPricing;
    }


    public void setIsTemporaryPricing(Short isTemporaryPricing) {
        this.isTemporaryPricing = isTemporaryPricing;
    }

    @Column(name = "amount_excluding_tax", nullable = true)
    public Double getAmountExcludingTax() {
        return this.amountExcludingTax;
    }


    public void setAmountExcludingTax(Double amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_kind", nullable = true)
    public ContractCategory getContractKind() {
        return this.contractKind;
    }


    public void setContractKind(ContractCategory contractKind) {
        this.contractKind = contractKind;
    }

    @Column(name = "contract_attribute", nullable = true, length = 36)
    public String getContractAttribute() {
        return this.contractAttribute;
    }


    public void setContractAttribute(String contractAttribute) {
        this.contractAttribute = contractAttribute;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contract_time", nullable = true, length = 29)
    public Date getContractTime() {
        return this.contractTime;
    }


    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    @Column(name = "ontract_address", nullable = true, length = 200)
    public String getOntractAddress() {
        return this.ontractAddress;
    }


    public void setOntractAddress(String ontractAddress) {
        this.ontractAddress = ontractAddress;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = true, length = 29)
    public Date getStartTime() {
        return this.startTime;
    }


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = true, length = 29)
    public Date getEndTime() {
        return this.endTime;
    }


    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "party_a", nullable = true, length = 36)
    public String getPartyA() {
        return this.partyA;
    }


    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    @Column(name = "party_b", nullable = true, length = 36)
    public String getPartyB() {
        return this.partyB;
    }


    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signing_people", nullable = true)
    public Person getSigningPeople() {
        return this.signingPeople;
    }


    public void setSigningPeople(Person signingPeople) {
        this.signingPeople = signingPeople;
    }

    @Column(name = "party_b_contact", nullable = true, length = 36)
    public String getPartyBContact() {
        return this.partyBContact;
    }


    public void setPartyBContact(String partyBContact) {
        this.partyBContact = partyBContact;
    }

    @Column(name = "party_a_contact_info", nullable = true, length = 36)
    public String getPartyAContactInfo() {
        return this.partyAContactInfo;
    }


    public void setPartyAContactInfo(String partyAContactInfo) {
        this.partyAContactInfo = partyAContactInfo;
    }

    @Column(name = "party_b_contact_info", nullable = true, length = 36)
    public String getPartyBContactInfo() {
        return this.partyBContactInfo;
    }


    public void setPartyBContactInfo(String partyBContactInfo) {
        this.partyBContactInfo = partyBContactInfo;
    }

    @Column(name = "brief_introduction", nullable = true, length = 500)
    public String getBriefIntroduction() {
        return this.briefIntroduction;
    }


    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seal_type", nullable = true)
    public DataOption getSealType() {
        return this.sealType;
    }


    public void setSealType(DataOption sealType) {
        this.sealType = sealType;
    }

    @Column(name = "is_include_pending_data", nullable = true)
    public Short getIsIncludePendingData() {
        return this.isIncludePendingData;
    }


    public void setIsIncludePendingData(Short isIncludePendingData) {
        this.isIncludePendingData = isIncludePendingData;
    }

    @Column(name = "virtual_contract", nullable = true, length = 36)
    public String getVirtualContract() {
        return this.virtualContract;
    }


    public void setVirtualContract(String virtualContract) {
        this.virtualContract = virtualContract;
    }

    @Column(name = "contract_return_info", nullable = true, length = 500)
    public String getContractReturnInfo() {
        return this.contractReturnInfo;
    }


    public void setContractReturnInfo(String contractReturnInfo) {
        this.contractReturnInfo = contractReturnInfo;
    }

    @Column(name = "countersignature_return", nullable = true, length = 500)
    public String getCountersignatureReturn() {
        return this.countersignatureReturn;
    }


    public void setCountersignatureReturn(String countersignatureReturn) {
        this.countersignatureReturn = countersignatureReturn;
    }

    @Column(name = "main_provisions", nullable = true, length = 500)
    public String getMainProvisions() {
        return this.mainProvisions;
    }


    public void setMainProvisions(String mainProvisions) {
        this.mainProvisions = mainProvisions;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_person", nullable = true)
    public Person getEntryPerson() {
        return this.entryPerson;
    }


    public void setEntryPerson(Person entryPerson) {
        this.entryPerson = entryPerson;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_time", nullable = true, length = 29)
    public Date getEntryTime() {
        return this.entryTime;
    }


    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }


    @Column(name = "creator", nullable = true, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = true, length = 29)
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


//    @Transient
//    public List<Archive> getArchivesToDelete() {
//        return this.archivesToDelete;
//    }
//
//
//
//    public void setArchivesToDelete(List<Archive> archivesToDelete) {
//        this.archivesToDelete = archivesToDelete;
//    }

    @Column(name = "data_source", nullable = false)
    public Integer getDataSource() {
        return this.dataSource;
    }


    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }
    
    @Column(name = "ext_id", nullable = true, length = 36)
    public String getExtId() {
        return this.extId;
    }


    public void setExtId(String extId) {
        this.extId = extId;
    }
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentContract")
    public Set<PaymentContractAttachment> getPaymentContractAttachments() {
        return paymentContractAttachments;
    }

    public void setPaymentContractAttachments(Set<PaymentContractAttachment> paymentContractAttachments) {
        this.paymentContractAttachments = paymentContractAttachments;
    }

    @Transient
    public List<PaymentContractAttachment> getAttachmentsToDelete() {
        return attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<PaymentContractAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }

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

}
