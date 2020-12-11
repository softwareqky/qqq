package project.edge.web.controller.general;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.OaProcessConstant;
import project.edge.domain.converter.MainInfoBudgetFeeListBeanConvert;
import project.edge.domain.converter.MainInfoNoticeListBeanConverter;
import project.edge.domain.converter.MainInfoPlanFinishListBeanConverter;
import project.edge.domain.converter.MainInfoPlanListBeanConverter;
import project.edge.domain.converter.MainInfoProjectInfoListBeanConverter;
import project.edge.domain.converter.MainInfoTaskListBeanConverter;
import project.edge.domain.converter.MainInfoVirtualOrgBeanConverter;
import project.edge.domain.converter.NoticeAnnouncementBeanConverter;
import project.edge.domain.converter.ProjectBeanConverter;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.Issue;
import project.edge.domain.entity.NoticeAnnouncement;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PlanTaskFavorite;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectFavorite;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.MainChartInfoBean;
import project.edge.domain.view.MainInfoBean;
import project.edge.domain.view.MainInfoBudgetFeeListBean;
import project.edge.domain.view.MainInfoIssueListBean;
import project.edge.domain.view.MainInfoNoticeInfoListBean;
import project.edge.domain.view.MainInfoProcessSummarySubBean;
import project.edge.domain.view.MainInfoProjectCountBean;
import project.edge.domain.view.MainInfoProjectInfoListBean;
import project.edge.domain.view.MainInfoProjectPersonListBean;
import project.edge.domain.view.MainInfoProjectSummarySubBean;
import project.edge.domain.view.MainInfoTaskListBean;
import project.edge.domain.view.NoticeAnnouncementBean;
import project.edge.service.acceptance.AcceptanceCheckService;
import project.edge.service.acceptance.ReviewService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.facility.SiteService;
import project.edge.service.flowable.FlowableSettingService;
import project.edge.service.hr.PersonService;
import project.edge.service.notice.NoticeAnnouncementService;
import project.edge.service.process.ProjectCheckService;
import project.edge.service.project.ProjectChangeService;
import project.edge.service.project.ProjectFavoriteService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectRoleService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.quality.IssueService;
import project.edge.service.schedule.PlanProgressService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskFavoriteService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.DataFieldsService;
import project.edge.service.system.DataOptionService;

/**
 * Home画面。
 * 
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
public class MainHomeController {

	private static final Logger logger = LoggerFactory.getLogger(MainHomeController.class);

	private static final String HOME_VIEW_NAME = "main/mainHomePage";

	private static final int LIST_PAGE = 1; // 获取首页
	private static final int LIST_ROW = 5; // 首页模块数据条数
	// private static final String BASIC_NETWORK_TYPE = "1";
	// private static final String PROGRAMMABLE_NETWORK_TYPE = "2";
	// private static final String SDN_NETWORK_TYPE = "3";
	private String projectId;

	@Autowired
	protected ServletContext context;

	@Resource
	private MessageSource messageSource;

	@Resource
	private ProjectService projectService;

	@Resource
	private TaskService taskService;
	
	@Resource
	private SiteService siteService;

	@Resource
	private HistoryService historyService;

	@Resource
	private PersonService personService;

	@Resource
	private ProjectFavoriteService projectFavoriteService;

	@Resource
	private PlanTaskFavoriteService planTaskFavoriteService;

	@Resource
	private NoticeAnnouncementService noticeAnnouncementService;

	@Resource
	private ProjectPersonService projectPersonService;

	@Resource
	private VirtualOrgService virtualOrgService;

	@Resource
	private PlanProgressService planProgressService;

	@Resource
	private PlanTaskService planTaskService;

	@Resource
	private BudgetEstimateService budgetEstimateService;

	@Resource
	private ProjectChangeService projectChangeService;

	@Resource
	private ReviewService reviewService;

	@Resource
	private ProjectCheckService projectCheckService;

	@Resource
	private AcceptanceCheckService acceptanceCheckService;

	@Resource
	private PlanService planService;

	@Resource
	private BudgetFeeService budgetFeeService;

	@Resource
	private IssueService issueService;

	@Resource
	private DataOptionService dataOptionService;

	@Resource
	protected DataFieldsService dataFieldsService;

	@Resource
	private ProjectRoleService projectRoleService;

	@Resource
	private FlowableSettingService flowableSettingService;

	/**
	 * 打开主画面。
	 */
	@RequestMapping("/home")
	public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Model model,
			HttpServletResponse response, Locale locale) {

		return HOME_VIEW_NAME;
	}

	/**
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/main-info")
	@ResponseBody
	public JsonResultBean getMainInfo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			MainInfoBean mainInfoBean = new MainInfoBean();

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			pageCtrl.setCurrentPage(LIST_PAGE);
			pageCtrl.setPageSize(LIST_ROW);

			String userid = userBean.getSessionUserId();

			MainInfoProjectSummarySubBean projectSummary = new MainInfoProjectSummarySubBean();

			// 立项过滤
//			CommonFilter projectInitfilter = null;
//			projectInitfilter = new CommonFilter().addExact("person.user.id", userid)
//					.addExact("project.flowStatusProject", 2);

//			List<ProjectPerson> projectInitList = this.projectPersonService.list(projectInitfilter, null);

			// 实施过滤
//			CommonFilter implementFilter = new CommonFilter().addExact("le.id", userid);

//			List<PlanTask> implementList = this.planTaskService.list(implementFilter, null);

			// 资金计划过滤
//			CommonFilter fundPlanFilter = new CommonFilter().addExact("pj.applicant.id", userid)
//					.addExact("pj.flowStatusProject", 2);

//			List<BudgetEstimate> fundPlanList = this.budgetEstimateService.list(fundPlanFilter, null);

			// 项目变更过滤
//			CommonFilter projectChangeFilter = new CommonFilter().addExact("applicant.id", userid);

//			List<ProjectChange> projectChangeList = this.projectChangeService.list(projectChangeFilter, null);

			// 阶段性检查过滤
//			CommonFilter periodicCheckFilter = new CommonFilter().addExact("pj.applicant.user.id", userid);

//			List<ProjectCheck> periodicCheckList = this.projectCheckService.list(null, null);

			// 验收过滤
//			CommonFilter acceptanceFilter = new CommonFilter().addExact("checker", userid);

//			List<AcceptanceCheck> acceptanceList = this.acceptanceCheckService.list(acceptanceFilter, null);

//			projectSummary.setProjectInit(projectInitList.size());
//			projectSummary.setImplement(implementList.size());
//			projectSummary.setFundPlan(fundPlanList.size());
//			projectSummary.setProjectChange(projectChangeList.size());
//			projectSummary.setPeriodicCheck(periodicCheckList.size());
//			projectSummary.setAcceptance(acceptanceList.size());
			mainInfoBean.setProjectSummary(projectSummary);

			// 待办事项
			CommonFilter pFilter = new CommonFilter();
			pFilter.addExact("person.id", userBean.getSessionUserId());

			List<ProjectPerson> persons = this.projectPersonService.list(pFilter, null);

			List<String> groups = new ArrayList<>();
			groups.add(Constants.STRING_EMPTY);
			for (ProjectPerson p : persons) {
				groups.add(p.getProjectRole().getId());
			}
			MainInfoProcessSummarySubBean processSummary = new MainInfoProcessSummarySubBean();

			CommonFilter flowFilter = new CommonFilter();
			flowFilter.addExact("isDisable", (short) 0);

			PageCtrlDto flowPage = new PageCtrlDto();
			flowPage.setCurrentPage(LIST_PAGE);
			flowPage.setPageSize(999);
			List<FlowableSetting> flows = flowableSettingService.list(flowFilter, null, flowPage);

			JSONArray pendingProcess = new JSONArray();
			JSONArray initiatedProcess = new JSONArray();
			JSONArray doneProcess = new JSONArray();
			try {
				for (FlowableSetting flow : flows) {
					TaskQuery taskQuery = this.taskService.createTaskQuery().includeProcessVariables().or()
							.taskCandidateGroupIn(groups).taskAssignee(userBean.getSessionUserId()).endOr();
					HistoricProcessInstanceQuery hiQuery = this.historyService.createHistoricProcessInstanceQuery()
							.includeProcessVariables().startedBy(userBean.getSessionUserId());
					HistoricTaskInstanceQuery htQuery = this.historyService.createHistoricTaskInstanceQuery()
							.taskCandidateGroupIn(groups).includeProcessVariables()
							.taskAssignee(userBean.getSessionUserId()).finished();

					JSONObject pend = new JSONObject();
					pend.put("id", flow.getId());
					pend.put("name", flow.getFlowName());
					taskQuery = taskQuery.processVariableValueEquals("flowableSettingId", flow.getId());
					List<Task> tasks = taskQuery.list();
					pend.put("num", tasks.size());

					JSONObject init = new JSONObject();
					init.put("id", flow.getId());
					init.put("name", flow.getFlowName());
					hiQuery = hiQuery.variableValueEquals("flowableSettingId", flow.getId());
					List<HistoricProcessInstance> list = hiQuery.list();
					init.put("num", list.size());

					JSONObject done = new JSONObject();
					done.put("id", flow.getId());
					done.put("name", flow.getFlowName());
					htQuery = htQuery.processVariableValueEquals("flowableSettingId", flow.getId());
					List<HistoricTaskInstance> doneList = htQuery.list();
					done.put("num", doneList.size());

					pendingProcess.add(pend);
					initiatedProcess.add(init);
					doneProcess.add(done);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			processSummary.setPendingProcess(pendingProcess);
			processSummary.setInitiatedProcess(initiatedProcess);
			processSummary.setDoneProcess(doneProcess);

			mainInfoBean.setProcessSummary(processSummary);

			// 通知公告
			List<NoticeAnnouncement> noticeList = this.noticeAnnouncementService.list(null, null, pageCtrl);
			if ((noticeList != null) && (noticeList.size() > 0)) {
				MainInfoNoticeListBeanConverter converter = new MainInfoNoticeListBeanConverter();

				for (NoticeAnnouncement n : noticeList) {
					MainInfoNoticeInfoListBean bean = converter.fromEntity(n, this.messageSource, locale);
					String recivers = "";
					if (!StringUtils.isEmpty(bean.getOriginator())) {
						String reciver[] = bean.getOriginator().split("\\|\\|");
						if (reciver.length == 2) {
							recivers = this.virtualOrgService.find(reciver[1].replace("virtualOrg:", ""))
									.getVirtualOrgName();
						}
					}
					bean.setOriginator(recivers);
					mainInfoBean.getNoticeInfo().add(bean);
				}
			}

			// 项目列表
			List<Project> projectList = this.projectService.list(null, null);
			List<MainInfoProjectInfoListBean> mainInfoProjectInfoList = new ArrayList<MainInfoProjectInfoListBean>();

			if ((projectList != null) && (projectList.size() > 0)) {

				projectId = projectList.get(0).getId();
				MainInfoProjectInfoListBeanConverter converter = new MainInfoProjectInfoListBeanConverter();

				for (Project p : projectList) {

					mainInfoProjectInfoList.add(converter.fromEntity(p, this.messageSource, locale));
				}

				mainInfoBean.setProjectInfo(mainInfoProjectInfoList);
			}

			// 我的关注
			// 验收过滤
			CommonFilter favoriteFilter = new CommonFilter().addExact("user.id", userid);
			List<ProjectFavorite> favoriteList = this.projectFavoriteService.list(favoriteFilter, null);

			List<MainInfoProjectInfoListBean> mainInfoFavoriteInfoList = new ArrayList<MainInfoProjectInfoListBean>();
			if ((favoriteList != null) && (favoriteList.size() > 0)) {

				MainInfoProjectInfoListBeanConverter converter = new MainInfoProjectInfoListBeanConverter();

				for (ProjectFavorite p : favoriteList) {

					mainInfoFavoriteInfoList.add(converter.fromEntityFavorite(p, this.messageSource, locale));
				}

				mainInfoBean.setFavoriteInfo(mainInfoFavoriteInfoList);
			}

			// 我关注的任务
			// 验收过滤
			CommonFilter taskfavoriteFilter = new CommonFilter().addExact("user.id", userBean.getSessionUserId());
			List<PlanTaskFavorite> planTaskFavorites = this.planTaskFavoriteService.list(taskfavoriteFilter, null);

			List<MainInfoTaskListBean> mainInfoTaskFavoriteInfoList = new ArrayList<MainInfoTaskListBean>();
			if ((planTaskFavorites != null) && (planTaskFavorites.size() > 0)) {

				MainInfoTaskListBeanConverter converter = new MainInfoTaskListBeanConverter();

				for (PlanTaskFavorite p : planTaskFavorites) {

					mainInfoTaskFavoriteInfoList.add(converter.fromEntity(p.getPlanTask(), this.messageSource, locale));
				}

				mainInfoBean.setTaskFavoriteInfo(mainInfoTaskFavoriteInfoList);
			}

			// // 过滤
			// CommonFilter personFilter = new CommonFilter().addExact("pj.id", projectId);

			// // 人数统计，放检索
			// List<ProjectPerson> personList =
			// this.projectPersonService.list(personFilter, null, pageCtrl);
			// List<MainInfoProjectPersonListBean> mainInfoPersonList =
			// new ArrayList<MainInfoProjectPersonListBean>();
			// if ((personList != null) && (personList.size() > 0)) {
			//
			// MainInfoProjectPersonListBeanConverter converter =
			// new MainInfoProjectPersonListBeanConverter();
			//
			// for (ProjectPerson p : personList) {
			// mainInfoPersonList.add(converter.fromEntity(p, this.messageSource, locale));
			// }
			//
			// mainInfoBean.setVirtualOrg(mainInfoPersonList);
			// }

			// // 过滤
			// CommonFilter finishFilter = new CommonFilter().addExact("p.id", projectId);
			//
			// // 完成度
			// List<Plan> planFinishList = this.planService.list(finishFilter, null,
			// pageCtrl);
			// if ((planFinishList != null) && (planFinishList.size() > 0)) {
			// MainInfoPlanFinishListBeanConverter converter =
			// new MainInfoPlanFinishListBeanConverter();
			//
			// for (Plan p : planFinishList) {
			// mainInfoBean.getPlanFinish()
			// .add(converter.fromEntity(p, this.messageSource, locale));
			// }
			// }
			//
			// // 过滤
			// CommonFilter issueFilter = new CommonFilter().addExact("project.id",
			// projectId);
			//
			// // 问题
			// List<Issue> issueList = this.issueService.list(issueFilter, null, pageCtrl);
			// if ((issueList != null) && (issueList.size() > 0)) {
			//
			// MainInfoIssueListBeanConverter converter = new
			// MainInfoIssueListBeanConverter();
			//
			// for (Issue i : issueList) {
			// mainInfoBean.getIssueInfo()
			// .add(converter.fromEntity(i, this.messageSource, locale));
			// }
			// }

			// ===========================================假数据=====================================
			/*
			 * MainInfoExposureTableListBean j1 = new MainInfoExposureTableListBean();
			 * j1.setId("1"); j1.setName("晋鹏"); j1.setPunishment("惩罚");
			 * MainInfoExposureTableListBean j2 = new MainInfoExposureTableListBean();
			 * j2.setId("2"); j2.setName("念蕾"); j2.setPunishment("惩罚");
			 * MainInfoExposureTableListBean j3 = new MainInfoExposureTableListBean();
			 * j3.setId("3"); j3.setName("紫夏"); j3.setPunishment("惩罚");
			 * MainInfoExposureTableListBean j4 = new MainInfoExposureTableListBean();
			 * j4.setId("4"); j4.setName("凌旋"); j4.setPunishment("奖励");
			 * MainInfoExposureTableListBean j5 = new MainInfoExposureTableListBean();
			 * j5.setId("5"); j5.setName("芷梦"); j5.setPunishment("惩罚");
			 * 
			 * mainInfoBean.getExposureTable().add(j1);
			 * mainInfoBean.getExposureTable().add(j2);
			 * mainInfoBean.getExposureTable().add(j3);
			 * mainInfoBean.getExposureTable().add(j4);
			 * mainInfoBean.getExposureTable().add(j5);
			 * 
			 * MainInfoFundsSummaryBean fundsSummary = new MainInfoFundsSummaryBean();
			 * 
			 * fundsSummary.setTotal(100); fundsSummary.setGeneral(20);
			 * fundsSummary.setUsed(13); fundsSummary.setRemanent(20);
			 * fundsSummary.setProgress(20);
			 * 
			 * mainInfoBean.setFundsSummary(fundsSummary);
			 * 
			 * MainInfoProjectSummarySubBean projectSummary = new
			 * MainInfoProjectSummarySubBean();
			 * 
			 * projectSummary.setApprove(20); projectSummary.setReview(13);
			 * projectSummary.setAcceptance(20); projectSummary.setNotice(20);
			 * 
			 * mainInfoBean.setProjectSummary(projectSummary);
			 */

			// ===========================================假数据=====================================

			mainInfoBean.setYears(DateUtils.yearToNow(2019));

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainInfoBean);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);

		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;

	}

	@RequestMapping("/main-chartInfo")
	@ResponseBody
	public JsonResultBean getMainChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 参数检查
			if (StringUtils.isEmpty(projectId)) {
				jsonResult.setStatus(JsonStatus.ERROR);
				return jsonResult;
			}

			MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			pageCtrl.setCurrentPage(LIST_PAGE);
			pageCtrl.setPageSize(LIST_ROW);

			// 过滤
			CommonFilter planFilter = new CommonFilter().addExact("project.id", projectId);

			// 我的任务
			List<Plan> planList = this.planService.list(planFilter, null);
			if ((planList != null) && (planList.size() > 0)) {
				MainInfoPlanListBeanConverter converter = new MainInfoPlanListBeanConverter();
				List<String> planIds = new ArrayList<>();
				for (Plan p : planList) {
					mainChartInfoBean.getPlan().add(converter.fromEntity(p, this.messageSource, locale));
					planIds.add(p.getId());
				}

				Person person = personService.find(userBean.getSessionUserId());
				
				// 查找作为参与人的计划任务
				CommonFilter taskFilter = new CommonFilter().addWithin("plan.id", planIds);
				taskFilter.addAnywhere("participantList", person.getId());
				List<PlanTask> taskList = this.planTaskService.list(taskFilter, null);
				if ((taskList != null) && (taskList.size() > 0)) {
					MainInfoTaskListBeanConverter taskConverter = new MainInfoTaskListBeanConverter();
					for (PlanTask t : taskList) {
						mainChartInfoBean.getTasks().add(taskConverter.fromEntity(t, this.messageSource, locale));
					}
				}
				
				// 查找作为负责人的计划任务
				taskFilter = new CommonFilter().addWithin("plan.id", planIds);
				taskFilter.addExact("leader.id", person.getId());
				taskList = this.planTaskService.list(taskFilter, null);
				if ((taskList != null) && (taskList.size() > 0)) {
					MainInfoTaskListBeanConverter taskConverter = new MainInfoTaskListBeanConverter();
					for (PlanTask t : taskList) {
						
						boolean alreadyExsits = false;
						for (MainInfoTaskListBean tb: mainChartInfoBean.getTasks()) {
							if (t.getId().equals(tb.getTaskId())) {
								alreadyExsits = true;
								break;
							}
						}
						
						if (!alreadyExsits) {
							mainChartInfoBean.getTasks().add(taskConverter.fromEntity(t, this.messageSource, locale));
						}
					}
				}
			}

			// 过滤
			// CommonFilter budgetFeeFilter = new CommonFilter().addExact("p.id",
			// projectId);

			// 资金计划
			List<BudgetFee> budgetFeeList = this.budgetFeeService.list(null, null, pageCtrl);
			if ((budgetFeeList != null) && (budgetFeeList.size() > 0)) {
				MainInfoBudgetFeeListBeanConvert converter = new MainInfoBudgetFeeListBeanConvert();

				for (BudgetFee b : budgetFeeList) {
					mainChartInfoBean.getBudget().add(converter.fromEntity(b, this.messageSource, locale));
				}
			}

			/*
			 * if ((issueList != null) && (issueList.size() > 0)) {
			 * 
			 * MainInfoIssueListBeanConverter converter = new
			 * MainInfoIssueListBeanConverter();
			 * 
			 * for (Issue i : issueList) { mainChartInfoBean.getIssueInfo()
			 * .add(converter.fromEntity(i, this.messageSource, locale)); } }
			 */

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);

		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;

	}

	@RequestMapping("/people-chartInfo")
	@ResponseBody
	public JsonResultBean getPeopleChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String year,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		List<String> monthList = DateUtils.monthList(year);

		MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

		// 过滤
		CommonFilter personFilter = new CommonFilter().addExact("project.id", projectId);

		// 人数统计

		// 加入按创建时间排序 start
		List<OrderByDto> personOrderList = new ArrayList<>();
		OrderByDto personOrder = new OrderByDto();
		personOrder.setColumnName("cDatetime");
		personOrder.setDesc(false);
		personOrderList.add(personOrder);
		// 加入按创建时间排序 end
		List<ProjectPerson> personList = this.projectPersonService.list(personFilter, personOrderList);

		// 根据当前用户项目角色判断是否展示所有小组
//		VirtualOrg userVirtualOrg = null; // 默认是高级用户
//		List<ProjectPerson> ppList = this.listProjectRole(userBean.getSessionUserId(), projectId);
//		List<ProjectRole> projectRoleList = this.projectRoleService.list(null, null);
//
//		for (ProjectRole prole : projectRoleList) {
//			for (ProjectPerson pp : ppList) {
//				if (pp.getProjectRole().getId().equals(prole.getId()) && prole.getProjectRoleName().contains("组员")) {
//					userVirtualOrg = pp.getVirtualOrg();
//					break;
//				}
//			}
//		}

		List<VirtualOrg> virtualOrgList = new ArrayList<VirtualOrg>();
//		if (userVirtualOrg != null) {
//			virtualOrgList.add(userVirtualOrg);
//		} else {
		CommonFilter virtualOrgFilter = new CommonFilter().addExact("project.id", projectId);
		virtualOrgList = this.virtualOrgService.list(virtualOrgFilter, null);
//		}
		// 获得统计起讫月份
//			List<String> monthList = DateUtils.monthToMonth(personList.get(0).getcDatetime(),
//					personList.get(personList.size() - 1).getcDatetime());
		TreeMap<String, List<MainInfoProjectPersonListBean>> virtualOrgMap = new TreeMap<String, List<MainInfoProjectPersonListBean>>();
		for (VirtualOrg v : virtualOrgList) {
			for (String month : monthList) {
				int num = 0;
				if ((personList != null) && (personList.size() > 0)) {
					for (ProjectPerson p : personList) {
						if (DateUtils.isLessThen(p.getcDatetime(), month) && p.getVirtualOrg() != null
								&& v.getId().equals(p.getVirtualOrg().getId())
								&& p.getProject().getId().equals(projectId)) {
							// 创建时间大于统计时间且在项目组，计数
							num++;
						}
					}
				}

				// 根据月份起讫时间，填充月份统计数量
				List<MainInfoProjectPersonListBean> beanList = new ArrayList<MainInfoProjectPersonListBean>();
				if (virtualOrgMap.get(month) == null) {
					// 未存储过月份，新建
					MainInfoProjectPersonListBean bean = new MainInfoProjectPersonListBean();
					bean.setTime(month);
					bean.setVirtualOrgName(v.getVirtualOrgName());
					bean.setCount(num);
					beanList.add(bean);
					virtualOrgMap.put(month, beanList);
				} else {
					// 存储过月份
					beanList = virtualOrgMap.get(month);
					boolean flag = false;
					for (MainInfoProjectPersonListBean bean : beanList) {
						if (bean.getVirtualOrgName().equals(v.getVirtualOrgName())) {
							flag = true;
							bean.setCount(bean.getCount() + num);
						}
					}
					if (!flag) {
						// 未出现过项目组
						MainInfoProjectPersonListBean bean = new MainInfoProjectPersonListBean();
						bean.setTime(month);
						bean.setVirtualOrgName(v.getVirtualOrgName());
						bean.setCount(num);
						beanList.add(bean);
					}
					virtualOrgMap.put(month, beanList);
				}
			}
//							mainInfoPersonList.add(converter.fromEntity(count, v, this.messageSource, locale));
		}
		// 统计总数
		for (String month : monthList) {
			int count = 0;
			if (virtualOrgMap.get(month) != null) {
				List<MainInfoProjectPersonListBean> beanList = virtualOrgMap.get(month);
				for (MainInfoProjectPersonListBean bean : beanList) {
					count += bean.getCount();
				}
				MainInfoProjectPersonListBean beanTotal = new MainInfoProjectPersonListBean();
				beanTotal.setTime(month);
				beanTotal.setCount(count);
				beanTotal.setVirtualOrgName("总人数");
				beanList.add(beanTotal);
				virtualOrgMap.put(month, beanList);
			}
		}
		mainChartInfoBean.setVirtualOrgMap(virtualOrgMap);
		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
		jsonResult.setStatus(JsonStatus.OK);
		return jsonResult;
	}

	@RequestMapping("/issue-chartInfo")
	@ResponseBody
	public JsonResultBean getIssueChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String year,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		List<String> monthList = DateUtils.monthList(year);

		MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

		// 设置分页信息
		PageCtrlDto pageCtrl = new PageCtrlDto();
		pageCtrl.setCurrentPage(LIST_PAGE);
		pageCtrl.setPageSize(LIST_ROW);

		// 根据当前用户项目角色判断是否展示所有小组
		VirtualOrg userVirtualOrg = null; // 默认是高级用户
		List<ProjectPerson> ppList = this.listProjectRole(userBean.getSessionUserId(), projectId);
		List<ProjectRole> projectRoleList = this.projectRoleService.list(null, null);

		for (ProjectRole prole : projectRoleList) {
			for (ProjectPerson pp : ppList) {
				if (pp.getProjectRole().getId().equals(prole.getId()) && prole.getProjectRoleName().contains("组员")) {
					userVirtualOrg = pp.getVirtualOrg();
					break;
				}
			}
		}

		List<VirtualOrg> virtualOrgList = new ArrayList<VirtualOrg>();
		if (userVirtualOrg != null) {
			virtualOrgList.add(userVirtualOrg);
		} else {
			CommonFilter virtualOrgFilter = new CommonFilter().addExact("project.id", projectId);
			virtualOrgList = this.virtualOrgService.list(virtualOrgFilter, null, pageCtrl);
		}

		// 过滤
		CommonFilter issueFilter = new CommonFilter().addExact("project.id", projectId);

		// 加入按创建时间排序 start
		List<OrderByDto> issueOrderList = new ArrayList<>();
		OrderByDto issueOrder = new OrderByDto();
		issueOrder.setColumnName("cDatetime");
		issueOrder.setDesc(false);
		issueOrderList.add(issueOrder);
		// 问题
		List<Issue> issueList = this.issueService.list(issueFilter, issueOrderList, pageCtrl);
		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.ISSUE_TYPE.value());
		// 获得统计起讫月份
		TreeMap<String, List<MainInfoIssueListBean>> virtualOrgMap = new TreeMap<String, List<MainInfoIssueListBean>>();
		for (VirtualOrg v : virtualOrgList) {
			for (String month : monthList) {
				int num = 0;
				if ((issueList != null) && (issueList.size() > 0)) {
					for (Issue issue : issueList) {
						if (DateUtils.isLessThen(issue.getcDatetime(), month) && issue.getVirtualOrg() != null
								&& v.getId().equals(issue.getVirtualOrg().getId())
								&& issue.getProject().getId().equals(projectId)) {
							// 创建时间大于统计时间且在项目组，计数
							num++;
						}
					}
				}

				// 根据月份起讫时间，填充月份统计数量
				List<MainInfoIssueListBean> beanList = new ArrayList<MainInfoIssueListBean>();
				if (virtualOrgMap.get(month) == null) {
					// 未存储过月份，新建
					MainInfoIssueListBean bean = new MainInfoIssueListBean();
					bean.setTime(month);
					bean.setVirtualOrgName(v.getVirtualOrgName());
					bean.setCount(num);
					beanList.add(bean);
					virtualOrgMap.put(month, beanList);
				} else {
					// 存储过月份
					beanList = virtualOrgMap.get(month);
					boolean flag = false;
					for (MainInfoIssueListBean bean : beanList) {
						if (bean.getVirtualOrgName().equals(v.getVirtualOrgName())) {
							flag = true;
							bean.setCount(bean.getCount() + num);
						}
					}
					if (!flag) {
						// 未出现过项目组
						MainInfoIssueListBean bean = new MainInfoIssueListBean();
						bean.setTime(month);
						bean.setVirtualOrgName(v.getVirtualOrgName());
						bean.setCount(num);
						beanList.add(bean);
					}
					virtualOrgMap.put(month, beanList);
				}
			}
//				mainInfoPersonList.add(converter.fromEntity(count, v, this.messageSource, locale));
		}
		// 统计总数
		for (String month : monthList) {
			int count = 0;
			if (virtualOrgMap.get(month) != null) {
				List<MainInfoIssueListBean> beanList = virtualOrgMap.get(month);
				for (MainInfoIssueListBean bean : beanList) {
					count += bean.getCount();
				}
				MainInfoIssueListBean beanTotal = new MainInfoIssueListBean();
				beanTotal.setTime(month);
				beanTotal.setCount(count);
				beanTotal.setVirtualOrgName("总问题数");
				beanList.add(beanTotal);
				virtualOrgMap.put(month, beanList);
			}
		}
		mainChartInfoBean.setIssueMap(virtualOrgMap);
		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
		jsonResult.setStatus(JsonStatus.OK);
		return jsonResult;
	}

	@RequestMapping("/fee-chartInfo")
	@ResponseBody
	public JsonResultBean getFeeChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String year,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		List<String> monthList = DateUtils.monthList(year);

		MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();
		// 设置分页信息
		PageCtrlDto pageCtrl = new PageCtrlDto();
		pageCtrl.setCurrentPage(LIST_PAGE);
		pageCtrl.setPageSize(LIST_ROW);

		// 根据当前用户项目角色判断是否展示所有小组
		VirtualOrg userVirtualOrg = null; // 默认是高级用户
		List<ProjectPerson> ppList = this.listProjectRole(userBean.getSessionUserId(), projectId);
		List<ProjectRole> projectRoleList = this.projectRoleService.list(null, null);

		for (ProjectRole prole : projectRoleList) {
			for (ProjectPerson pp : ppList) {
				if (pp.getProjectRole().getId().equals(prole.getId()) && prole.getProjectRoleName().contains("组员")) {
					userVirtualOrg = pp.getVirtualOrg();
					break;
				}
			}
		}

		List<VirtualOrg> virtualOrgList = new ArrayList<VirtualOrg>();
		if (userVirtualOrg != null) {
			virtualOrgList.add(userVirtualOrg);
		} else {
			CommonFilter virtualOrgFilter = new CommonFilter().addExact("project.id", projectId);
			virtualOrgList = this.virtualOrgService.list(virtualOrgFilter, null, pageCtrl);
		}
		List<String> gzzNames = new ArrayList<>();
		for (VirtualOrg org : virtualOrgList) {
			gzzNames.add(org.getVirtualOrgName());
		}
		// 过滤
		CommonFilter issueFilter = new CommonFilter().addWithin("gzz", gzzNames);

		// 加入按创建时间排序 start
		List<OrderByDto> feeOrderList = new ArrayList<>();
		OrderByDto feeOrder = new OrderByDto();
		feeOrder.setColumnName("cDatetime");
		feeOrder.setDesc(false);
		feeOrderList.add(feeOrder);
		// 资金
		List<BudgetFee> feeList = this.budgetFeeService.list(issueFilter, feeOrderList, pageCtrl);
		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.ISSUE_TYPE.value());
		TreeMap<String, List<MainInfoBudgetFeeListBean>> virtualOrgMap = new TreeMap<String, List<MainInfoBudgetFeeListBean>>();
		// 获得统计起讫月份
		for (VirtualOrg v : virtualOrgList) {
			for (String month : monthList) {
				BigDecimal sum = new BigDecimal(0);
				if ((feeList != null) && (feeList.size() > 0)) {
					for (BudgetFee budgetFee : feeList) {
						if (DateUtils.isLessThen(budgetFee.getcDatetime(), month)
								&& budgetFee.getGzz().equals(v.getVirtualOrgName())) {
							// 创建时间大于统计时间且在项目组，计数
							sum = sum.add(budgetFee.getBxjey());
						}
					}
				}

				// 根据月份起讫时间，填充月份统计数量
				List<MainInfoBudgetFeeListBean> beanList = new ArrayList<MainInfoBudgetFeeListBean>();
				if (virtualOrgMap.get(month) == null) {
					// 未存储过月份，新建
					MainInfoBudgetFeeListBean bean = new MainInfoBudgetFeeListBean();
					bean.setTime(month);
					bean.setVirtualOrgName(v.getVirtualOrgName());
					bean.setUsedSum(sum);
					beanList.add(bean);
					virtualOrgMap.put(month, beanList);
				} else {
					// 存储过月份
					beanList = virtualOrgMap.get(month);
					boolean flag = false;
					for (MainInfoBudgetFeeListBean bean : beanList) {
						if (bean.getVirtualOrgName().equals(v.getVirtualOrgName())) {
							flag = true;
							bean.setUsedSum(bean.getUsedSum().add(sum));
						}
					}
					if (!flag) {
						// 未出现过项目组
						MainInfoBudgetFeeListBean bean = new MainInfoBudgetFeeListBean();
						bean.setTime(month);
						bean.setVirtualOrgName(v.getVirtualOrgName());
						bean.setUsedSum(sum);
						beanList.add(bean);
					}
					virtualOrgMap.put(month, beanList);
				}
			}
//				mainInfoPersonList.add(converter.fromEntity(count, v, this.messageSource, locale));
		}
		// 统计总数
		for (String month : monthList) {
			BigDecimal count = new BigDecimal(0);
			if (virtualOrgMap.get(month) != null) {
				List<MainInfoBudgetFeeListBean> beanList = virtualOrgMap.get(month);
				for (MainInfoBudgetFeeListBean bean : beanList) {
					count = count.add(bean.getUsedSum());
				}
				MainInfoBudgetFeeListBean beanTotal = new MainInfoBudgetFeeListBean();
				beanTotal.setTime(month);
				beanTotal.setUsedSum(count);
				beanTotal.setVirtualOrgName("总资金");
				beanList.add(beanTotal);
				virtualOrgMap.put(month, beanList);
			}
		}
		mainChartInfoBean.setBudgetFee(virtualOrgMap);
		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
		jsonResult.setStatus(JsonStatus.OK);
		return jsonResult;
	}
	
	@RequestMapping("/main-siteInfo")
	@ResponseBody
	public JsonResultBean getSiteInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String virtualOrg,
			@RequestParam(required = true, defaultValue = "") String nowYear,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
		try {
			
			// 参数检查
			if (StringUtils.isEmpty(projectId)) {
				jsonResult.setStatus(JsonStatus.ERROR);
				return jsonResult;
			}
			
			if (OaProcessConstant.PROJECT_ID.equals(projectId) && StringUtils.isEmpty(virtualOrg)) {
				virtualOrg = "ae26cdfd-ac69-4de4-a44e-8a2b4cee6282";
			}
			
			MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();
			
			VirtualOrg vOrg = this.virtualOrgService.find(virtualOrg);

			// 根据当前用户项目角色判断是否展示所有小组
			VirtualOrg userVirtualOrg = null; // 默认是高级用户
			List<ProjectPerson> ppList = this.listProjectRole(userBean.getSessionUserId(), projectId);
			List<ProjectRole> projectRoleList = this.projectRoleService.list(null, null);

//			for (ProjectRole prole : projectRoleList) {
//				for (ProjectPerson pp : ppList) {
//					if (pp.getProjectRole().getId().equals(prole.getId())
//							&& prole.getProjectRoleName().contains("组员")) {
//						userVirtualOrg = pp.getVirtualOrg();
//						break;
//					}
//				}
//			}

			// 获取当前工程小组
			List<VirtualOrg> virtualOrgList = new ArrayList<VirtualOrg>();
			if (userVirtualOrg != null) {
				virtualOrg = userVirtualOrg.getId();
				virtualOrgList.add(userVirtualOrg);
			} else {
				virtualOrgList = this.virtualOrgService.findList(projectId);
			}
			if ((virtualOrgList != null) && (virtualOrgList.size() > 0)) {
				MainInfoVirtualOrgBeanConverter converter = new MainInfoVirtualOrgBeanConverter();
				for (VirtualOrg p : virtualOrgList) {
					mainChartInfoBean.getVirtualOrgList().add(converter.fromEntity(p, this.messageSource, locale));
				}
			}
			
			// 完成度
			Calendar date = Calendar.getInstance();
			if (nowYear == null || "".equals(nowYear)) {
				nowYear = String.valueOf(date.get(Calendar.YEAR));
			}
			
			List<Plan> planList = this.planService.findList(projectId, virtualOrg, nowYear);
			
			// 按月统计核心站点和边缘节点的当月完成数，和累计完成数
			List<Integer[]> countList = new ArrayList<Integer[]>();
			for (int i = 0; i < 12; i++) {
				Integer[] counts = new Integer[] {0, 0, 0, 0, 0};//核心节点计划，边缘节点计划，累计，核心节点实际，边缘节点实际
				countList.add(counts);
			}
			
			for (Plan plan: planList) {
				if (!StringUtils.isEmpty(plan.getPlanYear())) {
//					Integer year = org.apache.commons.lang3.time.DateUtils.toCalendar(plan.getActualEndDate()).get(Calendar.YEAR);
					if (!StringUtils.isEmpty(nowYear) && !"至今".equals(nowYear)) {
						if (Integer.parseInt(plan.getPlanYear()) != Integer.parseInt(nowYear)) {
							continue;
						}
					} else {
						if (Integer.parseInt(plan.getPlanYear()) > date.get(Calendar.YEAR)) {
							continue;
						}
					}
				}
				
				if (StringUtils.isEmpty(plan.getSite())) {
					continue;
				}
				Site site = plan.getSite();
				if (!"1".equals(plan.getSite().getBasicNetworkTransmitStationType()) && !"4".equals(plan.getSite().getBasicNetworkTransmitStationType())) {
					continue;
				}
				Integer month = 0;
				if (plan.getVirtualOrg().getVirtualOrgName().startsWith("基础网络")) {
					if (!StringUtils.isEmpty(site.getInitMaterialArchieveDate())) {
						month = org.apache.commons.lang3.time.DateUtils.toCalendar(site.getInitMaterialArchieveDate()).get(Calendar.MONTH);
					}
				} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("可编程")) {
					if (StringUtils.isEmpty(site.getEdgeNodeType())) {
						if (!StringUtils.isEmpty(site.getProgrammableInitMaterialArchieveDate())) {
							month = org.apache.commons.lang3.time.DateUtils.toCalendar(site.getProgrammableInitMaterialArchieveDate()).get(Calendar.MONTH);
						}
					} else {
						if (!StringUtils.isEmpty(site.getProgrammableEdgeNodeInitMaterialArchieveDate())) {
							month = org.apache.commons.lang3.time.DateUtils.toCalendar(site.getProgrammableEdgeNodeInitMaterialArchieveDate()).get(Calendar.MONTH);
						}
					}
				} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("SDN")) {
					if (StringUtils.isEmpty(site.getEdgeNodeType())) {
						if (!StringUtils.isEmpty(site.getSdnInitMaterialArchieveDate())) {
							month = org.apache.commons.lang3.time.DateUtils.toCalendar(site.getSdnInitMaterialArchieveDate()).get(Calendar.MONTH);
						}
					} else {
						if (!StringUtils.isEmpty(site.getSdnEdgeNodeInitMaterialArchieveDate())) {
							month = org.apache.commons.lang3.time.DateUtils.toCalendar(site.getSdnEdgeNodeInitMaterialArchieveDate()).get(Calendar.MONTH);
						}
					}
				}
				
				if (plan.getStatus() == 2) {
					CommonFilter filter = new CommonFilter().addExact("p.id", plan.getId());
					List<OrderByDto> orders = new ArrayList<>();
					orders.add(new OrderByDto("taskNum", false));
					List<PlanTask> planTaskList = planTaskService.list(filter, orders);
					for (int i = planTaskList.size() - 1; i >= 0; i--) {

						PlanTask pTask = planTaskList.get(i);
						
						
						
						if (pTask.getProgress() == null || new Double(pTask.getProgress()).intValue() != 100) {
//							plan.setStatus(pTask.getConstructionStatus());
//							break;
							continue;
						} else if (new Double(pTask.getProgress()).intValue() == 100){
							if (pTask.getConstructionStatus() == 2) {
//								Integer completeMonth = org.apache.commons.lang3.time.DateUtils.toCalendar(pTask.getActualEndDate()).get(Calendar.MONTH);
//								if (plan.getSite().getEdgeNodeType() != null && !StringUtils.isEmpty(vOrg)) {
//									if (!vOrg.getVirtualOrgName().startsWith("基础网络")) {
//										for (int a = completeMonth; a < 12; a++) {
//											countList.get(a)[4]++;
//										}
//									} else {
//										for (int a = completeMonth; a < 12; a++) {
//											countList.get(i)[3]++;
//										}
//									}
//								}
								if (!StringUtils.isEmpty(pTask.getActualEndDate())) {
									Integer completeMonth = org.apache.commons.lang3.time.DateUtils.toCalendar(pTask.getActualEndDate()).get(Calendar.MONTH);
									if (plan.getSite().getEdgeNodeType() != null && !plan.getVirtualOrg().getVirtualOrgName().startsWith("基础网络")) {
										if (!StringUtils.isEmpty(vOrg) && !vOrg.getVirtualOrgName().startsWith("基础网络")) {
											for (int a = completeMonth; a < 12; a++) {
												if ("边缘节点".equals(plan.getPlanName().split("_")[1])) {
													countList.get(a)[4]++;
												}
												countList.get(a)[2]++;
											}
										}
									}
									
									if ("1".equals(plan.getSite().getBasicNetworkTransmitStationType())) {
										for (int b = completeMonth; b < 12; b++) {
											countList.get(b)[3]++;
											countList.get(b)[2]++;
										}
									}
								}
							}
							break;
						}
					}
				}
				
				
//				if (!vOrg.getVirtualOrgName().startsWith("基础网络")) {
					// 如果是边缘节点，统计+1
					if (!StringUtils.isEmpty(plan.getSite().getEdgeNodeType()) && plan.getVirtualOrg().getId().equals(virtualOrg)) {
						for (int i = month; i < 12; i++) {
							if ("边缘节点".equals(plan.getPlanName().split("_")[1])) {
								countList.get(i)[1]++;
							}
						}
//						if (plan.getStatus() == 2) {//边缘节点已完成
//							for (int i = month; i < 12; i++) {
//								countList.get(i)[4]++;
//							}
//						}
					}
					// 核心节点
					else {
						for (int i = month; i < 12; i++) {
							countList.get(i)[0]++;
						}
//						if (plan.getStatus() == 2) {//核心节点已完成
//							for (int i = month; i < 12; i++) {
//								countList.get(i)[3]++;
//							}
//						}
					}
//				} else {
//					for (int i = month; i < 12; i++) {
//						countList.get(i)[0]++;
//					}
//				}
				
			}
			
			mainChartInfoBean.setPlanStats(countList);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
			jsonResult.setStatus(JsonStatus.OK);
			
		} catch (Exception e) {
			logger.error("Exception main.", e);
			e.printStackTrace();
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		
		return jsonResult;
	}

	@RequestMapping("/main-finishPlanInfo")
	@ResponseBody
	public JsonResultBean getFinishChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String virtualOrg,
			@RequestParam(required = true, defaultValue = "") String nowYear,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 参数检查
			if (StringUtils.isEmpty(projectId)) {
				jsonResult.setStatus(JsonStatus.ERROR);
				return jsonResult;
			}

			MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

			// 根据当前用户项目角色判断是否展示所有小组
			VirtualOrg userVirtualOrg = null; // 默认是高级用户
			List<ProjectPerson> ppList = this.listProjectRole(userBean.getSessionUserId(), projectId);
			List<ProjectRole> projectRoleList = this.projectRoleService.list(null, null);

			for (ProjectRole prole : projectRoleList) {
				for (ProjectPerson pp : ppList) {
					if (pp.getProjectRole().getId().equals(prole.getId())
							&& prole.getProjectRoleName().contains("组员")) {
						userVirtualOrg = pp.getVirtualOrg();
						break;
					}
				}
			}

			// 获取当前工程小组
			List<VirtualOrg> virtualOrgList = new ArrayList<VirtualOrg>();
			if (userVirtualOrg != null) {
				virtualOrg = userVirtualOrg.getId();
				virtualOrgList.add(userVirtualOrg);
			} else {
				virtualOrgList = this.virtualOrgService.findList(projectId);
			}
			if ((virtualOrgList != null) && (virtualOrgList.size() > 0)) {
				MainInfoVirtualOrgBeanConverter converter = new MainInfoVirtualOrgBeanConverter();
				for (VirtualOrg p : virtualOrgList) {
					mainChartInfoBean.getVirtualOrgList().add(converter.fromEntity(p, this.messageSource, locale));
				}
			}

			// 过滤

			// 完成度
			Calendar date = Calendar.getInstance();
			if (nowYear == null || "".equals(nowYear)) {
				nowYear = String.valueOf(date.get(Calendar.YEAR));
			}

			List<PlanTask> planTaskFinishList = this.planTaskService.findList(projectId, virtualOrg, nowYear);
			

			Map<Integer, Plan> planMap = new HashMap<Integer, Plan>(); // 任务总数
			Map<Integer, Plan> finishPlanMap = new HashMap<Integer, Plan>(); // 完成任务数、出问题任务数
			for (PlanTask task : planTaskFinishList) {
				if (task.getPlanEndDate() != null) {
					Integer month = org.apache.commons.lang3.time.DateUtils.toCalendar(task.getPlanEndDate())
							.get(Calendar.MONTH);
					Plan p = planMap.get(month);
					if (p == null) { // 没有重复月份的数据
						p = new Plan();
						p.setTotalTaskCount(1); // 第一条数据
						p.setTotalFinishTaskCount(0);
						p.setTotalDelayTaskCount(0);
					} else {
						// 有重复月份的数据，+1
						p.setTotalTaskCount(p.getTotalTaskCount() + 1);
					}
					planMap.put(month, p);
				}

				if (task.getActualEndDate() != null) { // 统计已完成和出现问题的任务数
					Integer month = org.apache.commons.lang3.time.DateUtils.toCalendar(task.getActualEndDate())
							.get(Calendar.MONTH);
					Plan p = finishPlanMap.get(month);
					if (p == null) {
						p = new Plan();
						p.setTotalDelayTaskCount(0);
						p.setTotalFinishTaskCount(0);
					}

					if (task.getIssueType() != null) { // 出问题任务
						p.setTotalDelayTaskCount(p.getTotalDelayTaskCount() + 1);
					}

					if (task.getFlowStatus() == 4) { // 已完成任务数
						p.setTotalFinishTaskCount(p.getTotalFinishTaskCount() + 1);
					}
					finishPlanMap.put(month, p);
				}
			}

			MainInfoPlanFinishListBeanConverter converter = new MainInfoPlanFinishListBeanConverter();

			Map<Integer, Plan> monthCountPlan = new HashMap<>();
			for (int i = 0; i < 12; i++) { // 每月数据处理
				Plan p = planMap.get(i);
				Plan finishP = finishPlanMap.get(i);

				if (p == null) { // 当月没有数据
					p = new Plan();
					p.setTotalTaskCount(0);
					p.setTotalDelayTaskCount(0);
					p.setTotalFinishTaskCount(0);
				}

				if (finishP != null) { // 出问题任务数、完成任务数，合并到每月任务总数对象中
					if (finishP.getTotalDelayTaskCount() != null) {
						p.setTotalDelayTaskCount(finishP.getTotalDelayTaskCount());
					}

					if (finishP.getTotalFinishTaskCount() != null) {
						p.setTotalFinishTaskCount(finishP.getTotalFinishTaskCount());
					}
				}

				// 当月数据+上月数据
				if (i > 0) { // 一月份数据不处理
					Plan lastP = monthCountPlan.get(i - 1);
					if (lastP == null) {
						lastP = new Plan();
						lastP.setTotalTaskCount(0);
						lastP.setTotalDelayTaskCount(0);
						lastP.setTotalFinishTaskCount(0);
					}
					p.setTotalTaskCount(p.getTotalTaskCount() + lastP.getTotalTaskCount());
					p.setTotalDelayTaskCount(p.getTotalDelayTaskCount() + lastP.getTotalDelayTaskCount());
					p.setTotalFinishTaskCount(p.getTotalFinishTaskCount() + lastP.getTotalFinishTaskCount());
				}

				mainChartInfoBean.getPlanFinish().add(converter.fromEntity(p, this.messageSource, locale));
				monthCountPlan.put(i, p);
			}
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}

	@RequestMapping("/main-projectCountInfo")
	@ResponseBody
	public JsonResultBean getProjectCountInfo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {
			MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

			List<Project> list = this.projectService.list(null, null);

			int gc_jianyi = 0, gc_keyan = 0, gc_chushe = 0, gc_gaisuan = 0, gc_jianshe = 0, gc_jdx = 0, gc_chuyan = 0,
					gc_zhongyan = 0, gc_yunwei = 0;
			for (Project p : list) {
				// 统计工程类项目总数
				if ("PROJECT_KIND_0".equals(p.getProjectKind().getId())) {
					if ("PROJECT_STATUS_0".equals(p.getProjectStatus().getId())) { // 建议
						gc_jianyi++;
					} else if ("PROJECT_STATUS_1".equals(p.getProjectStatus().getId())) { // 可研
						gc_keyan++;
					} else if ("PROJECT_STATUS_2_0".equals(p.getProjectStatus().getId())) { // 初设
						gc_chushe++;
					} else if ("PROJECT_STATUS_3".equals(p.getProjectStatus().getId())) { // 概算
						gc_gaisuan++;
					} else if ("PROJECT_STATUS_4_0".equals(p.getProjectStatus().getId())) { // 建设
						gc_jianshe++;
					} else if ("PROJECT_STATUS_5".equals(p.getProjectStatus().getId())) { // 阶段性验收
						gc_jdx++;
					} else if ("PROJECT_STATUS_6".equals(p.getProjectStatus().getId())) { // 初验
						gc_chuyan++;
					} else if ("PROJECT_STATUS_7".equals(p.getProjectStatus().getId())) { // 终验
						gc_zhongyan++;
					} else if ("PROJECT_STATUS_8".equals(p.getProjectStatus().getId())) { // 运维
						gc_yunwei++;
					}
				}
			}
			MainInfoProjectCountBean gc = new MainInfoProjectCountBean();
			gc.setJieduan_1(gc_jianyi);
			gc.setJieduan_2(gc_keyan);
			gc.setJieduan_3(gc_chushe);
			gc.setJieduan_4(gc_gaisuan);
			gc.setJieduan_5(gc_jianshe);
			gc.setJieduan_6(gc_jdx);
			gc.setJieduan_7(gc_chuyan);
			gc.setJieduan_8(gc_zhongyan);
			gc.setJieduan_9(gc_yunwei);
			mainChartInfoBean.setGongcheng(gc);

			int ky_jianyi = 0, ky_keyan = 0, ky_fangan = 0, ky_gaisuan = 0, ky_shishi = 0, ky_jdx = 0, ky_chuyan = 0,
					ky_zhongyan = 0, ky_yunwei = 0;
			for (Project p : list) {
				// 科研类项目总数统计
				if ("PROJECT_KIND_1".equals(p.getProjectKind().getId())) {
					if ("PROJECT_STATUS_0".equals(p.getProjectStatus().getId())) { // 建议
						ky_jianyi++;
					} else if ("PROJECT_STATUS_1".equals(p.getProjectStatus().getId())) { // 可研
						ky_keyan++;
					} else if ("PROJECT_STATUS_2_1".equals(p.getProjectStatus().getId())) { // 方案
						ky_fangan++;
					} else if ("PROJECT_STATUS_3".equals(p.getProjectStatus().getId())) { // 概算
						ky_gaisuan++;
					} else if ("PROJECT_STATUS_4_1".equals(p.getProjectStatus().getId())) { // 实施
						ky_shishi++;
					} else if ("PROJECT_STATUS_5".equals(p.getProjectStatus().getId())) { // 阶段性验收
						ky_jdx++;
					} else if ("PROJECT_STATUS_6".equals(p.getProjectStatus().getId())) { // 初验
						ky_chuyan++;
					} else if ("PROJECT_STATUS_7".equals(p.getProjectStatus().getId())) { // 终验
						ky_zhongyan++;
					} else if ("PROJECT_STATUS_8".equals(p.getProjectStatus().getId())) { // 运维
						ky_yunwei++;
					}
				}
			}
			MainInfoProjectCountBean ky = new MainInfoProjectCountBean();
			ky.setJieduan_1(ky_jianyi);
			ky.setJieduan_2(ky_keyan);
			ky.setJieduan_3(ky_fangan);
			ky.setJieduan_4(ky_gaisuan);
			ky.setJieduan_5(ky_shishi);
			ky.setJieduan_6(ky_jdx);
			ky.setJieduan_7(ky_chuyan);
			ky.setJieduan_8(ky_zhongyan);
			ky.setJieduan_9(ky_yunwei);
			mainChartInfoBean.setKeyan(ky);

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}
	

	@RequestMapping("/main-projectCountInfo1")
	@ResponseBody
	public JsonResultBean getProjectCountInfo1(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {
			MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

			List<Project> list = this.projectService.list(null, null);

			int gc_qianqi = 0, gc_jianshe = 0, gc_jdx = 0, gc_chuyan = 0, gc_zhongyan = 0;
			for (Project p : list) {
				// 统计工程类项目总数
				if ("PROJECT_STATUS_0".equals(p.getProjectStatus().getId())) { // 建议
					gc_qianqi++;
				} else if ("PROJECT_STATUS_1".equals(p.getProjectStatus().getId())) { // 可研
					gc_qianqi++;
				} else if ("PROJECT_STATUS_2_0".equals(p.getProjectStatus().getId())) { // 初设
					gc_qianqi++;
				} else if ("PROJECT_STATUS_3".equals(p.getProjectStatus().getId())) { // 概算
					gc_qianqi++;
				} else if ("PROJECT_STATUS_4_0".equals(p.getProjectStatus().getId())) { // 建设
					gc_jianshe++;
				} else if ("PROJECT_STATUS_5".equals(p.getProjectStatus().getId())) { // 阶段性验收
					gc_jdx++;
				} else if ("PROJECT_STATUS_6".equals(p.getProjectStatus().getId())) { // 初验
					gc_chuyan++;
				} else if ("PROJECT_STATUS_7".equals(p.getProjectStatus().getId())) { // 终验
					gc_zhongyan++;
				} else if ("PROJECT_STATUS_8".equals(p.getProjectStatus().getId())) { // 运维
					gc_zhongyan++;
				}
			}
			MainInfoProjectCountBean gc = new MainInfoProjectCountBean();
			gc.setJieduan_1(gc_qianqi);
			gc.setJieduan_2(gc_jianshe);
			gc.setJieduan_3(gc_jdx);
			gc.setJieduan_4(gc_chuyan);
			gc.setJieduan_5(gc_zhongyan);
			mainChartInfoBean.setGongcheng(gc);

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}

	@RequestMapping("/main-viewProject")
	@ResponseBody
	public JsonResultBean viewProject(@RequestParam(required = true, defaultValue = "") String id,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 参数检查
			if (StringUtils.isEmpty(id)) {
				jsonResult.setStatus(JsonStatus.ERROR);
			}

			Project project = this.projectService.find(id);

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT,
					new ProjectBeanConverter().fromEntity(project, this.messageSource, locale));
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}

	@RequestMapping("/main-viewNotice")
	@ResponseBody
	public JsonResultBean viewNotice(@RequestParam(required = true, defaultValue = "") String id,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 参数检查
			if (StringUtils.isEmpty(id)) {
				jsonResult.setStatus(JsonStatus.ERROR);
			}

			NoticeAnnouncement notice = this.noticeAnnouncementService.find(id);

			NoticeAnnouncementBean bean = new NoticeAnnouncementBeanConverter().fromEntity(notice, this.messageSource,
					locale);

			String reciversText = "";
			if (!bean.getRecivers_().equals("")) {
				VirtualOrg virtualOrg = virtualOrgService.find(bean.getRecivers_());
				if (virtualOrg != null) {
					reciversText = virtualOrg.getVirtualOrgName();
				}
			}
			bean.setReciversText(reciversText);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, bean);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}

	/**
	 * 获取用户当前项目的项目角色
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	private List<ProjectPerson> listProjectRole(String userId, String projectId) {

		CommonFilter personFilter = new CommonFilter().addExact("u.id", userId);
		List<Person> person = personService.list(personFilter, null);

		if (person != null && person.size() > 0) {
			CommonFilter projectRoleFilter = new CommonFilter().addExact("project.id", projectId).addExact("person.id",
					person.get(0).getId());
			List<ProjectPerson> ppList = this.projectPersonService.list(projectRoleFilter, null);
			return ppList;
		}
		return new ArrayList<ProjectPerson>();
	}
}
