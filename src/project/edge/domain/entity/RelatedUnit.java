package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
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
@Table(name = "t_related_unit")
public class RelatedUnit implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8394909060109675570L;

    private String id = UUID.randomUUID().toString();

    private DataOption unitType;
    private DataOption region;
    private DataOption unitGroup;
    private DataOption unitProperty;
    private DataOption businessType;

    private String unitName;
    private String unitCode;
    private String brand;
    private String productInfo;
    private String contact;
    private String contactMobile;
    private String unitPhone;
    private String fax;
    private String legalRepresentative;
    private String qulification;
    private String unitDesc;
    private String unitAddress;
    private String zipCode;
    private String country;
    private String email;
    private String website;
    private String businessLicense;
    private String taxNum;
    private String bankAccount;
    private String bankDeposit;
    private String delegation;
    private String fundAbility;
    private String fixedAsset;
    private String idCardNum;
    private Archive attachment;
    private short isRecommended;
    private short isDeleted;
    private short isBlacklist;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;


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
    @JoinColumn(name = "unit_type", nullable = true)
    public DataOption getUnitType() {
        return this.unitType;
    }


    public void setUnitType(DataOption unitType) {
        this.unitType = unitType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = true)
    public DataOption getRegion() {
        return this.region;
    }


    public void setRegion(DataOption region) {
        this.region = region;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_group", nullable = true)
    public DataOption getUnitGroup() {
        return this.unitGroup;
    }


    public void setUnitGroup(DataOption unitGroup) {
        this.unitGroup = unitGroup;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_property", nullable = true)
    public DataOption getUnitProperty() {
        return this.unitProperty;
    }


    public void setUnitProperty(DataOption unitProperty) {
        this.unitProperty = unitProperty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_type", nullable = true)
    public DataOption getBusinessType() {
        return this.businessType;
    }


    public void setBusinessType(DataOption businessType) {
        this.businessType = businessType;
    }

    @Column(name = "unit_name", nullable = false, length = 50)
    public String getUnitName() {
        return this.unitName;
    }


    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Column(name = "unit_code", nullable = true, length = 50)
    public String getUnitCode() {
        return this.unitCode;
    }


    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Column(name = "brand", nullable = true, length = 50)
    public String getBrand() {
        return this.brand;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "product_info", nullable = true, length = 1024)
    public String getProductInfo() {
        return this.productInfo;
    }


    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    @Column(name = "contact", nullable = true, length = 50)
    public String getContact() {
        return this.contact;
    }


    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "contact_mobile", nullable = true, length = 50)
    public String getContactMobile() {
        return this.contactMobile;
    }


    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    @Column(name = "unit_phone", nullable = true, length = 50)
    public String getUnitPhone() {
        return this.unitPhone;
    }


    public void setUnitPhone(String unitPhone) {
        this.unitPhone = unitPhone;
    }

    @Column(name = "fax", nullable = true, length = 50)
    public String getFax() {
        return this.fax;
    }


    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "legal_representative", nullable = true, length = 50)
    public String getLegalRepresentative() {
        return this.legalRepresentative;
    }


    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    @Column(name = "qulification", nullable = true, length = 50)
    public String getQulification() {
        return this.qulification;
    }


    public void setQulification(String qulification) {
        this.qulification = qulification;
    }

    @Column(name = "unit_desc", nullable = true, length = 1024)
    public String getUnitDesc() {
        return this.unitDesc;
    }


    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    @Column(name = "unit_address", nullable = true, length = 50)
    public String getUnitAddress() {
        return this.unitAddress;
    }


    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    @Column(name = "zip_code", nullable = true, length = 50)
    public String getZipCode() {
        return this.zipCode;
    }


    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "country", nullable = true, length = 50)
    public String getCountry() {
        return this.country;
    }


    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "website", nullable = true, length = 50)
    public String getWebsite() {
        return this.website;
    }


    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "business_license", nullable = false, length = 50)
    public String getBusinessLicense() {
        return this.businessLicense;
    }


    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    @Column(name = "tax_num", nullable = true, length = 50)
    public String getTaxNum() {
        return this.taxNum;
    }


    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    @Column(name = "bank_account", nullable = true, length = 50)
    public String getBankAccount() {
        return this.bankAccount;
    }


    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "bank_deposit", nullable = true, length = 50)
    public String getBankDeposit() {
        return this.bankDeposit;
    }


    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    @Column(name = "delegation", nullable = true, length = 50)
    public String getDelegation() {
        return this.delegation;
    }


    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    @Column(name = "fund_ability", nullable = true, length = 50)
    public String getFundAbility() {
        return this.fundAbility;
    }


    public void setFundAbility(String fundAbility) {
        this.fundAbility = fundAbility;
    }

    @Column(name = "fixed_asset", nullable = true, length = 50)
    public String getFixedAsset() {
        return this.fixedAsset;
    }


    public void setFixedAsset(String fixedAsset) {
        this.fixedAsset = fixedAsset;
    }

    @Column(name = "id_card_num", nullable = true, length = 50)
    public String getIdCardNum() {
        return this.idCardNum;
    }


    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment", nullable = true)
    public Archive getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Archive attachment) {
        this.attachment = attachment;
    }

    @Column(name = "is_recommended", nullable = true)
    public short getIsRecommended() {
        return this.isRecommended;
    }


    public void setIsRecommended(short isRecommended) {
        this.isRecommended = isRecommended;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    @Column(name = "is_blacklist", nullable = false)
    public short getIsBlacklist() {
        return this.isBlacklist;
    }

    
    public void setIsBlacklist(short isBlacklist) {
        this.isBlacklist = isBlacklist;
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


}
