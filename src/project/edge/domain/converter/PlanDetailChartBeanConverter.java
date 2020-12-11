/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.entity.PlanTask;
import project.edge.domain.view.PlanDetailChartBean;

/**
 * @author angel_000
 *
 */
public class PlanDetailChartBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public PlanDetailChartBean fromEntity(PlanTask p, MessageSource messageSource, Locale locale) {

        if (p == null) {
            return null;
        }

        PlanDetailChartBean bean = new PlanDetailChartBean();
        bean.setId(p.getId());
        bean.setTaskName(p.getTaskName());
        bean.setLayer(p.getTaskLayer());
        
        if(p.getProgress() != null) {
            bean.setProgress(p.getProgress());
        }else {
            bean.setProgress((double)0);
        }

        return bean;
    }
}
