package project.edge.domain.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;

import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.MainInfoVirtualOrgBean;

public class MainInfoVirtualOrgBeanConverter  {
	/**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public MainInfoVirtualOrgBean fromEntity(VirtualOrg p, MessageSource messageSource, Locale locale) {

        if (p == null) {
            return null;
        }

        MainInfoVirtualOrgBean bean = new MainInfoVirtualOrgBean();
        bean.setId(p.getId());
        bean.setVirtaulOrgName(p.getVirtualOrgName());

        return bean;

    }
}
