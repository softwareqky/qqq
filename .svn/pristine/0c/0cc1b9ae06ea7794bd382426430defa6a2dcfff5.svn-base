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
@Table(name = "t_tenderee")
public class Tenderee implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = -6142471142997178529L;

    private String id = UUID.randomUUID().toString();

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
    private DataOption unitProperty;
    private DataOption businessCategory;
    private String businessLicense;
    private String dutyParagraph;
    private String depositBank;
    private String bankAccount;
    private String clientele;
    private String raiseFundsAbility;
    private String fixedAssets;
    private String idCard;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<TendereeAttachment> archives = new HashSet<>(0);
    private List<TendereeAttachment> archivesToDelete = new ArrayList<>();

    @Override
    public Tenderee clone() {
        Tenderee p = null;
        try {
            p = (Tenderee) super.clone();
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

    @Column(name = "company_name", nullable = false, length = 36)
    public String getCompanyName() {
        return this.companyName;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "supplier_code", nullable = false, length = 36)
    public String getSupplierCode() {
        return this.supplierCode;
    }


    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "is_supplier", nullable = false)
    public short getIsSupplier() {
        return this.isSupplier;
    }


    public void setIsSupplier(short isSupplier) {
        this.isSupplier = isSupplier;
    }

    @Column(name = "product_overview", nullable = true, length = 1024)
    public String getProductOverview() {
        return this.productOverview;
    }


    public void setProductOverview(String productOverview) {
        this.productOverview = productOverview;
    }

    @Column(name = "contacts", nullable = false, length = 36)
    public String getContacts() {
        return this.contacts;
    }


    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Column(name = "contact_number", nullable = true, length = 36)
    public String getContactNumber() {
        return this.contactNumber;
    }


    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Column(name = "fax", nullable = true, length = 36)
    public String getFax() {
        return this.fax;
    }


    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "corporate_representative", nullable = true, length = 36)
    public String getCorporateRepresentative() {
        return this.corporateRepresentative;
    }


    public void setCorporateRepresentative(String corporateRepresentative) {
        this.corporateRepresentative = corporateRepresentative;
    }

    @Column(name = "corporate_number", nullable = true, length = 36)
    public String getCorporateNumber() {
        return this.corporateNumber;
    }


    public void setCorporateNumber(String corporateNumber) {
        this.corporateNumber = corporateNumber;
    }

    @Column(name = "qualifications", nullable = true, length = 1024)
    public String getQualifications() {
        return this.qualifications;
    }


    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    @Column(name = "unit_introduction", nullable = true, length = 1024)
    public String getUnitIntroduction() {
        return this.unitIntroduction;
    }


    public void setUnitIntroduction(String unitIntroduction) {
        this.unitIntroduction = unitIntroduction;
    }

    @Column(name = "address", nullable = true, length = 100)
    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "postal_code", nullable = true, length = 36)
    public String getPostalCode() {
        return this.postalCode;
    }


    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "country", nullable = true, length = 36)
    public String getCountry() {
        return this.country;
    }


    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "email", nullable = true, length = 36)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_property", nullable = true)
    public DataOption getUnitProperty() {
        return this.unitProperty;
    }


    public void setUnitProperty(DataOption unitProperty) {
        this.unitProperty = unitProperty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_category", nullable = true)
    public DataOption getBusinessCategory() {
        return this.businessCategory;
    }


    public void setBusinessCategory(DataOption businessCategory) {
        this.businessCategory = businessCategory;
    }

    @Column(name = "business_license", nullable = true, length = 36)
    public String getBusinessLicense() {
        return this.businessLicense;
    }


    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    @Column(name = "duty_paragraph", nullable = true, length = 36)
    public String getDutyParagraph() {
        return this.dutyParagraph;
    }


    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    @Column(name = "deposit_bank", nullable = true, length = 36)
    public String getDepositBank() {
        return this.depositBank;
    }


    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    @Column(name = "bank_account", nullable = true, length = 36)
    public String getBankAccount() {
        return this.bankAccount;
    }


    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Column(name = "clientele", nullable = true, length = 36)
    public String getClientele() {
        return this.clientele;
    }


    public void setClientele(String clientele) {
        this.clientele = clientele;
    }

    @Column(name = "raise_funds_ability", nullable = true, length = 50)
    public String getRaiseFundsAbility() {
        return this.raiseFundsAbility;
    }


    public void setRaiseFundsAbility(String raiseFundsAbility) {
        this.raiseFundsAbility = raiseFundsAbility;
    }

    @Column(name = "fixed_assets", nullable = true, length = 50)
    public String getFixedAssets() {
        return this.fixedAssets;
    }


    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    @Column(name = "id_card", nullable = true, length = 36)
    public String getIdCard() {
        return this.idCard;
    }


    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenderee")
	public Set<TendereeAttachment> getArchives() {
		return this.archives;
	}

	public void setArchives(Set<TendereeAttachment> archives) {
		this.archives = archives;
	}

	@Transient
	public List<TendereeAttachment> getArchivesToDelete() {
		return this.archivesToDelete;
	}

	public void setArchivesToDelete(List<TendereeAttachment> archivesToDelete) {
		this.archivesToDelete = archivesToDelete;
	}


}
