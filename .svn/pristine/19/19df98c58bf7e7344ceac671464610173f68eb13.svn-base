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
@Table(name = "t_income_contract_statement")
public class IncomeContractStatement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1230756758476188393L;

    private String id = UUID.randomUUID().toString();

    private IncomeContract incomeContract;
    private String contractNo;
    private String contractName;
    private double contractAmount;
    private Date signingTime;
    private double finalAmount;
    private Double settlementSum;
    private Double difference;
    private Double cumulativeSettlementProportion;
    private Integer currency;
    private String settlementNumber;
    private Double settlementAmount;
    private Double exchangeRate;
    private Double convertedIntoRmb;
    private Integer settlementType;
    private Integer paymentMethod;
    private String settlementObject;
    private Date settlementDate;
    private Double settlementRatio;
    private Date agreedCollectionDate;
    private Person settlementPerson;
    private String remark;
    private Person entryPerson;
    private Date entryTime;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public IncomeContractStatement clone() {
        IncomeContractStatement p = null;
        try {
            p = (IncomeContractStatement) super.clone();
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
    @JoinColumn(name = "income_contract_id", nullable = false)
    public IncomeContract getIncomeContract() {
        return this.incomeContract;
    }


    public void setIncomeContract(IncomeContract incomeContract) {
        this.incomeContract = incomeContract;
    }
    
    @Column(name = "contract_no", nullable = true, length = 36)
    public String getContractNo() {
        return this.contractNo;
    }


    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Column(name = "contract_name", nullable = true, length = 50)
    public String getContractName() {
        return this.contractName;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Column(name = "contract_amount", nullable = false)
    public double getContractAmount() {
        return this.contractAmount;
    }


    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signing_time", nullable = false, length = 29)
    public Date getSigningTime() {
        return this.signingTime;
    }


    public void setSigningTime(Date signingTime) {
        this.signingTime = signingTime;
    }

    @Column(name = "final_amount", nullable = false)
    public double getFinalAmount() {
        return this.finalAmount;
    }


    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    @Column(name = "settlement_sum", nullable = true)
    public Double getSettlementSum() {
        return this.settlementSum;
    }


    public void setSettlementSum(Double settlementSum) {
        this.settlementSum = settlementSum;
    }

    @Column(name = "difference", nullable = true)
    public Double getDifference() {
        return this.difference;
    }


    public void setDifference(Double difference) {
        this.difference = difference;
    }

    @Column(name = "cumulative_settlement_proportion", nullable = true)
    public Double getCumulativeSettlementProportion() {
        return this.cumulativeSettlementProportion;
    }


    public void setCumulativeSettlementProportion(Double cumulativeSettlementProportion) {
        this.cumulativeSettlementProportion = cumulativeSettlementProportion;
    }

    @Column(name = "currency", nullable = true)
    public Integer getCurrency() {
        return this.currency;
    }


    public void setCurrency(Integer currency) {
        this.currency = currency;
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

    @Column(name = "exchange_rate", nullable = true)
    public Double getExchangeRate() {
        return this.exchangeRate;
    }


    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Column(name = "converted_into_rmb", nullable = true)
    public Double getConvertedIntoRmb() {
        return this.convertedIntoRmb;
    }


    public void setConvertedIntoRmb(Double convertedIntoRmb) {
        this.convertedIntoRmb = convertedIntoRmb;
    }

    @Column(name = "settlement_type", nullable = true)
    public Integer getSettlementType() {
        return this.settlementType;
    }


    public void setSettlementType(Integer settlementType) {
        this.settlementType = settlementType;
    }

    @Column(name = "payment_method", nullable = true)
    public Integer getPaymentMethod() {
        return this.paymentMethod;
    }


    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name = "settlement_object", nullable = true, length = 36)
    public String getSettlementObject() {
        return this.settlementObject;
    }


    public void setSettlementObject(String settlementObject) {
        this.settlementObject = settlementObject;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "agreed_collection_date", nullable = false, length = 29)
    public Date getAgreedCollectionDate() {
        return this.agreedCollectionDate;
    }


    public void setAgreedCollectionDate(Date agreedCollectionDate) {
        this.agreedCollectionDate = agreedCollectionDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_person", nullable = true)
    public Person getSettlementPerson() {
        return this.settlementPerson;
    }


    public void setSettlementPerson(Person settlementPerson) {
        this.settlementPerson = settlementPerson;
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
