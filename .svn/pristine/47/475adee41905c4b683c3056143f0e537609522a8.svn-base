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
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.PlanDetailChartBeanConverter;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.view.PlanDetailChartBean;
import project.edge.domain.view.PlanTaskBean;
import project.edge.service.schedule.PlanTaskService;
import project.edge.web.controller.common.TreeChartController;

/**
 * @author angel_000
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/progress-detail")
public class PlanDetailController extends TreeChartController<PlanTask, PlanTaskBean> {

	private static final Logger logger = LoggerFactory.getLogger(PlanDetailController.class);

	private static final String PID = "P5023";

	@Resource
	private PlanTaskService planTaskService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.PLAN_DETAIL.value();
	}

	@Override
	protected Service<PlanTask, String> getDataService() {
		// TODO Auto-generated method stub
		return this.planTaskService;
	}

	@Override
	protected String getPageId() {
		// TODO Auto-generated method stub
		return PID;
	}

	@Override
	protected ViewConverter<PlanTask, PlanTaskBean> getViewConverter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/schedule/planDetailJs.jsp";
	}

	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
		jsMap.put(ControllerJsMapKeys.LINKED_FUNC, "PLAN_DETAIL.onSelectProjectTreeNode");
		return jsMap;
	}

	/**
	 * 画面Open的入口方法，用于生成JSP。
	 * 
	 * @param paramMap 画面请求中的任何参数，都会成为检索的字段
	 * @param model    model
	 * @param userBean session中的当前登录的用户信息
	 * @param locale   locale
	 * @return
	 */
	@RequestMapping("/main")
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		return super.main(paramMap, model, userBean, locale);
	}

	/**
	 * 进度明细
	 */
	@RequestMapping("/chart")
	@ResponseBody
	public JsonResultBean getChart(@RequestParam(required = false, defaultValue = "") String planId, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		try {

			CommonFilter filter = new CommonFilter();
			if (!StringUtils.isEmpty(planId)) {
				filter.addExact("p.id", planId);
			}

			// 增加排序
			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("isSiteTask", false));
			orders.add(new OrderByDto("taskNum", false));

			List<PlanTask> planTaskList = this.planTaskService.list(filter, orders);
			List<PlanDetailChartBean> planDetailBeanList = new ArrayList<PlanDetailChartBean>();

			if ((planTaskList != null) && (planTaskList.size() > 0)) {

				PlanDetailChartBeanConverter converter = new PlanDetailChartBeanConverter();

				for (PlanTask p : planTaskList) {
					planDetailBeanList.add(converter.fromEntity(p, this.messageSource, locale));
				}

			}

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, planDetailBeanList);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}
}
