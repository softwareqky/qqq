/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

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
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.MapLinkListBean;
import project.edge.domain.view.MapSegmentListBean;
import project.edge.domain.view.MapSiteListBean;

/**
 * @author angel_000
 *
 */
public class MapSiteListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MapSiteListBean fromEntity(Site s, MessageSource messageSource, Locale locale) {

        if (s == null) {
            return null;
        }

        MapSiteListBean bean = new MapSiteListBean();

        bean.setId(s.getId());

        bean.setSiteNum(s.getSiteNum());

        bean.setStationName(s.getStationName());
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
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
            }
        } else if (s.getProgrammableNetworkManualSiteStatus() != null) {
            if (s.getProgrammableNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
            }
        } else if (s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getSdnNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getSdnNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
            }
        } else if (s.getBasicNetworkManualSiteStatus() != null
                && s.getProgrammableNetworkManualSiteStatus() != null) {
            if (s.getBasicNetworkManualSiteStatus() == 2
                    && s.getProgrammableNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
            }
        } else if (s.getBasicNetworkManualSiteStatus() != null
                && s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getBasicNetworkManualSiteStatus() == 2
                    && s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getBasicNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getBasicNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
            }
        } else if (s.getProgrammableNetworkManualSiteStatus() != null
                && s.getSdnNetworkManualSiteStatus() != null) {
            if (s.getProgrammableNetworkManualSiteStatus() == 2
                    && s.getSdnNetworkManualSiteStatus() == 2) {
                bean.setManualSiteStatus(s.getProgrammableNetworkManualSiteStatus());
                bean.setManualSiteStatusText(messageSource.getMessage(FacilityConstructionStatusEnum
                        .getResouceName(s.getProgrammableNetworkManualSiteStatus()), null, locale));
            } else {
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
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
                bean.setManualSiteStatus(0);
                bean.setManualSiteStatusText(messageSource.getMessage(
                        FacilityConstructionStatusEnum.getResouceName(0), null, locale));
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

    public MapSegmentListBean fromEntitySeg(Segment s, MessageSource messageSource, Locale locale) {

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
            } else {
                bean.setEndALng((double) 0);
            }

            if (a.getLatitude() != null) {
                bean.setEndALat(a.getLatitude());
            } else {
                bean.setEndALat((double) 0);
            }
        }

        Site b = s.getEndB();
        if (b != null) {
            bean.setEndB_(b.getId());
            bean.setEndBText(b.getStationName());

            if (a.getLongitude() != null) {
                bean.setEndBLng(b.getLongitude());
            } else {
                bean.setEndBLng((double) 0);
            }

            if (a.getLatitude() != null) {
                bean.setEndBLat(b.getLatitude());
            } else {
                bean.setEndBLat((double) 0);
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
        }

        Site b = link.getEndB();
        if (b != null) {
            bean.setEndB(b.getStationName());
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
