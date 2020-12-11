package project.edge.web.controller.flowable;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.FlowableSettingBeanConverter;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.view.FlowableSettingBean;
import project.edge.service.flowable.FlowableSettingService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 流程监控画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/flowable/monitor")
public class FlowableMonitorController
        extends SingleGridController<FlowableSetting, FlowableSettingBean> {

    private static final Logger logger = LoggerFactory.getLogger(FlowableMonitorController.class);

    private static final String PID = "P9502";
    private static final String MONITOR_VIEW_NAME = "flowable/flowableMonitorPage"; // 修改默认的JSP

    @Resource
    private FlowableSettingService flowableSettingService;

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return MONITOR_VIEW_NAME;
    }

    @Override
    protected Service<FlowableSetting, String> getDataService() {
        return this.flowableSettingService;
    }

    @Override
    protected ViewConverter<FlowableSetting, FlowableSettingBean> getViewConverter() {
        return new FlowableSettingBeanConverter();
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.FLOWABLE_MONITOR.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return super.main(paramMap, model, userBean, locale);
    }

    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

        return jsonResult;
    }
}
