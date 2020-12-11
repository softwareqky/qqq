/**
 * 
 */
package project.edge.web.controller.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.PlanSummaryChartBeanConverter;
import project.edge.domain.entity.Plan;
import project.edge.domain.view.PlanBean;
import project.edge.domain.view.PlanSummaryChartBean;
import project.edge.service.schedule.PlanService;
import project.edge.web.controller.common.TreeChartController;


/**
 * @author angel_000
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/progress-summary")
public class PlanSummaryController extends TreeChartController<Plan, PlanBean> {

    private static final Logger logger = LoggerFactory.getLogger(PlanSummaryController.class);

    private static final String PID = "P5022";

    @Resource
    private PlanService planService;

    @Override
    protected Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.PLAN_SUMMARY.value();
    }

    @Override
    protected Service<Plan, String> getDataService() {
        // TODO Auto-generated method stub
        return this.planService;
    }

    @Override
    protected String getPageId() {
        // TODO Auto-generated method stub
        return PID;
    }

    @Override
    protected ViewConverter<Plan, PlanBean> getViewConverter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/schedule/planSummaryJs.jsp";
    }

    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
        jsMap.put(ControllerJsMapKeys.LINKED_FUNC, "PLAN_SUMMARY.onSelectProjectTreeNode");
        return jsMap;
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

    /**
     * 进度汇总
     */
    @RequestMapping("/chart")
    @ResponseBody
    public JsonResultBean getChart(
            @RequestParam(required = false, defaultValue = "") String projectId, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        try {

            CommonFilter filter = new CommonFilter();
            if (!StringUtils.isEmpty(projectId)) {
                filter.addExact("project.id", projectId);
            }
            
            filter.addNull("si.id");
            filter.addNull("se.id");

            List<Plan> planList = this.planService.list(filter, null);
            List<PlanSummaryChartBean> planSummaryBeanList = new ArrayList<PlanSummaryChartBean>();

            if ((planList != null) && (planList.size() > 0)) {

                PlanSummaryChartBeanConverter converter = new PlanSummaryChartBeanConverter();

                for (Plan p : planList) {
                    planSummaryBeanList.add(converter.fromEntity(p, this.messageSource, locale));
                }

            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, planSummaryBeanList);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
        } catch (Exception e) {

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

}
