package project.edge.domain.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.OnOffEnum;
import project.edge.common.constant.FacilityConstructionStatusEnum;
import project.edge.common.constant.InvolvedProjectGroupEnum;
import project.edge.common.constant.LinkStatusEnum;
import project.edge.common.constant.SegmentTypeEnum;
import project.edge.common.constant.SiteTypeEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.MapLinkListBean;
import project.edge.domain.view.MapSegmentListBean;
import project.edge.domain.view.MapSiteListBean;
import project.edge.domain.view.MapSitePieChartListBean;

/**
 * @author angel_000
 *
 */
public class LeaderHomePlanStieListBeanConvert {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MapSiteListBean fromEntity(String siteType, Site s, MessageSource messageSource,
            Locale locale) {


        if (s == null) {
            return null;
        }

        MapSiteListBean bean = new MapSiteListBean();

        bean.setId(s.getId());
        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }

        bean.setInitNum(s.getInitNum());
        bean.setSiteNum(s.getSiteNum());
        bean.setStationName(s.getStationName());
        bean.setNetworkRoomAddress(s.getNetworkRoomAddress());

        if (!StringUtils.isEmpty(siteType) && siteType.equals("1")) {

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

        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("2")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                }
            }


        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("3")) {

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

//        if (!StringUtils.isEmpty(siteType) && siteType.equals("1")) {
            if (s.getBasicNetworkManualSiteStatus() != null) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
                bean.setBasicProgress(s.getBasicNetworkProgress());
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
//        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("2")) {
            if (s.getProgrammableNetworkManualSiteStatus() != null) {
                bean.setManualSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
                bean.setProgramProgress(s.getProgrammableNetworkProgress());
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
//        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("3")) {
            if (s.getSdnNetworkManualSiteStatus() != null) {
                bean.setManualSiteStatus(s.getSdnNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
                bean.setSdnProgress(s.getSdnNetworkProgress());
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
//        }



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

    public MapSiteListBean fromEntityPro(String siteType, Integer constrStatus, Site s,
            MessageSource messageSource, Locale locale) {
        if (s == null) {
            return null;
        }

        MapSiteListBean bean = new MapSiteListBean();

        bean.setId(s.getId());
        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }

        bean.setInitNum(s.getInitNum());
        bean.setSiteNum(s.getSiteNum());
        bean.setStationName(s.getStationName());
        bean.setNetworkRoomAddress(s.getNetworkRoomAddress());

        if (!StringUtils.isEmpty(siteType) && siteType.equals("1")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType() != null
                        && !StringUtils.isEmpty(s.getBasicNetworkTransmitStationType())) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                    bean.setBasicProgress(s.getBasicNetworkProgress());
                }
            }

        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("2")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                    bean.setProgramProgress(s.getProgrammableNetworkProgress());
                }
            }


        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("3")) {

            if (s.getBasicNetworkTransmitStationType() != null) {

                if (s.getBasicNetworkTransmitStationType().equals("1")) {
                    bean.setSiteType(Integer.parseInt(s.getBasicNetworkTransmitStationType()));
                    bean.setSiteTypeText(messageSource.getMessage(
                            SiteTypeEnum.getResouceName(
                                    Integer.parseInt(s.getBasicNetworkTransmitStationType())),
                            null, locale));
                    bean.setSdnProgress(s.getSdnNetworkProgress());
                }
            }

        }

        bean.setLongitude(s.getLongitude());
        bean.setLatitude(s.getLatitude());

        if (!StringUtils.isEmpty(siteType) && siteType.equals("1")) {
            if (s.getBasicNetworkManualSiteStatus() != null) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                if (constrStatus != null) {
                    bean.setManualSiteStatus(constrStatus);
                    bean.setManualSiteStatusText(messageSource.getMessage(
                            FacilityConstructionStatusEnum.getResouceName(constrStatus), null,
                            locale));
                }

            }
        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("2")) {
            if (s.getProgrammableNetworkManualSiteStatus() != null) {
                bean.setManualSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            } else {
                if (constrStatus != null) {
                    bean.setManualSiteStatus(constrStatus);
                    bean.setManualSiteStatusText(messageSource.getMessage(
                            FacilityConstructionStatusEnum.getResouceName(constrStatus), null,
                            locale));
                }

            }
        } else if (!StringUtils.isEmpty(siteType) && siteType.equals("3")) {
            if (s.getSdnNetworkManualSiteStatus() != null) {
                bean.setManualSiteStatus(s.getSdnNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            } else {
                if (constrStatus != null) {
                    bean.setManualSiteStatus(constrStatus);
                    bean.setManualSiteStatusText(messageSource.getMessage(
                            FacilityConstructionStatusEnum.getResouceName(constrStatus), null,
                            locale));
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

    public MapSiteListBean fromEntityAll(Site s, MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapSiteListBean bean = new MapSiteListBean();

        bean.setId(s.getId());
        if (s.getProvince() != null) {
            bean.setProvince(s.getProvince().getProvinceName());
        }

        if (s.getCity() != null) {
            bean.setCity(s.getCity().getCityName());
        }

        bean.setInitNum(s.getInitNum());
        bean.setSiteNum(s.getSiteNum());
        bean.setStationName(s.getStationName());
        bean.setNetworkRoomAddress(s.getNetworkRoomAddress());
        bean.setSiteType(s.getSiteType());
        bean.setSiteTypeText(messageSource.getMessage(SiteTypeEnum.getResouceName(s.getSiteType()),
                null, locale));

        bean.setLongitude(s.getLongitude());
        bean.setLatitude(s.getLatitude());

        if (s.getBasicNetworkManualSiteStatus() != null) {
            if (s.getBasicNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        } else if (s.getProgrammableNetworkManualSiteStatus() != null) {
            if (s.getProgrammableNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        } else if (s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getSdnNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        } else if (s.getBasicNetworkManualSiteStatus() != null
                && s.getProgrammableNetworkManualSiteStatus() != null) {
            if (s.getBasicNetworkManualSiteStatus() == 2
                    && s.getProgrammableNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        } else if (s.getBasicNetworkManualSiteStatus() != null
                && s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getBasicNetworkManualSiteStatus() == 2
                    && s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        } else if (s.getProgrammableNetworkManualSiteStatus() != null
                && s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getProgrammableNetworkManualSiteStatus() == 2
                    && s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        } else if (s.getBasicNetworkManualSiteStatus() != null
                && s.getProgrammableNetworkManualSiteStatus() != null
                && s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getBasicNetworkManualSiteStatus() == 2
                    && s.getProgrammableNetworkManualSiteStatus() == 2
                    && s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(1);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
        }
        // if (s.getStationName().equals("南京")) {
        // bean.setIsMajorCity((short) 2);
        // bean.setIsMajorCityText(
        // messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
        // } else if (s.getStationName().equals("北京") || s.getStationName().equals("深圳")
        // || s.getStationName().equals("合肥")) {
        // bean.setIsMajorCity((short) 1);
        // bean.setIsMajorCityText(
        // messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale));
        //
        // } else {
        // bean.setIsMajorCity(s.getIsMajorCity());
        // bean.setIsMajorCityText(s.getIsMajorCity() == 1
        // ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
        // : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        // }

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
            bean.setEndAText(a.getStationName());

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
            bean.setEndBText(b.getStationName());

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

        bean.setSegmentStatus(s.getSegmentStatus());
        bean.setSegmentStatusText(messageSource.getMessage(
                FacilityConstructionStatusEnum.getResouceName(s.getSegmentStatus()), null, locale));

        if (layer.equals("1")) {
            if (manualSegmentStatus != null) {
                bean.setManualSegmentStatus(manualSegmentStatus);
                bean.setManualSegmentStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(manualSegmentStatus), null,
                        locale));
            } else if (s.getManualSegmentStatus() != null) {
                bean.setManualSegmentStatus(s.getManualSegmentStatus());
                bean.setManualSegmentStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(s.getManualSegmentStatus()),
                        null, locale));
            } else {
                bean.setManualSegmentStatus(1);
                bean.setManualSegmentStatusText(messageSource
                        .getMessage(FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }

        } else {
            if (s.getManualSegmentStatus() != null) {
                bean.setManualSegmentStatus(s.getManualSegmentStatus());
                bean.setManualSegmentStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(s.getManualSegmentStatus()), null,
                        locale));
            } else {
                bean.setManualSegmentStatus(1);
                bean.setManualSegmentStatusText(messageSource
                        .getMessage(FacilityConstructionStatusEnum.getResouceName(1), null, locale));
            }
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

    public MapSitePieChartListBean fromEntityCount(int finishCount, int ongoingCount,
            int unfinishCount, MessageSource messageSource, Locale locale) {


        MapSitePieChartListBean bean = new MapSitePieChartListBean();

        bean.setFinishCount(finishCount);
        bean.setOngoingCount(ongoingCount);
        bean.setUnfinishCount(unfinishCount);


        return bean;
    }
}
