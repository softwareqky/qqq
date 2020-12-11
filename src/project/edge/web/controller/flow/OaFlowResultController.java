package project.edge.web.controller.flow;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.view.JsonOaResultBean;
import garage.origin.domain.view.SessionUserBean;


/**
 * @author angel_000
 *         OA回传。
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class OaFlowResultController {

    private static final Logger logger = LoggerFactory.getLogger(OaFlowResultController.class);

    @Resource
    private MessageSource messageSource;


    /**
     * 审批结果回传。
     * 
     * @param locale
     * @return
     */
    @RequestMapping("/flow/oa-flow-result")
    @ResponseBody
    public JsonOaResultBean getFlowResult(@RequestParam(required = true, defaultValue = "") String id,
            @RequestParam(required = true, defaultValue = "") String category,
            @RequestParam(required = true, defaultValue = "") String flow_action,
            @RequestParam(required = false, defaultValue = "") String flow_datetime,
            @RequestParam(required = false, defaultValue = "") String remark,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonOaResultBean jsonResult = new JsonOaResultBean();
        try {


            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            logger.error("Exception main.", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;

    }
}
