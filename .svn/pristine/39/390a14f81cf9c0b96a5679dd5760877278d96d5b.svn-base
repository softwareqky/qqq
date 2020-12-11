package project.edge.web.controller.oa;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.integration.oa.HrmManager;

/**
 * 与OA系统集成相关的Controller。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/oa")
public class OaController {

    private static final Logger logger = LoggerFactory.getLogger(OaController.class);

    @Resource
    protected MessageSource messageSource;

    @Resource
    private HrmManager hrmManager;

    /**
     * 同步OA系统的人力资源相关的数据，包括分部、部门、岗位和人员。
     * 
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/hrm/sync")
    @ResponseBody
    public JsonResultBean delete(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            boolean r = this.hrmManager.doImport();

            // 准备JSON结果
            if (r) {
                jsonResult.setStatus(JsonStatus.OK);
                jsonResult.setMessage(
                        this.messageSource.getMessage("message.info.sync.hrm.ok", null, locale));
            } else {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource.getMessage("message.error.sync.hrm.failed",
                        null, locale));
            }

        } catch (Exception e) {
            logger.error("Exception synchronizing hrm data from OA.", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
}
