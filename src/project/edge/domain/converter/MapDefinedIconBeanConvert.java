package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import project.edge.domain.entity.DefinedSegmentIcon;
import project.edge.domain.entity.DefinedSiteIcon;
import project.edge.domain.view.DefinedSegmentIconBean;
import project.edge.domain.view.DefinedSiteIconBean;

/**
 * @author angel_000
 *
 */
public class MapDefinedIconBeanConvert {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public DefinedSiteIconBean fromEntitySite(DefinedSiteIcon d, MessageSource messageSource,
            Locale locale) {

        if (d == null) {
            return null;
        }

        DefinedSiteIconBean bean = new DefinedSiteIconBean();

        bean.setId(d.getId());
        bean.setSiteType(d.getSiteType());
        bean.setIconName(d.getIconName());
        bean.setX(d.getX());
        bean.setY(d.getY());
        bean.setSize(d.getSize());

        return bean;
    }

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public DefinedSegmentIconBean fromEntitySeg(DefinedSegmentIcon d, MessageSource messageSource,
            Locale locale) {

        if (d == null) {
            return null;
        }

        DefinedSegmentIconBean bean = new DefinedSegmentIconBean();

        bean.setId(d.getId());
        bean.setSegmentType(d.getSegmentType());
        bean.setWidth(d.getWidth());
        bean.setColor(d.getColor());
        bean.setLineStyle(d.getLineStyle());

        return bean;
    }



    public DefinedSiteIcon toEntitySite(String id, Short isMap, String siteType, String iconName,
            String x, String y, DefinedSiteIcon oldEntity, AbstractSessionUserBean user, Date now, Short size) {
        // TODO Auto-generated method stub

        DefinedSiteIcon entity = new DefinedSiteIcon();

        entity.setModifier(user.getSessionUserId());
        entity.setId(id);
        entity.setIsMap(isMap);
        entity.setSiteType(siteType);
        entity.setIconName(iconName);
        entity.setX(x);
        entity.setY(y);
        entity.setmDatetime(now);
        entity.setSize(size);

        return entity;
    }

    public DefinedSegmentIcon toEntitySeg(String id, Short isMap, String segmentType, String width,
            String color, String lineStyle, DefinedSegmentIcon oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        DefinedSegmentIcon entity = new DefinedSegmentIcon();

        entity.setModifier(user.getSessionUserId());

        entity.setId(id);
        entity.setIsMap(isMap);
        entity.setSegmentType(segmentType);
        entity.setWidth(width);
        entity.setColor(color);
        entity.setLineStyle(lineStyle);
        entity.setmDatetime(now);
        return entity;
    }
}
