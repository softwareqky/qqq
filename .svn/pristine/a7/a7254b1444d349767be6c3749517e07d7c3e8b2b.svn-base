package project.edge.mobile.controller.system;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.MobileJsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.entity.Page;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.mobile.converter.ApplicationBeanConverter;
import project.edge.domain.mobile.view.ApplicationBean;
import project.edge.service.auth.PageService;

/**
 * @author angel_000
 *         应用API。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Resource
    private MessageSource messageSource;

    @Resource
    private PageService pageService;

    /**
     * 应用。
     * 
     * @param locale
     * @return
     */
    @RequestMapping(value = "/system/application", method = RequestMethod.POST)
    @ResponseBody
    public MobileJsonResultBean getApplication(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Model model,
            Locale locale) {


        MobileJsonResultBean jsonResult = new MobileJsonResultBean();

        try {

            // 获取角色对应的模块
            PageFilter pageFilter = new PageFilter();

            pageFilter.addExact("id", userBean.getSessionUserId());

            if (userBean.getRoleId() == null) {
                return jsonResult.setErrorMessage("ui.common.unauthorized", this.messageSource,
                        locale);
            }

            List<Page> pageList = this.pageService.list(pageFilter, null);

            ApplicationBeanConverter converter = new ApplicationBeanConverter();
            ApplicationBean bean = converter.fromEntity(pageList, this.messageSource, locale);

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
