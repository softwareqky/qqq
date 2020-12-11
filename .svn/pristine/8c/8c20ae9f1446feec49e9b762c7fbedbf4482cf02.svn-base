package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.MainInfoProjectPersonListBean;

/**
 * @author angel_000
 *
 */
public class MainInfoProjectPersonListBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoProjectPersonListBean fromEntity(int count,VirtualOrg v, MessageSource messageSource,
            Locale locale) {

        if (v == null) {
            return null;
        }

        MainInfoProjectPersonListBean bean = new MainInfoProjectPersonListBean();
        bean.setId(v.getId());
        
        bean.setVirtualOrgName(v.getVirtualOrgName());
        bean.setCount(count);

        if (v.getcDatetime() != null) {
            bean.setTime(DateUtils.date2String(v.getcDatetime(), Constants.DATE_FORMAT));
        }

        return bean;

    }
}
