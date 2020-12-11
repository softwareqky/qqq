package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Locale;

import garage.origin.common.constant.OnOffEnum;
import project.edge.common.constant.FacilityConstructionStatusEnum;
import project.edge.common.constant.InvolvedProjectGroupEnum;
import project.edge.common.constant.LinkStatusEnum;
import project.edge.common.constant.LinkTypeEnum;
import project.edge.common.constant.SegmentTypeEnum;
import project.edge.common.constant.SiteTypeEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.MapIssueWarningListBean;
import project.edge.domain.view.MapLayerSiteListBean;
import project.edge.domain.view.MapLinkListBean;
import project.edge.domain.view.MapSegmentListBean;

/**
 * @author angel_000
 *
 */
public class MapLayerListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MapLayerSiteListBean fromEntity(String stationType, String id, String server, Site s,
            MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapLayerSiteListBean bean = new MapLayerSiteListBean();

        bean.setId(id);
        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }
        bean.setInitNum(s.getInitNum());
        bean.setSiteNum(s.getSiteNum());
        
        bean.setSdnNetworkProgress(s.getSdnNetworkProgress());
        bean.setBasicNetworkProgress(s.getBasicNetworkProgress());
        bean.setProgrammableNetworkProgress(s.getProgrammableNetworkProgress());
        bean.setNetworkRoomAddress(s.getNetworkRoomAddress());

        bean.setStationName(s.getStationName());
        if (!StringUtils.isEmpty(stationType) && stationType.equals("1")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType() != null
                        && !StringUtils.isEmpty(s.getBasicNetworkTransmitStationType())) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                }
            }

        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("2")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType().equals("1")) {

                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("3")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType().equals("1")) {

                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }
            }

        }


        bean.setLongitude(s.getLongitude());
        bean.setLatitude(s.getLatitude());

        if (!StringUtils.isEmpty(stationType) && stationType.equals("1")) {
            if (s.getBasicNetworkManualSiteStatus() != null) {
                bean.setSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            }
        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("2")) {
            if (s.getProgrammableNetworkManualSiteStatus() != null) {
                bean.setSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            }
        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("3")) {
            if (s.getSdnNetworkManualSiteStatus() != null) {
                bean.setSiteStatus(s.getSdnNetworkManualSiteStatus());
                bean.setSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            }
        }

        if (s.getEdgeNodeType() != null) {
            if (s.getEdgeNodeManualSiteStatus() != null) {
                bean.setEdgeNodeManualSiteStatus(s.getEdgeNodeManualSiteStatus());
                bean.setEdgeNodeManualSiteStatusText(
                        messageSource.getMessage(FacilityConstructionStatusEnum
                                .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            }
        }

        if (stationType.equals("2") || stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")
                        && s.getEdgeNodeType() != null) {
                    bean.setIsCombineSite((short) 1);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
                } else {
                    bean.setIsCombineSite((short) 0);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
                }
            }

        }

        if (!StringUtils.isEmpty(server) && server.equals("server")) {
            bean.setIsServer((short) 1);
            bean.setIsServerText(
                    messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
        }

        return bean;
    }

    public MapLayerSiteListBean fromEntityPro(String stationType, Integer constrStatus, Site s,
            MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapLayerSiteListBean bean = new MapLayerSiteListBean();

        bean.setId(s.getId());
        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }
        bean.setInitNum(s.getInitNum());
        bean.setSiteNum(s.getSiteNum());
        
        bean.setSdnNetworkProgress(s.getSdnNetworkProgress());
        bean.setBasicNetworkProgress(s.getBasicNetworkProgress());
        bean.setProgrammableNetworkProgress(s.getProgrammableNetworkProgress());
        bean.setNetworkRoomAddress(s.getNetworkRoomAddress());

        bean.setStationName(s.getStationName());
        if (!StringUtils.isEmpty(stationType) && stationType.equals("1")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType() != null
                        && !StringUtils.isEmpty(s.getBasicNetworkTransmitStationType())) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("2")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }

            }

        }

        bean.setLongitude(s.getLongitude());
        bean.setLatitude(s.getLatitude());
        if (constrStatus != null) {
            bean.setSiteStatus(constrStatus);
            bean.setSiteStatusText(messageSource.getMessage(
                    FacilityConstructionStatusEnum.getResouceName(constrStatus), null, locale));
        }

        if (s.getEdgeNodeType() != null) {
            if (constrStatus != null) {
                bean.setEdgeNodeManualSiteStatus(constrStatus);
                bean.setEdgeNodeManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(constrStatus), null, locale));
            }
        }


        if (stationType.equals("2") || stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")
                        && s.getEdgeNodeType() != null) {
                    bean.setIsCombineSite((short) 1);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
                } else {
                    bean.setIsCombineSite((short) 0);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
                }
            }

        }

        return bean;
    }

    public MapLayerSiteListBean fromEntityEdge(String stationType, Integer constrStatus,
            short isWarning, Site s, MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapLayerSiteListBean bean = new MapLayerSiteListBean();

        bean.setId(s.getId());
        bean.setIsWarning(isWarning);
        bean.setIsWarningText(
                isWarning == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                        : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }
        bean.setInitNum(s.getInitNum());
        bean.setSiteNum(s.getSiteNum());

        bean.setStationName(s.getStationName());
        if (!StringUtils.isEmpty(stationType) && stationType.equals("1")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType() != null
                        && !StringUtils.isEmpty(s.getBasicNetworkTransmitStationType())) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("2")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")) {

                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")) {

                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }

            }

        }


        bean.setLongitude(s.getLongitude());
        bean.setLatitude(s.getLatitude());
        if (constrStatus != null) {
            bean.setSiteStatus(constrStatus);
            bean.setSiteStatusText(messageSource.getMessage(
                    FacilityConstructionStatusEnum.getResouceName(constrStatus), null, locale));
        }

        if (s.getEdgeNodeType() != null) {
            if (constrStatus != null) {
                bean.setEdgeNodeManualSiteStatus(constrStatus);
                bean.setEdgeNodeManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(constrStatus), null, locale));
            }
        }


        if (stationType.equals("2") || stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")
                        && s.getEdgeNodeType() != null) {
                    bean.setIsCombineSite((short) 1);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
                } else {
                    bean.setIsCombineSite((short) 0);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
                }
            }

        }

        return bean;
    }

    public MapIssueWarningListBean fromEntityIssue(String stationType, short isWarning, Site s,
            MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapIssueWarningListBean bean = new MapIssueWarningListBean();

        bean.setId(s.getId());

        bean.setIsWarning(isWarning);
        bean.setIsWarningText(
                isWarning == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                        : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));



        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }
        bean.setSiteNum(s.getSiteNum());

        bean.setStationName(s.getStationName());
        if (!StringUtils.isEmpty(stationType) && stationType.equals("1")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType() != null
                        && !StringUtils.isEmpty(s.getBasicNetworkTransmitStationType())) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("2")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getIsProgrammableNetworkTransmitStationType() == 1
                        && s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));


                }
            }


        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getIsSdnNetworkTransmitStationType() == 1
                        && s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));

                }

            }

        }

        bean.setLongitude(s.getLongitude());
        bean.setLatitude(s.getLatitude());

        if (!StringUtils.isEmpty(stationType) && stationType.equals("1")) {
            if (s.getBasicNetworkManualSiteStatus() != null) {
                bean.setSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            }
        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("2")) {
            if (s.getProgrammableNetworkManualSiteStatus() != null) {
                bean.setSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            }
        } else if (!StringUtils.isEmpty(stationType) && stationType.equals("3")) {
            if (s.getSdnNetworkManualSiteStatus() != null) {
                bean.setSiteStatus(s.getSdnNetworkManualSiteStatus());
                bean.setSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            }
        }


        if (s.getEdgeNodeType() != null) {
            if (s.getEdgeNodeManualSiteStatus() != null) {
                bean.setEdgeNodeManualSiteStatus(s.getEdgeNodeManualSiteStatus());
                bean.setEdgeNodeManualSiteStatusText(
                        messageSource.getMessage(FacilityConstructionStatusEnum
                                .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            }
        }

        if (stationType.equals("2") || stationType.equals("3")) {
            if (s.getBasicNetworkTransmitStationType() != null) {
                if (s.getBasicNetworkTransmitStationType().equals("1")
                        && s.getEdgeNodeType() != null) {
                    bean.setIsCombineSite((short) 1);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
                } else {
                    bean.setIsCombineSite((short) 0);
                    bean.setIsCombineSiteText(
                            messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
                }
            }

        }

        bean.setIsMajorCity(s.getIsMajorCity());
        bean.setIsMajorCityText(s.getIsMajorCity() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setIsShow(s.getIsShow());
        bean.setIsShowText(s.getIsShow() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));


        return bean;
    }

    public MapSegmentListBean fromEntitySeg(String layer, Segment s, Integer manualSegmentStatus,
            MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapSegmentListBean bean = new MapSegmentListBean();

        bean.setId(s.getId());

        Site a = s.getEndA();
        if (a != null) {
            bean.setEndA_(a.getId());
            if (a.getCity() != null) {
                bean.setEndAText(a.getCity().getCityName());
            }

            if (a.getLongitude() != null) {
                bean.setEndALng(a.getLongitude());
            }

            if (a.getLatitude() != null) {
                bean.setEndALat(a.getLatitude());
            }
        }

        Site b = s.getEndB();
        if (b != null) {
            bean.setEndB_(b.getId());
            if (b.getCity() != null) {
                bean.setEndBText(b.getCity().getCityName());
            }

            if (b.getLongitude() != null) {
                bean.setEndBLng(b.getLongitude());
            }

            if (b.getLatitude() != null) {
                bean.setEndBLat(b.getLatitude());
            }
        }

        bean.setReuseSegment(s.getReuseSegment());
        
        if (s.getSystemName() != null) {
        	bean.setSystemName(s.getSystemName().getName());
        }

        bean.setSegmentType(s.getSegmentType());
        bean.setSegmentTypeText(messageSource
                .getMessage(SegmentTypeEnum.getResouceName(s.getSegmentType()), null, locale));
        DataOption fibreType = s.getFibreType();
        if (fibreType != null) {
            bean.setFibreType_(fibreType.getId());
            bean.setFibreTypeText(fibreType.getOptionName());
        }

        // bean.setSegmentStatus(s.getSegmentStatus());
        // bean.setSegmentStatusText(messageSource.getMessage(
        // FacilityConstructionStatusEnum.getResouceName(s.getSegmentStatus()), null, locale));
        if (layer.equals("progress") || layer.equals("problem")) {
            if (manualSegmentStatus != null) {
                bean.setSegmentStatus(manualSegmentStatus);
                bean.setSegmentStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(manualSegmentStatus), null,
                        locale));
            } else {
                bean.setSegmentStatus(s.getSegmentStatus());
                bean.setSegmentStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(s.getSegmentStatus()), null,
                        locale));
            }

        } else {
            bean.setSegmentStatus(s.getSegmentStatus());
            bean.setSegmentStatusText(messageSource.getMessage(
                    FacilityConstructionStatusEnum.getResouceName(s.getSegmentStatus()), null,
                    locale));
        }

        if (s.getManualSegmentStatus() != null) {
            bean.setManualSegmentStatus(s.getManualSegmentStatus());
            bean.setManualSegmentStatusText(messageSource.getMessage(
                    FacilityConstructionStatusEnum.getResouceName(s.getManualSegmentStatus()), null,
                    locale));
        }

        return bean;
    }

    public MapLinkListBean fromEntityLink(Link link, MessageSource messageSource, Locale locale) {


        if (link == null) {
            return null;
        }

        MapLinkListBean bean = new MapLinkListBean();
        bean.setId(link.getId());
        bean.setLinkName(link.getLinkName());

        Site a = link.getEndA();
        if (a != null) {
            bean.setEndA(a.getStationName());
            if (a.getLongitude() != null) {
                bean.setEndALng(a.getLongitude());
            }

            if (a.getLatitude() != null) {
                bean.setEndALat(a.getLatitude());
            }
        }

        Site b = link.getEndB();
        if (b != null) {
            bean.setEndB(b.getStationName());

            if (b.getLongitude() != null) {
                bean.setEndBLng(b.getLongitude());
            }

            if (b.getLatitude() != null) {
                bean.setEndBLat(b.getLatitude());
            }
        }

        bean.setLinkType(link.getLinkType());


        if (link.getDataLinkStatus() != null) {
            bean.setDataLinkStatus(link.getDataLinkStatus());
            bean.setDataLinkStatusText(messageSource.getMessage(
                    LinkStatusEnum.getResouceName(link.getDataLinkStatus()), null, locale));
        }

        bean.setDistance(link.getDistance());
        bean.setTransmissionLayerBandwidth(link.getTransmissionLayerBandwidth());
        bean.setDataLayerBandwidth(link.getDataLayerBandwidth());
        bean.setTransmitLinkStatus(link.getTransmitLinkStatus());

        if (link.getInvolvedProjectGroup() != null) {
            bean.setInvolvedProjectGroup(link.getInvolvedProjectGroup());
            bean.setInvolvedProjectGroupText(messageSource.getMessage(
                    InvolvedProjectGroupEnum.getResouceName(link.getInvolvedProjectGroup()), null,
                    locale));
        }
        return bean;
    }
}
