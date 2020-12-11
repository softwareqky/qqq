package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.Segment;
import project.edge.domain.view.LeaderHomeReuseNameListBean;

/**
 * @author angel_000
 *
 */
public class LeaderHomeReuseNameListBeanConvert {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public LeaderHomeReuseNameListBean fromEntity(Segment segment, MessageSource messageSource,
            Locale locale) {

        if (segment == null) {
            return null;
        }

        LeaderHomeReuseNameListBean bean = new LeaderHomeReuseNameListBean();
        bean.setId(segment.getId());
        bean.setReuseName(segment.getReuseSegment());
        if (segment.getcDatetime() != null) {
            bean.setTime(DateUtils.date2String(segment.getcDatetime(), Constants.DATE_FORMAT));
        }

        return bean;

    }
}
