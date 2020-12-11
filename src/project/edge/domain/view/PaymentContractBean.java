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
public class PaymentContractBean implements ViewBean {

    private String id;

    private String purchaseOrder_;
    private String purchaseOrderText;
    private String project_;
    private String projectText;
    private String virtualOrg_;
    private String virtualOrgText;
    private String contractName;
    private String serialNumber;
    private String contractYear;
    private Integer contractCount;
    private double contractAmount;
    private Short isTemporaryPricing;
    private String isTemporaryPricingText;
    private Double amountExcludingTax;
    private String contractKind_;
    private String contractKindText;
    private String contractAttribute;
    private String contractTime;
    private String ontractAddress;
    private String startTime;
    private String endTime;
    private String partyA;
    private String partyB;
    private String signingPeople_;
    private String signingPeopleText;
    private String partyBContact;
    private String partyAContactInfo;
    private String partyBContactInfo;
    private String briefIntroduction;
    private String sealType_;
    private String sealTypeText;
    private Short isIncludePendingData;
    private String isIncludePendingDataText;
    private String virtualContract;
    private String contractReturnInfo;
    private String countersignatureReturn;
    private String mainProvisions;
    private String remark;
    private String entryPerson_;
    private String entryPersonText;
    private String entryTime;
    private Integer dataSource;
    private String flowStartDate;
	private String flowEndDate;
	private int flowStatus;
	private String flowStatusText;


    private List<ArchiveBean> archivesList = new ArrayList<>();

    /**
     * 修改时，保留的档案文件id列表
     */
    private List<String> archivesReservedList = new ArrayList<>();

    @Override
    public String getId() {
        return this.id;
    }



    public String getPurchaseOrder_() {
        return this.purchaseOrder_;
    }



    public void setPurchaseOrder_(String purchaseOrder_) {
        this.purchaseOrder_ = purchaseOrder_;
    }



    public String getPurchaseOrderText() {
        return this.purchaseOrderText;
    }



    public void setPurchaseOrderText(String purchaseOrderText) {
        this.purchaseOrderText = purchaseOrderText;
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

    public String getVirtualOrg_() {
        return this.virtualOrg_;
    }


    public void setVirtualOrg_(String virtualOrg_) {
        this.virtualOrg_ = virtualOrg_;
    }


    public String getVirtualOrgText() {
        return this.virtualOrgText;
    }


    public void setVirtualOrgText(String virtualOrgText) {
        this.virtualOrgText = virtualOrgText;
    }


    public String getContractName() {
        return this.contractName;
    }



    public void setContractName(String contractName) {
        this.contractName = contractName;
    }



    public String getSerialNumber() {
        return this.serialNumber;
    }



    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }



    public String getContractYear() {
        return this.contractYear;
    }



    public void setContractYear(String contractYear) {
        this.contractYear = contractYear;
    }



    public Integer getContractCount() {
        return this.contractCount;
    }



    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }



    public double getContractAmount() {
        return this.contractAmount;
    }



    public void setContractAmount(double contractAmount) {
        this.contractAmount = contractAmount;
    }



    public Short getIsTemporaryPricing() {
        return this.isTemporaryPricing;
    }



    public void setIsTemporaryPricing(Short isTemporaryPricing) {
        this.isTemporaryPricing = isTemporaryPricing;
    }



    public String getIsTemporaryPricingText() {
        return this.isTemporaryPricingText;
    }



    public void setIsTemporaryPricingText(String isTemporaryPricingText) {
        this.isTemporaryPricingText = isTemporaryPricingText;
    }



    public Double getAmountExcludingTax() {
        return this.amountExcludingTax;
    }



    public void setAmountExcludingTax(Double amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }



    public String getContractKind_() {
        return this.contractKind_;
    }



    public void setContractKind_(String contractKind_) {
        this.contractKind_ = contractKind_;
    }
    
    
    public String getContractKindText() {
        return this.contractKindText;
    }


    public void setContractKindText(String contractKindText) {
        this.contractKindText = contractKindText;
    }



    public String getContractAttribute() {
        return this.contractAttribute;
    }



    public void setContractAttribute(String contractAttribute) {
        this.contractAttribute = contractAttribute;
    }



    public String getContractTime() {
        return this.contractTime;
    }



    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }



    public String getOntractAddress() {
        return this.ontractAddress;
    }



    public void setOntractAddress(String ontractAddress) {
        this.ontractAddress = ontractAddress;
    }



    public String getStartTime() {
        return this.startTime;
    }



    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }



    public String getEndTime() {
        return this.endTime;
    }



    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public String getPartyA() {
        return this.partyA;
    }



    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }



    public String getPartyB() {
        return this.partyB;
    }



    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }



    public String getSigningPeople_() {
        return this.signingPeople_;
    }



    public void setSigningPeople_(String signingPeople_) {
        this.signingPeople_ = signingPeople_;
    }



    public String getSigningPeopleText() {
        return this.signingPeopleText;
    }



    public void setSigningPeopleText(String signingPeopleText) {
        this.signingPeopleText = signingPeopleText;
    }



    public String getPartyBContact() {
        return this.partyBContact;
    }



    public void setPartyBContact(String partyBContact) {
        this.partyBContact = partyBContact;
    }



    public String getPartyAContactInfo() {
        return this.partyAContactInfo;
    }



    public void setPartyAContactInfo(String partyAContactInfo) {
        this.partyAContactInfo = partyAContactInfo;
    }



    public String getPartyBContactInfo() {
        return this.partyBContactInfo;
    }



    public void setPartyBContactInfo(String partyBContactInfo) {
        this.partyBContactInfo = partyBContactInfo;
    }



    public String getBriefIntroduction() {
        return this.briefIntroduction;
    }



    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }



    public String getSealType_() {
        return this.sealType_;
    }



    public void setSealType_(String sealType_) {
        this.sealType_ = sealType_;
    }


    public String getSealTypeText() {
        return this.sealTypeText;
    }


    public void setSealTypeText(String sealTypeText) {
        this.sealTypeText = sealTypeText;
    }

    public Short getIsIncludePendingData() {
        return this.isIncludePendingData;
    }



    public void setIsIncludePendingData(Short isIncludePendingData) {
        this.isIncludePendingData = isIncludePendingData;
    }



    public String getIsIncludePendingDataText() {
        return this.isIncludePendingDataText;
    }



    public void setIsIncludePendingDataText(String isIncludePendingDataText) {
        this.isIncludePendingDataText = isIncludePendingDataText;
    }



    public String getVirtualContract() {
        return this.virtualContract;
    }



    public void setVirtualContract(String virtualContract) {
        this.virtualContract = virtualContract;
    }



    public String getContractReturnInfo() {
        return this.contractReturnInfo;
    }



    public void setContractReturnInfo(String contractReturnInfo) {
        this.contractReturnInfo = contractReturnInfo;
    }



    public String getCountersignatureReturn() {
        return this.countersignatureReturn;
    }



    public void setCountersignatureReturn(String countersignatureReturn) {
        this.countersignatureReturn = countersignatureReturn;
    }



    public String getMainProvisions() {
        return this.mainProvisions;
    }



    public void setMainProvisions(String mainProvisions) {
        this.mainProvisions = mainProvisions;
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
    

    public Integer getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getFlowStartDate() {
		return this.flowStartDate;
	}

	public void setFlowStartDate(String flowStartDate) {
		this.flowStartDate = flowStartDate;
	}

	public String getFlowEndDate() {
		return this.flowEndDate;
	}

	public void setFlowEndDate(String flowEndDate) {
		this.flowEndDate = flowEndDate;
	}

	public int getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowStatusText() {
		return this.flowStatusText;
	}

	public void setFlowStatusText(String flowStatusText) {
		this.flowStatusText = flowStatusText;
	}
}
