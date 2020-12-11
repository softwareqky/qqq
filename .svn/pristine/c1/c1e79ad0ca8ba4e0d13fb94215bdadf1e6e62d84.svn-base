/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.MainInfoIssueItemListBean;
import project.edge.domain.view.MainInfoIssueListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoIssueListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoIssueListBean fromEntity(List<MainInfoIssueItemListBean> list, VirtualOrg v,
            MessageSource messageSource, Locale locale) {

        if (v == null) {
            return null;
        }

        MainInfoIssueListBean bean = new MainInfoIssueListBean();
        bean.setId(v.getId());
        bean.setVirtualOrgName(v.getVirtualOrgName());
        bean.setIssueItemList(list);
        if (v.getcDatetime() != null) {
            bean.setTime(DateUtils.date2String(v.getcDatetime(), Constants.DATE_FORMAT));
        }

        return bean;
    }
}
