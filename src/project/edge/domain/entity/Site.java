/**
 * 
 */
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


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_site")
public class Site implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3856130787975859035L;

    private String id = UUID.randomUUID().toString();

    private String siteNum;
    private Province province;
    private City city;
    private String initNum;
    private String initDesignFileStationName;
    private String constructionDrawingDesignNum;
    private String stationName;
    private int siteType;
    private DataOption networkRoomOwnerUnitType;
    private String networkRoomAddress;
    private String basicNetworkTransmitStationType;
    private String programmableNetworkTransmitStationType;
    private String sdnNetworkTransmitStationType;
    private Short isBasicNetworkTransmitStationType;
    private Short isProgrammableNetworkTransmitStationType;
    private Short isSdnNetworkTransmitStationType;

    private Double opticalLayerRack;
    private Double m24Rack;
    private Double u64Rack;
    private Double u32Rack;
    private Double transmitDeviceTotalPower;
    private Double transmitDevice63aReqAmount;
    private Double programmableRack;
    private Double programmableRackPower;
    private Double sdnRack;
    private Double sdnRackPower;
    private DataOption edgeNodeType;
    private Double edgeNodeRack;
    
    private Double directConnNodeTransmitRack;
    private Double directConnNodeTransmitDevicePower;
    private Double directConnNode63aReqAmount;
    private Double directConnNodeRouterRack;
    
    private Double programmableEdgeNodeRackPower;
    private Double programmableDirectConnNodeDataDevicePower;
    private Double sdnEdgeNodeRackPower;
    private Double sdnDirectConnNodeDataDevicePower;
    
    private Short isDivideSite;
    private Date siteDivideDate;
    private String siteDivideLocation;
    private Person siteDividePersonInCharge;
    
    private Double caictRack;
    private Double caictDeviceTotalPower;
    private Double caictDevice32aAmount;
    private Double compRack;
    private Double compRackTotalPower;
    private Double compRack32aAmount;
    private Double otherDevice;
    private Double dcDeviceRackAmount;
    private Double acDeviceRackAmount;
    private Double singleSiteSlotReqAmount;
    private Double dcFuse63aAmount;
    private Double dcTotalPower;
    private Double acFuse32aAmount;
    private Double acTotalPower;
    private Double singleSiteTotalPower;
    private Double spareRack;
    private String networkRoomContact;
    private String networkRoomContactTitle;
    private String networkRoomContactMobile;
    private String frameworkAgreementRemark;
    private Date initMaterialArchieveDate;
    private Date sdnInitMaterialArchieveDate;
    private Date programmableInitMaterialArchieveDate;
    private Date sdnEdgeNodeInitMaterialArchieveDate;
    private Date programmableEdgeNodeInitMaterialArchieveDate;
    private String networkPlan;
    private String systemDesign;
    private String deliveryCommissionConfigList;
    private String purchaseOrderNum;
    private Person basicNetworkGrantor;
    private Person basicNetworkGrantee;
    private String authority;
    private Double longitude;
    private Double latitude;
    private Short isCoordinate;
    private int siteStatus;
    private Integer basicNetworkManualSiteStatus;
    private Integer programmableNetworkManualSiteStatus;
    private Integer sdnNetworkManualSiteStatus;
    private Integer edgeNodeManualSiteStatus;
    private String remark;
    private short isDeleted;
    private Person personInCharge;
    private Person programmablePersonInCharge;
    private Person sdnPersonInCharge;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;


    private Short isEiaRegister;
    private String eiaRegister;
    private Short isBusinessInvitationReply;
    private String businessInvitationReply;
    private Short isFrameworkAgreement;
    private String frameworkAgreement;
    private Short isImplementAgreement;
    private String implementAgreement;
    private Short isNetworkRoomEnvironmentImprove;
    private String networkRoomEnvironmentImprove;
    private Short isNetworkPlan;
    private Short isSystemDesign;
    private Short isSiteTargetConfigDesign;
    private String siteTargetConfigDesign;
    private Short isOrderConfigList;
    private String orderConfigList;
    private Short isOrderDistribute;
    private String orderDistribute;
    private Short isMakeToStock;
    private String makeToStock;
    private Short isNetworkRoomImprove;
    private String networkRoomImprove;
    private Short isDeviceEnter;
    private String deviceEnter;
    private Short isDeviceReceipt;
    private String deviceReceipt;
    private Short isHardwareInstall;
    private String hardwareInstall;
    private Short isDevicePowerOn;
    private String devicePowerOn;
    private Short isInitMaterialArchieve;
    private String initMaterialArchieve;
    private Short isMaintainance;
    private String maintainance;
    private Short isPilot;
    private String pilot;
    private Short isFinalAcceptance;
    private String finalAcceptance;
    private Short isFixedAsset;
    private String fixedAsset;
    private Short isNationalAcceptance;
    private String nationalAcceptance;
    private Short isPgBidding;
    private String pgBidding;
    private Short isPgDeviceReceive;
    private String pgDeviceReceive;
    private Short isPgDeviceInstall;
    private String pgDeviceInstall;
    private Short isPgDeviceTest;
    private String pgDeviceTest;
    private Short isPgFibreTest;
    private String pgFibreTest;
    private Short isPgDeviceNetworkAccess;
    private String pgDeviceNetworkAccess;
    private Short isPgPilot;
    private String pgPilot;
    private Short isPgDataBusinessOn;
    private String pgDataBusinessOn;
    private Short isSdnBidding;
    private String sdnBidding;
    private Short isSdnDeviceReceive;
    private String sdnDeviceReceive;
    private Short isSdnDeviceInstall;
    private String sdnDeviceInstall;
    private Short isSdnDeviceTest;
    private String sdnDeviceTest;
    private Short isSdnDeviceNetworkAccess;
    private String sdnDeviceNetworkAccess;
    private short isMajorCity;
    private short isShow;
    private Short isCombineSite;

    private String networkRoomOwnerUnitName;
    private String edgeNodeTeam;
    private String mainTrunkNodeNum;
    private String mainTrunkNodeSiteName;
    
    private String sdnMainTrunkNodeDistance;
    private String sdnMainTrunkNodeModel;
    private String programmableMainTrunkNodeDistance;
    private String programmableMainTrunkNodeModel;
    
    private String sdnNetworkProgress;
    private String programmableNetworkProgress;
    private String basicNetworkProgress;
    //SDN边缘节点进展状态
    private String sdnEdgeProgress;
    //可编程边缘节点进展状态
    private String programmableEdgeProgress;
    private int totalProgress;          // 总体进度：当前已完成的建设计划数/总建设计划数
    
    private String sdnRackLocation;
    private String programmableRackLocation;
    private String basicRackLocation;
    
    private String dataNetworkNodeType;
    
    private String sdnRemark;
    private String programmableRemark;
    
    private Set<SiteAttachment> attachments = new HashSet<>(0);
    private List<SiteAttachment> attachmentsToDelete = new ArrayList<>();
    
    private Date flowStartDate;
	private Date flowEndDate;
	private int flowStatus;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "flow_start_date", nullable = true, length = 29)
	public Date getFlowStartDate() {
		return this.flowStartDate;
	}

	public void setFlowStartDate(Date flowStartDate) {
		this.flowStartDate = flowStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "flow_end_date", nullable = true, length = 29)
	public Date getFlowEndDate() {
		return this.flowEndDate;
	}

	public void setFlowEndDate(Date flowEndDate) {
		this.flowEndDate = flowEndDate;
	}

	@Column(name = "flow_status", nullable = false)
	public int getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}
	
	@Column(name = "is_divide_site", nullable = true)
	public Short getIsDivideSite() {
		return isDivideSite;
	}

	public void setIsDivideSite(Short isDivideSite) {
		this.isDivideSite = isDivideSite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "site_divide_date", nullable = true, length = 29)
	public Date getSiteDivideDate() {
		return siteDivideDate;
	}

	public void setSiteDivideDate(Date siteDivideDate) {
		this.siteDivideDate = siteDivideDate;
	}

	@Column(name = "site_divide_location", nullable = true, length = 50)
	public String getSiteDivideLocation() {
		return siteDivideLocation;
	}

	public void setSiteDivideLocation(String siteDivideLocation) {
		this.siteDivideLocation = siteDivideLocation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_divide_person_in_charge", nullable = true)
	public Person getSiteDividePersonInCharge() {
		return siteDividePersonInCharge;
	}

	public void setSiteDividePersonInCharge(Person siteDividePersonInCharge) {
		this.siteDividePersonInCharge = siteDividePersonInCharge;
	}

	@Column(name = "total_progress", nullable = false)
    public int getTotalProgress() {
		return totalProgress;
	}

	public void setTotalProgress(int totalProgress) {
		this.totalProgress = totalProgress;
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
    

    @Column(name = "site_num", nullable = true, length = 50)
    public String getSiteNum() {
        return this.siteNum;
    }

    @Column(name = "data_network_node_type", nullable = true, length = 50)
    public String getDataNetworkNodeType() {
		return dataNetworkNodeType;
	}

	public void setDataNetworkNodeType(String dataNetworkNodeType) {
		this.dataNetworkNodeType = dataNetworkNodeType;
	}

	public void setSiteNum(String siteNum) {
        this.siteNum = siteNum;
    }

    @Column(name = "init_num", nullable = true, length = 50)
    public String getInitNum() {
        return this.initNum;
    }


    public void setInitNum(String initNum) {
        this.initNum = initNum;
    }
    
    @Column(name = "sdn_rack_location", nullable = true, length = 50)
	public String getSdnRackLocation() {
		return sdnRackLocation;
	}

	public void setSdnRackLocation(String sdnRackLocation) {
		this.sdnRackLocation = sdnRackLocation;
	}

	@Column(name = "programmable_rack_location", nullable = true, length = 50)
	public String getProgrammableRackLocation() {
		return programmableRackLocation;
	}

	public void setProgrammableRackLocation(String programmableRackLocation) {
		this.programmableRackLocation = programmableRackLocation;
	}

	@Column(name = "basic_rack_location", nullable = true, length = 50)
	public String getBasicRackLocation() {
		return basicRackLocation;
	}

	public void setBasicRackLocation(String basicRackLocation) {
		this.basicRackLocation = basicRackLocation;
	}

	@Column(name = "init_design_file_station_name", nullable = true, length = 50)
    public String getInitDesignFileStationName() {
        return this.initDesignFileStationName;
    }


    public void setInitDesignFileStationName(String initDesignFileStationName) {
        this.initDesignFileStationName = initDesignFileStationName;
    }

    @Column(name = "construction_drawing_design_num", nullable = true, length = 50)
    public String getConstructionDrawingDesignNum() {
        return this.constructionDrawingDesignNum;
    }


    public void setConstructionDrawingDesignNum(String constructionDrawingDesignNum) {
        this.constructionDrawingDesignNum = constructionDrawingDesignNum;
    }

    @Column(name = "station_name", nullable = true, length = 50)
    public String getStationName() {
        return this.stationName;
    }


    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Column(name = "network_room_address", nullable = true, length = 100)
    public String getNetworkRoomAddress() {
        return this.networkRoomAddress;
    }


    public void setNetworkRoomAddress(String networkRoomAddress) {
        this.networkRoomAddress = networkRoomAddress;
    }

    @Column(name = "basic_network_transmit_station_type", nullable = true, length = 50)
    public String getBasicNetworkTransmitStationType() {
        return this.basicNetworkTransmitStationType;
    }

    public void setBasicNetworkTransmitStationType(String basicNetworkTransmitStationType) {
        this.basicNetworkTransmitStationType = basicNetworkTransmitStationType;
    }

    @Column(name = "programmable_network_transmit_station_type", nullable = true, length = 50)
    public String getProgrammableNetworkTransmitStationType() {
        return this.programmableNetworkTransmitStationType;
    }

    public void setProgrammableNetworkTransmitStationType(
            String programmableNetworkTransmitStationType) {
        this.programmableNetworkTransmitStationType = programmableNetworkTransmitStationType;
    }

    @Column(name = "sdn_network_transmit_station_type", nullable = true, length = 50)
    public String getSdnNetworkTransmitStationType() {
        return this.sdnNetworkTransmitStationType;
    }


    public void setSdnNetworkTransmitStationType(String sdnNetworkTransmitStationType) {
        this.sdnNetworkTransmitStationType = sdnNetworkTransmitStationType;
    }

    @Column(name = "optical_layer_rack", nullable = true)
    public Double getOpticalLayerRack() {
        return this.opticalLayerRack;
    }


    public void setOpticalLayerRack(Double opticalLayerRack) {
        this.opticalLayerRack = opticalLayerRack;
    }

    @Column(name = "m24_rack", nullable = true)
    public Double getM24Rack() {
        return this.m24Rack;
    }


    public void setM24Rack(Double m24Rack) {
        this.m24Rack = m24Rack;
    }

    @Column(name = "u64_rack", nullable = true)
    public Double getU64Rack() {
        return this.u64Rack;
    }


    public void setU64Rack(Double u64Rack) {
        this.u64Rack = u64Rack;
    }

    @Column(name = "u32_rack", nullable = true)
    public Double getU32Rack() {
        return this.u32Rack;
    }


    public void setU32Rack(Double u32Rack) {
        this.u32Rack = u32Rack;
    }

    @Column(name = "transmit_device_total_power", nullable = true)
    public Double getTransmitDeviceTotalPower() {
        return this.transmitDeviceTotalPower;
    }


    public void setTransmitDeviceTotalPower(Double transmitDeviceTotalPower) {
        this.transmitDeviceTotalPower = transmitDeviceTotalPower;
    }

    @Column(name = "transmit_device_63a_req_amount", nullable = true)
    public Double getTransmitDevice63aReqAmount() {
        return this.transmitDevice63aReqAmount;
    }


    public void setTransmitDevice63aReqAmount(Double transmitDevice63aReqAmount) {
        this.transmitDevice63aReqAmount = transmitDevice63aReqAmount;
    }

    @Column(name = "programmable_rack", nullable = true)
    public Double getProgrammableRack() {
        return this.programmableRack;
    }


    public void setProgrammableRack(Double programmableRack) {
        this.programmableRack = programmableRack;
    }

    @Column(name = "sdn_remark", nullable = true, length = 255)
    public String getSdnRemark() {
		return sdnRemark;
	}

	public void setSdnRemark(String sdnRemark) {
		this.sdnRemark = sdnRemark;
	}

	@Column(name = "programmable_remark", nullable = true, length = 255)
	public String getProgrammableRemark() {
		return programmableRemark;
	}

	public void setProgrammableRemark(String programmableRemark) {
		this.programmableRemark = programmableRemark;
	}

	@Column(name = "programmable_rack_power", nullable = true)
    public Double getProgrammableRackPower() {
        return this.programmableRackPower;
    }


    public void setProgrammableRackPower(Double programmableRackPower) {
        this.programmableRackPower = programmableRackPower;
    }

    @Column(name = "sdn_rack", nullable = true)
    public Double getSdnRack() {
        return this.sdnRack;
    }


    public void setSdnRack(Double sdnRack) {
        this.sdnRack = sdnRack;
    }

    @Column(name = "sdn_rack_power", nullable = true)
    public Double getSdnRackPower() {
        return this.sdnRackPower;
    }


    public void setSdnRackPower(Double sdnRackPower) {
        this.sdnRackPower = sdnRackPower;
    }

    @Column(name = "edge_node_rack", nullable = true)
    public Double getEdgeNodeRack() {
        return this.edgeNodeRack;
    }


    public void setEdgeNodeRack(Double edgeNodeRack) {
        this.edgeNodeRack = edgeNodeRack;
    }

    @Column(name = "programmable_edge_node_rack_power", nullable = true)
    public Double getProgrammableEdgeNodeRackPower() {
		return programmableEdgeNodeRackPower;
	}

	public void setProgrammableEdgeNodeRackPower(Double programmableEdgeNodeRackPower) {
		this.programmableEdgeNodeRackPower = programmableEdgeNodeRackPower;
	}

	@Column(name = "direct_conn_node_transmit_rack", nullable = true)
    public Double getDirectConnNodeTransmitRack() {
        return this.directConnNodeTransmitRack;
    }


    public void setDirectConnNodeTransmitRack(Double directConnNodeTransmitRack) {
        this.directConnNodeTransmitRack = directConnNodeTransmitRack;
    }

    @Column(name = "direct_conn_node_transmit_device_power", nullable = true)
    public Double getDirectConnNodeTransmitDevicePower() {
        return this.directConnNodeTransmitDevicePower;
    }


    public void setDirectConnNodeTransmitDevicePower(Double directConnNodeTransmitDevicePower) {
        this.directConnNodeTransmitDevicePower = directConnNodeTransmitDevicePower;
    }

    @Column(name = "direct_conn_node_63a_req_amount", nullable = true)
    public Double getDirectConnNode63aReqAmount() {
        return this.directConnNode63aReqAmount;
    }


    public void setDirectConnNode63aReqAmount(Double directConnNode63aReqAmount) {
        this.directConnNode63aReqAmount = directConnNode63aReqAmount;
    }

    @Column(name = "direct_conn_node_router_rack", nullable = true)
    public Double getDirectConnNodeRouterRack() {
        return this.directConnNodeRouterRack;
    }


    public void setDirectConnNodeRouterRack(Double directConnNodeRouterRack) {
        this.directConnNodeRouterRack = directConnNodeRouterRack;
    }
    
    @Column(name = "sdn_edge_node_rack_power", nullable = true)
    public Double getSdnEdgeNodeRackPower() {
		return sdnEdgeNodeRackPower;
	}

	public void setSdnEdgeNodeRackPower(Double sdnEdgeNodeRackPower) {
		this.sdnEdgeNodeRackPower = sdnEdgeNodeRackPower;
	}

	@Column(name = "sdn_direct_conn_node_data_device_power", nullable = true)
	public Double getSdnDirectConnNodeDataDevicePower() {
		return sdnDirectConnNodeDataDevicePower;
	}

	public void setSdnDirectConnNodeDataDevicePower(Double sdnDirectConnNodeDataDevicePower) {
		this.sdnDirectConnNodeDataDevicePower = sdnDirectConnNodeDataDevicePower;
	}

	@Column(name = "programmable_direct_conn_node_data_device_power", nullable = true)
    public Double getProgrammableDirectConnNodeDataDevicePower() {
		return programmableDirectConnNodeDataDevicePower;
	}

	public void setProgrammableDirectConnNodeDataDevicePower(Double programmableDirectConnNodeDataDevicePower) {
		this.programmableDirectConnNodeDataDevicePower = programmableDirectConnNodeDataDevicePower;
	}

	@Column(name = "caict_rack", nullable = true)
    public Double getCaictRack() {
        return this.caictRack;
    }


    public void setCaictRack(Double caictRack) {
        this.caictRack = caictRack;
    }

    @Column(name = "caict_device_total_power", nullable = true)
    public Double getCaictDeviceTotalPower() {
        return this.caictDeviceTotalPower;
    }


    public void setCaictDeviceTotalPower(Double caictDeviceTotalPower) {
        this.caictDeviceTotalPower = caictDeviceTotalPower;
    }

    @Column(name = "caict_device_32a_amount", nullable = true)
    public Double getCaictDevice32aAmount() {
        return this.caictDevice32aAmount;
    }


    public void setCaictDevice32aAmount(Double caictDevice32aAmount) {
        this.caictDevice32aAmount = caictDevice32aAmount;
    }

    @Column(name = "comp_rack", nullable = true)
    public Double getCompRack() {
        return this.compRack;
    }


    public void setCompRack(Double compRack) {
        this.compRack = compRack;
    }

    @Column(name = "comp_rack_total_power", nullable = true)
    public Double getCompRackTotalPower() {
        return this.compRackTotalPower;
    }


    public void setCompRackTotalPower(Double compRackTotalPower) {
        this.compRackTotalPower = compRackTotalPower;
    }

    @Column(name = "comp_rack_32a_amount", nullable = true)
    public Double getCompRack32aAmount() {
        return this.compRack32aAmount;
    }


    public void setCompRack32aAmount(Double compRack32aAmount) {
        this.compRack32aAmount = compRack32aAmount;
    }

    @Column(name = "other_device", nullable = true)
    public Double getOtherDevice() {
        return this.otherDevice;
    }


    public void setOtherDevice(Double otherDevice) {
        this.otherDevice = otherDevice;
    }

    @Column(name = "dc_device_rack_amount", nullable = true)
    public Double getDcDeviceRackAmount() {
        return this.dcDeviceRackAmount;
    }


    public void setDcDeviceRackAmount(Double dcDeviceRackAmount) {
        this.dcDeviceRackAmount = dcDeviceRackAmount;
    }

    @Column(name = "ac_device_rack_amount", nullable = true)
    public Double getAcDeviceRackAmount() {
        return this.acDeviceRackAmount;
    }


    public void setAcDeviceRackAmount(Double acDeviceRackAmount) {
        this.acDeviceRackAmount = acDeviceRackAmount;
    }

    @Column(name = "single_site_slot_req_amount", nullable = true)
    public Double getSingleSiteSlotReqAmount() {
        return this.singleSiteSlotReqAmount;
    }


    public void setSingleSiteSlotReqAmount(Double singleSiteSlotReqAmount) {
        this.singleSiteSlotReqAmount = singleSiteSlotReqAmount;
    }

    @Column(name = "dc_fuse_63a_amount", nullable = true)
    public Double getDcFuse63aAmount() {
        return this.dcFuse63aAmount;
    }


    public void setDcFuse63aAmount(Double dcFuse63aAmount) {
        this.dcFuse63aAmount = dcFuse63aAmount;
    }

    @Column(name = "dc_total_power", nullable = true)
    public Double getDcTotalPower() {
        return this.dcTotalPower;
    }


    public void setDcTotalPower(Double dcTotalPower) {
        this.dcTotalPower = dcTotalPower;
    }

    @Column(name = "ac_fuse_32a_amount", nullable = true)
    public Double getAcFuse32aAmount() {
        return this.acFuse32aAmount;
    }


    public void setAcFuse32aAmount(Double acFuse32aAmount) {
        this.acFuse32aAmount = acFuse32aAmount;
    }

    @Column(name = "ac_total_power", nullable = true)
    public Double getAcTotalPower() {
        return this.acTotalPower;
    }


    public void setAcTotalPower(Double acTotalPower) {
        this.acTotalPower = acTotalPower;
    }

    @Column(name = "single_site_total_power", nullable = true)
    public Double getSingleSiteTotalPower() {
        return this.singleSiteTotalPower;
    }


    public void setSingleSiteTotalPower(Double singleSiteTotalPower) {
        this.singleSiteTotalPower = singleSiteTotalPower;
    }

    @Column(name = "spare_rack", nullable = true)
    public Double getSpareRack() {
        return this.spareRack;
    }


    public void setSpareRack(Double spareRack) {
        this.spareRack = spareRack;
    }

    @Column(name = "network_room_contact_title", nullable = true, length = 100)
    public String getNetworkRoomContactTitle() {
        return this.networkRoomContactTitle;
    }


    public void setNetworkRoomContactTitle(String networkRoomContactTitle) {
        this.networkRoomContactTitle = networkRoomContactTitle;
    }

    @Column(name = "network_room_contact_mobile", nullable = true, length = 100)
    public String getNetworkRoomContactMobile() {
        return this.networkRoomContactMobile;
    }


    public void setNetworkRoomContactMobile(String networkRoomContactMobile) {
        this.networkRoomContactMobile = networkRoomContactMobile;
    }



    @Column(name = "framework_agreement_remark", nullable = true, length = 1024)
    public String getFrameworkAgreementRemark() {
        return this.frameworkAgreementRemark;
    }


    public void setFrameworkAgreementRemark(String frameworkAgreementRemark) {
        this.frameworkAgreementRemark = frameworkAgreementRemark;
    }


    @Column(name = "network_plan", nullable = true, length = 1024)
    public String getNetworkPlan() {
        return this.networkPlan;
    }


    public void setNetworkPlan(String networkPlan) {
        this.networkPlan = networkPlan;
    }

    @Column(name = "system_design", nullable = true, length = 1024)
    public String getSystemDesign() {
        return this.systemDesign;
    }


    public void setSystemDesign(String systemDesign) {
        this.systemDesign = systemDesign;
    }


    @Column(name = "purchase_order_num", nullable = true, length = 100)
    public String getPurchaseOrderNum() {
        return this.purchaseOrderNum;
    }


    public void setPurchaseOrderNum(String purchaseOrderNum) {
        this.purchaseOrderNum = purchaseOrderNum;
    }


    @Column(name = "authority", nullable = true, length = 50)
    public String getAuthority() {
        return this.authority;
    }


    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Column(name = "longitude", nullable = true)
    public Double getLongitude() {
        return this.longitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude", nullable = true)
    public Double getLatitude() {
        return this.latitude;
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "site_status", nullable = true)
    public int getSiteStatus() {
        return this.siteStatus;
    }


    public void setSiteStatus(int siteStatus) {
        this.siteStatus = siteStatus;
    }

    @Column(name = "basic_network_manual_site_status", nullable = true)
    public Integer getBasicNetworkManualSiteStatus() {
        return this.basicNetworkManualSiteStatus;
    }


    public void setBasicNetworkManualSiteStatus(Integer basicNetworkManualSiteStatus) {
        this.basicNetworkManualSiteStatus = basicNetworkManualSiteStatus;
    }

    @Column(name = "programmable_network_manual_site_status", nullable = true)
    public Integer getProgrammableNetworkManualSiteStatus() {
        return this.programmableNetworkManualSiteStatus;
    }


    public void setProgrammableNetworkManualSiteStatus(
            Integer programmableNetworkManualSiteStatus) {
        this.programmableNetworkManualSiteStatus = programmableNetworkManualSiteStatus;
    }

    @Column(name = "sdn_network_manual_site_status", nullable = true)
    public Integer getSdnNetworkManualSiteStatus() {
        return this.sdnNetworkManualSiteStatus;
    }


    public void setSdnNetworkManualSiteStatus(Integer sdnNetworkManualSiteStatus) {
        this.sdnNetworkManualSiteStatus = sdnNetworkManualSiteStatus;
    }

    @Column(name = "edge_node_manual_site_status", nullable = true)
    public Integer getEdgeNodeManualSiteStatus() {
        return this.edgeNodeManualSiteStatus;
    }


    public void setEdgeNodeManualSiteStatus(Integer edgeNodeManualSiteStatus) {
        this.edgeNodeManualSiteStatus = edgeNodeManualSiteStatus;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "is_deleted", nullable = true)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "network_room_owner_unit_type", nullable = true)
    public DataOption getNetworkRoomOwnerUnitType() {
        return this.networkRoomOwnerUnitType;
    }


    public void setNetworkRoomOwnerUnitType(DataOption networkRoomOwnerUnitType) {
        this.networkRoomOwnerUnitType = networkRoomOwnerUnitType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edge_node_type", nullable = true)
    public DataOption getEdgeNodeType() {
        return this.edgeNodeType;
    }


    public void setEdgeNodeType(DataOption edgeNodeType) {
        this.edgeNodeType = edgeNodeType;
    }

    @Column(name = "network_room_contact", nullable = true)
    public String getNetworkRoomContact() {
        return this.networkRoomContact;
    }


    public void setNetworkRoomContact(String networkRoomContact) {
        this.networkRoomContact = networkRoomContact;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_network_grantor", nullable = true)
    public Person getBasicNetworkGrantor() {
        return this.basicNetworkGrantor;
    }


    public void setBasicNetworkGrantor(Person basicNetworkGrantor) {
        this.basicNetworkGrantor = basicNetworkGrantor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_network_grantee", nullable = true)
    public Person getBasicNetworkGrantee() {
        return this.basicNetworkGrantee;
    }


    public void setBasicNetworkGrantee(Person basicNetworkGrantee) {
        this.basicNetworkGrantee = basicNetworkGrantee;
    }


    @Column(name = "delivery_commission_config_list", nullable = true, length = 1024)
    public String getDeliveryCommissionConfigList() {
        return this.deliveryCommissionConfigList;
    }


    public void setDeliveryCommissionConfigList(String deliveryCommissionConfigList) {
        this.deliveryCommissionConfigList = deliveryCommissionConfigList;
    }

    @Column(name = "is_eia_register", nullable = true)
    public Short getIsEiaRegister() {
        return this.isEiaRegister;
    }


    public void setIsEiaRegister(Short isEiaRegister) {
        this.isEiaRegister = isEiaRegister;
    }

    @Column(name = "eia_register", nullable = true, length = 1024)
    public String getEiaRegister() {
        return this.eiaRegister;
    }


    public void setEiaRegister(String eiaRegister) {
        this.eiaRegister = eiaRegister;
    }

    @Column(name = "is_business_invitation_reply", nullable = true)
    public Short getIsBusinessInvitationReply() {
        return this.isBusinessInvitationReply;
    }


    public void setIsBusinessInvitationReply(Short isBusinessInvitationReply) {
        this.isBusinessInvitationReply = isBusinessInvitationReply;
    }

    @Column(name = "business_invitation_reply", nullable = true, length = 1024)
    public String getBusinessInvitationReply() {
        return this.businessInvitationReply;
    }


    public void setBusinessInvitationReply(String businessInvitationReply) {
        this.businessInvitationReply = businessInvitationReply;
    }

    @Column(name = "is_framework_agreement", nullable = true)
    public Short getIsFrameworkAgreement() {
        return this.isFrameworkAgreement;
    }


    public void setIsFrameworkAgreement(Short isFrameworkAgreement) {
        this.isFrameworkAgreement = isFrameworkAgreement;
    }

    @Column(name = "framework_agreement", nullable = true, length = 1024)
    public String getFrameworkAgreement() {
        return this.frameworkAgreement;
    }


    public void setFrameworkAgreement(String frameworkAgreement) {
        this.frameworkAgreement = frameworkAgreement;
    }

    @Column(name = "is_implement_agreement", nullable = true)
    public Short getIsImplementAgreement() {
        return this.isImplementAgreement;
    }


    public void setIsImplementAgreement(Short isImplementAgreement) {
        this.isImplementAgreement = isImplementAgreement;
    }

    @Column(name = "implement_agreement", nullable = true, length = 1024)
    public String getImplementAgreement() {
        return this.implementAgreement;
    }


    public void setImplementAgreement(String implementAgreement) {
        this.implementAgreement = implementAgreement;
    }

    @Column(name = "is_network_room_environment_improve", nullable = true)
    public Short getIsNetworkRoomEnvironmentImprove() {
        return this.isNetworkRoomEnvironmentImprove;
    }


    public void setIsNetworkRoomEnvironmentImprove(Short isNetworkRoomEnvironmentImprove) {
        this.isNetworkRoomEnvironmentImprove = isNetworkRoomEnvironmentImprove;
    }

    @Column(name = "network_room_environment_improve", nullable = true, length = 1024)
    public String getNetworkRoomEnvironmentImprove() {
        return this.networkRoomEnvironmentImprove;
    }


    public void setNetworkRoomEnvironmentImprove(String networkRoomEnvironmentImprove) {
        this.networkRoomEnvironmentImprove = networkRoomEnvironmentImprove;
    }

    @Column(name = "is_network_plan", nullable = true)
    public Short getIsNetworkPlan() {
        return this.isNetworkPlan;
    }


    public void setIsNetworkPlan(Short isNetworkPlan) {
        this.isNetworkPlan = isNetworkPlan;
    }

    @Column(name = "is_system_design", nullable = true)
    public Short getIsSystemDesign() {
        return this.isSystemDesign;
    }


    public void setIsSystemDesign(Short isSystemDesign) {
        this.isSystemDesign = isSystemDesign;
    }

    @Column(name = "is_site_target_config_design", nullable = true)
    public Short getIsSiteTargetConfigDesign() {
        return this.isSiteTargetConfigDesign;
    }


    public void setIsSiteTargetConfigDesign(Short isSiteTargetConfigDesign) {
        this.isSiteTargetConfigDesign = isSiteTargetConfigDesign;
    }

    @Column(name = "site_target_config_design", nullable = true, length = 1024)
    public String getSiteTargetConfigDesign() {
        return this.siteTargetConfigDesign;
    }


    public void setSiteTargetConfigDesign(String siteTargetConfigDesign) {
        this.siteTargetConfigDesign = siteTargetConfigDesign;
    }

    @Column(name = "is_order_config_list", nullable = true)
    public Short getIsOrderConfigList() {
        return this.isOrderConfigList;
    }


    public void setIsOrderConfigList(Short isOrderConfigList) {
        this.isOrderConfigList = isOrderConfigList;
    }

    @Column(name = "order_config_list", nullable = true, length = 1024)
    public String getOrderConfigList() {
        return this.orderConfigList;
    }


    public void setOrderConfigList(String orderConfigList) {
        this.orderConfigList = orderConfigList;
    }

    @Column(name = "is_order_distribute", nullable = true)
    public Short getIsOrderDistribute() {
        return this.isOrderDistribute;
    }


    public void setIsOrderDistribute(Short isOrderDistribute) {
        this.isOrderDistribute = isOrderDistribute;
    }

    @Column(name = "order_distribute", nullable = true, length = 1024)
    public String getOrderDistribute() {
        return this.orderDistribute;
    }


    public void setOrderDistribute(String orderDistribute) {
        this.orderDistribute = orderDistribute;
    }

    @Column(name = "is_make_to_stock", nullable = true)
    public Short getIsMakeToStock() {
        return this.isMakeToStock;
    }


    public void setIsMakeToStock(Short isMakeToStock) {
        this.isMakeToStock = isMakeToStock;
    }

    @Column(name = "make_to_stock", nullable = true, length = 1024)
    public String getMakeToStock() {
        return this.makeToStock;
    }


    public void setMakeToStock(String makeToStock) {
        this.makeToStock = makeToStock;
    }

    @Column(name = "is_network_room_improve", nullable = true)
    public Short getIsNetworkRoomImprove() {
        return this.isNetworkRoomImprove;
    }


    public void setIsNetworkRoomImprove(Short isNetworkRoomImprove) {
        this.isNetworkRoomImprove = isNetworkRoomImprove;
    }

    @Column(name = "network_room_improve", nullable = true, length = 1024)
    public String getNetworkRoomImprove() {
        return this.networkRoomImprove;
    }


    public void setNetworkRoomImprove(String networkRoomImprove) {
        this.networkRoomImprove = networkRoomImprove;
    }

    @Column(name = "is_device_enter", nullable = true)
    public Short getIsDeviceEnter() {
        return this.isDeviceEnter;
    }


    public void setIsDeviceEnter(Short isDeviceEnter) {
        this.isDeviceEnter = isDeviceEnter;
    }

    @Column(name = "device_enter", nullable = true, length = 1024)
    public String getDeviceEnter() {
        return this.deviceEnter;
    }


    public void setDeviceEnter(String deviceEnter) {
        this.deviceEnter = deviceEnter;
    }

    @Column(name = "is_device_receipt", nullable = true)
    public Short getIsDeviceReceipt() {
        return this.isDeviceReceipt;
    }


    public void setIsDeviceReceipt(Short isDeviceReceipt) {
        this.isDeviceReceipt = isDeviceReceipt;
    }

    @Column(name = "device_receipt", nullable = true, length = 1024)
    public String getDeviceReceipt() {
        return this.deviceReceipt;
    }


    public void setDeviceReceipt(String deviceReceipt) {
        this.deviceReceipt = deviceReceipt;
    }

    @Column(name = "is_hardware_install", nullable = true)
    public Short getIsHardwareInstall() {
        return this.isHardwareInstall;
    }


    public void setIsHardwareInstall(Short isHardwareInstall) {
        this.isHardwareInstall = isHardwareInstall;
    }

    @Column(name = "hardware_install", nullable = true, length = 1024)
    public String getHardwareInstall() {
        return this.hardwareInstall;
    }


    public void setHardwareInstall(String hardwareInstall) {
        this.hardwareInstall = hardwareInstall;
    }

    @Column(name = "is_device_power_on", nullable = true)
    public Short getIsDevicePowerOn() {
        return this.isDevicePowerOn;
    }


    public void setIsDevicePowerOn(Short isDevicePowerOn) {
        this.isDevicePowerOn = isDevicePowerOn;
    }

    @Column(name = "device_power_on", nullable = true, length = 1024)
    public String getDevicePowerOn() {
        return this.devicePowerOn;
    }


    public void setDevicePowerOn(String devicePowerOn) {
        this.devicePowerOn = devicePowerOn;
    }

    @Column(name = "is_init_material_archieve", nullable = true)
    public Short getIsInitMaterialArchieve() {
        return this.isInitMaterialArchieve;
    }


    public void setIsInitMaterialArchieve(Short isInitMaterialArchieve) {
        this.isInitMaterialArchieve = isInitMaterialArchieve;
    }

    @Column(name = "init_material_archieve", nullable = true, length = 1024)
    public String getInitMaterialArchieve() {
        return this.initMaterialArchieve;
    }


    public void setInitMaterialArchieve(String initMaterialArchieve) {
        this.initMaterialArchieve = initMaterialArchieve;
    }

    @Column(name = "is_maintainance", nullable = true)
    public Short getIsMaintainance() {
        return this.isMaintainance;
    }


    public void setIsMaintainance(Short isMaintainance) {
        this.isMaintainance = isMaintainance;
    }

    @Column(name = "maintainance", nullable = true, length = 1024)
    public String getMaintainance() {
        return this.maintainance;
    }


    public void setMaintainance(String maintainance) {
        this.maintainance = maintainance;
    }

    @Column(name = "is_pilot", nullable = true)
    public Short getIsPilot() {
        return this.isPilot;
    }


    public void setIsPilot(Short isPilot) {
        this.isPilot = isPilot;
    }

    @Column(name = "pilot", nullable = true, length = 1024)
    public String getPilot() {
        return this.pilot;
    }


    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    @Column(name = "is_final_acceptance", nullable = true)
    public Short getIsFinalAcceptance() {
        return this.isFinalAcceptance;
    }


    public void setIsFinalAcceptance(Short isFinalAcceptance) {
        this.isFinalAcceptance = isFinalAcceptance;
    }

    @Column(name = "final_acceptance", nullable = true, length = 1024)
    public String getFinalAcceptance() {
        return this.finalAcceptance;
    }


    public void setFinalAcceptance(String finalAcceptance) {
        this.finalAcceptance = finalAcceptance;
    }

    @Column(name = "is_fixed_asset", nullable = true)
    public Short getIsFixedAsset() {
        return this.isFixedAsset;
    }


    public void setIsFixedAsset(Short isFixedAsset) {
        this.isFixedAsset = isFixedAsset;
    }

    @Column(name = "fixed_asset", nullable = true, length = 1024)
    public String getFixedAsset() {
        return this.fixedAsset;
    }


    public void setFixedAsset(String fixedAsset) {
        this.fixedAsset = fixedAsset;
    }

    @Column(name = "is_national_acceptance", nullable = true)
    public Short getIsNationalAcceptance() {
        return this.isNationalAcceptance;
    }


    public void setIsNationalAcceptance(Short isNationalAcceptance) {
        this.isNationalAcceptance = isNationalAcceptance;
    }

    @Column(name = "national_acceptance", nullable = true, length = 1024)
    public String getNationalAcceptance() {
        return this.nationalAcceptance;
    }


    public void setNationalAcceptance(String nationalAcceptance) {
        this.nationalAcceptance = nationalAcceptance;
    }

    @Column(name = "is_pg_bidding", nullable = true)
    public Short getIsPgBidding() {
        return this.isPgBidding;
    }


    public void setIsPgBidding(Short isPgBidding) {
        this.isPgBidding = isPgBidding;
    }

    @Column(name = "pg_bidding", nullable = true, length = 1024)
    public String getPgBidding() {
        return this.pgBidding;
    }


    public void setPgBidding(String pgBidding) {
        this.pgBidding = pgBidding;
    }

    @Column(name = "is_pg_device_receive", nullable = true)
    public Short getIsPgDeviceReceive() {
        return this.isPgDeviceReceive;
    }


    public void setIsPgDeviceReceive(Short isPgDeviceReceive) {
        this.isPgDeviceReceive = isPgDeviceReceive;
    }

    @Column(name = "pg_device_receive", nullable = true, length = 1024)
    public String getPgDeviceReceive() {
        return this.pgDeviceReceive;
    }


    public void setPgDeviceReceive(String pgDeviceReceive) {
        this.pgDeviceReceive = pgDeviceReceive;
    }

    @Column(name = "is_pg_device_install", nullable = true)
    public Short getIsPgDeviceInstall() {
        return this.isPgDeviceInstall;
    }


    public void setIsPgDeviceInstall(Short isPgDeviceInstall) {
        this.isPgDeviceInstall = isPgDeviceInstall;
    }

    @Column(name = "pg_device_install", nullable = true, length = 1024)
    public String getPgDeviceInstall() {
        return this.pgDeviceInstall;
    }


    public void setPgDeviceInstall(String pgDeviceInstall) {
        this.pgDeviceInstall = pgDeviceInstall;
    }

    @Column(name = "is_pg_device_test", nullable = true)
    public Short getIsPgDeviceTest() {
        return this.isPgDeviceTest;
    }


    public void setIsPgDeviceTest(Short isPgDeviceTest) {
        this.isPgDeviceTest = isPgDeviceTest;
    }

    @Column(name = "pg_device_test", nullable = true, length = 1024)
    public String getPgDeviceTest() {
        return this.pgDeviceTest;
    }


    public void setPgDeviceTest(String pgDeviceTest) {
        this.pgDeviceTest = pgDeviceTest;
    }

    @Column(name = "is_pg_fibre_test", nullable = true)
    public Short getIsPgFibreTest() {
        return this.isPgFibreTest;
    }


    public void setIsPgFibreTest(Short isPgFibreTest) {
        this.isPgFibreTest = isPgFibreTest;
    }

    @Column(name = "pg_fibre_test", nullable = true, length = 1024)
    public String getPgFibreTest() {
        return this.pgFibreTest;
    }


    public void setPgFibreTest(String pgFibreTest) {
        this.pgFibreTest = pgFibreTest;
    }

    @Column(name = "is_pg_device_network_access", nullable = true)
    public Short getIsPgDeviceNetworkAccess() {
        return this.isPgDeviceNetworkAccess;
    }


    public void setIsPgDeviceNetworkAccess(Short isPgDeviceNetworkAccess) {
        this.isPgDeviceNetworkAccess = isPgDeviceNetworkAccess;
    }

    @Column(name = "pg_device_network_access", nullable = true, length = 1024)
    public String getPgDeviceNetworkAccess() {
        return this.pgDeviceNetworkAccess;
    }


    public void setPgDeviceNetworkAccess(String pgDeviceNetworkAccess) {
        this.pgDeviceNetworkAccess = pgDeviceNetworkAccess;
    }

    @Column(name = "is_pg_pilot", nullable = true)
    public Short getIsPgPilot() {
        return this.isPgPilot;
    }


    public void setIsPgPilot(Short isPgPilot) {
        this.isPgPilot = isPgPilot;
    }

    @Column(name = "pg_pilot", nullable = true, length = 1024)
    public String getPgPilot() {
        return this.pgPilot;
    }


    public void setPgPilot(String pgPilot) {
        this.pgPilot = pgPilot;
    }

    @Column(name = "is_pg_data_business_on", nullable = true)
    public Short getIsPgDataBusinessOn() {
        return this.isPgDataBusinessOn;
    }


    public void setIsPgDataBusinessOn(Short isPgDataBusinessOn) {
        this.isPgDataBusinessOn = isPgDataBusinessOn;
    }

    @Column(name = "pg_data_business_on", nullable = true, length = 1024)
    public String getPgDataBusinessOn() {
        return this.pgDataBusinessOn;
    }


    public void setPgDataBusinessOn(String pgDataBusinessOn) {
        this.pgDataBusinessOn = pgDataBusinessOn;
    }

    @Column(name = "is_sdn_bidding", nullable = true)
    public Short getIsSdnBidding() {
        return this.isSdnBidding;
    }


    public void setIsSdnBidding(Short isSdnBidding) {
        this.isSdnBidding = isSdnBidding;
    }

    @Column(name = "sdn_bidding", nullable = true, length = 1024)
    public String getSdnBidding() {
        return this.sdnBidding;
    }


    public void setSdnBidding(String sdnBidding) {
        this.sdnBidding = sdnBidding;
    }

    @Column(name = "is_sdn_device_receive", nullable = true)
    public Short getIsSdnDeviceReceive() {
        return this.isSdnDeviceReceive;
    }


    public void setIsSdnDeviceReceive(Short isSdnDeviceReceive) {
        this.isSdnDeviceReceive = isSdnDeviceReceive;
    }

    @Column(name = "sdn_device_receive", nullable = true, length = 1024)
    public String getSdnDeviceReceive() {
        return this.sdnDeviceReceive;
    }


    public void setSdnDeviceReceive(String sdnDeviceReceive) {
        this.sdnDeviceReceive = sdnDeviceReceive;
    }

    @Column(name = "is_sdn_device_install", nullable = true)
    public Short getIsSdnDeviceInstall() {
        return this.isSdnDeviceInstall;
    }


    public void setIsSdnDeviceInstall(Short isSdnDeviceInstall) {
        this.isSdnDeviceInstall = isSdnDeviceInstall;
    }

    @Column(name = "sdn_device_install", nullable = true, length = 1024)
    public String getSdnDeviceInstall() {
        return this.sdnDeviceInstall;
    }


    public void setSdnDeviceInstall(String sdnDeviceInstall) {
        this.sdnDeviceInstall = sdnDeviceInstall;
    }

    @Column(name = "is_sdn_device_test", nullable = true)
    public Short getIsSdnDeviceTest() {
        return this.isSdnDeviceTest;
    }


    public void setIsSdnDeviceTest(Short isSdnDeviceTest) {
        this.isSdnDeviceTest = isSdnDeviceTest;
    }

    @Column(name = "sdn_device_test", nullable = true, length = 1024)
    public String getSdnDeviceTest() {
        return this.sdnDeviceTest;
    }


    public void setSdnDeviceTest(String sdnDeviceTest) {
        this.sdnDeviceTest = sdnDeviceTest;
    }

    @Column(name = "is_sdn_device_network_access", nullable = true)
    public Short getIsSdnDeviceNetworkAccess() {
        return this.isSdnDeviceNetworkAccess;
    }


    public void setIsSdnDeviceNetworkAccess(Short isSdnDeviceNetworkAccess) {
        this.isSdnDeviceNetworkAccess = isSdnDeviceNetworkAccess;
    }

    @Column(name = "sdn_device_network_access", nullable = true, length = 1024)
    public String getSdnDeviceNetworkAccess() {
        return this.sdnDeviceNetworkAccess;
    }


    public void setSdnDeviceNetworkAccess(String sdnDeviceNetworkAccess) {
        this.sdnDeviceNetworkAccess = sdnDeviceNetworkAccess;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = true)
    public Province getProvince() {
        return this.province;
    }


    public void setProvince(Province province) {
        this.province = province;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = true)
    public City getCity() {
        return this.city;
    }


    public void setCity(City city) {
        this.city = city;
    }

    @Column(name = "site_type", nullable = false)
    public int getSiteType() {
        return this.siteType;
    }


    public void setSiteType(int siteType) {
        this.siteType = siteType;
    }

    @Column(name = "is_coordinate", nullable = true)
    public Short getIsCoordinate() {
        return this.isCoordinate;
    }


    public void setIsCoordinate(Short isCoordinate) {
        this.isCoordinate = isCoordinate;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sdn_init_material_archieve_date", nullable = true, length = 29)
    public Date getSdnInitMaterialArchieveDate() {
		return sdnInitMaterialArchieveDate;
	}

	public void setSdnInitMaterialArchieveDate(Date sdnInitMaterialArchieveDate) {
		this.sdnInitMaterialArchieveDate = sdnInitMaterialArchieveDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sdn_edge_node_init_material_archieve_date", nullable = true, length = 29)
	public Date getSdnEdgeNodeInitMaterialArchieveDate() {
		return sdnEdgeNodeInitMaterialArchieveDate;
	}

	public void setSdnEdgeNodeInitMaterialArchieveDate(Date sdnEdgeNodeInitMaterialArchieveDate) {
		this.sdnEdgeNodeInitMaterialArchieveDate = sdnEdgeNodeInitMaterialArchieveDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "programmable_edge_node_init_material_archieve_date", nullable = true, length = 29)
	public Date getProgrammableEdgeNodeInitMaterialArchieveDate() {
		return programmableEdgeNodeInitMaterialArchieveDate;
	}

	public void setProgrammableEdgeNodeInitMaterialArchieveDate(Date programmableEdgeNodeInitMaterialArchieveDate) {
		this.programmableEdgeNodeInitMaterialArchieveDate = programmableEdgeNodeInitMaterialArchieveDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "programmable_init_material_archieve_date", nullable = true, length = 29)
	public Date getProgrammableInitMaterialArchieveDate() {
		return programmableInitMaterialArchieveDate;
	}

	public void setProgrammableInitMaterialArchieveDate(Date programmableInitMaterialArchieveDate) {
		this.programmableInitMaterialArchieveDate = programmableInitMaterialArchieveDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "init_material_archieve_date", nullable = false, length = 29)
    public Date getInitMaterialArchieveDate() {
        return this.initMaterialArchieveDate;
    }


    public void setInitMaterialArchieveDate(Date initMaterialArchieveDate) {
        this.initMaterialArchieveDate = initMaterialArchieveDate;
    }

    @Column(name = "is_major_city", nullable = false)
    public short getIsMajorCity() {
        return this.isMajorCity;
    }


    public void setIsMajorCity(short isMajorCity) {
        this.isMajorCity = isMajorCity;
    }

    @Column(name = "is_show", nullable = false)
    public short getIsShow() {
        return this.isShow;
    }


    public void setIsShow(short isShow) {
        this.isShow = isShow;
    }

    @Column(name = "is_combine_site", nullable = true)
    public Short getIsCombineSite() {
        return this.isCombineSite;
    }


    public void setIsCombineSite(Short isCombineSite) {
        this.isCombineSite = isCombineSite;
    }

    @Column(name = "is_basic_network_transmit_station_type", nullable = true)
    public Short getIsBasicNetworkTransmitStationType() {
        return this.isBasicNetworkTransmitStationType;
    }


    public void setIsBasicNetworkTransmitStationType(Short isBasicNetworkTransmitStationType) {
        this.isBasicNetworkTransmitStationType = isBasicNetworkTransmitStationType;
    }

    @Column(name = "is_programmable_network_transmit_station_type", nullable = true)
    public Short getIsProgrammableNetworkTransmitStationType() {
        return this.isProgrammableNetworkTransmitStationType;
    }


    public void setIsProgrammableNetworkTransmitStationType(
            Short isProgrammableNetworkTransmitStationType) {
        this.isProgrammableNetworkTransmitStationType = isProgrammableNetworkTransmitStationType;
    }

    @Column(name = "is_sdn_network_transmit_station_type", nullable = true)
    public Short getIsSdnNetworkTransmitStationType() {
        return this.isSdnNetworkTransmitStationType;
    }


    public void setIsSdnNetworkTransmitStationType(Short isSdnNetworkTransmitStationType) {
        this.isSdnNetworkTransmitStationType = isSdnNetworkTransmitStationType;
    }


    @Column(name = "network_room_owner_unit_name", nullable = true)
    public String getNetworkRoomOwnerUnitName() {
        return this.networkRoomOwnerUnitName;
    }

    public void setNetworkRoomOwnerUnitName(String networkRoomOwnerUnitName) {
        this.networkRoomOwnerUnitName = networkRoomOwnerUnitName;
    }

    @Column(name = "edge_node_team", nullable = true)
    public String getEdgeNodeTeam() {
        return this.edgeNodeTeam;
    }

    public void setEdgeNodeTeam(String edgeNodeTeam) {
        this.edgeNodeTeam = edgeNodeTeam;
    }

    @Column(name = "main_trunk_node_num", nullable = true)
    public String getMainTrunkNodeNum() {
        return this.mainTrunkNodeNum;
    }

    public void setMainTrunkNodeNum(String mainTrunkNodeNum) {
        this.mainTrunkNodeNum = mainTrunkNodeNum;
    }

    @Column(name = "main_trunk_node_site_name", nullable = true)
    public String getMainTrunkNodeSiteName() {
        return this.mainTrunkNodeSiteName;
    }

    public void setMainTrunkNodeSiteName(String mainTrunkNodeSiteName) {
        this.mainTrunkNodeSiteName = mainTrunkNodeSiteName;
    }
    
    @Column(name = "programmable_main_trunk_node_distance", nullable = true)
    public String getProgrammableMainTrunkNodeDistance() {
		return programmableMainTrunkNodeDistance;
	}

	public void setProgrammableMainTrunkNodeDistance(String programmableMainTrunkNodeDistance) {
		this.programmableMainTrunkNodeDistance = programmableMainTrunkNodeDistance;
	}

	@Column(name = "programmable_main_trunk_node_model", nullable = true)
	public String getProgrammableMainTrunkNodeModel() {
		return programmableMainTrunkNodeModel;
	}

	public void setProgrammableMainTrunkNodeModel(String programmableMainTrunkNodeModel) {
		this.programmableMainTrunkNodeModel = programmableMainTrunkNodeModel;
	}

	@Column(name = "sdn_main_trunk_node_model", nullable = true)
    public String getSdnMainTrunkNodeModel() {
		return sdnMainTrunkNodeModel;
	}

	public void setSdnMainTrunkNodeModel(String sdnMainTrunkNodeModel) {
		this.sdnMainTrunkNodeModel = sdnMainTrunkNodeModel;
	}

	@Column(name = "sdn_main_trunk_node_distance", nullable = true)
    public String getSdnMainTrunkNodeDistance() {
		return sdnMainTrunkNodeDistance;
	}

	public void setSdnMainTrunkNodeDistance(String sdnMainTrunkNodeDistance) {
		this.sdnMainTrunkNodeDistance = sdnMainTrunkNodeDistance;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_in_charge", nullable = true)
    public Person getPersonInCharge() {
        return this.personInCharge;
    }


    public void setPersonInCharge(Person personInCharge) {
        this.personInCharge = personInCharge;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programmable_person_in_charge", nullable = true)
	public Person getProgrammablePersonInCharge() {
		return this.programmablePersonInCharge;
	}

	public void setProgrammablePersonInCharge(Person programmablePersonInCharge) {
		this.programmablePersonInCharge = programmablePersonInCharge;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sdn_person_in_charge", nullable = true)
	public Person getSdnPersonInCharge() {
		return this.sdnPersonInCharge;
	}

	public void setSdnPersonInCharge(Person sdnPersonInCharge) {
		this.sdnPersonInCharge = sdnPersonInCharge;
	}

	@Column(name = "sdn_network_progress", nullable = true)
	public String getSdnNetworkProgress() {
		return sdnNetworkProgress;
	}

	public void setSdnNetworkProgress(String sdnNetworkProgress) {
		this.sdnNetworkProgress = sdnNetworkProgress;
	}

	@Column(name = "programmable_network_progress", nullable = true)
	public String getProgrammableNetworkProgress() {
		return programmableNetworkProgress;
	}

	public void setProgrammableNetworkProgress(String programmableNetworkProgress) {
		this.programmableNetworkProgress = programmableNetworkProgress;
	}

	@Column(name = "basic_network_progress", nullable = true)
	public String getBasicNetworkProgress() {
		return basicNetworkProgress;
	}

	public void setBasicNetworkProgress(String basicNetworkProgress) {
		this.basicNetworkProgress = basicNetworkProgress;
	}

	@Column(name = "sdn_edge_progress", nullable = true)
	public String getSdnEdgeProgress() {
		return sdnEdgeProgress;
	}

	public void setSdnEdgeProgress(String sdnEdgeProgress) {
		this.sdnEdgeProgress = sdnEdgeProgress;
	}

	@Column(name = "programmable_edge_progress", nullable = true)
	public String getProgrammableEdgeProgress() {
		return programmableEdgeProgress;
	}

	public void setProgrammableEdgeProgress(String programmableEdgeProgress) {
		this.programmableEdgeProgress = programmableEdgeProgress;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "site")
	public Set<SiteAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<SiteAttachment> attachments) {
		this.attachments = attachments;
	}

	@Transient
	public List<SiteAttachment> getAttachmentsToDelete() {
		return attachmentsToDelete;
	}

	public void setAttachmentsToDelete(List<SiteAttachment> attachmentsToDelete) {
		this.attachmentsToDelete = attachmentsToDelete;
	}
	
}
