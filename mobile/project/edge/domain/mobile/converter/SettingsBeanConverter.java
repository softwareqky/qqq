/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.business.SystemConfigSettings;
import project.edge.domain.mobile.view.SettingsBean;

/**
 * @author angel_000
 *
 */
public class SettingsBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param messageSource
     * @param locale
     * @return
     */
    public SettingsBean fromEntity(MessageSource messageSource, Locale locale) {

        SettingsBean bean = new SettingsBean();

        bean.setServerUri(SystemConfigSettings.getInstance().getMobileServerUri());
        bean.setCertUri(SystemConfigSettings.getInstance().getMobileCertUri());
        bean.setPollingInterval(SystemConfigSettings.getInstance().getMobilePollingInterval());
        bean.setAppVserion(SystemConfigSettings.getInstance().getMobileVserion());
        bean.setAppDownUrl(SystemConfigSettings.getInstance().getMobileDownUrl());

        return bean;

    }
}
