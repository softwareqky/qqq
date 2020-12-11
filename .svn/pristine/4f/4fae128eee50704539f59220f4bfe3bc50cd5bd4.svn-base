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
@Table(name = "t_income_contract")
public class IncomeContract implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9072785845743489416L;

    private String id = UUID.randomUUID().toString();

    private String contractName;
    private String serialNumber;
    private String contractYear;
    private Integer contractCount;
    private double contractAmount;
    private Short isTemporaryPricing;
    private Double amountExcludingTax;
    private int contractKind;
    private String contractAttribute;
    private Date contractTime;
    private String ontractAddress;
    private Date startTime;
    private Date endTime;
    private Person signingPeople;
    private Integer signingStatus;
    private String contractConstructionScale;
    private Integer signedContractsNumber;
    private String quantities;
    private String partyA;
    private String partyB;
    private String partyAAgent;
    private String partyBAgent;
    private String partyAContactInfo;
    private String partyBContactInfo;
    private String partyC;
    private String briefIntroduction;
    private Double amountCollected;
    private Date reminderDate;
    private String reminderPeople;
    private String generalContractor;
    private Integer chapterType;
    private String constructionUnitProjectNo;
    private String constructionUnitContractNo;
    private String insurance;
    private Short isFiled;
    private Date plannedOperationTime;
    private Date returnDate;
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

    private List<Archive> archivesToDelete = new ArrayList<>();

    @Override
    public IncomeContract clone() {
        IncomeContract p = null;
        try {
            p = (IncomeContract) super.clone();
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

    @Column(name = "contract_year", nullable = false, length = 36)
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

    @Column(name = "contract_kind", nullable = false)
    public int getContractKind() {
        return this.contractKind;
    }


    public void setContractKind(int contractKind) {
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
    @Column(name = "contract_time", nullable = false, length = 29)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signing_people", nullable = true)
    public Person getSigningPeople() {
        return this.signingPeople;
    }


    public void setSigningPeople(Person signingPeople) {
        this.signingPeople = signingPeople;
    }

    @Column(name = "signing_status", nullable = true)
    public Integer getSigningStatus() {
        return this.signingStatus;
    }


    public void setSigningStatus(Integer signingStatus) {
        this.signingStatus = signingStatus;
    }

    @Column(name = "contract_construction_scale", nullable = true, length = 36)
    public String getContractConstructionScale() {
        return this.contractConstructionScale;
    }


    public void setContractConstructionScale(String contractConstructionScale) {
        this.contractConstructionScale = contractConstructionScale;
    }

    @Column(name = "signed_contracts_number", nullable = true)
    public Integer getSignedContractsNumber() {
        return this.signedContractsNumber;
    }


    public void setSignedContractsNumber(Integer signedContractsNumber) {
        this.signedContractsNumber = signedContractsNumber;
    }

    @Column(name = "quantities", nullable = true, length = 36)
    public String getQuantities() {
        return this.quantities;
    }


    public void setQuantities(String quantities) {
        this.quantities = quantities;
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

    @Column(name = "party_a_agent", nullable = true, length = 36)
    public String getPartyAAgent() {
        return this.partyAAgent;
    }


    public void setPartyAAgent(String partyAAgent) {
        this.partyAAgent = partyAAgent;
    }

    @Column(name = "party_b_agent", nullable = true, length = 36)
    public String getPartyBAgent() {
        return this.partyBAgent;
    }


    public void setPartyBAgent(String partyBAgent) {
        this.partyBAgent = partyBAgent;
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

    @Column(name = "party_c", nullable = true, length = 36)
    public String getPartyC() {
        return this.partyC;
    }


    public void setPartyC(String partyC) {
        this.partyC = partyC;
    }

    @Column(name = "brief_introduction", nullable = true, length = 500)
    public String getBriefIntroduction() {
        return this.briefIntroduction;
    }


    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    @Column(name = "amount_collected", nullable = true)
    public Double getAmountCollected() {
        return this.amountCollected;
    }


    public void setAmountCollected(Double amountCollected) {
        this.amountCollected = amountCollected;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reminder_date", nullable = true, length = 29)
    public Date getReminderDate() {
        return this.reminderDate;
    }


    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    @Column(name = "reminder_people", nullable = true, length = 36)
    public String getReminderPeople() {
        return this.reminderPeople;
    }


    public void setReminderPeople(String reminderPeople) {
        this.reminderPeople = reminderPeople;
    }

    @Column(name = "general_contractor", nullable = true, length = 36)
    public String getGeneralContractor() {
        return this.generalContractor;
    }


    public void setGeneralContractor(String generalContractor) {
        this.generalContractor = generalContractor;
    }

    @Column(name = "chapter_type", nullable = true)
    public Integer getChapterType() {
        return this.chapterType;
    }


    public void setChapterType(Integer chapterType) {
        this.chapterType = chapterType;
    }

    @Column(name = "construction_unit_project_no", nullable = true, length = 36)
    public String getConstructionUnitProjectNo() {
        return this.constructionUnitProjectNo;
    }


    public void setConstructionUnitProjectNo(String constructionUnitProjectNo) {
        this.constructionUnitProjectNo = constructionUnitProjectNo;
    }

    @Column(name = "construction_unit_contract_no", nullable = true, length = 36)
    public String getConstructionUnitContractNo() {
        return this.constructionUnitContractNo;
    }


    public void setConstructionUnitContractNo(String constructionUnitContractNo) {
        this.constructionUnitContractNo = constructionUnitContractNo;
    }

    @Column(name = "insurance", nullable = true, length = 36)
    public String getInsurance() {
        return this.insurance;
    }


    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    @Column(name = "is_filed", nullable = true)
    public Short getIsFiled() {
        return this.isFiled;
    }


    public void setIsFiled(Short isFiled) {
        this.isFiled = isFiled;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "planned_operation_time", nullable = true, length = 29)
    public Date getPlannedOperationTime() {
        return this.plannedOperationTime;
    }


    public void setPlannedOperationTime(Date plannedOperationTime) {
        this.plannedOperationTime = plannedOperationTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_date", nullable = true, length = 29)
    public Date getReturnDate() {
        return this.returnDate;
    }


    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Column(name = "contract_return_info", nullable = true, length = 200)
    public String getContractReturnInfo() {
        return this.contractReturnInfo;
    }


    public void setContractReturnInfo(String contractReturnInfo) {
        this.contractReturnInfo = contractReturnInfo;
    }

    @Column(name = "countersignature_return", nullable = true, length = 200)
    public String getCountersignatureReturn() {
        return this.countersignatureReturn;
    }


    public void setCountersignatureReturn(String countersignatureReturn) {
        this.countersignatureReturn = countersignatureReturn;
    }

    @Column(name = "main_provisions", nullable = true, length = 200)
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
