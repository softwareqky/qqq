package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.entity.DataOption;
import project.edge.domain.view.MainInfoIssueItemListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoIssueItemListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoIssueItemListBean fromEntity(int count, DataOption d,
            MessageSource messageSource, Locale locale) {

        if (d == null) {
            return null;
        }

        MainInfoIssueItemListBean bean = new MainInfoIssueItemListBean();
        bean.setIssueCount(count);
        bean.setIssueType(d.getOptionName());

        return bean;
    }
}
