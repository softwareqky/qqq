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
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.view.PlanBean;
import project.edge.domain.view.TaskDelayAnalysisBean;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeChartController;

/**
 * 滞后分析画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/delay-analysis")
public class DelayAnalysisController extends TreeChartController<Plan, PlanBean> {

	private static final Logger logger = LoggerFactory.getLogger(DelayAnalysisController.class);

	private static final String PID = "P5025";

	@Resource
	private PlanService planService;

	@Resource
	private PlanTaskService planTaskService;

	@Resource
	private DataOptionService dataOptionService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.DELAY_ANALYSIS.value();
	}

	@Override
	protected Service<Plan, String> getDataService() {
		return this.planService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<Plan, PlanBean> getViewConverter() {
		return null;
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/schedule/delayAnalysisJs.jsp";
	}

	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
		jsMap.put(ControllerJsMapKeys.LINKED_FUNC, "DELAY_ANALYSIS.onSelectProjectTreeNode");
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
	 * 滞后分析
	 */
	@RequestMapping("/chart")
	@ResponseBody
	public JsonResultBean getChart(@RequestParam(required = false, defaultValue = "") String planId, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		try {

			CommonFilter filter = new CommonFilter();
			if (!StringUtils.isEmpty(planId)) {
				filter.addExact("plan.id", planId);
			}

			List<String> dataTypeList = new ArrayList<>();
			dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
			CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
			List<DataOption> list = this.dataOptionService.list(f, null);
			List<TaskDelayAnalysisBean> taskDelayAnalysisBeans = new ArrayList<>();
			for (DataOption o : list) {
				if (o.getDataType().equals(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value())) {
					TaskDelayAnalysisBean bean = new TaskDelayAnalysisBean();
					bean.setIssueType(o.getId());
					bean.setIssueTypeText(o.getOptionName());
					filter.addExact("issueType", o.getId());
					List<PlanTask> tasks = this.planTaskService.list(filter, null);
					bean.setNum(tasks.size());
					taskDelayAnalysisBeans.add(bean);
				}
			}
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, taskDelayAnalysisBeans);
//
//			List<Plan> planList = this.planService.list(filter, null);
//			DelayAnalysisBean delayAnalysisBean = new DelayAnalysisBean();
//
//			if ((planList != null) && (planList.size() > 0)) {
//				Plan p = planList.get(0);
//
//				delayAnalysisBean.setTotalTaskCount(p.getTotalTaskCount());
//				delayAnalysisBean.setTotalFinishTaskCount(p.getTotalFinishTaskCount());
//				delayAnalysisBean.setTotalWarningTaskCount(p.getTotalDelayTaskCount());
//				delayAnalysisBean.setTotalDelayTaskCount(p.getTotalDelayTaskCount());
//				delayAnalysisBean.setReqDelayTaskCount(p.getReqDelayTaskCount());
//				delayAnalysisBean.setSolutionDelayTaskCount(p.getSolutionDelayTaskCount());
//				delayAnalysisBean.setHrDelayTaskCount(p.getHrDelayTaskCount());
//				delayAnalysisBean.setBudgetDelayTaskCount(p.getBudgetDelayTaskCount());
//			}
//
//			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, delayAnalysisBean);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 计划下拉框
	 */
	@RequestMapping("/list-plan")
	@ResponseBody
	public JsonResultBean getPlanList(@RequestParam(required = false, defaultValue = "") String projectId,
			Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		try {

			CommonFilter filter = new CommonFilter();
			if (!StringUtils.isEmpty(projectId)) {
				filter.addExact("project.id", projectId);
			}

			List<Plan> list = this.planService.list(filter, null);
			List<ComboboxOptionBean> planOptionList = new ArrayList<>();
			// String format = "(%1$s) %2$s";

			if (list != null) {
				for (Plan v : list) {
					planOptionList.add(new ComboboxOptionBean(v.getId(), v.getPlanName()));
				}
			}

			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, planOptionList);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {

			this.getLogger().error("Exception while getting the projectId " + projectId, e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}
}
