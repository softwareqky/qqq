/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.entity.Plan;
import project.edge.domain.view.MainInfoPlanListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoPlanListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoPlanListBean fromEntity(Plan p, MessageSource messageSource, Locale locale) {

        if (p == null) {
            return null;
        }

        MainInfoPlanListBean bean = new MainInfoPlanListBean();
        bean.setId(p.getId());
        bean.setName(p.getPlanName());
        if (p.getIsComplete() != null) {
            bean.setProgress(p.getIsComplete() == 1
                    ? messageSource.getMessage("ui.fields.plan.completed", null, locale)
                    : messageSource.getMessage("ui.fields.plan.not.incomplete", null, locale));
        } else {
            bean.setProgress(
                    messageSource.getMessage("ui.fields.plan.not.incomplete", null, locale));
        }

        return bean;

    }
}
