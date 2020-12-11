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
@Table(name = "t_payment_invoice")
public class PaymentInvoice implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6484728379678117753L;

    private String id = UUID.randomUUID().toString();

    private PaymentContract paymentContract;
    private String contractNo;
    private String contractName;
    private double finalContractAmount;
    private Date signingTime;
    private Project project;
    private Double cumulativePaymentAmount;
    private Double cumulativeInvoiceReceived;
    private Double accumulatedOutstandingAmount;
    private String paymentUnit;
    private String collectionUnit;
    private Person entryPerson;
    private Date entryTime;
    private String storageNumber;
    private Double invoiceAmount;
    private String invoiceNo;
    private String invoiceCode;
    private String invoiceType;
    private String socialCreditCode;
    private String taxpayerIdentificationNumber;
    private String depositBank;
    private String bankAccount;
    private String address;
    private String phone;
    private String transactor;
    private Date invoiceDate;
    private String invoiceContent;
    private Date arrivalDate;
    private String remark;
    private Double deductibleInputTax;
    private Double inputTaxActuallyDeducted;
    private Double amountExcludingTax;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public PaymentInvoice clone() {
        PaymentInvoice p = null;
        try {
            p = (PaymentInvoice) super.clone();
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

    @Column(name = "final_contract_amount", nullable = false)
    public double getFinalContractAmount() {
        return this.finalContractAmount;
    }


    public void setFinalContractAmount(double finalContractAmount) {
        this.finalContractAmount = finalContractAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signing_time", nullable = false, length = 29)
    public Date getSigningTime() {
        return this.signingTime;
    }


    public void setSigningTime(Date signingTime) {
        this.signingTime = signingTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "cumulative_payment_amount", nullable = true)
    public Double getCumulativePaymentAmount() {
        return this.cumulativePaymentAmount;
    }


    public void setCumulativePaymentAmount(Double cumulativePaymentAmount) {
        this.cumulativePaymentAmount = cumulativePaymentAmount;
    }

    @Column(name = "cumulative_invoice_received", nullable = true)
    public Double getCumulativeInvoiceReceived() {
        return this.cumulativeInvoiceReceived;
    }


    public void setCumulativeInvoiceReceived(Double cumulativeInvoiceReceived) {
        this.cumulativeInvoiceReceived = cumulativeInvoiceReceived;
    }

    @Column(name = "accumulated_outstanding_amount", nullable = true)
    public Double getAccumulatedOutstandingAmount() {
        return this.accumulatedOutstandingAmount;
    }


    public void setAccumulatedOutstandingAmount(Double accumulatedOutstandingAmount) {
        this.accumulatedOutstandingAmount = accumulatedOutstandingAmount;
    }

    @Column(name = "payment_unit", nullable = true, length = 50)
    public String getPaymentUnit() {
        return this.paymentUnit;
    }


    public void setPaymentUnit(String paymentUnit) {
        this.paymentUnit = paymentUnit;
    }

    @Column(name = "collection_unit", nullable = true, length = 50)
    public String getCollectionUnit() {
        return this.collectionUnit;
    }


    public void setCollectionUnit(String collectionUnit) {
        this.collectionUnit = collectionUnit;
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

    @Column(name = "storage_number", nullable = true, length = 36)
    public String getStorageNumber() {
        return this.storageNumber;
    }


    public void setStorageNumber(String storageNumber) {
        this.storageNumber = storageNumber;
    }

    @Column(name = "invoice_amount", nullable = true)
    public Double getInvoiceAmount() {
        return this.invoiceAmount;
    }


    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Column(name = "invoice_no", nullable = true, length = 36)
    public String getInvoiceNo() {
        return this.invoiceNo;
    }


    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Column(name = "invoice_code", nullable = true, length = 36)
    public String getInvoiceCode() {
        return this.invoiceCode;
    }


    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Column(name = "invoice_type", nullable = true)
    public String getInvoiceType() {
        return this.invoiceType;
    }


    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name = "social_credit_code", nullable = true, length = 36)
    public String getSocialCreditCode() {
        return this.socialCreditCode;
    }


    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    @Column(name = "taxpayer_identification_number", nullable = true, length = 36)
    public String getTaxpayerIdentificationNumber() {
        return this.taxpayerIdentificationNumber;
    }


    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }

    @Column(name = "deposit_bank", nullable = true, length = 36)
    public String getDepositBank() {
        return this.depositBank;
    }


    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    @Column(name = "bank_account", nullable = true, length = 50)
    public String getBankAccount() {
        return this.bankAccount;
    }


    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "address", nullable = true, length = 200)
    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "phone", nullable = true, length = 36)
    public String getPhone() {
        return this.phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "transactor", nullable = true, length = 36)
    public String getTransactor() {
        return this.transactor;
    }


    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "invoice_date", nullable = true, length = 29)
    public Date getInvoiceDate() {
        return this.invoiceDate;
    }


    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Column(name = "invoice_content", nullable = true, length = 200)
    public String getInvoiceContent() {
        return this.invoiceContent;
    }


    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_date", nullable = true, length = 29)
    public Date getArrivalDate() {
        return this.arrivalDate;
    }


    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "deductible_input_tax", nullable = true)
    public Double getDeductibleInputTax() {
        return this.deductibleInputTax;
    }


    public void setDeductibleInputTax(Double deductibleInputTax) {
        this.deductibleInputTax = deductibleInputTax;
    }

    @Column(name = "input_tax_actually_deducted", nullable = true)
    public Double getInputTaxActuallyDeducted() {
        return this.inputTaxActuallyDeducted;
    }


    public void setInputTaxActuallyDeducted(Double inputTaxActuallyDeducted) {
        this.inputTaxActuallyDeducted = inputTaxActuallyDeducted;
    }

    @Column(name = "amount_excluding_tax", nullable = true)
    public Double getAmountExcludingTax() {
        return this.amountExcludingTax;
    }


    public void setAmountExcludingTax(Double amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
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
