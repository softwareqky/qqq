package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.view.LeaderHomePieChartListBean;

/**
 * @author angel_000
 *
 */
public class LeaderHomePieChartListBeanConvert {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public LeaderHomePieChartListBean fromEntity(int finishCount, int ongoingCount,
            int unfinishCount, int edgeFinishCount, int edgeOngoingCount, int edgeUnfinishCount,
            MessageSource messageSource, Locale locale) {


        LeaderHomePieChartListBean bean = new LeaderHomePieChartListBean();

        bean.setFinishCount(finishCount);
        bean.setOngoingCount(ongoingCount);
        bean.setUnfinishCount(unfinishCount);
        bean.setEdgeFinishCount(edgeFinishCount);
        bean.setEdgeOngoingCount(edgeOngoingCount);
        bean.setEdgeUnfinishCount(edgeUnfinishCount);

        return bean;
    }
}
