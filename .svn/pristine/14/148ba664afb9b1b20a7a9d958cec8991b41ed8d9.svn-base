/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.entity.Plan;
import project.edge.domain.view.PlanSummaryChartBean;

/**
 * @author angel_000
 *
 */
public class PlanSummaryChartBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public PlanSummaryChartBean fromEntity(Plan p, MessageSource messageSource, Locale locale) {

        if (p == null) {
            return null;
        }

        PlanSummaryChartBean bean = new PlanSummaryChartBean();
        bean.setId(p.getId());
        bean.setPlanName(p.getPlanName());

        if (p.getProgress() != null) {
            bean.setProgress(p.getProgress());
        } else {
            bean.setProgress((double) 0);
        }

        return bean;
    }
}
