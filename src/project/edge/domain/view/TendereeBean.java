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
public class TendereeBean implements ViewBean {


    private String id;
    private String companyName;
    private String supplierCode;
    private short isSupplier;
    private String productOverview;
    private String contacts;
    private String contactNumber;
    private String fax;
    private String corporateRepresentative;
    private String corporateNumber;
    private String qualifications;
    private String unitIntroduction;
    private String address;
    private String postalCode;
    private String country;
    private String email;
    private String website;
    private String unitProperty_;
    private String unitPropertyText;
    private String businessCategory_;
    private String businessCategoryText;
    private String businessLicense;
    private String dutyParagraph;
    private String depositBank;
    private String bankAccount;
    private String clientele;
    private String raiseFundsAbility;
    private String fixedAssets;
    private String idCard;
    
    private List<ArchiveBean> archiveList = new ArrayList<>();
    
    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archiveReservedList = new ArrayList<>();

    public String getCompanyName() {
        return this.companyName;
    }



    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    public String getSupplierCode() {
        return this.supplierCode;
    }



    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }



    public short getIsSupplier() {
        return this.isSupplier;
    }



    public void setIsSupplier(short isSupplier) {
        this.isSupplier = isSupplier;
    }



    public String getProductOverview() {
        return this.productOverview;
    }



    public void setProductOverview(String productOverview) {
        this.productOverview = productOverview;
    }



    public String getContacts() {
        return this.contacts;
    }



    public void setContacts(String contacts) {
        this.contacts = contacts;
    }



    public String getContactNumber() {
        return this.contactNumber;
    }



    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }



    public String getFax() {
        return this.fax;
    }



    public void setFax(String fax) {
        this.fax = fax;
    }



    public String getCorporateRepresentative() {
        return this.corporateRepresentative;
    }



    public void setCorporateRepresentative(String corporateRepresentative) {
        this.corporateRepresentative = corporateRepresentative;
    }



    public String getCorporateNumber() {
        return this.corporateNumber;
    }



    public void setCorporateNumber(String corporateNumber) {
        this.corporateNumber = corporateNumber;
    }



    public String getQualifications() {
        return this.qualifications;
    }



    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }



    public String getUnitIntroduction() {
        return this.unitIntroduction;
    }



    public void setUnitIntroduction(String unitIntroduction) {
        this.unitIntroduction = unitIntroduction;
    }



    public String getAddress() {
        return this.address;
    }



    public void setAddress(String address) {
        this.address = address;
    }



    public String getPostalCode() {
        return this.postalCode;
    }



    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }



    public String getCountry() {
        return this.country;
    }



    public void setCountry(String country) {
        this.country = country;
    }



    public String getEmail() {
        return this.email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public String getWebsite() {
        return this.website;
    }



    public void setWebsite(String website) {
        this.website = website;
    }



    public String getBusinessLicense() {
        return this.businessLicense;
    }



    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }



    public String getDutyParagraph() {
        return this.dutyParagraph;
    }



    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }



    public String getDepositBank() {
        return this.depositBank;
    }



    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }



    public String getBankAccount() {
        return this.bankAccount;
    }



    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }



    public String getClientele() {
        return this.clientele;
    }



    public void setClientele(String clientele) {
        this.clientele = clientele;
    }



    public String getRaiseFundsAbility() {
        return this.raiseFundsAbility;
    }



    public void setRaiseFundsAbility(String raiseFundsAbility) {
        this.raiseFundsAbility = raiseFundsAbility;
    }



    public String getFixedAssets() {
        return this.fixedAssets;
    }



    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }



    public String getIdCard() {
        return this.idCard;
    }



    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    

    public List<ArchiveBean> getArchiveList() {
		return this.archiveList;
	}



	public void setArchiveList(List<ArchiveBean> archiveList) {
		this.archiveList = archiveList;
	}



	public String getUnitProperty_() {
        return unitProperty_;
    }



    public void setUnitProperty_(String unitProperty_) {
        this.unitProperty_ = unitProperty_;
    }



    public String getUnitPropertyText() {
        return unitPropertyText;
    }



    public void setUnitPropertyText(String unitPropertyText) {
        this.unitPropertyText = unitPropertyText;
    }



    public String getBusinessCategory_() {
        return businessCategory_;
    }



    public void setBusinessCategory_(String businessCategory_) {
        this.businessCategory_ = businessCategory_;
    }



    public String getBusinessCategoryText() {
        return businessCategoryText;
    }



    public void setBusinessCategoryText(String businessCategoryText) {
        this.businessCategoryText = businessCategoryText;
    }



	public List<String> getArchiveReservedList() {
		return this.archiveReservedList;
	}



	public void setArchiveReservedList(List<String> archiveReservedList) {
		this.archiveReservedList = archiveReservedList;
	}
    
}
