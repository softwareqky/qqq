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
@Table(name = "t_income_invoice")
public class IncomeInvoice implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5109651964008853799L;

    private String id = UUID.randomUUID().toString();

    private IncomeContract incomeContract;
    private String contractNo;
    private String contractName;
    private double finalContractAmount;
    private Date signingTime;
    private Project project;
    private Double accumulatedCollectionAmount;
    private Double cumulativeInvoiced;
    private Double accumulatedInvoiceNotIssued;
    private String invoiceApplication;
    private Double currentInvoiceAmount;
    private Double taxRate;
    private String invoiceNumber;
    private String invoiceType;
    private String collectionUnit;
    private String paymentUnit;
    private String taxpayerIdentificationNumber;
    private String invoiceCode;
    private String transactor;
    private Date invoiceDate;
    private Double previousInputDeductedTax;
    private String unifiedSocialCreditCode;
    private String address;
    private String telephone;
    private Double vatPaidInAdvanceInOtherPlaces;
    private Double deposit;
    private String voucherNumber;
    private String invoiceContents;
    private Double additionalTaxPaidInAdvance;
    private String suspendOtherBusiness;
    private Double deductManagementFee;
    private Double inputDeductedTax;
    private Double currentRetainedInputTax;
    private Double finalRetainedInputTaxBalance;
    private Double prepaidIndividualIncomeTax;
    private Double otherTaxesPaidInAdvance;
    private Double enterpriseIncomeTaxPrepaid;
    private String openingBank;
    private String accountNumber;
    private String remark;
    private Person entryPerson;
    private Date entryTime;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public IncomeInvoice clone() {
        IncomeInvoice p = null;
        try {
            p = (IncomeInvoice) super.clone();
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

    @Column(name = "accumulated_collection_amount", nullable = true)
    public Double getAccumulatedCollectionAmount() {
        return this.accumulatedCollectionAmount;
    }


    public void setAccumulatedCollectionAmount(Double accumulatedCollectionAmount) {
        this.accumulatedCollectionAmount = accumulatedCollectionAmount;
    }

    @Column(name = "cumulative_invoiced", nullable = true)
    public Double getCumulativeInvoiced() {
        return this.cumulativeInvoiced;
    }


    public void setCumulativeInvoiced(Double cumulativeInvoiced) {
        this.cumulativeInvoiced = cumulativeInvoiced;
    }

    @Column(name = "accumulated_invoice_not_issued", nullable = true)
    public Double getAccumulatedInvoiceNotIssued() {
        return this.accumulatedInvoiceNotIssued;
    }


    public void setAccumulatedInvoiceNotIssued(Double accumulatedInvoiceNotIssued) {
        this.accumulatedInvoiceNotIssued = accumulatedInvoiceNotIssued;
    }

    @Column(name = "invoice_application", nullable = true, length = 36)
    public String getInvoiceApplication() {
        return this.invoiceApplication;
    }


    public void setInvoiceApplication(String invoiceApplication) {
        this.invoiceApplication = invoiceApplication;
    }

    @Column(name = "current_invoice_amount", nullable = true)
    public Double getCurrentInvoiceAmount() {
        return this.currentInvoiceAmount;
    }


    public void setCurrentInvoiceAmount(Double currentInvoiceAmount) {
        this.currentInvoiceAmount = currentInvoiceAmount;
    }

    @Column(name = "tax_rate", nullable = true)
    public Double getTaxRate() {
        return this.taxRate;
    }


    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name = "invoice_number", nullable = true, length = 36)
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }


    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "invoice_type", nullable = true)
    public String getInvoiceType() {
        return this.invoiceType;
    }


    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Column(name = "collection_unit", nullable = true, length = 36)
    public String getCollectionUnit() {
        return this.collectionUnit;
    }


    public void setCollectionUnit(String collectionUnit) {
        this.collectionUnit = collectionUnit;
    }

    @Column(name = "payment_unit", nullable = true, length = 36)
    public String getPaymentUnit() {
        return this.paymentUnit;
    }


    public void setPaymentUnit(String paymentUnit) {
        this.paymentUnit = paymentUnit;
    }

    @Column(name = "taxpayer_identification_number", nullable = true, length = 36)
    public String getTaxpayerIdentificationNumber() {
        return this.taxpayerIdentificationNumber;
    }


    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }

    @Column(name = "invoice_code", nullable = true, length = 36)
    public String getInvoiceCode() {
        return this.invoiceCode;
    }


    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
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

    @Column(name = "previous_input_deducted_tax", nullable = true)
    public Double getPreviousInputDeductedTax() {
        return this.previousInputDeductedTax;
    }


    public void setPreviousInputDeductedTax(Double previousInputDeductedTax) {
        this.previousInputDeductedTax = previousInputDeductedTax;
    }

    @Column(name = "unified_social_credit_code", nullable = true, length = 36)
    public String getUnifiedSocialCreditCode() {
        return this.unifiedSocialCreditCode;
    }


    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }

    @Column(name = "address", nullable = true, length = 200)
    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "telephone", nullable = true, length = 36)
    public String getTelephone() {
        return this.telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "vat_paid_in_advance_in_other_places", nullable = true)
    public Double getVatPaidInAdvanceInOtherPlaces() {
        return this.vatPaidInAdvanceInOtherPlaces;
    }


    public void setVatPaidInAdvanceInOtherPlaces(Double vatPaidInAdvanceInOtherPlaces) {
        this.vatPaidInAdvanceInOtherPlaces = vatPaidInAdvanceInOtherPlaces;
    }

    @Column(name = "deposit", nullable = true)
    public Double getDeposit() {
        return this.deposit;
    }


    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    @Column(name = "voucher_number", nullable = true, length = 36)
    public String getVoucherNumber() {
        return this.voucherNumber;
    }


    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    @Column(name = "invoice_contents", nullable = true, length = 200)
    public String getInvoiceContents() {
        return this.invoiceContents;
    }


    public void setInvoiceContents(String invoiceContents) {
        this.invoiceContents = invoiceContents;
    }

    @Column(name = "additional_tax_paid_in_advance", nullable = true)
    public Double getAdditionalTaxPaidInAdvance() {
        return this.additionalTaxPaidInAdvance;
    }


    public void setAdditionalTaxPaidInAdvance(Double additionalTaxPaidInAdvance) {
        this.additionalTaxPaidInAdvance = additionalTaxPaidInAdvance;
    }

    @Column(name = "suspend_other_business", nullable = true, length = 36)
    public String getSuspendOtherBusiness() {
        return this.suspendOtherBusiness;
    }


    public void setSuspendOtherBusiness(String suspendOtherBusiness) {
        this.suspendOtherBusiness = suspendOtherBusiness;
    }

    @Column(name = "deduct_management_fee", nullable = true)
    public Double getDeductManagementFee() {
        return this.deductManagementFee;
    }


    public void setDeductManagementFee(Double deductManagementFee) {
        this.deductManagementFee = deductManagementFee;
    }

    @Column(name = "input_deducted_tax", nullable = true)
    public Double getInputDeductedTax() {
        return this.inputDeductedTax;
    }


    public void setInputDeductedTax(Double inputDeductedTax) {
        this.inputDeductedTax = inputDeductedTax;
    }

    @Column(name = "current_retained_input_tax", nullable = true)
    public Double getCurrentRetainedInputTax() {
        return this.currentRetainedInputTax;
    }


    public void setCurrentRetainedInputTax(Double currentRetainedInputTax) {
        this.currentRetainedInputTax = currentRetainedInputTax;
    }

    @Column(name = "final_retained_input_tax_balance", nullable = true)
    public Double getFinalRetainedInputTaxBalance() {
        return this.finalRetainedInputTaxBalance;
    }


    public void setFinalRetainedInputTaxBalance(Double finalRetainedInputTaxBalance) {
        this.finalRetainedInputTaxBalance = finalRetainedInputTaxBalance;
    }

    @Column(name = "prepaid_individual_income_tax", nullable = true)
    public Double getPrepaidIndividualIncomeTax() {
        return this.prepaidIndividualIncomeTax;
    }


    public void setPrepaidIndividualIncomeTax(Double prepaidIndividualIncomeTax) {
        this.prepaidIndividualIncomeTax = prepaidIndividualIncomeTax;
    }

    @Column(name = "other_taxes_paid_in_advance", nullable = true)
    public Double getOtherTaxesPaidInAdvance() {
        return this.otherTaxesPaidInAdvance;
    }


    public void setOtherTaxesPaidInAdvance(Double otherTaxesPaidInAdvance) {
        this.otherTaxesPaidInAdvance = otherTaxesPaidInAdvance;
    }

    @Column(name = "enterprise_income_tax_prepaid", nullable = true)
    public Double getEnterpriseIncomeTaxPrepaid() {
        return this.enterpriseIncomeTaxPrepaid;
    }


    public void setEnterpriseIncomeTaxPrepaid(Double enterpriseIncomeTaxPrepaid) {
        this.enterpriseIncomeTaxPrepaid = enterpriseIncomeTaxPrepaid;
    }

    @Column(name = "opening_bank", nullable = true, length = 36)
    public String getOpeningBank() {
        return this.openingBank;
    }


    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    @Column(name = "account_number", nullable = true, length = 50)
    public String getAccountNumber() {
        return this.accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
