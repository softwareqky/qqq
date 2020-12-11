package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.view.MapSitePieChartListBean;

/**
 * @author angel_000
 *
 */
public class MapSitePieChartListBeanConvert {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MapSitePieChartListBean fromEntity(int unfinishCount, int ongoingCount, int finishCount,
            MessageSource messageSource, Locale locale) {

        MapSitePieChartListBean bean = new MapSitePieChartListBean();

        bean.setUnfinishCount(unfinishCount);
        bean.setOngoingCount(ongoingCount);
        bean.setFinishCount(finishCount);

        return bean;
    }
}
