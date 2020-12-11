package project.edge.web.controller.schedule;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.math.BigDecimal;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.filter.DataFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.GanttCalendarExceptionBean;
import garage.origin.domain.view.GanttDataBean;
import garage.origin.domain.view.GanttLinkBean;
import garage.origin.domain.view.GanttTaskAttachment;
import garage.origin.domain.view.GanttTaskBean;
import garage.origin.domain.view.GanttWorkTimeBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.BasicNetworkTransmitStationTypeEnum;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.DateTypeEnum;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.common.constant.PlanTaskConstraintTypeEnum;
import project.edge.common.constant.PlanTaskTypeEnum;
import project.edge.common.constant.PreTaskTypeEnum;
import project.edge.common.constant.ReportTypeEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.exception.ExcelParseException;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.common.util.MultipartUtils;
import project.edge.domain.business.MultipartWrapper;
import project.edge.domain.converter.ArchiveBeanConverter;
import project.edge.domain.converter.PlanTaskBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.entity.PlanCalendarExceptions;
import project.edge.domain.entity.PlanChange;
import project.edge.domain.entity.PlanChangeTask;
import project.edge.domain.entity.PlanChangeTaskPreTask;
import project.edge.domain.entity.PlanProgress;
import project.edge.domain.entity.PlanProgressTask;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PlanTaskAttachment;
import project.edge.domain.entity.PlanTaskFavorite;
import project.edge.domain.entity.PlanTaskPreTask;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.User;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PlanProgressBean;
import project.edge.domain.view.PlanProgressTaskBean;
import project.edge.domain.view.PlanTaskBean;
import project.edge.service.auth.UserService;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.service.notice.NoticeNotifyService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanCalendarExceptionsService;
import project.edge.service.schedule.PlanCalendarService;
import project.edge.service.schedule.PlanChangeService;
import project.edge.service.schedule.PlanChangeTaskPreTaskService;
import project.edge.service.schedule.PlanChangeTaskService;
import project.edge.service.schedule.PlanProgressService;
import project.edge.service.schedule.PlanProgressTaskService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskFavoriteService;
import project.edge.service.schedule.PlanTaskPreTaskService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.DataOptionService;
import project.edge.service.system.SystemConfigService;
import project.edge.web.apiService.notify.NotifyApiService;
import project.edge.web.apiService.plan.PlanApiService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 计划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/plan-task")
public class PlanTaskController extends TreeGridController<PlanTask, PlanTaskBean> {

	private static final Logger logger = LoggerFactory.getLogger(PlanTaskController.class);

	private static final String PID = "P5001";

	private static final String MAIN_VIEW = "schedule/plantask";

	private static final String KEY_GANTT_DATA = "ganttData";
	private static final String KEY_GANTT_IS_MODIFY = "ganttIsModify";
	private static final String KEY_GANTT_SELECTED = "selectedTask";
	private static final String KEY_PLANTASK_TITLE = "plantaskTitleExtend";
	
	@Resource
	private SystemConfigService systemConfigService;

	@Resource
	private PlanService planService;
	
	@Resource
	private SiteService siteService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private NoticeNotifyService notifyService;

	@Resource
	private PlanTaskService planTaskService;

	@Resource
	private PlanTaskPreTaskService planTaskPreTaskService;

	@Resource
	private PlanTaskFavoriteService planTaskFavoriteService;

	@Resource
	private PlanCalendarService planCalendarService;

	@Resource
	private PlanCalendarExceptionsService planCalendarExceptionsService;

	@Resource
	private PersonService personService;
	
	@Resource
	private ProjectService projectService;

	@Resource
	private ProjectPersonService projectPersonService;

	@Resource
	private PlanChangeService planChangeService;

	@Resource
	private PlanChangeTaskService planChangeTaskService;

	@Resource
	private PlanChangeTaskPreTaskService planChangeTaskPreTaskService;

	@Resource
	private PlanProgressService planProgressService;

	@Resource
	private PlanProgressTaskService planProgressTaskService;

	@Resource
	private DataOptionService dataOptionService;

	@Resource
	private VirtualOrgService virtualOrgService;
	
	@Resource
	private NotifyApiService notifApiService;
	
	@Resource
	private PlanApiService planApiService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PLAN_TASK.value();
	}

	@Override
	protected Service<PlanTask, String> getDataService() {
		return this.planTaskService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<PlanTask, PlanTaskBean> getViewConverter() {
		return new PlanTaskBeanConverter();
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

		String planId = paramMap.get("planId");
		String taskId = paramMap.get("taskId");
		String planChangeId = paramMap.get("planChangeId");
		String isModify = paramMap.get("isModify");

		GanttDataBean ganttDataBean = new GanttDataBean();

		String json = "{}";
		
		// 如果是比较进度，需要把当前任务和变更的任务合并并返回
		if ("6".equals(isModify)) {
			
			Person person = this.personService.find(userBean.getSessionUserId());
			PlanChange planChange = planChangeService.find(planChangeId);

			GanttDataBean ganttDataBeanNext = new GanttDataBean();
			GanttDataBean ganttDataBeanPrev = new GanttDataBean();
			
			this.loadGanttDataFromDBForPlanChange(planChangeId, isModify, ganttDataBeanNext);
			this.loadGanttDataFromDB(planChange.getPlan().getId(), isModify, ganttDataBeanPrev, person);
			
			ganttDataBean = this.mergeGanttDataBeanForCompare(ganttDataBeanPrev, ganttDataBeanNext);
		}
		else if ("4".equals(isModify) || "5".equals(isModify)) {
			this.loadGanttDataFromDBForPlanChange(planId, isModify, ganttDataBean);
		} else {
			Person person = this.personService.find(userBean.getSessionUserId());
			this.loadGanttDataFromDB(planId, isModify, ganttDataBean, person);
		}

		try {
			json = new ObjectMapper().writeValueAsString(ganttDataBean);
		} catch (JsonProcessingException e) {
			logger.error("While parse gantt data.", e);
		}

		model.addAttribute(KEY_GANTT_SELECTED, taskId);

		model.addAttribute(KEY_GANTT_IS_MODIFY, isModify);
		model.addAttribute(KEY_GANTT_DATA, json);

		Plan plan = this.planService.find(planId);
		String plantaskTitleExtend = "";
		if (plan != null) {
			plantaskTitleExtend = " - " + plan.getPlanName();
			if (!StringUtils.isEmpty(plan.getPlanVersion())) {
				plantaskTitleExtend = plantaskTitleExtend + "(" + plan.getPlanVersion() + ")";
			}
		}
		model.addAttribute(KEY_PLANTASK_TITLE, plantaskTitleExtend);

		return MAIN_VIEW;
	}
	
	
	private GanttDataBean mergeGanttDataBeanForCompare(GanttDataBean prev, GanttDataBean next) {
		
		GanttDataBean compare = prev;
		
		List<GanttTaskBean> prevTaskList = prev.getTaskBeans();
		List<GanttTaskBean> nextTaskList = next.getTaskBeans();
		
		for (int i = 0; i < prevTaskList.size(); i++) {
			
			GanttTaskBean prevTask = prevTaskList.get(i);
			GanttTaskBean nextTask = null;
			for (int j = 0; j < nextTaskList.size(); j++) {
				if (prevTask.getId().equals(nextTaskList.get(j).getPlanTaskId())) {
					nextTask = nextTaskList.get(j);
					break;
				}
			}
			
			// 如果不存在同ID的任务，说明该任务已经被删除
			if (nextTask == null) {
				prevTask.setCompareStatus(3);
			} 
			// 比较判断该任务是否已修改
			else {
				prevTask = this.compareTask(prevTask, nextTask);
				
				// 从被比较的列表中删除已比较的任务
				nextTaskList.remove(nextTask);
			}
		}
		
		// 如果被比较的列表长度仍大于0 ，说明剩余的都是新增的任务
		if (nextTaskList.size() > 0) {
			for (int i = 0; i < nextTaskList.size(); i++) {
				GanttTaskBean task = nextTaskList.get(i);
				task.setCompareStatus(2);
				prevTaskList.add(task);
			}
		}
		
		// TODO 根据wbs，重新排序所有的任务
		
		
		return compare;
	}
	
	/**
	 * 比较两个任务是否有修改
	 * @param prev
	 * @param next
	 * @return
	 */
	private GanttTaskBean compareTask(GanttTaskBean prev, GanttTaskBean next) {
		
		// 直接保存返回，前台去比较是否有变更及变更内容
		prev.setNextBean(next);
		return prev;
	}

	private void loadGanttDataFromDBForPlanChange(String planId, String isModify, GanttDataBean ganttDataBean) {

		if (!StringUtils.isEmpty(planId)) {

			PlanChange plan = this.planChangeService.find(planId);

			if (plan != null) {

				ganttDataBean.setId(planId);

				CommonFilter filter = new CommonFilter();
				filter.addExact("planChange.id", planId);

				List<OrderByDto> orders = new ArrayList<>();
				orders.add(new OrderByDto("taskNum", false));

				List<PlanChangeTask> planTasks = this.planChangeTaskService.list(filter, orders);

				List<PlanChangeTaskPreTask> planTaskPreTasks = this.planChangeTaskPreTaskService.list(filter, null);

				PlanCalendar calendar = null;
				List<PlanCalendarExceptions> exceptions = new ArrayList<>();

				if (plan.getPlanCalendar() != null) {

					calendar = this.planCalendarService.find(plan.getPlanCalendar().getId());

					if (calendar != null) {
						CommonFilter calendarFilter = new CommonFilter();

						calendarFilter.addExact("calendar.id", calendar.getId());
						List<OrderByDto> cOrders = new ArrayList<>();
						cOrders.add(new OrderByDto("startDate", false));

						exceptions = this.planCalendarExceptionsService.list(calendarFilter, cOrders);
					}
				}

				this.generateGanttDataForPlanChange(ganttDataBean, planTasks, planTaskPreTasks, calendar, exceptions,
						isModify);
			}
		}
	}

	private void generateGanttDataForPlanChange(GanttDataBean ganttDataBean, List<PlanChangeTask> planTasks,
			List<PlanChangeTaskPreTask> planTaskPreTasks, PlanCalendar calendar,
			List<PlanCalendarExceptions> exceptions, String isModify) {

		// default
		boolean editable = false;

		ganttDataBean.getOptionsMap().put("leaderOption", this.generateLeaderCombo());
		ganttDataBean.getOptionsMap().put("issueOption", this.generateIssueCombo());

		if (!StringUtils.isEmpty(isModify) && OnOffEnum.ON.value().equals(Short.valueOf(isModify))) {
			editable = true;
		}

		if (planTasks.size() > 0) {
			for (PlanChangeTask planTask : planTasks) {
				GanttTaskBean taskBean = new GanttTaskBean();

				taskBean.setId(planTask.getId());
				taskBean.setPlanTaskId(planTask.getPlanTask() != null ? planTask.getPlanTask().getId() : "");

				taskBean.setText(planTask.getTaskName());
				taskBean.setTaskType(planTask.getTaskType());

				taskBean.setStart_date(
						DateUtils.date2String(planTask.getPlanStartDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
				taskBean.setEnd_date(
						DateUtils.date2String(planTask.getPlanEndDate(), Constants.SIMPLE_DATE_TIME_FORMAT));

				taskBean.setDuration(planTask.getDurationDays());

				// 数据库保存 66.66 前台显示需要 0.6666
				// if (planTask.getProgress() != null) {
				// taskBean.setProgress(planTask.getProgress() / 100);
				// }

				if (planTask.getLeader() != null) {
					taskBean.setLeader(planTask.getLeader().getId());
				}

				if (planTask.getParticipantNameList() != null) {
					taskBean.setParticipantName(planTask.getParticipantNameList());
				}
				
				if (planTask.getParticipantList() != null) {
					taskBean.setParticipantId(planTask.getParticipantList());
				}
				
				// 添加计划交付物和实际交付物
				taskBean.setAchievement(planTask.getAchievement());
				taskBean.setDelivery(planTask.getDelivery());

				if (OnOffEnum.ON.value().equals(planTask.getIsSummary())
						&& OnOffEnum.ON.value().equals(planTask.getIsMilestone())) {
					taskBean.setType(PlanTaskTypeEnum.PROJECT.value());
				} else if (OnOffEnum.ON.value().equals(planTask.getIsSummary())) {
					taskBean.setType(PlanTaskTypeEnum.PROJECT.value());
				} else if (OnOffEnum.ON.value().equals(planTask.getIsMilestone())) {
					taskBean.setType(PlanTaskTypeEnum.MILESTONE.value());
				} else if (OnOffEnum.ON.value().equals(planTask.getIsCritical())) {
					taskBean.setType(PlanTaskTypeEnum.CRITICAL_TASK.value());
				} else if (OnOffEnum.ON.value().equals(planTask.getIsSiteTask())) {
					taskBean.setType(PlanTaskTypeEnum.SITE_TASK.value());
				} else {
					taskBean.setType(PlanTaskTypeEnum.TASK.value());
				}

				taskBean.setOpen(false);

				if (PlanTaskConstraintTypeEnum.EARLY.value().equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.EARLY.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.LATE.value().equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.LATE.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.MUST_START.value().equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.MUST_START.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.MUST_FINISH.value().equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.MUST_FINISH.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.value()
						.equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_START.value().equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_LATER_TO_START.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.value()
						.equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.ganttValue());
				} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.value().equals(planTask.getConstraintType())) {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.ganttValue());
				} else {
					taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.EARLY.ganttValue());
				}

				if (planTask.getConstraintDate() != null) {
					taskBean.setConstraint_date(
							DateUtils.date2String(planTask.getConstraintDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
				}

				if (planTask.getDeadlineDate() != null) {
					taskBean.setDeadline_date(
							DateUtils.date2String(planTask.getDeadlineDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
				}

				taskBean.setIsSummary(planTask.getIsSummary());
				taskBean.setIsMilestone(planTask.getIsMilestone());
				taskBean.setIsCritical(planTask.getIsCritical());
				taskBean.setIsSiteTask(planTask.getIsSiteTask());

				if (planTask.getWorkload() != null) {
					taskBean.setWorkload(planTask.getWorkload());
				}

				if (planTask.getWeight() != null) {
					taskBean.setWeight(planTask.getWeight());
				}

				taskBean.setParent(planTask.getPid());

				taskBean.setEditable(editable);
				// 设置关联日历
				taskBean.setCalendar_id(calendar.getId());

				// 设置进度上报相关
				// if (planTask.getActualStartDate() != null) {
				// taskBean.setActualStartDate(DateUtils.date2String(planTask.getActualStartDate(),
				// Constants.DATE_FORMAT));
				// }
				//
				// if (planTask.getActualEndDate() != null) {
				// taskBean.setActualEndDate(DateUtils.date2String(planTask.getActualEndDate(),
				// Constants.DATE_FORMAT));
				// }
				//
				// taskBean.setCumulativeProgress(planTask.getCumulativeProgress());
				// taskBean.setCurrentWeekProgress(planTask.getCurrentWeekProgress());
				// taskBean.setDescription(planTask.getDescription());
				// taskBean.setNextWeekGoals(planTask.getNextWeekGoals());
				// taskBean.setFlowStatus(planTask.getFlowStatus());

				taskBean.setRemark(planTask.getRemark());

				ganttDataBean.getTaskBeans().add(taskBean);
			}
		}

		if (planTaskPreTasks.size() > 0) {

			for (PlanChangeTaskPreTask preTask : planTaskPreTasks) {
				GanttLinkBean linkBean = new GanttLinkBean();

				linkBean.setId(preTask.getId());
				linkBean.setLag(preTask.getLagDays());
				linkBean.setSource(preTask.getPreTask().getId());
				linkBean.setTarget(preTask.getPlanChangeTask().getId());

				if (PreTaskTypeEnum.FINISH_START.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.FINISH_START.ganttValue());
				} else if (PreTaskTypeEnum.START_START.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.START_START.ganttValue());
				} else if (PreTaskTypeEnum.FINISH_FINISH.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.FINISH_FINISH.ganttValue());
				} else if (PreTaskTypeEnum.START_FINISH.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.START_FINISH.ganttValue());
				}

				ganttDataBean.getLinkBeans().add(linkBean);
			}
		}

		if (calendar != null) {

			GanttWorkTimeBean workTimeBean = new GanttWorkTimeBean();

			workTimeBean.setId(calendar.getId());
			// gantt_work_day from 0 - Sunday, to 6 - Saturday
			Integer[] workTime = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };

			// 1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
			if (!StringUtils.isEmpty(calendar.getWeekdays())) {
				String[] dayArr = StringUtils.commaDelimitedListToStringArray(calendar.getWeekdays());

				if (dayArr.length > 0) {

					for (String day : dayArr) {

						int _tempIndex = Integer.valueOf(day);

						if (7 == _tempIndex) {
							_tempIndex = 0;
						}

						workTime[_tempIndex] = 1;
					}
				}
			}
			workTimeBean.setWorkTimes(workTime);
			if (exceptions.size() > 0) {

				for (PlanCalendarExceptions calendarExceptions : exceptions) {
					GanttCalendarExceptionBean calendarExceptionBean = new GanttCalendarExceptionBean();

					calendarExceptionBean.setId(calendarExceptions.getId());
					calendarExceptionBean.setExceptionName(calendarExceptions.getExceptionName());
					calendarExceptionBean.setIsWorkday(calendarExceptions.getIsWorkday());

					calendarExceptionBean.setStartDate(
							DateUtils.date2String(calendarExceptions.getStartDate(), Constants.DATE_FORMAT));
					calendarExceptionBean
							.setEndDate(DateUtils.date2String(calendarExceptions.getEndDate(), Constants.DATE_FORMAT));

					ganttDataBean.getCalendarExceptionBeans().add(calendarExceptionBean);
				}
			}

			ganttDataBean.setWorkTimeBean(workTimeBean);
		}

	}

	// 从数据库获取数据，并解析成dhtml格式的gantt数据
	private void loadGanttDataFromDB(String planId, String isModify, GanttDataBean ganttDataBean, Person loginPerson) {
		if (!StringUtils.isEmpty(planId)) {

			Plan plan = this.planService.find(planId);

			if (plan != null) {

				ganttDataBean.setId(planId);

				CommonFilter filter = new CommonFilter();
				filter.addExact("plan.id", planId);

				List<OrderByDto> orders = new ArrayList<>();
				orders.add(new OrderByDto("taskNum", false));

				List<PlanTask> planTasks = this.planTaskService.list(filter, orders);
				List<PlanProgressTask> planProgressTasks = new ArrayList<>();
				
				// 找出最近提交的进度报告中包含的计划任务
				CommonFilter planProgressFilter = new CommonFilter();
				planProgressFilter.addExact("plan.id", planId);
				planProgressFilter.addExact("applicant.id", loginPerson.getId());
				List<OrderByDto> planProgressOrders = new ArrayList<>();
				planProgressOrders.add(new OrderByDto("mDatetime", false));
				List<PlanProgress> planProgressList = this.planProgressService.list(planProgressFilter, planProgressOrders);
				
				if (planProgressList.size() > 0) {
					
					CommonFilter planProgressTaskFilter = new CommonFilter();
					planProgressTaskFilter.addExact("plan.id", planId);
					PlanProgress latestPlanProgress = planProgressList.get(planProgressList.size() - 1);
					planProgressTaskFilter.addExact("planProgress.id", latestPlanProgress.getId());
					
					planProgressTasks = this.planProgressTaskService.list(planProgressTaskFilter, null);
				}
				
				List<PlanTaskPreTask> planTaskPreTasks = this.planTaskPreTaskService.list(filter, null);

				PlanCalendar calendar = null;
				List<PlanCalendarExceptions> exceptions = new ArrayList<>();

				if (plan.getPlanCalendar() != null) {

					calendar = this.planCalendarService.find(plan.getPlanCalendar().getId());

					if (calendar != null) {
						CommonFilter calendarFilter = new CommonFilter();

						calendarFilter.addExact("calendar.id", calendar.getId());
						List<OrderByDto> cOrders = new ArrayList<>();
						cOrders.add(new OrderByDto("startDate", false));

						exceptions = this.planCalendarExceptionsService.list(calendarFilter, cOrders);
					}
				}

				this.generateGanttData(ganttDataBean, planTasks, planProgressTasks, planTaskPreTasks, calendar, exceptions, isModify);
			}
		}
	}

	private void generateGanttData(GanttDataBean ganttDataBean, List<PlanTask> planTasks, List<PlanProgressTask> planProgressTasks,
			List<PlanTaskPreTask> planTaskPreTasks, PlanCalendar calendar, List<PlanCalendarExceptions> exceptions,
			String isModify) {

		// default
		boolean editable = false;

		ganttDataBean.getOptionsMap().put("leaderOption", this.generateLeaderCombo());
		ganttDataBean.getOptionsMap().put("issueOption", this.generateIssueCombo());

		if (!StringUtils.isEmpty(isModify) && OnOffEnum.ON.value().equals(Short.valueOf(isModify))) {
			editable = true;
		}

		if (planTasks.size() > 0) {
			for (PlanTask planTask : planTasks) {
				GanttTaskBean taskBean = this.generateGanttTask(calendar, editable, planTask, planProgressTasks);
				ganttDataBean.getTaskBeans().add(taskBean);
			}
		}

		if (planTaskPreTasks.size() > 0) {

			for (PlanTaskPreTask preTask : planTaskPreTasks) {
				GanttLinkBean linkBean = new GanttLinkBean();

				linkBean.setId(preTask.getId());
				linkBean.setLag(preTask.getLagDays());
				linkBean.setSource(preTask.getPreTask().getId());
				linkBean.setTarget(preTask.getPlanTask().getId());

				if (PreTaskTypeEnum.FINISH_START.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.FINISH_START.ganttValue());
				} else if (PreTaskTypeEnum.START_START.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.START_START.ganttValue());
				} else if (PreTaskTypeEnum.FINISH_FINISH.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.FINISH_FINISH.ganttValue());
				} else if (PreTaskTypeEnum.START_FINISH.value().equals(preTask.getPreTaskType())) {
					linkBean.setType(PreTaskTypeEnum.START_FINISH.ganttValue());
				}

				ganttDataBean.getLinkBeans().add(linkBean);
			}
		}

		if (calendar != null) {

			GanttWorkTimeBean workTimeBean = new GanttWorkTimeBean();

			workTimeBean.setId(calendar.getId());
			// gantt_work_day from 0 - Sunday, to 6 - Saturday
			Integer[] workTime = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };

			// 1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
			if (!StringUtils.isEmpty(calendar.getWeekdays())) {
				String[] dayArr = StringUtils.commaDelimitedListToStringArray(calendar.getWeekdays());

				if (dayArr.length > 0) {

					for (String day : dayArr) {

						int _tempIndex = Integer.valueOf(day);

						if (7 == _tempIndex) {
							_tempIndex = 0;
						}

						workTime[_tempIndex] = 1;
					}
				}
			}
			workTimeBean.setWorkTimes(workTime);
			if (exceptions.size() > 0) {

				for (PlanCalendarExceptions calendarExceptions : exceptions) {
					GanttCalendarExceptionBean calendarExceptionBean = new GanttCalendarExceptionBean();

					calendarExceptionBean.setId(calendarExceptions.getId());
					calendarExceptionBean.setExceptionName(calendarExceptions.getExceptionName());
					calendarExceptionBean.setIsWorkday(calendarExceptions.getIsWorkday());

					calendarExceptionBean.setStartDate(
							DateUtils.date2String(calendarExceptions.getStartDate(), Constants.DATE_FORMAT));
					calendarExceptionBean
							.setEndDate(DateUtils.date2String(calendarExceptions.getEndDate(), Constants.DATE_FORMAT));

					ganttDataBean.getCalendarExceptionBeans().add(calendarExceptionBean);
				}
			}

			ganttDataBean.setWorkTimeBean(workTimeBean);
		}
	}

	private GanttTaskBean generateGanttTask(PlanCalendar calendar, boolean editable, PlanTask planTask, List<PlanProgressTask> planProgressTasks) {
		GanttTaskBean taskBean = new GanttTaskBean();

		taskBean.setId(planTask.getId());

		taskBean.setText(planTask.getTaskName());
		taskBean.setPlanTaskId(planTask.getId());

		taskBean.setTaskType(planTask.getTaskType());
		taskBean.setAchievement(planTask.getAchievement());
		taskBean.setDelivery(planTask.getDelivery());

		taskBean.setStart_date(DateUtils.date2String(planTask.getPlanStartDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
		taskBean.setEnd_date(DateUtils.date2String(planTask.getPlanEndDate(), Constants.SIMPLE_DATE_TIME_FORMAT));

		taskBean.setDuration(planTask.getDurationDays());
		
		for (PlanProgressTask ppTask: planProgressTasks) {
			if (ppTask.getPlanTask().getId().equals(planTask.getId())) {
				taskBean.setHasProgress(true);
				break;
			}
		}
		
		for (PlanTaskAttachment attachment: planTask.getAttachments()) {
			Archive archive = attachment.getArchive();
			GanttTaskAttachment ganttAttachment = new GanttTaskAttachment();
			try {
				BeanUtils.copyProperties(ganttAttachment, archive);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			taskBean.getAttachmentList().add(ganttAttachment);
		}

		// 数据库保存 66.66 前台显示需要 0.6666
		if (planTask.getProgress() != null) {
			taskBean.setProgress(planTask.getProgress());
		}

		if (planTask.getLastProgress() != null) {
			taskBean.setLastProgress(planTask.getLastProgress());
		}

		if (planTask.getLeader() != null) {
			taskBean.setLeader(planTask.getLeader().getId());
		}

		if (planTask.getParticipantNameList() != null) {
			taskBean.setParticipantName(planTask.getParticipantNameList());
		}
		if (planTask.getParticipantList() != null) {
			taskBean.setParticipantId(planTask.getParticipantList());
		}

		if (OnOffEnum.ON.value().equals(planTask.getIsSummary())
				&& OnOffEnum.ON.value().equals(planTask.getIsMilestone())) {
			taskBean.setType(PlanTaskTypeEnum.PROJECT.value());
		} else if (OnOffEnum.ON.value().equals(planTask.getIsSummary())) {
			taskBean.setType(PlanTaskTypeEnum.PROJECT.value());
		} else if (OnOffEnum.ON.value().equals(planTask.getIsMilestone())) {
			taskBean.setType(PlanTaskTypeEnum.MILESTONE.value());
		} else if (OnOffEnum.ON.value().equals(planTask.getIsCritical())) {
			taskBean.setType(PlanTaskTypeEnum.CRITICAL_TASK.value());
		} else if (OnOffEnum.ON.value().equals(planTask.getIsSiteTask())) {
			taskBean.setType(PlanTaskTypeEnum.SITE_TASK.value());
		} else {
			taskBean.setType(PlanTaskTypeEnum.TASK.value());
		}

		taskBean.setOpen(false);

		if (PlanTaskConstraintTypeEnum.EARLY.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.EARLY.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.LATE.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.LATE.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.MUST_START.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.MUST_START.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.MUST_FINISH.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.MUST_FINISH.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_START.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_LATER_TO_START.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.ganttValue());
		} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.value().equals(planTask.getConstraintType())) {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.ganttValue());
		} else {
			taskBean.setConstraint_type(PlanTaskConstraintTypeEnum.EARLY.ganttValue());
		}

		if (planTask.getConstraintDate() != null) {
			taskBean.setConstraint_date(
					DateUtils.date2String(planTask.getConstraintDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
		}

		if (planTask.getDeadlineDate() != null) {
			taskBean.setDeadline_date(
					DateUtils.date2String(planTask.getDeadlineDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
		}

		taskBean.setIsSummary(planTask.getIsSummary());
		taskBean.setIsMilestone(planTask.getIsMilestone());
		taskBean.setIsCritical(planTask.getIsCritical());
		taskBean.setIsSiteTask(planTask.getIsSiteTask());

		if (planTask.getWorkload() != null) {
			taskBean.setWorkload(planTask.getWorkload());
		}

		if (planTask.getWeight() != null) {
			taskBean.setWeight(planTask.getWeight());
		}

		taskBean.setParent(planTask.getPid());

		taskBean.setEditable(editable);
		// 设置关联日历
		taskBean.setCalendar_id(calendar.getId());

		// 设置进度上报相关
		if (planTask.getActualStartDate() != null) {
			taskBean.setActualStartDate(DateUtils.date2String(planTask.getActualStartDate(), Constants.DATE_FORMAT));
		}

		if (planTask.getActualEndDate() != null) {
			taskBean.setActualEndDate(DateUtils.date2String(planTask.getActualEndDate(), Constants.DATE_FORMAT));
		}

		taskBean.setCumulativeProgress(planTask.getCumulativeProgress());
		taskBean.setCurrentWeekProgress(planTask.getCurrentWeekProgress());
		taskBean.setDescription(planTask.getDescription());
		taskBean.setNextWeekGoals(planTask.getNextWeekGoals());
		taskBean.setFlowStatus(planTask.getFlowStatus());

		taskBean.setRemark(planTask.getRemark());
		taskBean.setIssueType(planTask.getIssueType());
		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
		CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
		List<DataOption> list = this.dataOptionService.list(f, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value())) {
				if (o.getId().equals(taskBean.getIssueType())) {
					taskBean.setIssueTypeText(o.getOptionName());
				}
			}
		}
		return taskBean;
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
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);

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
	public JsonResultBean create(@ModelAttribute PlanTaskBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.create(bean, null, userBean, locale);
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
	public JsonResultBean update(@ModelAttribute PlanTaskBean bean,
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
		
		JsonResultBean result =  super.delete(idsToDelete, null, userBean, locale);
		
		// 重新生成站点的进展计划
		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToDelete);
		if (idArray.length > 0) {
			PlanTask planTask = planTaskService.find(idArray[0]);
			if (planTask != null) {
				Plan plan = planService.find(planTask.getPlan().getId());
				planApiService.updateSiteProgress(plan.getSite().getId());
			}
		}
        
		return result;
	}

	@RequestMapping("/save-gantt-data")
	@ResponseBody
	public JsonResultBean saveGanttData(@RequestBody GanttDataBean ganttDataBean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			Plan plan = this.planService.find(ganttDataBean.getId());

			if (plan != null) {

				Date now = new Date();

				List<PlanTask> planTasks = new ArrayList<>();
				List<PlanTaskPreTask> planTaskPreTasks = new ArrayList<>();

				if (!ganttDataBean.getTaskBeans().isEmpty()) {
					for (GanttTaskBean taskBean : ganttDataBean.getTaskBeans()) {

						PlanTask planTask = this.generatePlanTask(taskBean, plan, now, userBean);
						planTasks.add(planTask);
					}
				}

				if (!ganttDataBean.getLinkBeans().isEmpty()) {

					for (GanttLinkBean linkBean : ganttDataBean.getLinkBeans()) {

						PlanTaskPreTask preTask = this.generatePreTask(linkBean, plan);
						planTaskPreTasks.add(preTask);
					}
				}

				this.planTaskService.createTasksAndLinks(plan.getId(), planTasks, planTaskPreTasks);
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
		} catch (Exception e) {
			getLogger().error("Exception save gantt data.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}
	
	
	@RequestMapping("/upload-gantt-data-attachment")
	@ResponseBody
	public JsonResultBean uploadGanttDataAttachment(HttpServletResponse response, HttpServletRequest request, 
			@RequestParam String planTaskIds, @RequestParam("files") MultipartFile[] files,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
		
		try {
			
			System.out.println(files.length);
			
			// 取得任务ID和对应的上传物列表
			String[] taskIds = planTaskIds.split(",");
			List<MultipartWrapper> multipartList = MultipartUtils.parseMultipart(files);
			List<MultipartWrapper> uploadedList = new ArrayList<MultipartWrapper>();
			List<GanttTaskBean> updatedGanttTaskBeanList = new ArrayList<GanttTaskBean>();
			
			// 将上传物分类保存（一个任务可能有多个上传附件）
			for (int i = 0; i < taskIds.length; i++) {
				
				String planTaskId = taskIds[i];
				PlanTask planTask = planTaskService.find(planTaskId);
				
				// 找到任务对应的附件（根据任务名）  TODO: 处理待优化，有点性能损失
				List<ArchiveBean> taskArchiveList = new ArrayList<>();
				for (MultipartWrapper wrapper: multipartList) {
					ArchiveBean archive = wrapper.getArchiveBean();
					if (archive.getArchiveName().indexOf(planTask.getWbs() + "-" + planTask.getTaskName()) == 0) {
						taskArchiveList.add(archive);
						uploadedList.add(wrapper);
					}
				}
				
				ArchiveBeanConverter archiveConverter = new ArchiveBeanConverter();
				for (ArchiveBean archive: taskArchiveList) {
					planTask.getAttachments().add(((PlanTaskBeanConverter) this.getViewConverter()).toAttachmentEntity(archive, archiveConverter, planTask, 1, userBean, new Date()));
				}
				
				// 更新Entity
				if (taskArchiveList.size() > 0) {
					planTaskService.update(planTask);
					
					PlanTask entity = planTaskService.find(planTask.getId());
					CommonFilter filter = new CommonFilter();
					filter.addExact("plan.id", entity.getPlan().getId());
					List<PlanProgressTask> planProgressTasks = this.planProgressTaskService.list(filter, null);
					
					GanttTaskBean ganttTaskBean = this.generateGanttTask(entity.getPlan().getPlanCalendar(), false,
							planTask, planProgressTasks);
					updatedGanttTaskBeanList.add(ganttTaskBean);
				}
			}
			
			// 保存文件到本地
			if (uploadedList.size() > 0) {
				String rootDir = systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
				MultipartUtils.saveMultipartToLocal(multipartList, rootDir);
				jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, updatedGanttTaskBeanList);
			}
			
			
		} catch (Exception e) {
            logger.error("Exception while upload file.", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }
		
		return jsonResult;
	}

	@RequestMapping("/save-gantt-data-plan-change")
	@ResponseBody
	public JsonResultBean saveGanttDataForPlanChange(@RequestBody GanttDataBean ganttDataBean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			PlanChange plan = this.planChangeService.find(ganttDataBean.getId());

			if (plan != null) {

				Date now = new Date();

				List<PlanChangeTask> planTasks = new ArrayList<>();
				List<PlanChangeTaskPreTask> planTaskPreTasks = new ArrayList<>();

				if (!ganttDataBean.getTaskBeans().isEmpty()) {
					for (GanttTaskBean taskBean : ganttDataBean.getTaskBeans()) {
						PlanTask planTask = planTaskService.find(taskBean.getPlanTaskId());
						PlanChangeTask planChangeTask = this.generatePlanChangeTask(taskBean, plan, now, userBean, 
								plan.getPlan(), planTask);
						planTasks.add(planChangeTask);
					}
				}

				if (!ganttDataBean.getLinkBeans().isEmpty()) {

					for (GanttLinkBean linkBean : ganttDataBean.getLinkBeans()) {

						PlanChangeTaskPreTask preTask = this.generateChangePreTask(linkBean, plan);
						planTaskPreTasks.add(preTask);
					}
				}

				this.planChangeTaskService.createTasksAndLinks(plan.getId(), planTasks, planTaskPreTasks);
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
		} catch (Exception e) {
			getLogger().error("Exception save gantt data.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	private PlanChangeTaskPreTask generateChangePreTask(GanttLinkBean bean, PlanChange plan) {
		PlanChangeTaskPreTask preTask = new PlanChangeTaskPreTask();

		preTask.setId(bean.getId());
		// 默认设置0
		preTask.setLagDays(bean.getLag() == null ? 0 : bean.getLag());

		preTask.setPlanChange(plan);

		PlanChangeTask sourceTask = new PlanChangeTask();
		sourceTask.setId(bean.getSource());

		PlanChangeTask targetTask = new PlanChangeTask();
		targetTask.setId(bean.getTarget());

		preTask.setPreTask(sourceTask);
		preTask.setPlanChangeTask(targetTask);

		if (PreTaskTypeEnum.FINISH_START.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.FINISH_START.value());
		} else if (PreTaskTypeEnum.START_START.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.START_START.value());
		} else if (PreTaskTypeEnum.FINISH_FINISH.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.FINISH_FINISH.value());
		} else if (PreTaskTypeEnum.START_FINISH.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.START_FINISH.value());
		}

		return preTask;
	}

	private PlanChangeTask generatePlanChangeTask(GanttTaskBean bean, PlanChange plan, Date now,
			SessionUserBean userBean, Plan historyPlan, PlanTask planTask) throws ParseException {
		PlanChangeTask task = new PlanChangeTask();

		task.setPlan(historyPlan);
		task.setcDatetime(now);
		task.setmDatetime(now);
		task.setCreator(userBean.getSessionLoginName());
		task.setModifier(userBean.getSessionLoginName());

		task.setId(bean.getId());
		task.setPlanChange(plan);
		task.setPlanTask(planTask);

		task.setTaskNum(bean.getTaskNum());
		task.setPid(bean.getParent());
		task.setTaskLayer(bean.getTaskLayer());
		task.setTaskName(bean.getText());
		task.setTaskType(bean.getTaskType());
		task.setDurationDays(bean.getDuration());
		task.setWorkload(bean.getWorkload());
		task.setWeight(bean.getWeight());
		task.setWbs(bean.getWbs());
		task.setPlanStartDate(DateUtils.string2Date(bean.getStart_date(), Constants.DATE_FORMAT));
		task.setPlanEndDate(DateUtils.string2Date(bean.getEnd_date(), Constants.DATE_FORMAT));
		
		task.setParticipantList(bean.getParticipantId());
		task.setParticipantNameList(bean.getParticipantName());
		
		task.setAchievement(bean.getAchievement());
		task.setDelivery(bean.getDelivery());

		if (!StringUtils.isEmpty(bean.getLeader())) {
			Person person = new Person();
			person.setId(bean.getLeader());
			task.setLeader(person);
		}

		task.setIsSummary(bean.getIsSummary());
		task.setIsMilestone(bean.getIsMilestone());
		task.setIsCritical(bean.getIsCritical());
		task.setIsSiteTask(bean.getIsSiteTask());

		if (!StringUtils.isEmpty(bean.getConstraint_type())) {
			if (PlanTaskConstraintTypeEnum.EARLY.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.EARLY.value());
			} else if (PlanTaskConstraintTypeEnum.LATE.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.LATE.value());
			} else if (PlanTaskConstraintTypeEnum.MUST_START.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.MUST_START.value());
			} else if (PlanTaskConstraintTypeEnum.MUST_FINISH.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.MUST_FINISH.value());
			} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.value());
			} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_START.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_LATER_TO_START.value());
			} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.value());
			} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.value());
			}
		}

		if (!StringUtils.isEmpty(bean.getConstraint_date())) {
			task.setConstraintDate(DateUtils.string2Date(bean.getConstraint_date(), Constants.DATE_FORMAT));
		}

		if (!StringUtils.isEmpty(bean.getDeadline_date())) {
			task.setDeadlineDate(DateUtils.string2Date(bean.getDeadline_date(), Constants.DATE_FORMAT));
		}

		task.setRemark(bean.getRemark());

		return task;
	}

	@RequestMapping("/save-calendar-data")
	@ResponseBody
	public JsonResultBean saveCalendarData(@RequestBody GanttDataBean ganttDataBean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			if (ganttDataBean.getWorkTimeBean() != null) {

				Date now = new Date();

				PlanCalendar planCalendar = this.planCalendarService.find(ganttDataBean.getWorkTimeBean().getId());

				if (planCalendar != null) {
					planCalendar.setModifier(userBean.getSessionLoginName());
					planCalendar.setmDatetime(now);

					List<Integer> workTimeResult = new ArrayList<>();

					// gantt_work_day from 0 - Sunday, to 6 - Saturday
					Integer[] workTime = new Integer[] { 6, 1, 2, 3, 4, 5, 7 };

					// 1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
					Integer[] workTimeArr = ganttDataBean.getWorkTimeBean().getWorkTimes();

					if (workTimeArr.length != workTime.length) {
						getLogger().error("Exception save calendar data.");

						// 在JSON对象内设定服务器处理结果状态
						jsonResult.setStatus(JsonStatus.ERROR);
						return jsonResult;
					}

					for (int i = 0; i < workTime.length; i++) {
						int value = workTimeArr[i];
						if (1 == value) {
							workTimeResult.add(workTime[i]);
						}
					}

					StringBuilder sb = new StringBuilder();
					for (Integer i : workTimeResult) {
						sb.append(i).append(Constants.COMMA);
					}

					if (sb.length() > 0) {
						planCalendar.setWeekdays(sb.substring(0, sb.length() - 1));
					}

					List<PlanCalendarExceptions> planCalendarExceptions = this
							.generateExceptions(ganttDataBean.getCalendarExceptionBeans(), planCalendar);

					this.planCalendarService.updateCalendarAndExceptions(planCalendar, planCalendarExceptions);
				}
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
		} catch (Exception e) {
			getLogger().error("Exception save calendar data.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	private List<PlanCalendarExceptions> generateExceptions(List<GanttCalendarExceptionBean> beans,
			PlanCalendar planCalendar) throws ParseException {
		List<PlanCalendarExceptions> exceptions = new ArrayList<>();

		if (beans != null && !beans.isEmpty()) {
			for (GanttCalendarExceptionBean bean : beans) {
				PlanCalendarExceptions entity = new PlanCalendarExceptions();

				entity.setCalendar(planCalendar);
				entity.setId(bean.getId());

				entity.setIsWorkday(bean.getIsWorkday());
				entity.setExceptionName(bean.getExceptionName());

				entity.setStartDate(DateUtils.string2Date(bean.getStartDate(), Constants.DATE_FORMAT));
				entity.setEndDate(DateUtils.string2Date(bean.getEndDate(), Constants.DATE_FORMAT));

				exceptions.add(entity);
			}
		}

		return exceptions;
	}

	private PlanTask generatePlanTask(GanttTaskBean bean, Plan plan, Date now, SessionUserBean userBean)
			throws ParseException {
		PlanTask task = new PlanTask();

		task.setcDatetime(now);
		task.setmDatetime(now);
		task.setCreator(userBean.getSessionLoginName());
		task.setModifier(userBean.getSessionLoginName());

		task.setId(bean.getId());
		task.setPlan(plan);

		task.setProgress(bean.getProgress());
		task.setTaskNum(bean.getTaskNum());
		task.setPid(bean.getParent());
		task.setTaskLayer(bean.getTaskLayer());
		task.setTaskName(bean.getText());

		task.setTaskType(bean.getTaskType());
		task.setAchievement(bean.getAchievement());
		task.setDelivery(bean.getDelivery());

		task.setDurationDays(bean.getDuration());
		task.setWorkload(bean.getWorkload());
		task.setWeight(bean.getWeight());
		task.setWbs(bean.getWbs());
		task.setPlanStartDate(DateUtils.string2Date(bean.getStart_date(), Constants.DATE_FORMAT));
		task.setPlanEndDate(DateUtils.string2Date(bean.getEnd_date(), Constants.DATE_FORMAT));

		if (!StringUtils.isEmpty(bean.getLeader())) {
			Person person = new Person();
			person.setId(bean.getLeader());
			task.setLeader(person);
		}

		if (!StringUtils.isEmpty(bean.getParticipantName())) {
			task.setParticipantNameList(bean.getParticipantName());
		}
		
		if (!StringUtils.isEmpty(bean.getParticipantId())) {
			task.setParticipantList(bean.getParticipantId());
		}

		task.setIsSummary(bean.getIsSummary());
		task.setIsMilestone(bean.getIsMilestone());
		task.setIsCritical(bean.getIsCritical());
		task.setIsSiteTask(bean.getIsSiteTask());

		if (!StringUtils.isEmpty(bean.getConstraint_type())) {
			if (PlanTaskConstraintTypeEnum.EARLY.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.EARLY.value());
			} else if (PlanTaskConstraintTypeEnum.LATE.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.LATE.value());
			} else if (PlanTaskConstraintTypeEnum.MUST_START.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.MUST_START.value());
			} else if (PlanTaskConstraintTypeEnum.MUST_FINISH.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.MUST_FINISH.value());
			} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_START.value());
			} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_START.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_LATER_TO_START.value());
			} else if (PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_EARLIER_TO_FINISH.value());
			} else if (PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.ganttValue().equals(bean.getConstraint_type())) {
				task.setConstraintType(PlanTaskConstraintTypeEnum.NO_LATER_TO_FINISH.value());
			}
		}

		if (!StringUtils.isEmpty(bean.getConstraint_date())) {
			task.setConstraintDate(DateUtils.string2Date(bean.getConstraint_date(), Constants.DATE_FORMAT));
		}

		if (!StringUtils.isEmpty(bean.getDeadline_date())) {
			task.setDeadlineDate(DateUtils.string2Date(bean.getDeadline_date(), Constants.DATE_FORMAT));
		}

		task.setIsDeleted(OnOffEnum.OFF.value());
		task.setRemark(bean.getRemark());
		task.setIssueType(bean.getIssueType());
		return task;
	}

	private PlanTaskPreTask generatePreTask(GanttLinkBean bean, Plan plan) {
		PlanTaskPreTask preTask = new PlanTaskPreTask();

		preTask.setId(bean.getId());
		// 默认设置0
		preTask.setLagDays(bean.getLag() == null ? 0 : bean.getLag());

		preTask.setPlan(plan);

		PlanTask sourceTask = new PlanTask();
		sourceTask.setId(bean.getSource());

		PlanTask targetTask = new PlanTask();
		targetTask.setId(bean.getTarget());

		preTask.setPreTask(sourceTask);
		preTask.setPlanTask(targetTask);

		if (PreTaskTypeEnum.FINISH_START.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.FINISH_START.value());
		} else if (PreTaskTypeEnum.START_START.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.START_START.value());
		} else if (PreTaskTypeEnum.FINISH_FINISH.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.FINISH_FINISH.value());
		} else if (PreTaskTypeEnum.START_FINISH.ganttValue().equals(bean.getType())) {
			preTask.setPreTaskType(PreTaskTypeEnum.START_FINISH.value());
		}

		return preTask;
	}

	@RequestMapping("/person-combo-list")
	@ResponseBody
	public JsonResultBean comboList() {

		JsonResultBean jsonResult = new JsonResultBean();

		try {
			List<ComboboxOptionBean> resultList = this.generateLeaderCombo();

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);

		} catch (Exception e) {
			getLogger().error("Exception while person combo list.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	private List<ComboboxOptionBean> generateLeaderCombo() {
		CommonFilter filter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());

		List<ComboboxOptionBean> resultList = new ArrayList<>();

		List<OrderByDto> orders = new ArrayList<>();
		orders.add(new OrderByDto("jobNum", false));

		List<Person> persons = this.personService.list(filter, orders);

		for (Person p : persons) {
			resultList.add(new ComboboxOptionBean(p.getId(), p.getPersonName()));
		}
		return resultList;
	}

	private List<ComboboxOptionBean> generateIssueCombo() {
		List<ComboboxOptionBean> resultList = new ArrayList<>();
		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
		CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
		List<DataOption> list = this.dataOptionService.list(f, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value())) {
				resultList.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			}
		}
		return resultList;
	}
	
	/**
	 * 对于过期的计划任务，发送消息提醒（每日凌晨2点提取）
	 * 同一个计划任务，只提醒一次
	 */
	@Scheduled(cron = "0 0 2 * * ?")
	public void pullOverduePlanTask() {
		
		List<PlanTask> planTaskList = planTaskService.findOverdueList();
		System.out.println("共有" + planTaskList.size() + "条逾期的计划任务");
		
		for (PlanTask planTask: planTaskList) {
			
			// 找出要发送的目标用户（Leader和参与者）
			List<User> targetUserList = new ArrayList<User>();
			
			if (planTask.getLeader() != null) {
				Person leader = personService.find(planTask.getLeader().getId());
				targetUserList.add(leader.getUser());
			}
			
			if (!StringUtils.isEmpty(planTask.getParticipantList())) {
				String[] paticipantPersonIds = planTask.getParticipantList().split(",");
				
				CommonFilter personFilter = new CommonFilter();
				personFilter.addWithin("user.id", paticipantPersonIds);
				List<Person> paticipantPersonList = personService.list(personFilter, null);
				for (Person paticipant: paticipantPersonList) {
					targetUserList.add(paticipant.getUser());
				}
			}
			
			if (targetUserList.size() == 0) {
				continue;
			}
			System.out.println("发送的目标用户数=" + targetUserList.size());
			
			// 如果已经提醒过，则不提醒
			String subject = "计划任务逾期";
			String message = "计划任务已逾期，计划名=" + planTask.getPlan().getPlanName() + ", 计划名=" + planTask.getTaskName();
			System.out.println(planTask.getPlanEndDate());
			System.out.println(message);
			
			CommonFilter notifyFilter = new CommonFilter();
			notifyFilter.addExact("content", message);
			long count = notifyService.count(notifyFilter);
			if (count == 0) {
				System.out.println("未查询到记录，插入");
				notifApiService.sendNotify(targetUserList, subject, message, NotifyTargetTypeEnum.PLAN_TASK_PROGRESS.value());
			}
		}
	}

	@RequestMapping("/save-edit-report-progress-data")
	@ResponseBody
	public JsonResultBean saveEditReportProgressData(
			@RequestParam("file") MultipartFile[] files, @RequestParam("task") String planTaskParams,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {
			
			System.out.println(files.length);
			System.out.println(planTaskParams);
			PlanTaskBean planTaskBean = JSONObject.parseObject(planTaskParams, PlanTaskBean.class);

			PlanTask planTask = this.planTaskService.find(planTaskBean.getId());
			List<MultipartWrapper> multipartList = MultipartUtils.parseMultipart(files);

			if (planTask != null) {
				
				Double progress = planTaskBean.getProgress();
				if (planTaskBean.getFlowStatus() == 4) {
					progress = 100.0;
				}
				if (!progress.toString().equals(StringUtils.isEmpty(planTask.getProgress()) ? "0":planTask.getProgress().toString())) {
					planTask.setLastProgress(planTask.getProgress());
					planTask.setProgress(progress);
				}
				
				planTask.setActualStartDate(
						DateUtils.string2Date(planTaskBean.getActualStartDate(), Constants.DATE_FORMAT));
				
				if (!StringUtils.isEmpty(planTaskBean.getActualEndDate())) {
					planTask.setActualEndDate(DateUtils.string2Date(planTaskBean.getActualEndDate(), Constants.DATE_FORMAT));
				} else {
					planTask.setActualEndDate(null);
				}
				
				planTask.setFlowStatus(planTaskBean.getFlowStatus());
				planTask.setCumulativeProgress(planTaskBean.getCumulativeProgress());
				planTask.setCurrentWeekProgress(planTaskBean.getCurrentWeekProgress());
				planTask.setIssueType(planTaskBean.getIssueType());
				planTask.setDescription(planTaskBean.getDescription());
				planTask.setNextWeekGoals(planTaskBean.getNextWeekGoals());
				planTask.setDelivery(planTaskBean.getDelivery());
				
				// 删除的附件列表
				Map<String, String> reservedAttachmentIdMap = new HashMap<>();
				for (String aid : planTaskBean.getReservedAttachmentList()) {
					reservedAttachmentIdMap.put(aid, aid);
	            }
				
				for (PlanTaskAttachment attachment: planTask.getAttachments()) {
					if (!reservedAttachmentIdMap.containsKey(attachment.getArchive().getId())) {
						planTask.getAttachmentsToDelete().add(attachment);
						
					}
				}
				for (PlanTaskAttachment attachment: planTask.getAttachmentsToDelete()) {
					planTask.getAttachments().remove(attachment);
				}
				
				// 保存附件列表
				List<ArchiveBean> taskArchiveList = new ArrayList<>();
				for (MultipartWrapper wrapper: multipartList) {
					ArchiveBean archive = wrapper.getArchiveBean();
					taskArchiveList.add(archive);
				}
				
				ArchiveBeanConverter archiveConverter = new ArchiveBeanConverter();
				for (ArchiveBean archive: taskArchiveList) {
					planTask.getAttachments().add(((PlanTaskBeanConverter) this.getViewConverter()).toAttachmentEntity(archive, archiveConverter, planTask, 1, userBean, new Date()));
				}

				this.planTaskService.update(planTask);
				
				// 保存文件到本地
				String rootDir = systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
				MultipartUtils.saveMultipartToLocal(multipartList, rootDir);
				
				Plan plan = planService.find(planTask.getPlan().getId());
				
				// 如果任务已完成，发送通知提醒给关注该任务的用户
				if (planTask.getLastProgress() != 100.0 && planTask.getProgress() == 100.0) {
					CommonFilter filter = new CommonFilter().addExact("task.id", planTask.getId());
					List<PlanTaskFavorite> favoriteList = planTaskFavoriteService.list(filter, null);
					List<User> targetUserList = new ArrayList<>();
					for (PlanTaskFavorite favorite: favoriteList) {
						targetUserList.add(favorite.getUser());
					}
					
					notifApiService.sendNotify(targetUserList, "计划任务完成", "计划任务已完成，任务名=" + planTask.getTaskName(), NotifyTargetTypeEnum.PLAN_TASK_PROGRESS.value());
					
					// 如果是核心站点，且基础网络组的建设计划完成了第3步，通知两个数通组已具备条件
					if (BasicNetworkTransmitStationTypeEnum.CORE_NODE.value().toString().equals(plan.getSite().getBasicNetworkTransmitStationType())) {
						if (plan.getVirtualOrg().getVirtualOrgName().startsWith("基础网络") && planTask.getTaskName().equals("机房环境勘察及整改")) {
							
							// 找出两个数通组
							CommonFilter planFilter = new CommonFilter().addExact("site.id", plan.getSite().getId());
							List<Plan> planList = planService.list(planFilter, null);
							List<String> orgIdList = new ArrayList<>();
							for (Plan sitePlan : planList) {
								if (!sitePlan.getVirtualOrg().getVirtualOrgName().equals(plan.getVirtualOrg().getVirtualOrgName())) {
									orgIdList.add(sitePlan.getVirtualOrg().getId());
								}
							}
							
							// 找出两个组的用户
							targetUserList.clear();
							CommonFilter personFilter = new CommonFilter().addWithin("virtualOrg.id", orgIdList);
							List<ProjectPerson> projectPersonList = projectPersonService.list(personFilter, null);
							for (ProjectPerson projectPerson: projectPersonList) {
								targetUserList.add(projectPerson.getPerson().getUser());
							}
							
							// 发送通知给两个数通组
							notifApiService.sendNotify(targetUserList, "进场条件已具备", "机房具备数通进场条件，传输具备能力", NotifyTargetTypeEnum.PLAN_TASK_PROGRESS.value());
						}
					}
					
				}
				
				// 如果任务的进度发生了变化，①计划的进度状态也要调整 ②站点中的进展字段也要更新
				if (!planTask.getProgress().equals(planTask.getLastProgress())) {
					if (plan.getSite() != null) {
						planApiService.updateSiteProgress(plan.getSite().getId());
					} else if (plan.getSegment() != null) {
						planApiService.updateSegmentProgress(plan.getSegment().getId());
					}
				}
				
//				// 如果任务的进度发生了变化，①计划的进度状态也要调整 ②站点中的进展字段也要更新
//				if (!planTask.getProgress().equals(planTask.getLastProgress())) {
//					
//					/************** 先更新计划的进度状态 **************/
//					Plan plan = planService.find(planTask.getPlan().getId());
//					
//					CommonFilter filter = new CommonFilter().addExact("p.id", plan.getId());
//					List<OrderByDto> orders = new ArrayList<>();
//					orders.add(new OrderByDto("taskNum", false));
//					List<PlanTask> planTaskList = planTaskService.list(filter, orders);
//					PlanTask firstTask = planTaskList.get(0);
//					PlanTask lastTask = planTaskList.get(planTaskList.size() - 1);
//					
//					// 如果更新的是第一个task
//					if (firstTask.getId().equals(planTask.getId())) {
//						if (planTask.getProgress() == null || planTask.getProgress() == 0) {
//							plan.setStatus(0);
//						} else {
//							plan.setStatus(1);
//						}
//					}
//					else if (lastTask.getId().equals(planTask.getId())) {
//						if (planTask.getProgress() == 100) {
//							plan.setStatus(2);
//						} else {
//							plan.setStatus(1);
//						}
//					}
//					planService.update(plan);
//					
//					/***************** 再更新站点中进展字段 *********************/
//					
//					if (plan.getSite() != null) {
//						
//						Collections.reverse(planTaskList);
//						String progressText = "";
//						for (PlanTask pTask: planTaskList) {
//							if (pTask.getProgress() != null && pTask.getProgress() > 0) {
//								progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
//								break;
//							}
//						}
//						if(progressText.equals("")) {
//							PlanTask firstPlanTask = planTaskList.get(planTaskList.size() - 1);
//							progressText = "[" + firstPlanTask.getTaskNum() + "-" + firstPlanTask.getTaskName() + "]";
//						}
//					
//						Site site = siteService.find(plan.getSite().getId());
//						if (plan.getPlanName().startsWith("SDN")) {
//							site.setSdnNetworkProgress(progressText);
//						} else if (plan.getPlanName().startsWith("基础网络")) {
//							site.setBasicNetworkProgress(progressText);
//						} else if (plan.getPlanName().startsWith("可编程")) {
//							site.setProgrammableNetworkProgress(progressText);
//						}
//						siteService.update(site);
//					}
//				}
				
				CommonFilter filter = new CommonFilter();
				filter.addExact("plan.id", planTask.getPlan().getId());
				List<PlanProgressTask> planProgressTasks = this.planProgressTaskService.list(filter, null);

				GanttTaskBean ganttTaskBean = this.generateGanttTask(planTask.getPlan().getPlanCalendar(), false,
						planTask, planProgressTasks);

				jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, ganttTaskBean);
				// 准备JSON结果
				jsonResult.setStatus(JsonStatus.OK);
				jsonResult.setMessage(this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
			} else {
				jsonResult.setStatus(JsonStatus.ERROR);
			}
		} catch (Exception e) {
			getLogger().error("Exception save-edit-report-progress-data.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	@RequestMapping("/save-plan-progress-data")
	@ResponseBody
	public JsonResultBean savePlanProgressData(@RequestBody PlanProgressBean planProgressBean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			Plan plan = this.planService.find(planProgressBean.getPlan_());

			if (plan != null) {

				Date now = new Date();
				Project project = plan.getProject();
				Person person = this.personService.find(userBean.getSessionUserId());

				PlanProgress planProgress = this.generatePlanProgress(planProgressBean, plan, project, person,
						userBean.getSessionLoginName(), now);

				List<PlanTask> planTasks = this.generateUpdatePlanTasks(planProgressBean.getPlanProgressTaskBeans(),
						userBean.getSessionLoginName(), now);

				List<PlanProgressTask> progressTasks = this.generatePlanProgressTasks(
						planProgressBean.getPlanProgressTaskBeans(), plan, planProgress, userBean.getSessionLoginName(),
						now);

				this.planTaskService.createPlanProgress(planProgress, progressTasks, planTasks);
				
				

				//更新站点表进展
				Plan planUpdate = planService.find(planProgressBean.getPlan_());
				if (planUpdate.getSite() != null) {
					planApiService.updateSiteProgress(planUpdate.getSite().getId());
				} else if (planUpdate.getSegment() != null) {
					planApiService.updateSegmentProgress(planUpdate.getSegment().getId());
				}
				
				GanttDataBean ganttDataBean = new GanttDataBean();

				this.loadGanttDataFromDB(plan.getId(), OnOffEnum.OFF.value().toString(), ganttDataBean, person);

				jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, ganttDataBean);
				// 准备JSON结果
				jsonResult.setStatus(JsonStatus.OK);
				jsonResult.setMessage(this.messageSource.getMessage("message.info.record.add.ok", null, locale));
			} else {
				jsonResult.setStatus(JsonStatus.ERROR);
			}
		} catch (Exception e) {
			getLogger().error("Exception save plan progress data.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	private List<PlanTask> generateUpdatePlanTasks(List<PlanProgressTaskBean> planProgressTaskBeans,
			String sessionLoginName, Date now) throws ParseException {
		List<PlanTask> planTasks = new ArrayList<>();

		for (PlanProgressTaskBean progressTaskBean : planProgressTaskBeans) {

			PlanTask planTask = this.planTaskService.find(progressTaskBean.getPlanTask_());

			planTask.setActualStartDate(
					DateUtils.string2Date(progressTaskBean.getActualStartDate(), Constants.DATE_FORMAT));
			if (progressTaskBean.getActualEndDate() != null && !"".equals(progressTaskBean.getActualEndDate())) {
				planTask.setActualEndDate(
						DateUtils.string2Date(progressTaskBean.getActualEndDate(), Constants.DATE_FORMAT));
			}
			if (StringUtils.isEmpty(planTask.getProgress())) {
				planTask.setLastProgress(planTask.getProgress());
				planTask.setProgress(progressTaskBean.getProgress());
			} else if (!StringUtils.isEmpty(planTask.getProgress()) && !planTask.getProgress().toString().equals(progressTaskBean.getProgress().toString())) {
				planTask.setLastProgress(planTask.getProgress());
				planTask.setProgress(progressTaskBean.getProgress());
			}

			planTask.setCumulativeProgress(progressTaskBean.getCumulativeProgress());
			planTask.setCurrentWeekProgress(progressTaskBean.getCurrentWeekProgress());
			planTask.setDescription(progressTaskBean.getDescription());
			planTask.setNextWeekGoals(progressTaskBean.getNextWeekGoals());
			planTask.setFlowStatus(progressTaskBean.getFlowStatus());

			planTask.setModifier(sessionLoginName);
			planTask.setIssueType(progressTaskBean.getIssueType());
			planTask.setmDatetime(now);

			planTasks.add(planTask);
			progressTaskBean.setPlanTask(planTask);
		}

		return planTasks;
	}

	private List<PlanProgressTask> generatePlanProgressTasks(List<PlanProgressTaskBean> planProgressTaskBeans,
			Plan plan, PlanProgress planProgress, String creator, Date now) throws ParseException {

		List<PlanProgressTask> progressTasks = new ArrayList<>();

		for (PlanProgressTaskBean progressTaskBean : planProgressTaskBeans) {
			PlanProgressTask planProgressTask = new PlanProgressTask();

			planProgressTask.setPlan(plan);
			planProgressTask.setPlanProgress(planProgress);

			PlanTask planTask = progressTaskBean.getPlanTask();

			if (planTask != null) {
				planProgressTask.setPlanTask(planTask);
				planProgressTask.setTaskName(planTask.getTaskName());
				planProgressTask.setDurationDays(planTask.getDurationDays());
				planProgressTask.setPlanStartDate(planTask.getPlanStartDate());
				planProgressTask.setPlanEndDate(planTask.getPlanEndDate());
				planProgressTask.setWeight(planTask.getWeight());
				planProgressTask.setLeader(planTask.getLeader());
				planProgressTask.setProgress(planTask.getProgress());
				planProgressTask.setLastProgress(planTask.getLastProgress() == null ? 0 : planTask.getLastProgress());
				planProgressTask.setTaskNum(planTask.getTaskNum());
				planProgressTask.setTaskType(planTask.getTaskType());
				planProgressTask.setWbs(planTask.getWbs());
				planProgressTask.setParticipantList(planTask.getParticipantList());
				planProgressTask.setParticipantNameList(planTask.getParticipantNameList());
			}

			planProgressTask.setActualStartDate(
					DateUtils.string2Date(progressTaskBean.getActualStartDate(), Constants.DATE_FORMAT));
			if (progressTaskBean.getActualEndDate() != null && !"".equals(progressTaskBean.getActualEndDate())) {
				planProgressTask.setActualEndDate(
						DateUtils.string2Date(progressTaskBean.getActualEndDate(), Constants.DATE_FORMAT));
			}
			planProgressTask.setProgress(progressTaskBean.getProgress());

			planProgressTask.setCumulativeProgress(progressTaskBean.getCumulativeProgress());
			planProgressTask.setCurrentWeekProgress(progressTaskBean.getCurrentWeekProgress());
			planProgressTask.setDescription(progressTaskBean.getDescription());
			planProgressTask.setNextWeekGoals(progressTaskBean.getNextWeekGoals());
			planProgressTask.setFlowStatus(progressTaskBean.getFlowStatus());
			planProgressTask.setProgressDate(planProgress.getProgressDate());

			planProgressTask.setCreator(creator);
			planProgressTask.setcDatetime(now);
			planProgressTask.setmDatetime(now);

			progressTasks.add(planProgressTask);
		}

		return progressTasks;
	}

	private PlanProgress generatePlanProgress(PlanProgressBean planProgressBean, Plan plan, Project project,
			Person person, String creator, Date now) throws ParseException {
		PlanProgress progress = new PlanProgress();
		progress.setPlan(plan);
		progress.setProject(project);
		progress.setApplicant(person);

		progress.setDateType(planProgressBean.getDateType());
		progress.setProgressDate(DateUtils.string2Date(planProgressBean.getProgressDate(), Constants.DATE_FORMAT));
		progress.setProgressName(planProgressBean.getProgressName());

		// 内部简报
		progress.setReportType(planProgressBean.getReportType());

		progress.setDescription(planProgressBean.getDescription());
		progress.setRemark(planProgressBean.getRemark());

		this.handleFlowStartAndEndDate(progress);

		progress.setIsDeleted(OnOffEnum.OFF.value());
		progress.setCreator(creator);
		progress.setcDatetime(now);
		progress.setmDatetime(now);

		return progress;
	}

	private void handleFlowStartAndEndDate(PlanProgress planProgress) {

		int dateType = planProgress.getDateType();
		Date date = planProgress.getProgressDate();

		if (planProgress.getProgressDate() != null) {
			if (DateTypeEnum.WEEKLY.value().equals(dateType)) {

				planProgress.setFlowStartDate(handleLastWeekDay(date));
				planProgress.setFlowEndDate(handleNextWeekDay(date));
			} else if (DateTypeEnum.MONTHLY.value().equals(dateType)) {

				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTime(date);
				startCalendar.set(Calendar.DAY_OF_MONTH, 1);
				startCalendar.add(Calendar.MONTH, 0);

				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(date);
				endCalendar.add(Calendar.MONTH, 1);
				endCalendar.set(Calendar.DAY_OF_MONTH, 0);

				planProgress.setFlowStartDate(startCalendar.getTime());
				planProgress.setFlowEndDate(endCalendar.getTime());
			} else if (DateTypeEnum.QUARTERLY.value().equals(dateType)) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				Integer[] firstSeason = new Integer[] { 0, 1, 2 };
				Integer[] secondSeason = new Integer[] { 3, 4, 5 };
				Integer[] thirdSeason = new Integer[] { 6, 7, 8 };
				Integer[] forthSeason = new Integer[] { 9, 10, 11 };

				int month = calendar.get(Calendar.MONTH);
				int year = calendar.get(Calendar.YEAR);

				Calendar startCalendar = Calendar.getInstance();
				Calendar endCalendar = Calendar.getInstance();

				if (this.isExistSeason(firstSeason, month)) {

					startCalendar.set(year, 0, 1);
					endCalendar.set(year, 2, 31);
				} else if (this.isExistSeason(secondSeason, month)) {

					startCalendar.set(year, 3, 1);
					endCalendar.set(year, 6, 0);
				} else if (this.isExistSeason(thirdSeason, month)) {

					startCalendar.set(year, 6, 1);
					endCalendar.set(year, 9, 0);
				} else if (this.isExistSeason(forthSeason, month)) {

					startCalendar.set(year, 9, 1);
					endCalendar.set(year, 11, 31);
				}

				planProgress.setFlowStartDate(startCalendar.getTime());
				planProgress.setFlowEndDate(endCalendar.getTime());
			} else if (DateTypeEnum.HALF_A_YEAR.value().equals(dateType)) {

				Integer[] firstSeason = new Integer[] { 0, 1, 2, 3, 4, 5 };
				Integer[] secondSeason = new Integer[] { 6, 7, 8, 9, 10, 11 };

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				int month = calendar.get(Calendar.MONTH);
				int year = calendar.get(Calendar.YEAR);

				Calendar startCalendar = Calendar.getInstance();
				Calendar endCalendar = Calendar.getInstance();

				if (isExistSeason(firstSeason, month)) {

					startCalendar.set(year, 0, 1);
					endCalendar.set(year, 6, 0);
				} else if (isExistSeason(secondSeason, month)) {

					startCalendar.set(year, 6, 1);
					endCalendar.set(year, 11, 31);
				}

				planProgress.setFlowStartDate(startCalendar.getTime());
				planProgress.setFlowEndDate(endCalendar.getTime());
			} else if (DateTypeEnum.YEAR.value().equals(dateType)) {

				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTime(date);
				startCalendar.set(Calendar.DAY_OF_YEAR, 1);

				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(date);
				endCalendar.add(Calendar.YEAR, 1);
				endCalendar.set(Calendar.DAY_OF_YEAR, 0);

				planProgress.setFlowStartDate(startCalendar.getTime());
				planProgress.setFlowEndDate(endCalendar.getTime());
			}
		}
	}

	private boolean isExistSeason(Integer[] season, Integer month) {
		for (Integer i : season) {
			if (i == month) {
				return true;
			}
		}

		return false;
	}

	private Date handleLastWeekDay(Date date) {

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(date);
		startCalendar.setFirstDayOfWeek(Calendar.MONDAY);

		int weekOfMonth = startCalendar.get(Calendar.WEEK_OF_MONTH);

		startCalendar.add(Calendar.DATE, -1);

		int tempWeekOfMonth = startCalendar.get(Calendar.WEEK_OF_MONTH);

		if (weekOfMonth != tempWeekOfMonth) {
			startCalendar.add(Calendar.DATE, 1);
			return startCalendar.getTime();
		}

		return handleLastWeekDay(startCalendar.getTime());
	}

	private Date handleNextWeekDay(Date date) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(date);
		startCalendar.setFirstDayOfWeek(Calendar.MONDAY);

		int weekOfMonth = startCalendar.get(Calendar.WEEK_OF_MONTH);

		startCalendar.add(Calendar.DATE, 1);

		int tempWeekOfMonth = startCalendar.get(Calendar.WEEK_OF_MONTH);

		if (weekOfMonth != tempWeekOfMonth) {
			startCalendar.add(Calendar.DATE, -1);
			return startCalendar.getTime();
		}

		return handleNextWeekDay(startCalendar.getTime());
	}

	/**
	 * 根据excel模版数据解析并导入至数据库
	 * 
	 * @param sourceFileName
	 * @param taskId
	 * @param pathType
	 * @param response
	 * @param request
	 */
	@RequestMapping("/import-excel-file")
	public void validateSourceModelType(String planId, HttpServletResponse response, HttpServletRequest request,
			Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		// 当浏览器是IE时，如果返回application/json，IE会提示用户保存upload.json文件
		response.setContentType("text/html");

		try {

			if (request instanceof MultipartHttpServletRequest) {

				MultipartFile importFile = ((MultipartHttpServletRequest) request).getFile("importFile");

				String path = this.context.getRealPath("/");

				// mtstar\webapp\webapps\app
				File root = new File(path);
				String randomID = UUID.randomUUID().toString();

				File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
						"temp" + File.separator + planId);

				FileUtils.forceMkdir(uploadPath);

				String uploadFilePath = uploadPath.getAbsolutePath() + File.separator + randomID;

				String templateExtension = FilenameUtils.getExtension(importFile.getOriginalFilename());

				String templateFilePath = uploadFilePath + Constants.DOT + templateExtension;

				if (templateFilePath != null && !importFile.isEmpty()) {

					File tempFile = new File(templateFilePath);

					FileUtils.writeByteArrayToFile(new File(templateFilePath), importFile.getBytes());

					// 解析excel文件，xls/xlsx需要分开处理
					GanttDataBean ganttDataBean = this.parseExcelFileToGanttBean(tempFile, planId);

					// 准备JSON结果
					jsonResult.setStatus(JsonStatus.OK);
					jsonResult.setMessage(this.messageSource.getMessage("message.info.import.ok", null, locale));
					jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, ganttDataBean);
				}
			}
		} catch (ExcelParseException e) {
			logger.warn("Excel数据解析错误！", e);
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage("ParseError");
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, e.getDetails());
		} catch (Exception e) {
			logger.error("Exception while import excel file.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		try {
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			response.getWriter().write("<textarea>" + result + "</textarea>");
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
	}

	// 解析excel根据后缀名分开处理 xls或者xlsx
	private GanttDataBean parseExcelFileToGanttBean(File tempFile, String planId)
			throws FileNotFoundException, IOException, ExcelParseException {

		GanttDataBean ganttDataBean = new GanttDataBean();

		Map<String, GanttTaskBean> taskMap = new HashedMap<>();
		Set<String> pids = new HashSet<>();
		String extension = FilenameUtils.getExtension(tempFile.getName());

		Workbook wb = null;
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(tempFile);

			if ("xlsx".equals(extension)) {
				wb = new XSSFWorkbook(fileInputStream);
			} else if ("xls".equals(extension)) {
				wb = new HSSFWorkbook(fileInputStream);
			}

			if (wb != null) {

				Sheet sheet = wb.getSheetAt(0);

				Date now = new Date();

				ExcelParseException parseException = new ExcelParseException();
				for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {

					// 跳过第一二行
					if (i < 2) {
						continue;
					}

					Row row = sheet.getRow(i);
					GanttTaskBean taskBean = new GanttTaskBean();

					try {

						Cell idCell = row.getCell(0);
						Cell taskNameCell = row.getCell(1);
						Cell taskTypeCell = row.getCell(2);
						// to do
						Cell prewbs = row.getCell(3);

						Cell startDateCell = row.getCell(4);
						Cell endDateCell = row.getCell(5);
						Cell achievementCell = row.getCell(6);
						// Cell deadlineDateCell = row.getCell(5);
						Cell leaderCell = row.getCell(7);
						Cell participantName = row.getCell(8);
						Cell weightCell = row.getCell(9);
						Cell remarkCell = row.getCell(12);
						Cell isMilestoreCell = row.getCell(10);
						Cell isCriticalCell = row.getCell(11);

						if (idCell != null && taskNameCell != null) {

							taskNameCell.setCellType(CellType.STRING);
							taskTypeCell.setCellType(CellType.STRING);

							String id = idCell.toString();

							if (id.endsWith(".0")) {
								id = id.substring(0, id.length() - 2);
							}

							// if (StringUtils.isEmpty(id)
							// || StringUtils.isEmpty(taskTypeCell.getStringCellValue())
							// || StringUtils.isEmpty(taskNameCell.getStringCellValue())) {
							// continue;
							// }

							if (StringUtils.isEmpty(id) || StringUtils.isEmpty(taskNameCell.getStringCellValue())) {
								continue;
							}

							taskBean.setId(id);
							taskBean.setParent(this.handleWBSwithId(id));

							pids.add(taskBean.getParent());

							taskBean.setText(taskNameCell.getStringCellValue());
							taskBean.setTaskType(taskTypeCell.getStringCellValue());

							if (StringUtils.isEmpty(taskNameCell.getStringCellValue())) {
								throw new ExcelParseException.Detail(i, i, "任务名称未填写！");
							}

						} else {
							continue;
						}

						String startStr = ExcelUtils.getCellValue(startDateCell);

						if (!StringUtils.isEmpty(startStr)) {
							try {
								DateUtils.string2Date(startStr, Constants.DATE_FORMAT);
							} catch (Exception e) {
								throw new ExcelParseException.Detail(4, i, "开始日期格式不正确！");
							}
							taskBean.setStart_date(startStr);
						} else {
							taskBean.setStart_date(DateUtils.date2String(now, Constants.DATE_FORMAT));
						}

						String endStr = ExcelUtils.getCellValue(endDateCell);

						if (!StringUtils.isEmpty(endStr)) {
							try {
								DateUtils.string2Date(startStr, Constants.DATE_FORMAT);
							} catch (Exception e) {
								throw new ExcelParseException.Detail(5, i, "结束日期格式不正确！");
							}
							taskBean.setEnd_date(endStr);
						} else {
							taskBean.setEnd_date(DateUtils.date2String(now, Constants.DATE_FORMAT));
						}

						// String deadlineStr = ExcelUtils.getCellValue(deadlineDateCell);
						//
						// if (!StringUtils.isEmpty(deadlineStr)) {
						// taskBean.setDeadline_date(deadlineStr);
						// }
						if (achievementCell != null) {
							achievementCell.setCellType(CellType.STRING);
							taskBean.setAchievement(achievementCell.getStringCellValue());
						}

						if (leaderCell != null) {
							leaderCell.setCellType(CellType.STRING);

							CommonFilter filter = new CommonFilter();
							filter.addExact("personName", leaderCell.getStringCellValue());

							List<Person> persons = this.personService.list(filter, null);

							if (!persons.isEmpty()) {
								taskBean.setLeader(persons.get(0).getId());
							}
						}

						if (participantName != null) {
							participantName.setCellType(CellType.STRING);
							taskBean.setParticipantName(participantName.getStringCellValue());
							String participantNameText = participantName.getStringCellValue();
							String[] participantNameList = null;
							if (participantNameText.indexOf(",") > 0) {
								participantNameList = participantNameText.split(",");
							}
							if (participantNameText.indexOf("、") > 0) {
								participantNameList = participantNameText.split("、");
							}
							if (!StringUtils.isEmpty(participantNameList)) {
								DataFilter df;
								String participantId = "";
								List<Person> person;
								for (int a = 0; a < participantNameList.length; a++) {
									try {
										if (!StringUtils.isEmpty(participantNameList[a])) {
											df = new CommonFilter().addExact("personName", participantNameList[a]);
											person = personService.list(df, null);
											if (person != null && person.get(0) != null) {
												participantId += person.get(0).getId();
												if (a != participantNameList.length - 1) {
													participantId += ",";
												}
											}
										}
									} catch (Exception e) {
										throw new ExcelParseException.Detail(9, i, "此人员在系统中不存在："+participantNameList[a]);
									}
								}
								taskBean.setParticipantId(participantId);
							}
						}

						if (weightCell != null) {
							try {
								weightCell.setCellType(CellType.NUMERIC);
								taskBean.setWeight(weightCell.getNumericCellValue());
							} catch (Exception e) {
								throw new ExcelParseException.Detail(9, i, "优先级应为数字！");
							}
						}

						if (remarkCell != null) {
							remarkCell.setCellType(CellType.STRING);
							taskBean.setRemark(remarkCell.getStringCellValue());
						}

						if (isMilestoreCell != null) {
							isMilestoreCell.setCellType(CellType.STRING);
							if ("是".equals(isMilestoreCell.getStringCellValue())) {
								taskBean.setIsMilestone(OnOffEnum.ON.value());
							} else {
								taskBean.setIsMilestone(OnOffEnum.OFF.value());
							}
						} else {
							taskBean.setIsMilestone(OnOffEnum.OFF.value());
						}

						if (isCriticalCell != null) {
							isCriticalCell.setCellType(CellType.STRING);

							if ("是".equals(isCriticalCell.getStringCellValue())) {
								taskBean.setIsCritical(OnOffEnum.ON.value());
							} else {
								taskBean.setIsCritical(OnOffEnum.OFF.value());
							}
						} else {
							taskBean.setIsCritical(OnOffEnum.OFF.value());
						}

						if (!StringUtils.isEmpty(taskBean.getId())) {
							taskMap.put(taskBean.getId(), taskBean);
						}

					} catch (ExcelParseException.Detail e) {
						parseException.add(e);
					}
				}

				if (parseException.getDetails().size() > 0) {
					throw parseException;
				}
			}
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}

			if (wb != null) {
				wb.close();
			}
		}

		Plan plan = this.planService.find(planId);

		PlanCalendar calendar = this.planCalendarService.find(plan.getPlanCalendar().getId());

		Set<String> set = taskMap.keySet();
		Object[] arr = set.toArray();
		Arrays.sort(arr);

		for (Object key : arr) {

			GanttTaskBean bean = taskMap.get(key);
			bean.setCalendar_id(calendar.getId());

			if (pids.contains(bean.getId())) {
				bean.setIsSummary(OnOffEnum.ON.value());
			}

			// // 特殊处理无父节点数据
			// if (taskMap.get(bean.getParent()) == null) {
			// bean.setParent("0");
			// }

			bean.setIsSiteTask(OnOffEnum.OFF.value());

			if (OnOffEnum.ON.value().equals(bean.getIsSummary())
					&& OnOffEnum.ON.value().equals(bean.getIsMilestone())) {
				bean.setType(PlanTaskTypeEnum.PROJECT.value());
			} else if (OnOffEnum.ON.value().equals(bean.getIsSummary())) {
				bean.setType(PlanTaskTypeEnum.PROJECT.value());
			} else if (OnOffEnum.ON.value().equals(bean.getIsMilestone())) {
				bean.setType(PlanTaskTypeEnum.MILESTONE.value());
			} else if (OnOffEnum.ON.value().equals(bean.getIsCritical())) {
				bean.setType(PlanTaskTypeEnum.CRITICAL_TASK.value());
			} else if (OnOffEnum.ON.value().equals(bean.getIsSiteTask())) {
				bean.setType(PlanTaskTypeEnum.SITE_TASK.value());
			} else {
				bean.setType(PlanTaskTypeEnum.TASK.value());
			}

			ganttDataBean.getTaskBeans().add(bean);
		}

		return ganttDataBean;
	}

	private String handleWBSwithId(String stringCellValue) {

		if (!StringUtils.isEmpty(stringCellValue)) {

			String[] ids = stringCellValue.split("\\.");

			if (ids.length == 1) {
				return "0";
			} else {

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < ids.length - 1; i++) {
					sb.append(ids[i]).append(Constants.DOT);
				}

				return sb.substring(0, sb.length() - 1);
			}
		}

		return "0";
	}

	/**
	 * 导出计划任务
	 * 
	 * @param planId
	 * @param response
	 * @param locale
	 */
	@RequestMapping("/export-excel-file")
	public void exportPlanTaskExcel(@RequestParam String planId, boolean isExportReport, HttpServletResponse response,
			Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {
			File downloadFile = this.generatePlanTaskExcelFile(planId);

			String fileName = downloadFile.getName();

			String mimeType = URLConnection.guessContentTypeFromName(fileName);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);

			// 解决中文文件名显示问题
			fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			// response.setContentLength(downloadFile..getLabelFileContent().length);

			InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));

			// Copy bytes from source to destination, closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			inputStream.close();
			response.getOutputStream().close();

			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while export plan task xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}
	}

	private File generatePlanTaskExcelFile(String planId) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			Plan plan = this.planService.find(planId);

			CommonFilter filter = new CommonFilter();
			filter.addExact("plan.id", planId);

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));

			List<PlanTask> planTasks = this.planTaskService.list(filter, orders);

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet(plan.getPlanName());
			Row header = sheet.createRow(0);
			header.setHeight((short) 1000);

			/*
			 * CellStyle cellStyle_top_left = wb.createCellStyle();
			 * cellStyle_top_left.setBorderLeft(BorderStyle.THICK);
			 * cellStyle_top_left.setBorderTop(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_left_buttom = wb.createCellStyle();
			 * cellStyle_left_buttom.setBorderBottom(BorderStyle.THICK);
			 * cellStyle_left_buttom.setBorderLeft(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_right_buttom = wb.createCellStyle();
			 * cellStyle_right_buttom.setBorderBottom(BorderStyle.THICK);
			 * cellStyle_right_buttom.setBorderRight(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_buttom = wb.createCellStyle();
			 * cellStyle_buttom.setBorderBottom(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_left = wb.createCellStyle();
			 * cellStyle_left.setBorderLeft(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_right = wb.createCellStyle();
			 * cellStyle_right.setBorderRight(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_top_right = wb.createCellStyle();
			 * cellStyle_top_right.setBorderRight(BorderStyle.THICK);
			 * cellStyle_top_right.setBorderTop(BorderStyle.THICK);
			 * 
			 * CellStyle cellStyle_top = wb.createCellStyle();
			 * cellStyle_top.setBorderTop(BorderStyle.THICK);
			 */

			CellStyle border = wb.createCellStyle();
			// 边框
			border.setBorderBottom(CellStyle.BORDER_THIN);
			border.setBorderLeft(CellStyle.BORDER_THIN);
			border.setBorderRight(CellStyle.BORDER_THIN);
			border.setBorderTop(CellStyle.BORDER_THIN);

			Cell headCell = header.createCell(0);
			header.createCell(1).setCellStyle(border);
			header.createCell(2).setCellStyle(border);
			header.createCell(3).setCellStyle(border);
			header.createCell(4).setCellStyle(border);
			header.createCell(5).setCellStyle(border);
			header.createCell(6).setCellStyle(border);
			header.createCell(7).setCellStyle(border);
			header.createCell(8).setCellStyle(border);
			header.createCell(9).setCellStyle(border);
			header.createCell(10).setCellStyle(border);
			header.createCell(11).setCellStyle(border);
			header.createCell(12).setCellStyle(border);

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));

			sheet.setColumnWidth(0, 15 * 256);
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			sheet.setColumnWidth(5, 20 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 20 * 256);
			sheet.setColumnWidth(8, 10 * 256);
			sheet.setColumnWidth(9, 30 * 256);
			sheet.setColumnWidth(10, 15 * 256);
			sheet.setColumnWidth(11, 15 * 256);
			sheet.setColumnWidth(12, 20 * 256);

			headCell.setCellValue(plan.getPlanName());

			CellStyle cellStyle = wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			cellStyle.setBorderLeft(BorderStyle.THICK);
			cellStyle.setBorderTop(BorderStyle.THICK);

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);

			Row properties = sheet.createRow(1);
			properties.setHeight((short) 800);

			Cell cell_0 = properties.createCell(0);
			cell_0.setCellValue("项目编号");
			cell_0.setCellStyle(border);

			Project p = this.projectService.find(plan.getProject().getId());
			Cell cell_1 = properties.createCell(1);
			cell_1.setCellStyle(border);

			Cell cell_2 = properties.createCell(2);
			cell_2.setCellStyle(border);

			properties.createCell(7).setCellStyle(border);
			properties.createCell(8).setCellStyle(border);
			properties.createCell(9).setCellStyle(border);
			properties.createCell(10).setCellStyle(border);
			properties.createCell(11).setCellStyle(border);
			properties.createCell(12).setCellStyle(border);

			sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 8, 12));

			if (p != null) {
				cell_1.setCellValue(p.getProjectNum());

				properties.createCell(4).setCellValue(p.getProjectName());
				properties.getCell(7).setCellValue(DateUtils.date2String(plan.getcDatetime(), Constants.DATE_FORMAT));
			}

			Cell cell_3 = properties.createCell(3);
			cell_3.setCellStyle(border);
			cell_3.setCellValue("项目名称");

			Cell cell_4 = properties.createCell(4);
			cell_4.setCellStyle(border);

			if (p != null) {
				cell_4.setCellValue(p.getProjectName());
			}

			Cell cell_5 = properties.createCell(5);
			cell_5.setCellStyle(border);

			// properties.createCell(6).setCellValue("项目类型");

			Cell cell_6 = properties.createCell(6);
			cell_6.setCellStyle(border);

			cell_6.setCellValue("上报时间");
			cell_6.setCellStyle(border);

			Row title = sheet.createRow(2);
			title.setHeight((short) 800);
			Cell cell_t_0 = title.createCell(0);
			cell_t_0.setCellValue("标识");
			cell_t_0.setCellStyle(border);

			title.createCell(1).setCellStyle(border);
			title.createCell(2).setCellStyle(border);
			title.createCell(3).setCellStyle(border);
			title.createCell(4).setCellStyle(border);
			title.createCell(5).setCellStyle(border);

			title.getCell(1).setCellValue("任务名称");
			title.getCell(2).setCellValue("任务类型");
			title.getCell(3).setCellValue("开始日期（年月日）");
			title.getCell(4).setCellValue("结束日期（年月日）");
			title.getCell(5).setCellValue("交付物");

			Cell cell_t_6 = title.createCell(6);
			cell_t_6.setCellValue("负责人");
			cell_t_6.setCellStyle(border);

			title.createCell(7).setCellStyle(border);
			title.createCell(8).setCellStyle(border);
			title.createCell(9).setCellStyle(border);
			title.createCell(10).setCellStyle(border);
			title.createCell(11).setCellStyle(border);
			title.createCell(12).setCellStyle(border);

			title.getCell(7).setCellValue("参与人员");
			title.getCell(8).setCellValue("优先级");
			title.getCell(9).setCellValue("备注（特殊设备或软件备注说明）");
			title.getCell(10).setCellValue("是否为里程碑");
			title.getCell(11).setCellValue("是否为关键任务");
			title.getCell(12).setCellValue("最后期限日（年月日）");

			for (int i = 0; i < planTasks.size(); i++) {

				PlanTask task = planTasks.get(i);
				Row row = sheet.createRow(i + 3);
				row.setHeight((short) 800);

				Cell cell_p_0 = row.createCell(0);
				Cell cell_p_1 = row.createCell(1);
				Cell cell_p_2 = row.createCell(2);
				Cell cell_p_3 = row.createCell(3);
				Cell cell_p_4 = row.createCell(4);
				Cell cell_p_5 = row.createCell(5);
				Cell cell_p_6 = row.createCell(6);
				Cell cell_p_7 = row.createCell(7);
				Cell cell_p_8 = row.createCell(8);
				Cell cell_p_9 = row.createCell(9);
				Cell cell_p_10 = row.createCell(10);
				Cell cell_p_11 = row.createCell(11);
				Cell cell_p_12 = row.createCell(12);

				cell_p_0.setCellStyle(border);
				cell_p_1.setCellStyle(border);
				cell_p_2.setCellStyle(border);
				cell_p_3.setCellStyle(border);
				cell_p_4.setCellStyle(border);
				cell_p_5.setCellStyle(border);
				cell_p_6.setCellStyle(border);
				cell_p_7.setCellStyle(border);
				cell_p_8.setCellStyle(border);
				cell_p_9.setCellStyle(border);
				cell_p_10.setCellStyle(border);
				cell_p_11.setCellStyle(border);
				cell_p_12.setCellStyle(border);

				cell_p_0.setCellValue(task.getWbs());

				cell_p_1.setCellValue(task.getTaskName());

				cell_p_2.setCellValue(task.getTaskType());

				if (task.getPlanStartDate() != null) {
					cell_p_3.setCellValue(DateUtils.date2String(task.getPlanStartDate(), Constants.DATE_FORMAT));
				}

				if (task.getPlanEndDate() != null) {
					cell_p_4.setCellValue(DateUtils.date2String(task.getPlanEndDate(), Constants.DATE_FORMAT));
				}

				if (!StringUtils.isEmpty(task.getAchievement())) {
					cell_p_5.setCellValue(task.getAchievement());
				}

				if (task.getLeader() != null) {
					Person person = this.personService.find(task.getLeader().getId());

					cell_p_6.setCellValue(person.getPersonName());
				}

				if (task.getParticipantNameList() != null) {
					cell_p_7.setCellValue(task.getParticipantNameList());
				}

				if (task.getWeight() != null) {
					cell_p_8.setCellValue(task.getWeight());
				}

				if (!StringUtils.isEmpty(task.getRemark())) {
					cell_p_9.setCellValue(task.getRemark());
				}

				if (OnOffEnum.ON.value().equals(task.getIsMilestone())) {
					cell_p_10.setCellValue("是");
				} else {
					cell_p_10.setCellValue("否");
				}

				if (OnOffEnum.ON.value().equals(task.getIsCritical())) {
					cell_p_11.setCellValue("是");
				} else {
					cell_p_11.setCellValue("否");
				}

				if (task.getDeadlineDate() != null) {
					cell_p_12.setCellValue(DateUtils.date2String(task.getDeadlineDate(), Constants.DATE_FORMAT));
				}

			}

			Date now = new Date();

			String fileName = plan.getPlanName() + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + planId + File.separator + randomID);

			FileUtils.forceMkdir(uploadPath);

			file = new File(uploadPath.getAbsoluteFile(), fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();
		} finally {

			if (output != null) {
				output.close();
			}
		}

		return file;
	}

	/**
	 * 关注任务
	 * 
	 * @param idsToConcern
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/concern")
	@ResponseBody
	public JsonResultBean Concern(@RequestParam String idsToConcern,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToConcern);
		List<String> idList = Arrays.asList(idArray);
		List<String> ids = new ArrayList<>(idList);
		try {

			if ((idList != null) && (idList.size() > 0)) {
				CommonFilter filter = new CommonFilter().addExact("user.id", userBean.getSessionUserId())
						.addWithin("task.id", idList);
				List<PlanTaskFavorite> planTaskFavorites = planTaskFavoriteService.list(filter, null);
				if (planTaskFavorites != null && planTaskFavorites.size() > 0) {
					for (PlanTaskFavorite favorite : planTaskFavorites) {
						if (idList.contains(favorite.getPlanTask().getId())) {
							ids.remove(favorite.getPlanTask().getId());
						}
					}
				}
				List<PlanTaskFavorite> list = new ArrayList<>();
				for (String id : ids) {
					PlanTaskFavorite favorite = new PlanTaskFavorite();
					PlanTask task = new PlanTask();
					task.setId(id);
					favorite.setPlanTask(task);
					User u = new User();
					u.setId(userBean.getSessionUserId());
					favorite.setUser(u);
					list.add(favorite);
				}
				if (list.size() > 0) {
					planTaskFavoriteService.create(list);
				}
			}
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.concern.ok", null, locale));
		} catch (Exception e) {
			this.getLogger().error("Exception Concern the " + this.getDataType(), e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}

	/**
	 * 取消关注
	 * 
	 * @param idsToNoConcern
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/no-concern")
	@ResponseBody
	public JsonResultBean NoConcern(@RequestParam String idsToNoConcern,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToNoConcern);
		List<String> idList = Arrays.asList(idArray);
		try {

			if ((idList != null) && (idList.size() > 0)) {
				CommonFilter filter = new CommonFilter().addExact("user.id", userBean.getSessionUserId())
						.addWithin("task.id", idList);
				List<PlanTaskFavorite> planTaskFavorites = planTaskFavoriteService.list(filter, null);
				if ((planTaskFavorites != null) && (planTaskFavorites.size() >= 0)) {
					List<String> delIds = new ArrayList<>();
					for (PlanTaskFavorite p : planTaskFavorites) {
						delIds.add(p.getId());
					}

					if (delIds.size() > 0) {
						planTaskFavoriteService.delete(delIds);
					}

				}
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.no.concern.ok", null, locale));

		} catch (Exception e) {
			this.getLogger().error("Exception Concern the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	@RequestMapping("/export-task-excel-file")
	public void ExportExcelTaskFile(@RequestParam String planId, HttpServletResponse response, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			Plan plan = this.planService.find(planId);
			CommonFilter filter = new CommonFilter();
			filter.addExact("p.id", plan.getId());
			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("cDatetime", true));
			List<PlanProgress> planProgresses = this.planProgressService.list(filter, orders);

			List<PlanProgress> handleProgresses = new ArrayList<>();

			for (PlanProgress pp : planProgresses) {
				// 内部简报
				if (ReportTypeEnum.INTERNAL_REPORT.value().equals(pp.getReportType())) {
					handleProgresses.add(pp);
				}
			}

			File downloadFile = null;
			if (handleProgresses.size() > 0) {
				downloadFile = this.handleDownloadReportFile(handleProgresses.get(0), locale);
			} else {
				downloadFile = this.generatePlanTaskReportExcelFileByPlanId(planId, locale);
			}

			ControllerDownloadUtils.downloadFile(downloadFile, response);
			FileUtils.deleteQuietly(downloadFile);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while export project init xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}
	}

	private File handleDownloadReportFile(PlanProgress pp, Locale locale) throws IOException {

		File downloadFile = null;
		// 内部简报
		if (ReportTypeEnum.INTERNAL_REPORT.value().equals(pp.getReportType())) {
			downloadFile = this.generatePlanTaskReportExcelFile(pp.getId(), locale);
		}
		return downloadFile;
	}

	// 上报简报导出
	private File generatePlanTaskReportExcelFile(String planProgressId, Locale locale) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			PlanProgress planProgress = this.planProgressService.find(planProgressId);

			CommonFilter filter = new CommonFilter();
			filter.addExact("p.id", planProgress.getPlan().getId());

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));

			List<PlanTask> planTasks = this.planTaskService.list(filter, orders);

			Plan plan = this.planService.find(planProgress.getPlan().getId());

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();

			XSSFCellStyle textCellStyle2 = (XSSFCellStyle) wb.createCellStyle();
			textCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			textCellStyle2.setBorderBottom(BorderStyle.THIN);
			textCellStyle2.setBorderLeft(BorderStyle.THIN);
			textCellStyle2.setBorderRight(BorderStyle.THIN);
			textCellStyle2.setBorderTop(BorderStyle.THIN);

			Sheet sheet = wb.createSheet(plan.getPlanName());
			Row header = sheet.createRow(0);
			header.setHeight((short) 800);
			CellRangeAddress cheader = new CellRangeAddress(0, 0, 0, 17); // 起始行, 终止行, 起始列, 终止列
			sheet.addMergedRegion(cheader);

			for (int i = 0; i < 17; i++) {
				if (i == 0 || i == 1) {
					sheet.setColumnWidth(i, 20 * 300);
				} else {
					sheet.setColumnWidth(i, 20 * 200);
				}
			}

			Cell headCell = header.createCell(0);

			Project p = this.projectService.find(plan.getProject().getId());

			String resourceStr = this.messageSource.getMessage(DateTypeEnum.getResouceName(planProgress.getDateType()),
					null, locale);
			headCell.setCellValue(p.getProjectName() + "项目 " + resourceStr);

			XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);
			RegionUtil.setBorderBottom(1, cheader, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cheader, sheet); // 左边框
			RegionUtil.setBorderRight(1, cheader, sheet); // 有边框
			RegionUtil.setBorderTop(1, cheader, sheet); // 上边框

			Row row1 = sheet.createRow(1);
			row1.setHeight((short) 800);

			row1.createCell(0).setCellValue("计划ID");
			row1.getCell(0).setCellStyle(textCellStyle2);
			row1.createCell(1).setCellValue(plan.getId());
			row1.getCell(1).setCellStyle(textCellStyle2);
			row1.createCell(2).setCellValue("计划名称");
			row1.getCell(2).setCellStyle(textCellStyle2);

			CellRangeAddress cell_4 = new CellRangeAddress(1, 1, 3, 5);
			sheet.addMergedRegion(cell_4);
			row1.createCell(3).setCellValue(plan.getPlanName());
			row1.getCell(3).setCellStyle(textCellStyle2);
			RegionUtil.setBorderBottom(1, cell_4, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cell_4, sheet); // 左边框
			RegionUtil.setBorderRight(1, cell_4, sheet); // 有边框
			RegionUtil.setBorderTop(1, cell_4, sheet); // 上边框

			row1.createCell(6).setCellValue("简报名称");
			row1.getCell(6).setCellStyle(textCellStyle2);

			CellRangeAddress cell_6 = new CellRangeAddress(1, 1, 7, 8);
			sheet.addMergedRegion(cell_6);
			row1.createCell(7).setCellValue(planProgress.getProgressName());
			row1.getCell(7).setCellStyle(textCellStyle2);
			RegionUtil.setBorderBottom(1, cell_6, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cell_6, sheet); // 左边框
			RegionUtil.setBorderRight(1, cell_6, sheet); // 有边框
			RegionUtil.setBorderTop(1, cell_6, sheet); // 上边框

			row1.createCell(9).setCellValue("上报时间");
			row1.getCell(9).setCellStyle(textCellStyle2);
			row1.createCell(10)
					.setCellValue(DateUtils.date2String(planProgress.getProgressDate(), Constants.DATE_FORMAT));
			row1.getCell(10).setCellStyle(textCellStyle2);
			row1.createCell(11).setCellValue("专业成效");
			row1.getCell(11).setCellStyle(textCellStyle2);

			CellRangeAddress cell_10 = new CellRangeAddress(1, 1, 12, 17);
			sheet.addMergedRegion(cell_10);
			row1.createCell(12).setCellValue(planProgress.getRemark());
			row1.getCell(12).setCellStyle(textCellStyle2);

			RegionUtil.setBorderBottom(1, cell_10, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cell_10, sheet); // 左边框
			RegionUtil.setBorderRight(1, cell_10, sheet); // 有边框
			RegionUtil.setBorderTop(1, cell_10, sheet); // 上边框

			Row title = sheet.createRow(2);
			title.setHeight((short) 800);
			title.createCell(0).setCellValue("任务ID");
			title.getCell(0).setCellStyle(textCellStyle2);
			title.createCell(1).setCellValue("任务名称");
			title.getCell(1).setCellStyle(textCellStyle2);
			title.createCell(2).setCellValue("完成状态");
			title.getCell(2).setCellStyle(textCellStyle2);
			title.createCell(3).setCellValue("上期进度");
			title.getCell(2).setCellStyle(textCellStyle2);
			title.createCell(4).setCellValue("本期进度");
			title.getCell(3).setCellStyle(textCellStyle2);
			title.createCell(5).setCellValue("实际开始时间");
			title.getCell(4).setCellStyle(textCellStyle2);
			title.createCell(6).setCellValue("实际完成时间");
			title.getCell(5).setCellStyle(textCellStyle2);
			title.createCell(7).setCellValue("累计进展情况");
			title.getCell(6).setCellStyle(textCellStyle2);
			title.createCell(8).setCellValue("本期进展情况");
			title.getCell(7).setCellStyle(textCellStyle2);
			title.createCell(9).setCellValue("问题类型");
			title.getCell(8).setCellStyle(textCellStyle2);
			title.createCell(10).setCellValue("计划差异/偏离/问题描述");
			title.getCell(9).setCellStyle(textCellStyle2);
			title.createCell(11).setCellValue("下期计划目标");
			title.getCell(10).setCellStyle(textCellStyle2);
			title.createCell(12).setCellValue("任务类型");
			title.getCell(11).setCellStyle(textCellStyle2);
			title.createCell(13).setCellValue("工期");
			title.getCell(12).setCellStyle(textCellStyle2);
			title.createCell(14).setCellValue("开始日期");
			title.getCell(13).setCellStyle(textCellStyle2);
			title.createCell(15).setCellValue("结束日期");
			title.getCell(14).setCellStyle(textCellStyle2);
			title.createCell(16).setCellValue("备注");
			title.getCell(15).setCellStyle(textCellStyle2);
			title.createCell(17).setCellValue("最后期限日");
			title.getCell(16).setCellStyle(textCellStyle2);
			title.getCell(17).setCellStyle(textCellStyle2);

			String[] flowStatuss = { "未提交", "审核中", "审核通过", "审核不通过", "撤回中", "已撤回" };
			for (int i = 0; i < planTasks.size(); i++) {
				PlanTask task = planTasks.get(i);
				Row info = sheet.createRow(3 + i);
				info.setHeight((short) 800);
				info.createCell(0).setCellValue(task.getId());
				info.createCell(1).setCellValue(task.getTaskName());
				info.createCell(2).setCellValue(flowStatuss[task.getFlowStatus()]);

				DecimalFormat percent = new DecimalFormat("0.00%");
				String lastprogress = percent.format(task.getLastProgress() == null ? 0 : task.getLastProgress());
				String progress = percent.format(task.getProgress() == null ? 0 : task.getProgress());
				info.createCell(3).setCellValue(lastprogress);
				info.createCell(4).setCellValue(progress);
				info.createCell(5).setCellValue(task.getActualStartDate() == null ? ""
						: DateUtils.date2String(task.getActualStartDate(), Constants.DATE_FORMAT));
				info.createCell(6).setCellValue(task.getActualEndDate() == null ? ""
						: DateUtils.date2String(task.getActualEndDate(), Constants.DATE_FORMAT));
				info.createCell(7).setCellValue(task.getCumulativeProgress());
				info.createCell(8).setCellValue(task.getCurrentWeekProgress());

				String issueType = "";

				List<String> dataTypeList = new ArrayList<>();
				dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
				CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
				List<DataOption> list = this.dataOptionService.list(f, null);
				for (DataOption o : list) {
					if (o.getId().equals(task.getIssueType())) {
						issueType = o.getOptionName();
					}
				}

				info.createCell(9).setCellValue(issueType);
				info.createCell(10).setCellValue(task.getDescription());
				info.createCell(11).setCellValue(task.getNextWeekGoals());
				info.createCell(12).setCellValue(task.getTaskType());
				info.createCell(13).setCellValue(task.getDueProgressDays() == null ? 0 : task.getDueProgressDays());
				info.createCell(14).setCellValue(task.getPlanStartDate() == null ? ""
						: DateUtils.date2String(task.getPlanStartDate(), Constants.DATE_FORMAT));
				info.createCell(15).setCellValue(task.getPlanEndDate() == null ? ""
						: DateUtils.date2String(task.getPlanEndDate(), Constants.DATE_FORMAT));
				info.createCell(16).setCellValue(task.getRemark());
				info.createCell(17).setCellValue(task.getDeadlineDate() == null ? ""
						: DateUtils.date2String(task.getDeadlineDate(), Constants.DATE_FORMAT));
			}

			Date now = new Date();

			String fileName = "内部简报_" + plan.getPlanName() + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + planProgressId + File.separator + randomID);

			FileUtils.forceMkdir(uploadPath);

			file = new File(uploadPath.getAbsoluteFile(), fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();
		} finally {

			if (output != null) {
				output.close();
			}
		}

		return file;
	}

	// 上报简报导出
	private File generatePlanTaskReportExcelFileByPlanId(String planId, Locale locale) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {

			CommonFilter filter = new CommonFilter();
			filter.addExact("p.id", planId);

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));

			List<PlanTask> planTasks = this.planTaskService.list(filter, orders);

			Plan plan = this.planService.find(planId);

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();

			XSSFCellStyle textCellStyle2 = (XSSFCellStyle) wb.createCellStyle();
			textCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			textCellStyle2.setBorderBottom(BorderStyle.THIN);
			textCellStyle2.setBorderLeft(BorderStyle.THIN);
			textCellStyle2.setBorderRight(BorderStyle.THIN);
			textCellStyle2.setBorderTop(BorderStyle.THIN);

			Sheet sheet = wb.createSheet(plan.getPlanName());
			Row header = sheet.createRow(0);
			header.setHeight((short) 800);
			CellRangeAddress cheader = new CellRangeAddress(0, 0, 0, 17); // 起始行, 终止行, 起始列, 终止列
			sheet.addMergedRegion(cheader);

			for (int i = 0; i < 17; i++) {
				if (i == 0 || i == 1) {
					sheet.setColumnWidth(i, 20 * 300);
				} else {
					sheet.setColumnWidth(i, 20 * 200);
				}
			}

			Cell headCell = header.createCell(0);

			Project p = this.projectService.find(plan.getProject().getId());

			headCell.setCellValue(p.getProjectName() + "项目 ");

			XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);
			RegionUtil.setBorderBottom(1, cheader, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cheader, sheet); // 左边框
			RegionUtil.setBorderRight(1, cheader, sheet); // 有边框
			RegionUtil.setBorderTop(1, cheader, sheet); // 上边框

			Row row1 = sheet.createRow(1);
			row1.setHeight((short) 800);

/*			row1.createCell(0).setCellValue("计划ID");
			row1.getCell(0).setCellStyle(textCellStyle2);
			row1.createCell(1).setCellValue(plan.getId());
			row1.getCell(1).setCellStyle(textCellStyle2);*/
			row1.createCell(0).setCellValue("计划名称");
			row1.getCell(0).setCellStyle(textCellStyle2);

			CellRangeAddress cell_4 = new CellRangeAddress(1, 1, 1, 5);
			sheet.addMergedRegion(cell_4);
			row1.createCell(1).setCellValue(plan.getPlanName());
			row1.getCell(1).setCellStyle(textCellStyle2);
			RegionUtil.setBorderBottom(1, cell_4, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cell_4, sheet); // 左边框
			RegionUtil.setBorderRight(1, cell_4, sheet); // 有边框
			RegionUtil.setBorderTop(1, cell_4, sheet); // 上边框

			row1.createCell(6).setCellValue("简报名称");
			row1.getCell(6).setCellStyle(textCellStyle2);

			CellRangeAddress cell_6 = new CellRangeAddress(1, 1, 7, 8);
			sheet.addMergedRegion(cell_6);
			row1.createCell(7).setCellValue("");
			row1.getCell(7).setCellStyle(textCellStyle2);
			RegionUtil.setBorderBottom(1, cell_6, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cell_6, sheet); // 左边框
			RegionUtil.setBorderRight(1, cell_6, sheet); // 有边框
			RegionUtil.setBorderTop(1, cell_6, sheet); // 上边框

			row1.createCell(9).setCellValue("上报时间");
			row1.getCell(9).setCellStyle(textCellStyle2);
			row1.createCell(10).setCellValue("");
			row1.getCell(10).setCellStyle(textCellStyle2);
			row1.createCell(11).setCellValue("专业成效");
			row1.getCell(11).setCellStyle(textCellStyle2);

			CellRangeAddress cell_10 = new CellRangeAddress(1, 1, 12, 17);
			sheet.addMergedRegion(cell_10);
			row1.createCell(12).setCellValue("");
			row1.getCell(12).setCellStyle(textCellStyle2);

			RegionUtil.setBorderBottom(1, cell_10, sheet); // 下边框
			RegionUtil.setBorderLeft(1, cell_10, sheet); // 左边框
			RegionUtil.setBorderRight(1, cell_10, sheet); // 有边框
			RegionUtil.setBorderTop(1, cell_10, sheet); // 上边框

			Row title = sheet.createRow(2);
			title.setHeight((short) 800);
			title.createCell(0).setCellValue("标识");
			title.getCell(0).setCellStyle(textCellStyle2);
			title.createCell(1).setCellValue("任务名称");
			title.getCell(1).setCellStyle(textCellStyle2);
			title.createCell(2).setCellValue("完成状态");
			title.getCell(2).setCellStyle(textCellStyle2);
			title.createCell(3).setCellValue("上期进度");
			title.getCell(2).setCellStyle(textCellStyle2);
			title.createCell(4).setCellValue("本期进度");
			title.getCell(3).setCellStyle(textCellStyle2);
			title.createCell(5).setCellValue("实际开始时间");
			title.getCell(4).setCellStyle(textCellStyle2);
			title.createCell(6).setCellValue("实际完成时间");
			title.getCell(5).setCellStyle(textCellStyle2);
			title.createCell(7).setCellValue("累计进展情况");
			title.getCell(6).setCellStyle(textCellStyle2);
			title.createCell(8).setCellValue("本期进展情况");
			title.getCell(7).setCellStyle(textCellStyle2);
			title.createCell(9).setCellValue("问题类型");
			title.getCell(8).setCellStyle(textCellStyle2);
			title.createCell(10).setCellValue("计划差异/偏离/问题描述");
			title.getCell(9).setCellStyle(textCellStyle2);
			title.createCell(11).setCellValue("下期计划目标");
			title.getCell(10).setCellStyle(textCellStyle2);
			title.createCell(12).setCellValue("任务类型");
			title.getCell(11).setCellStyle(textCellStyle2);
			title.createCell(13).setCellValue("工期");
			title.getCell(12).setCellStyle(textCellStyle2);
			title.createCell(14).setCellValue("开始日期");
			title.getCell(13).setCellStyle(textCellStyle2);
			title.createCell(15).setCellValue("结束日期");
			title.getCell(14).setCellStyle(textCellStyle2);
			title.createCell(16).setCellValue("备注");
			title.getCell(15).setCellStyle(textCellStyle2);
			title.createCell(17).setCellValue("最后期限日");
			title.getCell(16).setCellStyle(textCellStyle2);
			title.getCell(17).setCellStyle(textCellStyle2);

			String[] flowStatuss = { "未启动", "延期中", "进行中", "完成", "搁置", "取消" };
			for (int i = 0; i < planTasks.size(); i++) {
				PlanTask task = planTasks.get(i);
				Row info = sheet.createRow(3 + i);
				info.setHeight((short) 800);
				info.createCell(0).setCellValue(task.getWbs());
				info.createCell(1).setCellValue(task.getTaskName());
				info.createCell(2).setCellValue(flowStatuss[task.getFlowStatus()]);

				DecimalFormat percent = new DecimalFormat("0.00%");
				String lastprogress = percent.format(task.getLastProgress() == null ? 0 : task.getLastProgress());
				String progress = percent.format(task.getProgress() == null ? 0 : task.getProgress());
				info.createCell(3).setCellValue(lastprogress);
				info.createCell(4).setCellValue(progress);
				info.createCell(5).setCellValue(task.getActualStartDate() == null ? ""
						: DateUtils.date2String(task.getActualStartDate(), Constants.DATE_FORMAT));
				info.createCell(6).setCellValue(task.getActualEndDate() == null ? ""
						: DateUtils.date2String(task.getActualEndDate(), Constants.DATE_FORMAT));
				info.createCell(7).setCellValue(task.getCumulativeProgress());
				info.createCell(8).setCellValue(task.getCurrentWeekProgress());

				String issueType = "";

				List<String> dataTypeList = new ArrayList<>();
				dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
				CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
				List<DataOption> list = this.dataOptionService.list(f, null);
				for (DataOption o : list) {
					if (o.getId().equals(task.getIssueType())) {
						issueType = o.getOptionName();
					}
				}

				info.createCell(9).setCellValue(issueType);
				info.createCell(10).setCellValue(task.getDescription());
				info.createCell(11).setCellValue(task.getNextWeekGoals());
				info.createCell(12).setCellValue(task.getTaskType());
				info.createCell(13).setCellValue(task.getDueProgressDays() == null ? 0 : task.getDueProgressDays());
				info.createCell(14).setCellValue(task.getPlanStartDate() == null ? ""
						: DateUtils.date2String(task.getPlanStartDate(), Constants.DATE_FORMAT));
				info.createCell(15).setCellValue(task.getPlanEndDate() == null ? ""
						: DateUtils.date2String(task.getPlanEndDate(), Constants.DATE_FORMAT));
				info.createCell(16).setCellValue(task.getRemark());
				info.createCell(17).setCellValue(task.getDeadlineDate() == null ? ""
						: DateUtils.date2String(task.getDeadlineDate(), Constants.DATE_FORMAT));
			}

			Date now = new Date();

			String fileName = "内部简报_" + plan.getPlanName() + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + planId + File.separator + randomID);

			FileUtils.forceMkdir(uploadPath);

			file = new File(uploadPath.getAbsoluteFile(), fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();
		} finally {

			if (output != null) {
				output.close();
			}
		}

		return file;
	}

	@RequestMapping("/import-task-excel-file")
	public void ImportExcelFile(@RequestParam String planId, HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		// 当浏览器是IE时，如果返回application/json，IE会提示用户保存upload.json文件
		response.setContentType("text/html");

		try {

			if (request instanceof MultipartHttpServletRequest) {

				MultipartFile importFile = ((MultipartHttpServletRequest) request).getFile("importFile");

				String path = this.context.getRealPath("/");

				// mtstar\webapp\webapps\app
				File root = new File(path);
				String randomID = UUID.randomUUID().toString();

				File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
						"temp" + File.separator + randomID);
				FileUtils.forceMkdir(uploadPath);
				String uploadFilePath = uploadPath.getAbsolutePath() + File.separator + randomID;
				String templateExtension = FilenameUtils.getExtension(importFile.getOriginalFilename());
				String templateFilePath = uploadFilePath + Constants.DOT + templateExtension;
				if (templateFilePath != null && !importFile.isEmpty()) {
					File tempFile = new File(templateFilePath);

					FileUtils.writeByteArrayToFile(new File(templateFilePath), importFile.getBytes());

					// 解析excel文件，xls/xlsx需要分开处理
					List<PlanTaskBean> beans = this.parseExcelFileToPlanTask(planId, tempFile, userBean);

					if (!beans.isEmpty()) {
						Date now = new Date();
						PlanTaskBeanConverter converter = new PlanTaskBeanConverter();
						List<PlanTask> tasks = new ArrayList<>();
						for (PlanTaskBean bean : beans) {
							PlanTask oldEntity = this.planTaskService.find(bean.getId());
							PlanTask p = converter.toImportEntity(bean, oldEntity, userBean, now);
							tasks.add(p);

						}

						this.planTaskService.update(tasks);
					}
					// 准备JSON结果
					jsonResult.setStatus(JsonStatus.OK);
					jsonResult.setMessage(this.messageSource.getMessage("message.info.import.ok", null, locale));
					jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while import excel file.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		try {
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			response.getWriter().write("<textarea>" + result + "</textarea>");
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
	}

	private List<PlanTaskBean> parseExcelFileToPlanTask(String plan_id, File tempFile, SessionUserBean userBean)
			throws FileNotFoundException, IOException, Exception {

		List<PlanTaskBean> beans = new ArrayList<>();

		String extension = FilenameUtils.getExtension(tempFile.getName());

		Workbook wb = null;
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(tempFile);

			if ("xlsx".equals(extension)) {
				wb = new XSSFWorkbook(fileInputStream);
			} else if ("xls".equals(extension)) {
				wb = new HSSFWorkbook(fileInputStream);
			}

			if (wb != null) {

				Sheet sheet = wb.getSheetAt(0);

				List<String> dataTypeList = new ArrayList<>();
				dataTypeList.add(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value());
				CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
				List<DataOption> list = this.dataOptionService.list(f, null);
				for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
					// 跳过第一二三行
					if (i < 3) {
						if (i == 1) {
							Row row = sheet.getRow(i);
							//String planId = ExcelUtils.getCellValue(row.getCell(1));
							Plan plan = this.planService.find(plan_id);
							if (plan == null) {
								throw new Exception();
							}
							PlanProgress planProgress = new PlanProgress();
							planProgress.setPlan(plan);
							planProgress.setProject(plan.getProject());
							planProgress.setProgressName(ExcelUtils.getCellValue(row.getCell(6)));
							planProgress.setDateType(1);
							planProgress.setReportType(1);
							Person person = this.personService.find(userBean.getSessionUserId());
							planProgress.setApplicant(person);
							planProgress.setCreator(person.getPersonName());
							if(!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(10)))){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								planProgress.setProgressDate(simpleDateFormat.parse(ExcelUtils.getCellValue(row.getCell(10))));
							}
							else {
								planProgress.setProgressDate(new Date());
							}
							planProgress.setcDatetime(new Date());
							planProgress.setmDatetime(new Date());
							try {
								planProgress.setProgressDate(DateUtils
										.string2Date(ExcelUtils.getCellValue(row.getCell(10)), Constants.DATE_FORMAT));
							} catch (Exception e) {
							}
							planProgress.setRemark(ExcelUtils.getCellValue(row.getCell(12)));
							//this.planProgressService.create(planProgress);
						}
						continue;
					}

					Row row = sheet.getRow(i);
					PlanTaskBean bean = new PlanTaskBean();

					if (row == null) {
						continue;
					}

					String taskId = ExcelUtils.getCellValue(row.getCell(0));
					if (StringUtils.isEmpty(taskId)) {
						continue;
					}
					
					String taskName = ExcelUtils.getCellValue(row.getCell(1));
					if (StringUtils.isEmpty(taskName)) {
						continue;
					}
					
					CommonFilter pf = new CommonFilter().addExact("taskName", taskName).addExact("plan.id", plan_id);
					List<PlanTask> taskList = this.planTaskService.list(pf, null);
					if (taskList.size() == 0) {
						continue;
					}
					
					PlanTask task = taskList.get(0);

					bean.setId(task.getId());
					// 完成状态
					String[] flowStatuss = { "未启动", "延期中", "进行中", "完成", "搁置", "取消" };
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
						String flowStatus = ExcelUtils.getCellValue(row.getCell(2));
						for (int j = 0; j < flowStatuss.length; j++) {
							if (flowStatuss[j].equals(flowStatus)) {
								bean.setFlowStatus(j + 1);
							}
						}
					}
					// 上期进度
					bean.setLastProgress(task.getProgress());
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(4)))) {
						// 本期进度
						String progeress = ExcelUtils.getCellValue(row.getCell(4));
						bean.setProgress(new BigDecimal(progeress.replace("%", "")).doubleValue());
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(5)))) {
						// 实际开始时间
						bean.setActualStartDate(ExcelUtils.getCellValue(row.getCell(5)));
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(6)))) {
						// 实际结束时间
						bean.setActualEndDate(ExcelUtils.getCellValue(row.getCell(6)));
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(7)))) {
						// 累计进展情况
						bean.setCumulativeProgress(ExcelUtils.getCellValue(row.getCell(7)));
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(8)))) {
						// 本周进展情况
						bean.setCurrentWeekProgress(ExcelUtils.getCellValue(row.getCell(8)));
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(9)))) {
						// 问题类型
						for (DataOption o : list) {
							if (o.getDataType().equals(DataTypeEnum.PLAN_TASK_ISSUES_TYPE.value())) {
								if (o.getOptionName().equals(ExcelUtils.getCellValue(row.getCell(9)))) {
									bean.setIssueType(o.getId());
								}
							}
						}
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(10)))) {
						// 计划差异/偏离/问题描述
						bean.setDescription(ExcelUtils.getCellValue(row.getCell(10)));
					}
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(11)))) {
						// 下周目标计划
						bean.setNextWeekGoals(ExcelUtils.getCellValue(row.getCell(11)));
					}

					beans.add(bean);
				}
			}
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}

			if (wb != null) {
				wb.close();
			}
		}

		return beans;
	}
}
