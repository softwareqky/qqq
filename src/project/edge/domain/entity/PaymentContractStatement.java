package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;

@Entity
@Table(name = "t_payment_contract_statement")
public class PaymentContractStatement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2492049352261451690L;

    private String id = UUID.randomUUID().toString();

    private PaymentContract paymentContract;
    private String contractNo;
    private String contractName;
    private ContractCategory contractKind;
    private String paymentCondition;
    private double finalContractAmount;
    private String currency;
    private Double accumulatedCollectionAmount;
    private String partyA;
    private String partyB;
    private Date signingTime;
    private String signingAddress;
    private Double accumulatedSettlementAmount;
    private Double cumulativeSettlementProportion;
    private String settlementNumber;
    private Double settlementAmount;
    private Integer settlementType;
    private Double difference;
    private String settlementObject;
    private Short isIncludePendingData;
    private Person settlementPerson;
    private Integer paymentMethod;
    private Date settlementDate;
    private Double settlementRatio;
    private String remark;
    private Person entryPerson;
    private Date entryTime;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public PaymentContractStatement clone() {
        PaymentContractStatement p = null;
        try {
            p = (PaymentContractStatement) super.clone();
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
    @JoinColumn(name = "payment_contract_id", nullable = false)
    public PaymentContract getPaymentContract() {
        return this.paymentContract;
    }


    public void setPaymentContract(PaymentContract paymentContract) {
        this.paymentContract = paymentContract;
    }

    @Column(name = "contract_no", nullable = false, length = 36)
    public String getContractNo() {
        return this.contractNo;
    }


    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Column(name = "contract_name", nullable = false, length = 50)
    public String getContractName() {
        return this.contractName;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    
    @Column(name = "payment_condition", nullable = true, length = 500)
    public String getPaymentCondition() {
        return this.paymentCondition;
    }


    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_kind", nullable = true)
    public ContractCategory getContractKind() {
        return this.contractKind;
    }


    public void setContractKind(ContractCategory contractKind) {
        this.contractKind = contractKind;
    }

    @Column(name = "final_contract_amount", nullable = false)
    public double getFinalContractAmount() {
        return this.finalContractAmount;
    }


    public void setFinalContractAmount(double finalContractAmount) {
        this.finalContractAmount = finalContractAmount;
    }

    @Column(name = "currency", nullable = true, length = 36)
    public String getCurrency() {
        return this.currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "accumulated_collection_amount", nullable = true)
    public Double getAccumulatedCollectionAmount() {
        return this.accumulatedCollectionAmount;
    }


    public void setAccumulatedCollectionAmount(Double accumulatedCollectionAmount) {
        this.accumulatedCollectionAmount = accumulatedCollectionAmount;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signing_time", nullable = true, length = 29)
    public Date getSigningTime() {
        return this.signingTime;
    }


    public void setSigningTime(Date signingTime) {
        this.signingTime = signingTime;
    }

    @Column(name = "signing_address", nullable = true, length = 200)
    public String getSigningAddress() {
        return this.signingAddress;
    }


    public void setSigningAddress(String signingAddress) {
        this.signingAddress = signingAddress;
    }

    @Column(name = "accumulated_settlement_amount", nullable = true)
    public Double getAccumulatedSettlementAmount() {
        return this.accumulatedSettlementAmount;
    }


    public void setAccumulatedSettlementAmount(Double accumulatedSettlementAmount) {
        this.accumulatedSettlementAmount = accumulatedSettlementAmount;
    }

    @Column(name = "cumulative_settlement_proportion", nullable = true)
    public Double getCumulativeSettlementProportion() {
        return this.cumulativeSettlementProportion;
    }


    public void setCumulativeSettlementProportion(Double cumulativeSettlementProportion) {
        this.cumulativeSettlementProportion = cumulativeSettlementProportion;
    }

    @Column(name = "settlement_number", nullable = true, length = 36)
    public String getSettlementNumber() {
        return this.settlementNumber;
    }


    public void setSettlementNumber(String settlementNumber) {
        this.settlementNumber = settlementNumber;
    }

    @Column(name = "settlement_amount", nullable = true)
    public Double getSettlementAmount() {
        return this.settlementAmount;
    }


    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    @Column(name = "settlement_type", nullable = true)
    public Integer getSettlementType() {
        return this.settlementType;
    }


    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    @Column(name = "difference", nullable = true)
    public Double getDifference() {
        return this.difference;
    }


    public void setDifference(Double difference) {
        this.difference = difference;
    }

    @Column(name = "settlement_object", nullable = true, length = 36)
    public String getSettlementObject() {
        return this.settlementObject;
    }


    public void setSettlementObject(String settlementObject) {
        this.settlementObject = settlementObject;
    }

    @Column(name = "is_include_pending_data", nullable = true)
    public Short getIsIncludePendingData() {
        return this.isIncludePendingData;
    }


    public void setIsIncludePendingData(Short isIncludePendingData) {
        this.isIncludePendingData = isIncludePendingData;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_person", nullable = true)
    public Person getSettlementPerson() {
        return this.settlementPerson;
    }


    public void setSettlementPerson(Person settlementPerson) {
        this.settlementPerson = settlementPerson;
    }

    @Column(name = "payment_method", nullable = true)
    public Integer getPaymentMethod() {
        return this.paymentMethod;
    }


    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "settlement_date", nullable = true, length = 29)
    public Date getSettlementDate() {
        return this.settlementDate;
    }


    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Column(name = "settlement_ratio", nullable = true)
    public Double getSettlementRatio() {
        return this.settlementRatio;
    }


    public void setSettlementRatio(Double settlementRatio) {
        this.settlementRatio = settlementRatio;
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

    @Transient
    public List<Archive> getArchivesToDelete() {
        return this.archivesToDelete;
    }


    public void setArchivesToDelete(List<Archive> archivesToDelete) {
        this.archivesToDelete = archivesToDelete;
    }


}
