/**
 * 
 */
package project.edge.mobile.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

import javax.annotation.Resource;

import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.MobileJsonResultBean;
import project.edge.domain.mobile.converter.SettingsBeanConverter;
import project.edge.domain.mobile.view.SettingsBean;

/**
 * @author angel_000
 *         系统配置API。
 */
@Controller
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Resource
    private MessageSource messageSource;

    /**
     * 系统配置。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getSettings(Locale locale) {

        MobileJsonResultBean jsonResult = new MobileJsonResultBean();
        try {

            // 转换
            SettingsBeanConverter converter = new SettingsBeanConverter();

            SettingsBean bean = converter.fromEntity(this.messageSource, locale);

            jsonResult.setResult(bean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception while getting the settings.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setErrorMessage("message.error.remote.server", this.messageSource, locale);
        }

        return jsonResult;

    }
}
