/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.view.MainInfoImproveListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoImproveListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoImproveListBean fromEntity(ProjectCheck p, MessageSource messageSource,
            Locale locale) {


        if (p == null) {
            return null;
        }

        MainInfoImproveListBean bean = new MainInfoImproveListBean();
        bean.setId(p.getId());
        bean.setName(p.getProject().getProjectName());
        if(p.getCheckDate() != null) {
            bean.setTime(DateUtils.date2String(p.getCheckDate(), Constants.DATE_FORMAT));
        }
        
        return bean;
    }
}
