/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.BasicNetworkTransmitStationTypeEnum;
import project.edge.common.constant.DataNetworkNodeTypeEnum;
import project.edge.common.constant.FacilityConstructionStatusEnum;
import project.edge.common.constant.FacilityStatusEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.City;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Province;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.SiteAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.SiteBean;


/**
 * @author angel_000
 *         转换站点信息对应的view和entity的转换器。
 */
public class SiteBeanConverter implements ViewConverter<Site, SiteBean> {

    @Override
    public SiteBean fromEntity(Site entity, MessageSource messageSource, Locale locale) {

        SiteBean bean = new SiteBean();
        bean.setId(entity.getId());

        bean.setSiteNum(entity.getSiteNum());

        Province province = entity.getProvince();
        if (province != null) {
            bean.setProvince_(province.getId());
            bean.setProvinceText(province.getProvinceName());
        }

        City city = entity.getCity();
        if (city != null) {
            bean.setCity_(city.getId());
            bean.setCityText(city.getCityName());
        }

        bean.setInitNum(entity.getInitNum());
        bean.setInitDesignFileStationName(entity.getInitDesignFileStationName());
        bean.setConstructionDrawingDesignNum(entity.getConstructionDrawingDesignNum());
        bean.setStationName(entity.getStationName());
        // bean.setSiteType(entity.getSiteType());
        // bean.setSiteTypeText(messageSource
        // .getMessage(SiteTypeEnum.getResouceName(entity.getSiteType()), null, locale));

        DataOption networkRoomOwnerUnitType = entity.getNetworkRoomOwnerUnitType();

        if (networkRoomOwnerUnitType != null) {
            bean.setNetworkRoomOwnerUnitType_(networkRoomOwnerUnitType.getId());
            bean.setNetworkRoomOwnerUnitTypeText(networkRoomOwnerUnitType.getOptionName());
        }

        bean.setNetworkRoomAddress(entity.getNetworkRoomAddress());

        if (!StringUtils.isEmpty(entity.getBasicNetworkTransmitStationType())) {
            bean.setBasicNetworkTransmitStationType(entity.getBasicNetworkTransmitStationType());
            bean.setBasicNetworkTransmitStationTypeText(messageSource.getMessage(
                    BasicNetworkTransmitStationTypeEnum.getResouceName(
                            Integer.parseInt(entity.getBasicNetworkTransmitStationType())),
                    null, locale));
        } else {
            bean.setBasicNetworkTransmitStationType(
                    BasicNetworkTransmitStationTypeEnum.EDGE_NODE.value().toString());
            bean.setBasicNetworkTransmitStationTypeText(
                    messageSource.getMessage(
                            BasicNetworkTransmitStationTypeEnum.getResouceName(
                                    BasicNetworkTransmitStationTypeEnum.EDGE_NODE.value()),
                            null, locale));
        }
        
        if (!StringUtils.isEmpty(entity.getDataNetworkNodeType())) {
            bean.setDataNetworkNodeType(entity.getDataNetworkNodeType());
            bean.setDataNetworkNodeTypeText(messageSource.getMessage(
            		DataNetworkNodeTypeEnum.getResouceName(
                            Integer.parseInt(entity.getDataNetworkNodeType())),
                    null, locale));
        }


        if (!StringUtils.isEmpty(entity.getProgrammableNetworkTransmitStationType())) {
            bean.setProgrammableNetworkTransmitStationType(
                    entity.getProgrammableNetworkTransmitStationType());
//            bean.setProgrammableNetworkTransmitStationTypeText(messageSource.getMessage(
//                    ProgrammableNetworkTransmitStationTypeEnum.getResouceName(
//                            Integer.parseInt(entity.getProgrammableNetworkTransmitStationType())),
//                    null, locale));
        }

        if (!StringUtils.isEmpty(entity.getSdnNetworkTransmitStationType())) {
            bean.setSdnNetworkTransmitStationType(entity.getSdnNetworkTransmitStationType());
//            bean.setSdnNetworkTransmitStationTypeText(messageSource.getMessage(
//                    SdnNetworkTransmitStationTypeEnum.getResouceName(
//                            Integer.parseInt(entity.getSdnNetworkTransmitStationType())),
//                    null, locale));
        }

        if (entity.getIsBasicNetworkTransmitStationType() != null) {
            bean.setIsBasicNetworkTransmitStationType(
                    entity.getIsBasicNetworkTransmitStationType());
            bean.setIsBasicNetworkTransmitStationTypeText(
                    entity.getIsBasicNetworkTransmitStationType() == 1
                            ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                            : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        if (entity.getIsProgrammableNetworkTransmitStationType() != null) {
            bean.setIsProgrammableNetworkTransmitStationType(
                    entity.getIsProgrammableNetworkTransmitStationType());
            bean.setIsProgrammableNetworkTransmitStationTypeText(
                    entity.getIsProgrammableNetworkTransmitStationType() == 1
                            ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                            : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        if (entity.getIsSdnNetworkTransmitStationType() != null) {
            bean.setIsSdnNetworkTransmitStationType(entity.getIsSdnNetworkTransmitStationType());
            bean.setIsSdnNetworkTransmitStationTypeText(
                    entity.getIsSdnNetworkTransmitStationType() == 1
                            ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                            : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        }

        bean.setOpticalLayerRack(entity.getOpticalLayerRack());
        bean.setM24Rack(entity.getM24Rack());
        bean.setU64Rack(entity.getU64Rack());
        bean.setU32Rack(entity.getU32Rack());
        bean.setTransmitDeviceTotalPower(entity.getTransmitDeviceTotalPower());
        bean.setTransmitDevice63aReqAmount(entity.getTransmitDevice63aReqAmount());
        bean.setProgrammableRack(entity.getProgrammableRack());
        bean.setProgrammableRackPower(entity.getProgrammableRackPower());
        bean.setSdnRack(entity.getSdnRack());
        bean.setSdnRackPower(entity.getSdnRackPower());

        DataOption edgeNodeType = entity.getEdgeNodeType();
        if (edgeNodeType != null) {
            bean.setEdgeNodeType_(edgeNodeType.getId());
            bean.setEdgeNodeTypeText(edgeNodeType.getOptionName());
        }

        bean.setEdgeNodeRack(entity.getEdgeNodeRack());
        bean.setProgrammableEdgeNodeRackPower(entity.getProgrammableEdgeNodeRackPower());
        bean.setDirectConnNodeTransmitRack(entity.getDirectConnNodeTransmitRack());
        bean.setDirectConnNodeTransmitDevicePower(entity.getDirectConnNodeTransmitDevicePower());
        bean.setDirectConnNode63aReqAmount(entity.getDirectConnNode63aReqAmount());
        bean.setDirectConnNodeRouterRack(entity.getDirectConnNodeRouterRack());
        bean.setProgrammableDirectConnNodeDataDevicePower(entity.getProgrammableDirectConnNodeDataDevicePower());
        bean.setCaictRack(entity.getCaictRack());
        bean.setCaictDeviceTotalPower(entity.getCaictDeviceTotalPower());
        bean.setCaictDevice32aAmount(entity.getCaictDevice32aAmount());
        bean.setCompRack(entity.getCompRack());
        bean.setCompRackTotalPower(entity.getCompRackTotalPower());
        bean.setCompRack32aAmount(entity.getCompRack32aAmount());
        bean.setOtherDevice(entity.getOtherDevice());
        bean.setDcDeviceRackAmount(entity.getDcDeviceRackAmount());
        bean.setAcDeviceRackAmount(entity.getAcDeviceRackAmount());
        bean.setSingleSiteSlotReqAmount(entity.getSingleSiteSlotReqAmount());
        bean.setDcFuse63aAmount(entity.getDcFuse63aAmount());
        bean.setDcTotalPower(entity.getDcTotalPower());
        bean.setAcFuse32aAmount(entity.getAcFuse32aAmount());
        bean.setAcTotalPower(entity.getAcTotalPower());
        bean.setSingleSiteTotalPower(entity.getSingleSiteSlotReqAmount());
        bean.setSpareRack(entity.getSpareRack());

        bean.setNetworkRoomContact(entity.getNetworkRoomContact());

        bean.setNetworkRoomContactTitle(entity.getNetworkRoomContactTitle());
        bean.setNetworkRoomContactMobile(entity.getNetworkRoomContactMobile());

        if (entity.getIsEiaRegister() != null) {
            bean.setIsEiaRegister(entity.getIsEiaRegister());
            bean.setIsEiaRegisterText(entity.getIsEiaRegister() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        bean.setEiaRegister(entity.getEiaRegister());
        if (entity.getIsBusinessInvitationReply() != null) {
            bean.setIsBusinessInvitationReply(entity.getIsBusinessInvitationReply());
            bean.setIsBusinessInvitationReplyText(entity.getIsBusinessInvitationReply() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        Person personInCharge = entity.getPersonInCharge();
        if (personInCharge != null) {
            bean.setPersonInCharge_(personInCharge.getId());
            bean.setPersonInChargeText(personInCharge.getPersonName());
        }
        
        Person programmablePersonInCharge = entity.getProgrammablePersonInCharge();
        if (programmablePersonInCharge != null) {
            bean.setProgrammablePersonInCharge_(programmablePersonInCharge.getId());
            bean.setProgrammablePersonInChargeText(programmablePersonInCharge.getPersonName());
        }

        Person sdnPersonInCharge = entity.getSdnPersonInCharge();
        if (sdnPersonInCharge != null) {
            bean.setSdnPersonInCharge_(sdnPersonInCharge.getId());
            bean.setSdnPersonInChargeText(sdnPersonInCharge.getPersonName());
        }


        bean.setBusinessInvitationReply(entity.getBusinessInvitationReply());
        bean.setFrameworkAgreementRemark(entity.getFrameworkAgreementRemark());

        // bean.setIsFrameworkAgreement(entity.getIsFrameworkAgreement());
        // bean.setIsFrameworkAgreementText(entity.getIsFrameworkAgreement() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        //
        // bean.setFrameworkAgreement(entity.getFrameworkAgreement());
        // bean.setIsImplementAgreement(entity.getIsImplementAgreement());
        // bean.setIsImplementAgreementText(entity.getIsImplementAgreement() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setImplementAgreement(entity.getImplementAgreement());
        // bean.setIsNetworkRoomEnvironmentImprove(entity.getIsNetworkRoomEnvironmentImprove());
        // bean.setIsNetworkRoomEnvironmentImproveText(entity.getIsNetworkRoomEnvironmentImprove()
        // == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        //
        // bean.setNetworkRoomEnvironmentImprove(entity.getNetworkRoomEnvironmentImprove());
        // bean.setIsNetworkPlan(entity.getIsNetworkPlan());
        // bean.setIsNetworkPlanText(entity.getIsNetworkPlan() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        //
        // bean.setNetworkPlan(entity.getNetworkPlan());
        // bean.setIsSystemDesign(entity.getIsSystemDesign());
        // bean.setIsSystemDesignText(entity.getIsSystemDesign() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setSystemDesign(entity.getSystemDesign());
        // bean.setIsSiteTargetConfigDesign(entity.getIsSiteTargetConfigDesign());
        // bean.setIsSiteTargetConfigDesignText(entity.getIsSiteTargetConfigDesign() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        //
        // bean.setSiteTargetConfigDesign(entity.getSiteTargetConfigDesign());
        // bean.setIsOrderConfigList(entity.getIsOrderConfigList());
        // bean.setIsOrderConfigListText(entity.getIsOrderConfigList() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        //
        // bean.setOrderConfigList(entity.getOrderConfigList());
        // bean.setIsOrderDistribute(entity.getIsOrderDistribute());
        // bean.setIsOrderDistributeText(entity.getIsOrderDistribute() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setOrderDistribute(entity.getOrderDistribute());
        // bean.setDeliveryCommissionConfigList(entity.getDeliveryCommissionConfigList());
        // bean.setPurchaseOrderNum(entity.getPurchaseOrderNum());
        // bean.setIsMakeToStock(entity.getIsMakeToStock());
        // bean.setIsMakeToStockText(entity.getIsMakeToStock() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setMakeToStock(entity.getMakeToStock());
        // bean.setIsNetworkRoomImprove(entity.getIsNetworkRoomImprove());
        // bean.setIsNetworkRoomImproveText(entity.getIsNetworkRoomImprove() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setNetworkRoomImprove(entity.getNetworkRoomImprove());
        // bean.setIsDeviceEnter(entity.getIsDeviceEnter());
        // bean.setIsDeviceEnterText(entity.getIsDeviceEnter() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setDeviceEnter(entity.getDeviceEnter());
        // bean.setIsDeviceReceipt(entity.getIsDeviceReceipt());
        // bean.setIsDeviceReceiptText(entity.getIsDeviceReceipt() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setDeviceReceipt(entity.getDeviceReceipt());
        // bean.setIsHardwareInstall(entity.getIsHardwareInstall());
        // bean.setIsHardwareInstallText(entity.getIsHardwareInstall() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setHardwareInstall(entity.getHardwareInstall());
        // bean.setIsDevicePowerOn(entity.getIsDevicePowerOn());
        // bean.setIsDeviceEnterText(entity.getIsDevicePowerOn() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setDevicePowerOn(entity.getDevicePowerOn());
        // bean.setIsInitMaterialArchieve(entity.getIsInitMaterialArchieve());
        // bean.setIsInitMaterialArchieveText(entity.getIsInitMaterialArchieve() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setInitMaterialArchieve(entity.getInitMaterialArchieve());
        // bean.setIsMaintainance(entity.getIsMaintainance());
        // bean.setIsMaintainanceText(entity.getIsMaintainance() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setMaintainance(entity.getMaintainance());
        // bean.setIsPilot(entity.getIsPilot());
        // bean.setIsPilotText(entity.getIsPilot() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPilot(entity.getPilot());
        // bean.setIsFinalAcceptance(entity.getIsFinalAcceptance());
        // bean.setIsFinalAcceptanceText(entity.getIsFinalAcceptance() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setFinalAcceptance(entity.getFinalAcceptance());
        // bean.setIsFixedAsset(entity.getIsFixedAsset());
        // bean.setIsFixedAssetText(entity.getIsFixedAsset() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setFixedAsset(entity.getFixedAsset());
        // bean.setIsNationalAcceptance(entity.getIsNationalAcceptance());
        // bean.setIsNationalAcceptanceText(entity.getIsNationalAcceptance() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setNationalAcceptance(entity.getNationalAcceptance());
        // Person basicNetworkGrantor = entity.getBasicNetworkGrantor();
        //
        // if (basicNetworkGrantor != null) {
        // bean.setBasicNetworkGrantor_(basicNetworkGrantor.getId());
        // bean.setBasicNetworkGrantorText(basicNetworkGrantor.getPersonName());
        // }
        //
        // Person basicNetworkGrantee = entity.getBasicNetworkGrantee();
        // if (basicNetworkGrantee != null) {
        // bean.setBasicNetworkGrantee_(basicNetworkGrantee.getId());
        // bean.setBasicNetworkGranteeText(basicNetworkGrantee.getPersonName());
        // }
        //
        // bean.setAuthority(entity.getAuthority());
        // bean.setIsPgBidding(entity.getIsPgBidding());
        // bean.setIsPgBiddingText(entity.getIsPgBidding() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgBidding(entity.getPgBidding());
        // bean.setIsPgDeviceReceive(entity.getIsPgDeviceReceive());
        // bean.setIsPgDeviceReceiveText(entity.getIsPgDeviceReceive() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgDeviceReceive(entity.getPgDeviceReceive());
        // bean.setIsPgDeviceInstall(entity.getIsPgDeviceInstall());
        // bean.setIsPgDeviceInstallText(entity.getIsPgDeviceInstall() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgDeviceInstall(entity.getPgDeviceInstall());
        // bean.setIsPgDeviceTest(entity.getIsPgDeviceTest());
        // bean.setIsPgDeviceTestText(entity.getIsPgDeviceTest() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgDeviceTest(entity.getPgDeviceTest());
        // bean.setIsPgFibreTest(entity.getIsPgFibreTest());
        // bean.setIsPgFibreTestText(entity.getIsPgFibreTest() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgFibreTest(entity.getPgFibreTest());
        // bean.setIsPgDeviceNetworkAccess(entity.getIsPgDeviceNetworkAccess());
        // bean.setIsPgDeviceNetworkAccessText(entity.getIsPgDeviceNetworkAccess() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgDeviceNetworkAccess(entity.getPgDeviceNetworkAccess());
        // bean.setIsPgPilot(entity.getIsPgPilot());
        // bean.setIsPgPilotText(entity.getIsPgPilot() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgPilot(entity.getPgPilot());
        // bean.setIsPgDataBusinessOn(entity.getIsPgDataBusinessOn());
        // bean.setIsPgDataBusinessOnText(entity.getIsPgDataBusinessOn() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setPgDataBusinessOn(entity.getPgDataBusinessOn());
        // bean.setIsSdnBidding(entity.getIsSdnBidding());
        // bean.setIsSdnBiddingText(entity.getIsSdnBidding() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setSdnBidding(entity.getSdnBidding());
        // bean.setIsSdnDeviceReceive(entity.getIsSdnDeviceReceive());
        // bean.setIsSdnDeviceReceiveText(entity.getIsSdnDeviceReceive() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setSdnDeviceReceive(entity.getSdnDeviceReceive());
        // bean.setIsSdnDeviceInstall(entity.getIsSdnDeviceInstall());
        // bean.setIsSdnDeviceInstallText(entity.getIsSdnDeviceInstall() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setSdnDeviceInstall(entity.getSdnDeviceInstall());
        // bean.setIsSdnDeviceTest(entity.getIsSdnDeviceTest());
        // bean.setIsSdnDeviceTestText(entity.getIsSdnDeviceTest() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setSdnDeviceTest(entity.getSdnDeviceTest());
        // bean.setIsSdnDeviceNetworkAccess(entity.getIsSdnDeviceNetworkAccess());
        // bean.setIsSdnDeviceNetworkAccessText(entity.getIsSdnDeviceNetworkAccess() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // bean.setSdnDeviceNetworkAccess(entity.getSdnDeviceNetworkAccess());
        
        bean.setIsMajorCity(entity.getIsMajorCity());
        bean.setIsMajorCityText(entity.getIsMajorCity() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setIsShow(entity.getIsShow());
        bean.setIsShowText(entity.getIsShow() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setLongitude(entity.getLongitude());
        bean.setLatitude(entity.getLatitude());
        bean.setIsCoordinate(entity.getIsCoordinate());
        bean.setIsCoordinateText(entity.getIsCoordinate() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        bean.setSiteStatus(entity.getSiteStatus());
        bean.setSiteStatusText(messageSource.getMessage(
                FacilityConstructionStatusEnum.getResouceName(entity.getSiteStatus()), null,
                locale));
        if (entity.getBasicNetworkManualSiteStatus() != null) {
            bean.setBasicNetworkManualSiteStatus(entity.getBasicNetworkManualSiteStatus());
            bean.setBasicNetworkManualSiteStatusText(messageSource.getMessage(
                    FacilityStatusEnum.getResouceName(entity.getBasicNetworkManualSiteStatus()),
                    null, null, locale));
        }

        bean.setIsCombineSite(entity.getIsCombineSite());

        if (entity.getIsCombineSite() != null) {
            bean.setIsCombineSiteText(entity.getIsCombineSite() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }


        if (entity.getProgrammableNetworkManualSiteStatus() != null) {
            bean.setProgrammableNetworkManualSiteStatus(
                    entity.getProgrammableNetworkManualSiteStatus());
            bean.setProgrammableNetworkManualSiteStatusText(
                    messageSource.getMessage(
                            FacilityStatusEnum.getResouceName(
                                    entity.getProgrammableNetworkManualSiteStatus()),
                            null, null, locale));
        }

        if (entity.getSdnNetworkManualSiteStatus() != null) {
            bean.setSdnNetworkManualSiteStatus(entity.getSdnNetworkManualSiteStatus());
            bean.setSdnNetworkManualSiteStatusText(messageSource.getMessage(
                    FacilityStatusEnum.getResouceName(entity.getSdnNetworkManualSiteStatus()), null,
                    null, locale));
        }

        if (entity.getEdgeNodeManualSiteStatus() != null) {
            bean.setEdgeNodeManualSiteStatus(entity.getEdgeNodeManualSiteStatus());
            bean.setEdgeNodeManualSiteStatusText(messageSource.getMessage(
                    FacilityStatusEnum.getResouceName(entity.getEdgeNodeManualSiteStatus()), null,
                    null, locale));
        }

        if (entity.getInitMaterialArchieveDate() != null) {
            bean.setInitMaterialArchieveDate(
                    DateUtils.date2String(entity.getInitMaterialArchieveDate()));
        }
        
        if (entity.getSdnInitMaterialArchieveDate() != null) {
        	bean.setSdnInitMaterialArchieveDate(DateUtils.date2String(entity.getSdnInitMaterialArchieveDate()));
        }
        
        if (entity.getProgrammableInitMaterialArchieveDate() != null) {
        	bean.setProgrammableInitMaterialArchieveDate(DateUtils.date2String(entity.getProgrammableInitMaterialArchieveDate()));
        }
        
        if (entity.getSdnEdgeNodeInitMaterialArchieveDate() != null) {
        	bean.setSdnEdgeNodeInitMaterialArchieveDate(DateUtils.date2String(entity.getSdnEdgeNodeInitMaterialArchieveDate()));
        }
        
        if (entity.getProgrammableEdgeNodeInitMaterialArchieveDate() != null) {
        	bean.setProgrammableEdgeNodeInitMaterialArchieveDate(DateUtils.date2String(entity.getProgrammableEdgeNodeInitMaterialArchieveDate()));
        }

        bean.setRemark(entity.getRemark());
        bean.setSdnRemark(entity.getSdnRemark());
        bean.setProgrammableRemark(entity.getProgrammableRemark());
        
        bean.setNetworkRoomOwnerUnitName(entity.getNetworkRoomOwnerUnitName());
        bean.setEdgeNodeTeam(entity.getEdgeNodeTeam());
        
        bean.setMainTrunkNodeNum(entity.getMainTrunkNodeNum());
        bean.setMainTrunkNodeSiteName(entity.getMainTrunkNodeSiteName());
        bean.setSdnMainTrunkNodeDistance(entity.getSdnMainTrunkNodeDistance());
        bean.setSdnMainTrunkNodeModel(entity.getSdnMainTrunkNodeModel());
        bean.setProgrammableMainTrunkNodeDistance(entity.getProgrammableMainTrunkNodeDistance());
        bean.setProgrammableMainTrunkNodeModel(entity.getProgrammableMainTrunkNodeModel());
        
        bean.setBasicNetworkProgress(entity.getBasicNetworkProgress());
        bean.setSdnNetworkProgress(entity.getSdnNetworkProgress());
        bean.setProgrammableNetworkProgress(entity.getProgrammableNetworkProgress());
        bean.setSdnEdgeProgress(entity.getSdnEdgeProgress());
        bean.setProgrammableEdgeProgress(entity.getProgrammableEdgeProgress());
        bean.setTotalProgress(entity.getTotalProgress());
        
        bean.setBasicRackLocation(entity.getBasicRackLocation());
        bean.setSdnRackLocation(entity.getSdnRackLocation());
        bean.setProgrammableRackLocation(entity.getProgrammableRackLocation());
        
        if (entity.getmDatetime() != null) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	bean.setmDatetime(sdf.format(entity.getmDatetime()));
        }
        
        if (entity.getFlowStartDate() != null) {
			bean.setFlowStartDate(DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getFlowEndDate() != null) {
			bean.setFlowEndDate(DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		}
		bean.setFlowStatus(entity.getFlowStatus());
		bean.setFlowStatusText(
				messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
		
		// 裂站相关
		Person siteDividePersonInCharge = entity.getSiteDividePersonInCharge();
        if (siteDividePersonInCharge != null) {
            bean.setSiteDividePersonInCharge_(siteDividePersonInCharge.getId());
            bean.setSiteDividePersonInChargeText(siteDividePersonInCharge.getPersonName());
        }
        
        bean.setIsDivideSite(entity.getIsDivideSite());
        bean.setIsDivideSiteText(entity.getIsDivideSite()!= null && entity.getIsDivideSite() == 1
        		? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        
        bean.setSiteDivideLocation(entity.getSiteDivideLocation());
        bean.setSiteDivideDate(DateUtils.date2String(entity.getSiteDivideDate()));
        
        // 附件处理，对应基础网络、SDN网络和可编程网络
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        Set<SiteAttachment> attachments = entity.getAttachments();
        for (SiteAttachment a : attachments) {
        	
        	ArchiveBean archiveBean = abConverter.fromEntity(a.getArchive(), messageSource, locale);
        	// 基础网络
        	if (a.getType() == 0) {
        		bean.getBasicArchivesList().add(archiveBean);
        	} 
        	// SDN网络
        	else if (a.getType() == 1) {
        		bean.getSdnArchivesList().add(archiveBean);
        	} 
        	// 可编程网络
        	else {
        		bean.getProgrammableArchivesList().add(archiveBean);
        	}
        }
        
        return bean;
    }

    @Override
    public Site toEntity(SiteBean bean, Site oldEntity, AbstractSessionUserBean user, Date now) {

        Site entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new Site();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setSiteNum(bean.getSiteNum());

        if (!StringUtils.isEmpty(bean.getProvince_())) {
            Province province = new Province();
            province.setId(bean.getProvince_());
            entity.setProvince(province);
        }

        if (!StringUtils.isEmpty(bean.getCity_())) {
            City city = new City();
            city.setId(bean.getCity_());
            entity.setCity(city);
        }

        entity.setInitNum(bean.getInitNum());
        entity.setInitDesignFileStationName(bean.getInitDesignFileStationName());
        entity.setConstructionDrawingDesignNum(bean.getConstructionDrawingDesignNum());
        entity.setStationName(bean.getStationName());

        // entity.setSiteType(bean.getSiteType());

        if (!StringUtils.isEmpty(bean.getNetworkRoomOwnerUnitType_())) {
            DataOption networkRoomOwnerUnitType = new DataOption();
            networkRoomOwnerUnitType.setId(bean.getNetworkRoomOwnerUnitType_());
            entity.setNetworkRoomOwnerUnitType(networkRoomOwnerUnitType);
        }

        entity.setNetworkRoomAddress(bean.getNetworkRoomAddress());
        if (bean.getBasicNetworkTransmitStationType() != null) {
        	 entity.setBasicNetworkTransmitStationType(bean.getBasicNetworkTransmitStationType());
        }
       
        entity.setProgrammableNetworkTransmitStationType(
                bean.getProgrammableNetworkTransmitStationType());
        entity.setSdnNetworkTransmitStationType(bean.getSdnNetworkTransmitStationType());
        entity.setDataNetworkNodeType(bean.getDataNetworkNodeType());

        entity.setIsBasicNetworkTransmitStationType(bean.getIsBasicNetworkTransmitStationType());
        entity.setIsProgrammableNetworkTransmitStationType(
                bean.getIsProgrammableNetworkTransmitStationType());
        entity.setIsSdnNetworkTransmitStationType(bean.getIsSdnNetworkTransmitStationType());

        entity.setOpticalLayerRack(bean.getOpticalLayerRack());
        entity.setM24Rack(bean.getM24Rack());
        entity.setU64Rack(bean.getU64Rack());
        entity.setU32Rack(bean.getU32Rack());
        entity.setTransmitDeviceTotalPower(bean.getTransmitDeviceTotalPower());
        entity.setTransmitDevice63aReqAmount(bean.getTransmitDevice63aReqAmount());
        entity.setProgrammableRack(bean.getProgrammableRack());
        entity.setProgrammableRackPower(bean.getProgrammableRackPower());
        entity.setSdnRack(bean.getSdnRack());
        entity.setSdnRackPower(bean.getSdnRackPower());

        if (!StringUtils.isEmpty(bean.getEdgeNodeType_())) {
            DataOption edgeNodeType = new DataOption();
            edgeNodeType.setId(bean.getEdgeNodeType_());
            entity.setEdgeNodeType(edgeNodeType);
        }
        entity.setEdgeNodeRack(bean.getEdgeNodeRack());
        entity.setProgrammableEdgeNodeRackPower(bean.getProgrammableEdgeNodeRackPower());
        entity.setDirectConnNodeTransmitRack(bean.getDirectConnNodeTransmitRack());
        entity.setProgrammableDirectConnNodeDataDevicePower(bean.getProgrammableDirectConnNodeDataDevicePower());
        entity.setDirectConnNode63aReqAmount(bean.getDirectConnNode63aReqAmount());
        entity.setDirectConnNodeRouterRack(bean.getDirectConnNodeRouterRack());
        entity.setProgrammableDirectConnNodeDataDevicePower(bean.getProgrammableDirectConnNodeDataDevicePower());
        entity.setCaictRack(bean.getCaictRack());
        entity.setCaictDeviceTotalPower(bean.getCaictDeviceTotalPower());
        entity.setCaictDevice32aAmount(bean.getCaictDevice32aAmount());
        entity.setCompRack(bean.getCompRack());
        entity.setCompRackTotalPower(bean.getCompRackTotalPower());
        entity.setCompRack32aAmount(bean.getCompRack32aAmount());
        entity.setOtherDevice(bean.getOtherDevice());
        entity.setDcDeviceRackAmount(bean.getDcDeviceRackAmount());
        entity.setAcDeviceRackAmount(bean.getAcDeviceRackAmount());
        entity.setSingleSiteSlotReqAmount(bean.getSingleSiteSlotReqAmount());
        entity.setDcFuse63aAmount(bean.getDcFuse63aAmount());
        entity.setDcTotalPower(bean.getDcTotalPower());
        entity.setAcFuse32aAmount(bean.getAcFuse32aAmount());
        entity.setAcTotalPower(bean.getAcTotalPower());
        entity.setSingleSiteTotalPower(bean.getSingleSiteSlotReqAmount());
        entity.setSpareRack(bean.getSpareRack());

        entity.setNetworkRoomContact(bean.getNetworkRoomContact());

        entity.setNetworkRoomContactTitle(bean.getNetworkRoomContactTitle());
        entity.setNetworkRoomContactMobile(bean.getNetworkRoomContactMobile());
        entity.setIsEiaRegister(bean.getIsEiaRegister());
        entity.setEiaRegister(bean.getEiaRegister());
        entity.setIsBusinessInvitationReply(bean.getIsBusinessInvitationReply());
        entity.setBusinessInvitationReply(bean.getBusinessInvitationReply());
        entity.setFrameworkAgreementRemark(bean.getFrameworkAgreementRemark());
        // entity.setIsFrameworkAgreement(bean.getIsFrameworkAgreement());
        // entity.setFrameworkAgreement(bean.getFrameworkAgreement());
        // entity.setIsImplementAgreement(bean.getIsImplementAgreement());
        // entity.setImplementAgreement(bean.getImplementAgreement());
        // entity.setIsNetworkRoomEnvironmentImprove(bean.getIsNetworkRoomEnvironmentImprove());
        // entity.setNetworkRoomEnvironmentImprove(bean.getNetworkRoomEnvironmentImprove());
        // entity.setIsNetworkPlan(bean.getIsNetworkPlan());
        // entity.setNetworkPlan(bean.getNetworkPlan());
        // entity.setIsSystemDesign(bean.getIsSystemDesign());
        // entity.setSystemDesign(bean.getSystemDesign());
        // entity.setIsSiteTargetConfigDesign(bean.getIsSiteTargetConfigDesign());
        // entity.setSiteTargetConfigDesign(bean.getSiteTargetConfigDesign());
        // entity.setIsOrderConfigList(bean.getIsOrderConfigList());
        // entity.setOrderConfigList(bean.getOrderConfigList());
        // entity.setIsOrderDistribute(bean.getIsOrderDistribute());
        // entity.setOrderDistribute(bean.getOrderDistribute());
        // entity.setDeliveryCommissionConfigList(bean.getDeliveryCommissionConfigList());
        // entity.setPurchaseOrderNum(bean.getPurchaseOrderNum());
        // entity.setIsMakeToStock(bean.getIsMakeToStock());
        // entity.setMakeToStock(bean.getMakeToStock());
        // entity.setIsNetworkRoomImprove(bean.getIsNetworkRoomImprove());
        // entity.setNetworkRoomImprove(bean.getNetworkRoomImprove());
        // entity.setIsDeviceEnter(bean.getIsDeviceEnter());
        // entity.setDeviceEnter(bean.getDeviceEnter());
        // entity.setIsDeviceReceipt(bean.getIsDeviceReceipt());
        // entity.setDeviceReceipt(bean.getDeviceReceipt());
        // entity.setIsHardwareInstall(bean.getIsHardwareInstall());
        // entity.setHardwareInstall(bean.getHardwareInstall());
        // entity.setIsDevicePowerOn(bean.getIsDevicePowerOn());
        // entity.setDevicePowerOn(bean.getDevicePowerOn());
        // entity.setIsInitMaterialArchieve(bean.getIsInitMaterialArchieve());
        // entity.setInitMaterialArchieve(bean.getInitMaterialArchieve());
        // entity.setIsMaintainance(bean.getIsMaintainance());
        // entity.setMaintainance(bean.getMaintainance());
        // entity.setIsPilot(bean.getIsPgPilot());
        // entity.setPilot(bean.getPilot());
        // entity.setIsFinalAcceptance(bean.getIsFinalAcceptance());
        // entity.setFinalAcceptance(bean.getFinalAcceptance());
        // entity.setIsFixedAsset(bean.getIsFixedAsset());
        // entity.setFixedAsset(bean.getFixedAsset());
        // entity.setIsNationalAcceptance(bean.getIsNationalAcceptance());
        // entity.setNationalAcceptance(bean.getNationalAcceptance());
        //
        // if (!StringUtils.isEmpty(bean.getBasicNetworkGrantor_())) {
        // Person basicNetworkGrantor = new Person();
        // basicNetworkGrantor.setId(bean.getBasicNetworkGrantor_());
        // entity.setBasicNetworkGrantor(basicNetworkGrantor);
        // }
        //
        // if (!StringUtils.isEmpty(bean.getBasicNetworkGrantee_())) {
        // Person basicNetworkGrantee = new Person();
        // basicNetworkGrantee.setId(bean.getBasicNetworkGrantee_());
        // entity.setBasicNetworkGrantee(basicNetworkGrantee);
        // }
        //
        // entity.setAuthority(bean.getAuthority());
        // entity.setIsPgBidding(bean.getIsPgBidding());
        // entity.setPgBidding(bean.getPgBidding());
        // entity.setIsPgDeviceReceive(bean.getIsPgDeviceReceive());
        // entity.setPgDeviceReceive(bean.getPgDeviceReceive());
        // entity.setIsPgDeviceInstall(bean.getIsPgDeviceInstall());
        // entity.setPgDeviceInstall(bean.getPgDeviceInstall());
        // entity.setIsPgDeviceTest(bean.getIsPgDeviceTest());
        // entity.setPgDeviceTest(bean.getPgDeviceTest());
        // entity.setIsPgFibreTest(bean.getIsPgFibreTest());
        // entity.setPgFibreTest(bean.getPgFibreTest());
        // entity.setIsPgDeviceNetworkAccess(bean.getIsPgDeviceNetworkAccess());
        // entity.setPgDeviceNetworkAccess(bean.getPgDeviceNetworkAccess());
        // entity.setIsPgPilot(bean.getIsPgPilot());
        // entity.setPgPilot(bean.getPgPilot());
        // entity.setIsPgDataBusinessOn(bean.getIsPgDataBusinessOn());
        // entity.setPgDataBusinessOn(bean.getPgDataBusinessOn());
        // entity.setIsSdnBidding(bean.getIsSdnBidding());
        // entity.setSdnBidding(bean.getSdnBidding());
        // entity.setIsSdnDeviceReceive(bean.getIsSdnDeviceReceive());
        // entity.setSdnDeviceReceive(bean.getSdnDeviceReceive());
        // entity.setIsSdnDeviceInstall(bean.getIsSdnDeviceInstall());
        // entity.setSdnDeviceInstall(bean.getSdnDeviceInstall());
        // entity.setIsSdnDeviceTest(bean.getIsSdnDeviceTest());
        // entity.setSdnDeviceTest(bean.getSdnDeviceTest());
        // entity.setIsSdnDeviceNetworkAccess(bean.getIsSdnDeviceNetworkAccess());
        // entity.setSdnDeviceNetworkAccess(bean.getSdnDeviceNetworkAccess());

        if (!StringUtils.isEmpty(bean.getPersonInCharge_())) {
            Person personInCharge = new Person();
            personInCharge.setId(bean.getPersonInCharge_());
            entity.setPersonInCharge(personInCharge);
        }
        
        if (!StringUtils.isEmpty(bean.getProgrammablePersonInCharge_())) {
            Person programmablePersonInCharge = new Person();
            programmablePersonInCharge.setId(bean.getProgrammablePersonInCharge_());
            entity.setProgrammablePersonInCharge(programmablePersonInCharge);
        }
        
        if (!StringUtils.isEmpty(bean.getSdnPersonInCharge_())) {
            Person sdnPersonInCharge = new Person();
            sdnPersonInCharge.setId(bean.getSdnPersonInCharge_());
            entity.setSdnPersonInCharge(sdnPersonInCharge);
        }
        
        // 裂站相关
        if (!StringUtils.isEmpty(bean.getSiteDividePersonInCharge_())) {
        	Person personInCharge = new Person();
        	personInCharge.setId(bean.getSiteDividePersonInCharge_());
        	entity.setSiteDividePersonInCharge(personInCharge);
        }
        
        entity.setSiteDivideLocation(bean.getSiteDivideLocation());
        entity.setIsDivideSite(bean.getIsDivideSite());
        
        if (!StringUtils.isEmpty(bean.getSiteDivideDate())) {
        	try {
        		entity.setSiteDivideDate(DateUtils.string2Date(bean.getSiteDivideDate(), Constants.DATE_FORMAT));
        	} catch (ParseException e) {
        		
        	}
        }
        
        entity.setLongitude(bean.getLongitude());
        entity.setLatitude(bean.getLatitude());
        entity.setIsCoordinate((short) 0);
        entity.setIsMajorCity(bean.getIsMajorCity());
        entity.setIsShow(bean.getIsShow());
        entity.setSiteStatus(bean.getSiteStatus());
        entity.setBasicNetworkManualSiteStatus(bean.getBasicNetworkManualSiteStatus());
        entity.setProgrammableNetworkManualSiteStatus(
                bean.getProgrammableNetworkManualSiteStatus());
        entity.setSdnNetworkManualSiteStatus(bean.getSdnNetworkManualSiteStatus());
        entity.setEdgeNodeManualSiteStatus(bean.getEdgeNodeManualSiteStatus());
        entity.setRemark(bean.getRemark());
        entity.setSdnRemark(bean.getSdnRemark());
        entity.setProgrammableRemark(bean.getProgrammableRemark());
        
        entity.setNetworkRoomOwnerUnitName(bean.getNetworkRoomOwnerUnitName());
        entity.setEdgeNodeTeam(bean.getEdgeNodeTeam());
        
        entity.setMainTrunkNodeNum(bean.getMainTrunkNodeNum());
        entity.setMainTrunkNodeSiteName(bean.getMainTrunkNodeSiteName());
        entity.setSdnMainTrunkNodeDistance(bean.getSdnMainTrunkNodeDistance());
        entity.setSdnMainTrunkNodeModel(bean.getSdnMainTrunkNodeModel());
        entity.setProgrammableMainTrunkNodeDistance(bean.getSdnMainTrunkNodeDistance());
        entity.setProgrammableMainTrunkNodeModel(bean.getProgrammableMainTrunkNodeModel());

        entity.setIsCombineSite(bean.getIsCombineSite());
        if (!StringUtils.isEmpty(bean.getInitMaterialArchieveDate())) {
            try {
                entity.setInitMaterialArchieveDate(DateUtils
                        .string2Date(bean.getInitMaterialArchieveDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                // do nothing
            }
        }
        
        if (!StringUtils.isEmpty(bean.getSdnInitMaterialArchieveDate())) {
        	try {
        		entity.setSdnInitMaterialArchieveDate(
        				DateUtils.string2Date(bean.getSdnInitMaterialArchieveDate(), Constants.DATE_FORMAT));
        	} catch (ParseException e) {
        		
        	}
        }
        
        if (!StringUtils.isEmpty(bean.getProgrammableInitMaterialArchieveDate())) {
        	try {
        		entity.setProgrammableInitMaterialArchieveDate(
        				DateUtils.string2Date(bean.getProgrammableInitMaterialArchieveDate(), Constants.DATE_FORMAT));
        	} catch (ParseException e) {
        		
        	}
        }
        
        if (!StringUtils.isEmpty(bean.getSdnEdgeNodeInitMaterialArchieveDate())) {
        	try {
        		entity.setSdnEdgeNodeInitMaterialArchieveDate(
        				DateUtils.string2Date(bean.getSdnEdgeNodeInitMaterialArchieveDate(), Constants.DATE_FORMAT));
        	} catch (ParseException e) {
        		
        	}
        }
        
        if (!StringUtils.isEmpty(bean.getProgrammableEdgeNodeInitMaterialArchieveDate())) {
        	try {
        		entity.setProgrammableEdgeNodeInitMaterialArchieveDate(
        				DateUtils.string2Date(bean.getProgrammableEdgeNodeInitMaterialArchieveDate(), Constants.DATE_FORMAT));
        	} catch (ParseException e) {
        		
        	}
        }

        if (!StringUtils.isEmpty(bean.getBasicNetworkProgress())) {
        	entity.setBasicNetworkProgress(bean.getBasicNetworkProgress());
        }
        
        if (!StringUtils.isEmpty(bean.getSdnNetworkProgress())) {
        	entity.setSdnNetworkProgress(bean.getSdnNetworkProgress());
        }
        
        if (!StringUtils.isEmpty(bean.getProgrammableNetworkProgress())) {
        	entity.setProgrammableNetworkProgress(bean.getProgrammableNetworkProgress());
        }
        
        if (!StringUtils.isEmpty(bean.getProgrammableEdgeProgress())) {
        	entity.setProgrammableEdgeProgress(bean.getProgrammableEdgeProgress());
        }
        
        if (!StringUtils.isEmpty(bean.getSdnEdgeProgress())) {
        	entity.setSdnEdgeProgress(bean.getSdnEdgeProgress());
        }
        
        entity.setProgrammableRackLocation(bean.getProgrammableRackLocation());
        entity.setSdnRackLocation(bean.getSdnRackLocation());
        entity.setBasicRackLocation(bean.getBasicRackLocation());
        
        entity.setFlowStatus(bean.getFlowStatus());
        
        // 需要删除的附件
        if (oldEntity != null) {
        	Map<String, String> map = new HashMap<>();
        	for (String aid: bean.getBasicArchivesReservedList()) {
        		map.put(aid, aid);
        	}
        	for (String aid: bean.getSdnArchivesReservedList()) {
        		map.put(aid, aid);
        	}
        	for (String aid: bean.getProgrammableArchivesReservedList()) {
        		map.put(aid, aid);
        	}
        	
        	for (SiteAttachment dbAttachment: oldEntity.getAttachments()) {
        		if (!map.containsKey(dbAttachment.getArchive().getId())) {
        			entity.getAttachmentsToDelete().add(dbAttachment);
        		}
        	}
        }
        
        // 需要添加的附件
        Set<SiteAttachment> attachments = new HashSet<>();
        ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
        
        for (ArchiveBean a : bean.getBasicArchivesList()) {
        	attachments.add(this.toAttachmentEntity(a, abConverter, entity, (short)0, user, now));
        }
        for (ArchiveBean a : bean.getSdnArchivesList()) {
        	attachments.add(this.toAttachmentEntity(a, abConverter, entity, (short)1, user, now));
        }
        for (ArchiveBean a : bean.getProgrammableArchivesList()) {
        	attachments.add(this.toAttachmentEntity(a, abConverter, entity, (short)2, user, now));
        }
        entity.setAttachments(attachments);

        return entity;
    }

    private SiteAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter, Site entity, short type, AbstractSessionUserBean user, Date now) {
    	
    	SiteAttachment attachment = new SiteAttachment();
    	attachment.setSite(entity);
    	attachment.setType(type);
    	
    	ab.setLevel(1);
    	
    	ab.setPid(FolderPath.SITE);
		ab.setPath(Constants.SLASH + "_" + FolderPath.SITE);
		 
		Archive aentity = abConverter.toEntity(ab, null, user, now);
		String relativePath = File.separator + FolderPath.SITE + File.separator + aentity.getId();        
		ab.setRelativePath(relativePath);
		aentity.setRelativePath(relativePath);
		
		attachment.setArchive(aentity);
		return attachment;
    }
}
