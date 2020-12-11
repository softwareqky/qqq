/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

import garage.origin.domain.view.ViewBean;


/**
 * @author angel_000
 *
 */
public class IncomeInvoiceBean implements ViewBean {

    private String id;
    private String incomeContract_;
    private String incomeContractText;
    private String contractNo;
    private String contractName;
    private double finalContractAmount;
    private String signingTime;
    private String project_;
    private String projectText;
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
    private String invoiceDate;
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
    private String entryPerson_;
    private String entryPersonText;
    private String entryTime;


    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }


    public String getContractNo() {
        return this.contractNo;
    }

    public String getIncomeContract_() {
        return this.incomeContract_;
    }


    public void setIncomeContract_(String incomeContract_) {
        this.incomeContract_ = incomeContract_;
    }
    
    public String getIncomeContractText() {
        return this.incomeContractText;
    }


    public void setIncomeContractText(String incomeContractText) {
        this.incomeContractText = incomeContractText;
    }


    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }


    public String getContractName() {
        return this.contractName;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }


    public double getFinalContractAmount() {
        return this.finalContractAmount;
    }


    public void setFinalContractAmount(double finalContractAmount) {
        this.finalContractAmount = finalContractAmount;
    }


    public String getSigningTime() {
        return this.signingTime;
    }


    public void setSigningTime(String signingTime) {
        this.signingTime = signingTime;
    }


    public String getProject_() {
        return this.project_;
    }


    public void setProject_(String project_) {
        this.project_ = project_;
    }


    public String getProjectText() {
        return this.projectText;
    }


    public void setProjectText(String projectText) {
        this.projectText = projectText;
    }


    public Double getAccumulatedCollectionAmount() {
        return this.accumulatedCollectionAmount;
    }


    public void setAccumulatedCollectionAmount(Double accumulatedCollectionAmount) {
        this.accumulatedCollectionAmount = accumulatedCollectionAmount;
    }


    public Double getCumulativeInvoiced() {
        return this.cumulativeInvoiced;
    }


    public void setCumulativeInvoiced(Double cumulativeInvoiced) {
        this.cumulativeInvoiced = cumulativeInvoiced;
    }


    public Double getAccumulatedInvoiceNotIssued() {
        return this.accumulatedInvoiceNotIssued;
    }


    public void setAccumulatedInvoiceNotIssued(Double accumulatedInvoiceNotIssued) {
        this.accumulatedInvoiceNotIssued = accumulatedInvoiceNotIssued;
    }


    public String getInvoiceApplication() {
        return this.invoiceApplication;
    }


    public void setInvoiceApplication(String invoiceApplication) {
        this.invoiceApplication = invoiceApplication;
    }


    public Double getCurrentInvoiceAmount() {
        return this.currentInvoiceAmount;
    }


    public void setCurrentInvoiceAmount(Double currentInvoiceAmount) {
        this.currentInvoiceAmount = currentInvoiceAmount;
    }


    public Double getTaxRate() {
        return this.taxRate;
    }


    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }


    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }


    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    public String getInvoiceType() {
        return this.invoiceType;
    }


    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }


    public String getCollectionUnit() {
        return this.collectionUnit;
    }


    public void setCollectionUnit(String collectionUnit) {
        this.collectionUnit = collectionUnit;
    }


    public String getPaymentUnit() {
        return this.paymentUnit;
    }


    public void setPaymentUnit(String paymentUnit) {
        this.paymentUnit = paymentUnit;
    }


    public String getTaxpayerIdentificationNumber() {
        return this.taxpayerIdentificationNumber;
    }


    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }


    public String getInvoiceCode() {
        return this.invoiceCode;
    }


    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }


    public String getTransactor() {
        return this.transactor;
    }


    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }


    public String getInvoiceDate() {
        return this.invoiceDate;
    }


    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }


    public Double getPreviousInputDeductedTax() {
        return this.previousInputDeductedTax;
    }


    public void setPreviousInputDeductedTax(Double previousInputDeductedTax) {
        this.previousInputDeductedTax = previousInputDeductedTax;
    }


    public String getUnifiedSocialCreditCode() {
        return this.unifiedSocialCreditCode;
    }


    public void setUnifiedSocialCreditCode(String unifiedSocialCreditCode) {
        this.unifiedSocialCreditCode = unifiedSocialCreditCode;
    }


    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getTelephone() {
        return this.telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public Double getVatPaidInAdvanceInOtherPlaces() {
        return this.vatPaidInAdvanceInOtherPlaces;
    }


    public void setVatPaidInAdvanceInOtherPlaces(Double vatPaidInAdvanceInOtherPlaces) {
        this.vatPaidInAdvanceInOtherPlaces = vatPaidInAdvanceInOtherPlaces;
    }


    public Double getDeposit() {
        return this.deposit;
    }


    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }


    public String getVoucherNumber() {
        return this.voucherNumber;
    }


    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }


    public String getInvoiceContents() {
        return this.invoiceContents;
    }


    public void setInvoiceContents(String invoiceContents) {
        this.invoiceContents = invoiceContents;
    }


    public Double getAdditionalTaxPaidInAdvance() {
        return this.additionalTaxPaidInAdvance;
    }


    public void setAdditionalTaxPaidInAdvance(Double additionalTaxPaidInAdvance) {
        this.additionalTaxPaidInAdvance = additionalTaxPaidInAdvance;
    }


    public String getSuspendOtherBusiness() {
        return this.suspendOtherBusiness;
    }


    public void setSuspendOtherBusiness(String suspendOtherBusiness) {
        this.suspendOtherBusiness = suspendOtherBusiness;
    }


    public Double getDeductManagementFee() {
        return this.deductManagementFee;
    }


    public void setDeductManagementFee(Double deductManagementFee) {
        this.deductManagementFee = deductManagementFee;
    }


    public Double getInputDeductedTax() {
        return this.inputDeductedTax;
    }


    public void setInputDeductedTax(Double inputDeductedTax) {
        this.inputDeductedTax = inputDeductedTax;
    }


    public Double getCurrentRetainedInputTax() {
        return this.currentRetainedInputTax;
    }


    public void setCurrentRetainedInputTax(Double currentRetainedInputTax) {
        this.currentRetainedInputTax = currentRetainedInputTax;
    }


    public Double getFinalRetainedInputTaxBalance() {
        return this.finalRetainedInputTaxBalance;
    }


    public void setFinalRetainedInputTaxBalance(Double finalRetainedInputTaxBalance) {
        this.finalRetainedInputTaxBalance = finalRetainedInputTaxBalance;
    }


    public Double getPrepaidIndividualIncomeTax() {
        return this.prepaidIndividualIncomeTax;
    }


    public void setPrepaidIndividualIncomeTax(Double prepaidIndividualIncomeTax) {
        this.prepaidIndividualIncomeTax = prepaidIndividualIncomeTax;
    }


    public Double getOtherTaxesPaidInAdvance() {
        return this.otherTaxesPaidInAdvance;
    }


    public void setOtherTaxesPaidInAdvance(Double otherTaxesPaidInAdvance) {
        this.otherTaxesPaidInAdvance = otherTaxesPaidInAdvance;
    }


    public Double getEnterpriseIncomeTaxPrepaid() {
        return this.enterpriseIncomeTaxPrepaid;
    }


    public void setEnterpriseIncomeTaxPrepaid(Double enterpriseIncomeTaxPrepaid) {
        this.enterpriseIncomeTaxPrepaid = enterpriseIncomeTaxPrepaid;
    }


    public String getOpeningBank() {
        return this.openingBank;
    }


    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }


    public String getAccountNumber() {
        return this.accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getEntryPerson_() {
        return this.entryPerson_;
    }


    public void setEntryPerson_(String entryPerson_) {
        this.entryPerson_ = entryPerson_;
    }


    public String getEntryPersonText() {
        return this.entryPersonText;
    }


    public void setEntryPersonText(String entryPersonText) {
        this.entryPersonText = entryPersonText;
    }


    public String getEntryTime() {
        return this.entryTime;
    }


    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }


    public void setId(String id) {
        this.id = id;
    }

    public List<ArchiveBean> getArchivesList() {
        return this.archivesList;
    }

    public void setArchivesList(List<ArchiveBean> archivesList) {
        this.archivesList = archivesList;
    }

    public List<String> getArchivesReservedList() {
        return this.archivesReservedList;
    }

    public void setArchivesReservedList(List<String> archivesReservedList) {
        this.archivesReservedList = archivesReservedList;
    }

}
