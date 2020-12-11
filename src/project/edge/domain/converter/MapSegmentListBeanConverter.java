/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.common.constant.FacilityConstructionStatusEnum;
import project.edge.common.constant.SegmentTypeEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.MapSegmentListBean;

/**
 * @author angel_000
 *
 */
public class MapSegmentListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MapSegmentListBean fromEntity(Segment s, MessageSource messageSource, Locale locale) {


        if (s == null) {
            return null;
        }

        MapSegmentListBean bean = new MapSegmentListBean();

        bean.setId(s.getId());

        Site a = s.getEndA();
        if (a != null) {
            bean.setEndA_(a.getId());
            bean.setEndAText(a.getStationName());
            
            if(a.getLongitude() != null) {
                bean.setEndALng(a.getLongitude());
            }else {
                bean.setEndALng((double)0);
            }
            
            if(a.getLatitude() != null) {
                bean.setEndALat(a.getLatitude());
            }else {
                bean.setEndALat((double)0);
            }
        }

        Site b = s.getEndB();
        if (b != null) {
            bean.setEndB_(b.getId());
            bean.setEndBText(b.getStationName());
            
            if(a.getLongitude() != null) {
                bean.setEndBLng(b.getLongitude());
            }else {
                bean.setEndBLng((double)0);
            }
            
            if(a.getLatitude() != null) {
                bean.setEndBLat(b.getLatitude());
            }else {
                bean.setEndBLat((double)0);
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
}
