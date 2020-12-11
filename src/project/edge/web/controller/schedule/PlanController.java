package project.edge.web.controller.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.CombineTypeEnum;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.domain.converter.PlanBeanConverter;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.PlanBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SiteService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanCalendarService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.web.apiService.plan.PlanApiService;
import project.edge.web.controller.common.TreeGridController;
import project.edge.web.controller.general.DataPermissionService;

/**
 * 计划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/plan")
public class PlanController extends TreeGridController<Plan, PlanBean> {

	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

	private static final String PID = "P5001";
	private static final String OA_TYPE_PLAN = "9";
	private static final String MAP_KEY_MERGE_FIELDS = "mergeFields";
	private static final String SELECT_PLAN = "selectPlan";
	private static final String SELECT_TASK = "selectTask";

	@Autowired
	HttpServletRequest request;

	@Resource
	CreateWorkFlowManager createWorkFlowManager;

	@Resource
	private PlanService planService;

	@Resource
	private PlanTaskService planTaskService;

	@Resource
	private ProjectService projectService;

	@Resource
	private PlanCalendarService planCalendarService;

	@Resource
	private VirtualOrgService virtualOrgService;

	@Resource
	private ProjectPersonService projectPersonService;

	@Resource
	private SiteService siteService;

	@Resource
	private SegmentService segmentService;
	
	@Resource
	private DataPermissionService dataPermissionService;
	
	@Resource
	private PlanApiService planApiService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PLAN.value();
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
		return new PlanBeanConverter();
	}

	@Override
	protected String getJsCallbackObjName() {
		return "PlanUtils";
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/schedule/planJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/schedule/planHidden.jsp";
	}

	/**
	 * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
	 * 
	 * @param locale
	 * @return key:[v_data_option].option_source，value:[v_data_option]
	 */
	@Override
	protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

		// CommonFilter f = null;
		//
		// List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
		// // CommonFilter f = null;
		// // f.addExact("projectRoleName", DataTypeEnum.PROJECT_ROLE.value());
		// List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f,
		// null);
		// if (virtualOrgList != null) {
		// for (VirtualOrg v : virtualOrgList) {
		// projectGroupOptions.add(new ComboboxOptionBean(v.getId(),
		// v.getVirtualOrgName()));
		// }
		// }
		// optionMap.put("groupOptions", projectGroupOptions);
		//
		// String[] mergeDateArr = new String[] {"2019", "2020", "2021", "2022",
		// "2023", "2024",
		// "2025", "2026", "2027", "2028", "2029", "2030"};
		//
		// List<ComboboxOptionBean> mergeDateOptions = new ArrayList<>();
		//
		// for (String s : mergeDateArr) {
		// mergeDateOptions.add(new ComboboxOptionBean(s, s));
		// }
		//
		// optionMap.put("mergeDateOptions", mergeDateOptions);

		String[] planYearArr = new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
				"2028", "2029", "2030" };

		List<ComboboxOptionBean> planYearOptions = new ArrayList<>();

		for (String s : planYearArr) {
			planYearOptions.add(new ComboboxOptionBean(s, s));
		}

		optionMap.put("planYearOptions", planYearOptions);

		String[] isYearArr = new String[] { "否", "是" };
		List<ComboboxOptionBean> isYearOptions = new ArrayList<>();
		for (int i = 0; i < isYearArr.length; i++) {
			isYearOptions.add(new ComboboxOptionBean(i + "", isYearArr[i]));
		}
		optionMap.put("isYearOptions", isYearOptions);

		String[] planTypeArr = new String[] { "年度建设工作计划", "站点计划", "中继段计划" };
		List<ComboboxOptionBean> planTypeOptions = new ArrayList<>();
		for (int i = 0; i < planTypeArr.length; i++) {
			planTypeOptions.add(new ComboboxOptionBean(i + "", planTypeArr[i]));
		}
		optionMap.put("planTypeOptions", planTypeOptions);

		CommonFilter filter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		filter.addExact("isEchartsShow", OnOffEnum.ON.value().intValue());
		List<VirtualOrg> virtualOrgs = this.virtualOrgService.list(filter, null);
		List<ComboboxOptionBean> groupOptions = new ArrayList<>();
		for (int i = 0; i < virtualOrgs.size(); i++) {
			groupOptions
					.add(new ComboboxOptionBean(virtualOrgs.get(i).getId(), virtualOrgs.get(i).getVirtualOrgName()));
		}
		optionMap.put("groupOptions", groupOptions);

		// ArrayList<ComboboxOptionBean> combineTypeOptions = new
		// ArrayList<ComboboxOptionBean>();
		//
		// for (CombineTypeEnum l : CombineTypeEnum.values()) {
		// combineTypeOptions.add(new ComboboxOptionBean(l.value().toString(),
		// messageSource.getMessage(l.resourceName(), null, locale)));
		// }
		//
		// optionMap.put("combineTypeOptions", combineTypeOptions);

		CommonFilter pFilter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		List<Site> sites = this.siteService.list(pFilter, null);
		List<Segment> segments = this.segmentService.list(pFilter, null);
		pFilter.addNull("site.id");
		pFilter.addNull("segment.id");
		List<Plan> plans = this.planService.list(pFilter, null);
		List<ComboboxOptionBean> personOptions = new ArrayList<>();
		List<String> personIds = new ArrayList<>();
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).getPersonInCharge() != null
					&& !personIds.contains(sites.get(i).getPersonInCharge().getId())) {
				personIds.add(sites.get(i).getPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getPersonInCharge().getId(),
						sites.get(i).getPersonInCharge().getPersonName()));
			}
			if (sites.get(i).getProgrammablePersonInCharge() != null
					&& !personIds.contains(sites.get(i).getProgrammablePersonInCharge().getId())) {
				personIds.add(sites.get(i).getProgrammablePersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getProgrammablePersonInCharge().getId(),
						sites.get(i).getProgrammablePersonInCharge().getPersonName()));
			}
			if (sites.get(i).getSdnPersonInCharge() != null
					&& !personIds.contains(sites.get(i).getSdnPersonInCharge().getId())) {
				personIds.add(sites.get(i).getSdnPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getSdnPersonInCharge().getId(),
						sites.get(i).getSdnPersonInCharge().getPersonName()));
			}
		}
		for (int i = 0; i < segments.size(); i++) {
			if (segments.get(i).getPersonInCharge() != null
					&& !personIds.contains(segments.get(i).getPersonInCharge().getId())) {
				personIds.add(segments.get(i).getPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(segments.get(i).getPersonInCharge().getId(),
						segments.get(i).getPersonInCharge().getPersonName()));
			}
		}
		for (int i = 0; i < plans.size(); i++) {
			if (plans.get(i).getCreator() != null && !personIds.contains(plans.get(i).getCreator().getId())) {
				personIds.add(plans.get(i).getCreator().getId());
				personOptions.add(new ComboboxOptionBean(plans.get(i).getCreator().getId(),
						plans.get(i).getCreator().getPersonName()));
			}
		}
		optionMap.put("personOptions", personOptions);
		return optionMap;
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

		String mainView = super.main(paramMap, model, userBean, locale);
		// 1. 将默认的新增删除用的字段移除，重新添加秤字段Map，这样就不会生成默认的新建/修改弹出框
		// 需求变更，去除按年份合并
		Map<String, Object> modelMap = model.asMap();

		if (paramMap != null) {
			String planId = paramMap.get("planId");
			String taskId = paramMap.get("taskId");
			model.addAttribute(SELECT_PLAN, planId);
			model.addAttribute(SELECT_TASK, taskId);
		}

		@SuppressWarnings("unchecked")
		ArrayList<FieldBean> addEditFields = (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.ADD_FIELDS);

		List<FieldBean> mergeFields = new ArrayList<>();

		// CommonFilter filter = new CommonFilter();
		// filter.addWithin("id", new String[] {"PLAN.planYear"});
		//
		// List<DataFields> fields = this.dataFieldsService.list(filter, null);
		// // 默认按fieldOrder字段排序

		for (FieldBean bean : addEditFields) {
			mergeFields.add(bean);
		}

		// for (DataFields f : fields) {
		// FieldBean fieldBean = new FieldBean(f, this.messageSource, locale);
		// mergeFields.add(fieldBean);
		// }

		model.addAttribute(MAP_KEY_MERGE_FIELDS, mergeFields);
		model.addAttribute(MAP_KEY_MERGE_FIELDS, addEditFields);

		return mainView;
	}

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param page             页数
	 * @param rows             每页的记录数
	 * @param sort             排序的字段，CSV
	 * @param order            顺序，CSV
	 * @param locale
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {
		
		
		commonFilterJson = dataPermissionService.editFilterBySelfVirtualOrg(userBean, commonFilterJson);

		JsonResultBean jsonResult = planService.list(commonFilterJson, page, rows, sort, order, locale);
		// super.list(commonFilterJsonNew,
		// f, page,
		// rows, sort,
		// order,
		// locale);

		return jsonResult;
	}

	@RequestMapping("/list2")
	@ResponseBody
	public JsonResultBean list2(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		commonFilterJson = dataPermissionService.editFilterBySelfVirtualOrg(userBean, commonFilterJson);
		
		JsonResultBean jsonResult = planService.list(commonFilterJson, page, rows, sort, order, locale);

		return jsonResult;
	}

	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id     ID
	 * @param locale
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	@Override
	public JsonResultBean find(@RequestParam String id, Locale locale) {
		return super.find(id, locale);
	}

	/**
	 * 新建，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	@SysLogAnnotation(description = "计划管理", action = "新增")
	public JsonResultBean create(@ModelAttribute PlanBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		String projectId = bean.getProject_();
		String userId = userBean.getSessionUserId();
		CommonFilter f = new CommonFilter().addExact("project.id", projectId).addExact("person.id", userId);

		List<ProjectPerson> projectPersons = projectPersonService.list(f, null);
		if ((projectPersons != null) && (projectPersons.size() > 0)) {
			bean.setVirtualOrg_(projectPersons.get(0).getVirtualOrg().getId());
		}

		return super.create(bean, null, userBean, locale);
	}

	@Override
	protected void postCreate(Plan entity, PlanBean bean, Map<String, Object> paramMap, Locale locale)
			throws Exception {

		if (!StringUtils.isEmpty(bean.getMergeIds())) {

			List<PlanTask> newTasks = new ArrayList<>();

			Date cDatetime = entity.getcDatetime();
			String creator = entity.getCreator().getId();
			Date mDatetime = entity.getmDatetime();

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));

			CommonFilter planFilter = new CommonFilter();

			String[] idArray = StringUtils.commaDelimitedListToStringArray(bean.getMergeIds());
			List<String> idList = Arrays.asList(idArray);

			planFilter.addWithin("id", idList);

			List<OrderByDto> cDatetimeOrders = new ArrayList<>();
			cDatetimeOrders.add(new OrderByDto("cDatetime", false));

			List<Plan> plans = this.planService.list(planFilter, cDatetimeOrders);

			for (Plan plan : plans) {

				List<PlanTask> planATasks = this.planTaskService
						.list(new CommonFilter().addExact("plan.id", plan.getId()), orders);

				for (PlanTask task : planATasks) {
					task.setPlan(entity);
					task.setcDatetime(cDatetime);
					task.setmDatetime(mDatetime);
					task.setCreator(creator);
					newTasks.add(task);
				}
			}

			this.hanldePlanTaskIdAndPid(newTasks);

			if (newTasks.size() > 0) {
				this.planTaskService.create(newTasks);
			}

			entity.setCombineType(CombineTypeEnum.COMBINE.value());
			this.planService.update(entity);
		}
	}

	// 更新原始ID、Pid、排序
	private void hanldePlanTaskIdAndPid(List<PlanTask> newTasks) {

		int taskNum = 0;
		for (PlanTask planTask : newTasks) {
			String originId = planTask.getId();

			String newId = UUID.randomUUID().toString();

			for (PlanTask planTaskB : newTasks) {

				if (planTaskB.getPid().equals(originId)) {
					planTaskB.setPid(newId);
				}
			}

			planTask.setId(newId);
			planTask.setTaskNum(++taskNum);
		}
	}

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "计划管理", action = "更新")
	public JsonResultBean update(@ModelAttribute PlanBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.update(bean, null, userBean, locale);
	}

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "计划管理", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToDelete);
		String[] siteIds = new String[idArray.length];
		for (int i = 0; i < idArray.length; i++) {
			Plan plan = planService.find(idArray[i]);
			if (plan != null && plan.getSite() != null) {
				siteIds[i] = plan.getSite().getId();
			}
		}
		
		JsonResultBean result = super.delete(idsToDelete, null, userBean, locale);
		
		// 重新生成站点的进展计划
		for (String siteId: siteIds) {
			planApiService.updateSiteProgress(siteId);
		}
		
		return result;
	}

	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "计划管理", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		Plan entity = this.getDataService().find(id);
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}

		// 插入到t_oa_flow_history
		String dataId = entity.getId();
		String remark = entity.getRemark();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PLAN.value();
		// String userName = "wangtest";
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("planName", entity.getPlanName());
		reqMap.put("planVersion", entity.getPlanVersion());
		// reqMap.put("projectName", entity.getProject().getProjectName());
		// reqMap.put("virtualOrgName",
		// entity.getVirtualOrg().getVirtualOrgName());
		reqMap.put("planYear", entity.getPlanYear());
		reqMap.put("creator", entity.getCreator().getId());
		String siteId = entity.getSite() == null ? "":entity.getSite().getId();
		String segmentId = entity.getSegment()==null?"":entity.getSegment().getId();

		if (entity.getIsYear() == 1) {
			reqMap.put("planType", "年度建设工作计划");
		} else {
			if (!StringUtils.isEmpty(siteId)) {
				reqMap.put("planType", "站点计划");
			} else if (!StringUtils.isEmpty(segmentId)) {
				reqMap.put("planType", "中继段计划");
			} else {
				reqMap.put("planType", "");
			}
		}
		// if(entity.getSite()!=null){
		// reqMap.put("siteName", entity.getSite().getStationName());
		// }
		//
		// Segment segment=entity.getSegment();
		// if(segment!=null){
		// reqMap.put("segmentName", entity.getSegment().getSystemName());
		// }

		reqMap.put("remark", entity.getRemark());

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
		// 调用oa接口
		String rtnObject = "";
		logger.info("[OA Audit] No.9 Plan");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		// jsonResult.setStatus(JsonStatus.OK);
		// 如果请求审批成功更新表t_plan 审批中
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.9 Plan Audit request success, plan data id was {}", dataId);
			Plan plan = planService.find(dataId);
			plan.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			plan.setmDatetime(new Date());
			plan.setModifier(userName);
			planService.setData(plan);
		}

		return jsonResult;
	}
}
