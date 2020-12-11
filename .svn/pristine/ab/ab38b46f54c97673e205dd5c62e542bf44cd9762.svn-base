package project.edge.web.controller.oa;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.JsonResultBean;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.PaperLibraryStatusEnum;
import project.edge.domain.converter.BudgetEstimateVersionBeanConverter;
import project.edge.domain.converter.BudgetFinalVersionBeanConverter;
import project.edge.domain.entity.AcceptanceCheck;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateChange;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.BudgetFinal;
import project.edge.domain.entity.BudgetFinalVersion;
import project.edge.domain.entity.CapitalApply;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.OaFlowHistory;
import project.edge.domain.entity.PaperLibrary;
import project.edge.domain.entity.PaperLibraryLendHistory;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanChange;
import project.edge.domain.entity.PlanChangeTask;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectChange;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.ProjectPerformanceAward;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.ProjectPersonChange;
import project.edge.domain.entity.ProjectPersonChangeDetail;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.Review;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.view.BudgetEstimateVersionBean;
import project.edge.domain.view.BudgetFinalVersionBean;
import project.edge.service.acceptance.AcceptanceCheckService;
import project.edge.service.acceptance.ReviewService;
import project.edge.service.archive.PaperLibraryLendHistoryService;
import project.edge.service.archive.PaperLibraryService;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.bidding.TenderingPlanService;
import project.edge.service.budget.BudgetEstimateChangeService;
import project.edge.service.budget.BudgetEstimateMainService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.budget.BudgetFinalService;
import project.edge.service.budget.BudgetFinalVersionService;
import project.edge.service.budget.CapitalApplyService;
import project.edge.service.budget.CapitalPlanService;
import project.edge.service.budget.CapitalPlanVersionService;
import project.edge.service.flow.OaFlowHistoryService;
import project.edge.service.process.ProjectCheckService;
import project.edge.service.process.ProjectInspectService;
import project.edge.service.process.ProjectPerformanceAwardService;
import project.edge.service.project.ProjectChangeService;
import project.edge.service.project.ProjectPersonChangeDetailService;
import project.edge.service.project.ProjectPersonChangeService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.schedule.PlanChangeService;
import project.edge.service.schedule.PlanChangeTaskPreTaskService;
import project.edge.service.schedule.PlanChangeTaskService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskPreTaskService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.DataOptionService;

@RestController
@RequestMapping("/oaAuditRes")
public class OaAuditResController {
	private static final Logger logger = LoggerFactory.getLogger(OaAuditResController.class);
	@Resource
	private DataOptionService dataOptionService;
	@Resource
	private OaFlowHistoryService oaFlowHistoryService;
	@Resource
	private ProjectService projectService;
	@Resource
	private PlanChangeService planChangeService;
	@Resource
	private PurchaseOrderService purchaseOrderService;
	@Resource
	private PlanService planService;
	@Resource
	private BudgetEstimateService budgetEstimateService;
	@Resource
	private BudgetEstimateChangeService budgetEstimateChangeService;
    @Resource
    private CapitalPlanService capitalPlanService;
    @Resource
    private CapitalPlanVersionService capitalPlanVersionService;
    @Resource
    private BudgetFinalVersionService budgetFinalVersionService;
	@Resource
	private BudgetFinalService budgetFinalService;
	@Resource
	private ProjectPersonService projectPersonService;
	@Resource
	private ProjectPersonChangeService projectPersonChangeService;
	@Resource
	private ProjectPersonChangeDetailService projectPersonChangeDetailService;
	@Resource
	private TenderingPlanService tenderingPlanService;
	@Resource
	private ProjectChangeService projectChangeService;
	@Resource
	private ProjectCheckService projectCheckService;
	@Resource
	private ProjectInspectService projectInspectService;
	@Resource
	private ProjectPerformanceAwardService projectPerformanceAwardService;
	@Resource
	private AcceptanceCheckService acceptanceCheckService;
	@Resource
	private ReviewService reviewService;
	@Resource
	private PaperLibraryService paperLibraryService;
	@Resource
	private PaperLibraryLendHistoryService paperLibraryLendHistoryService;
    @Resource
    private CapitalApplyService capitalApplyService;
	@Resource
	private BudgetEstimateVersionService budgetEstimateVersionService;
	@Resource
	private PlanChangeTaskService planChangeTaskService;
	@Resource
	private PlanTaskService planTaskService;
	@Resource
	private PlanTaskPreTaskService preTaskService;
	@Resource
	private PlanChangeTaskPreTaskService changeTaskPreTaskService;
    @Resource
    private BudgetEstimateMainService budgetEstimateMainService;

	/**
	 * @param kind
	 * @param dataId
	 * @param flowStatus
	 * @return
	 */
	@RequestMapping(value = "/notice", method = RequestMethod.POST)
	@ResponseBody

	public JsonResultBean auditSubmitCallback(@RequestParam(required = true) String kind,
			@RequestParam(required = true) String dataId, @RequestParam(required = true) String flowStatus,
			@RequestParam(required = false) String approvalId, @RequestParam(required = false) String approver,
			@RequestParam(required = false) String approvalTime) {
		logger.info("[OA Audit] Receive callback from oa. kind:" + kind + ", dataId:" + dataId + ", flowStatus:" + flowStatus
				 + ", approvalId:" + approvalId  + ", approver:" + approver  + ", approvalTime:" + approvalTime);
		
		JsonResultBean jsonResult = new JsonResultBean();

		OaFlowHistory entity = oaFlowHistoryService.find(dataId);

		if (entity != null) {
			// 更新审批状态
			logger.info("[OA Audit] Receive callback to update audit status. RelatedFormId: " + entity.getRelatedFormId());
			entity.setFlowAction(Integer.parseInt(flowStatus));
			entity.setmDatetime(new Date());
			oaFlowHistoryService.update(entity);

			if (String.valueOf(OAAuditApiType.OA_TYPE_PROJECTINIT.value()).equals(kind) ||
					String.valueOf(OAAuditApiType.OA_TYPE_PROJECTAUDIT.value()).equals(kind)) {
				// 1 项目立项, 2 立项审核
				Project proEntity = projectService.find(entity.getRelatedFormId());
				proEntity.setFlowStatusProject(Integer.parseInt(flowStatus));
				projectService.setData(proEntity);
				if (String.valueOf(FlowStatusEnum.REVIEW_PASSED.value()).equals(flowStatus)) {
					jsonResult = projectInitAuditHandler(entity.getRelatedFormId(), flowStatus);
				}
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECT_PERSON_CHANGE.value()).equals(kind)){
				// 4 项目成员变更
				ProjectPersonChangeDetail personChangeDetail = projectPersonChangeDetailService.find(entity.getRelatedFormId());
				ProjectPersonChange personChange = projectPersonChangeService.find(personChangeDetail.getProjectPersonChange().getId());
				personChange.setFlowStatus(Integer.parseInt(flowStatus));
				personChange.setFlowEndDate(new Date());
				projectPersonChangeService.setData(personChange);
				personChangeDetail.setFlowStatus(Integer.parseInt(flowStatus));
				projectPersonChangeDetailService.update(personChangeDetail);
				
				CommonFilter filter = new CommonFilter();
				filter.addExact("project.id", personChangeDetail.getProject().getId());
				filter.addExact("person.id", personChangeDetail.getPerson().getId());
				List<ProjectPerson> projectPersonList = projectPersonService.list(filter, null);
				if (projectPersonList.size() > 0) {
					// 变更
					ProjectPerson projectPerson = projectPersonList.get(0);
					projectPerson.setVirtualOrg(personChangeDetail.getVirtualOrg());
					projectPerson.setProjectRole(personChangeDetail.getProjectRole());
					projectPersonService.setData(projectPerson);					
				} else {
					// 新增
					ProjectPerson projectPerson = new ProjectPerson();
					projectPerson.setProject(personChangeDetail.getProject());
					projectPerson.setVirtualOrg(personChangeDetail.getVirtualOrg());
					projectPerson.setPerson(personChangeDetail.getPerson());
					projectPerson.setProjectRole(personChangeDetail.getProjectRole());
					Person creator = new Person();
					creator.setId(personChangeDetail.getCreator());
					projectPerson.setCreator(creator);
					projectPerson.setcDatetime(personChangeDetail.getcDatetime());
					projectPerson.setmDatetime(personChangeDetail.getmDatetime());
					projectPersonService.create(projectPerson);
				}
			} else if (String.valueOf(OAAuditApiType.OA_TYPE_PURCHASEORDER.value()).equals(kind)) {
				// 5 申购信息审核接口
				PurchaseOrder purchaseOrder = purchaseOrderService.find(entity.getRelatedFormId());
				purchaseOrder.setFlowStatus(Integer.parseInt(flowStatus));
				purchaseOrderService.setData(purchaseOrder);				
				
			} else if (String.valueOf(OAAuditApiType.OA_TYPE_TENDINGPLAN.value()).equals(kind)) {
				// 6 招标文件审核
				TenderingPlan tend = tenderingPlanService.find(entity.getRelatedFormId());
				tend.setFlowStatus(Integer.parseInt(flowStatus));
				tenderingPlanService.setData(tend);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PLAN.value()).equals(kind)){
				// 9 进度计划审核接口
				Plan planEntity=planService.find(entity.getRelatedFormId());
				planEntity.setFlowStatus(Integer.parseInt(flowStatus));
				planService.setData(planEntity);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PLANCHANGE.value()).equals(kind)){
				// 10 进度计划调整审核接口
				PlanChange planEntity=planChangeService.find(entity.getRelatedFormId());
				planEntity.setFlowStatus(Integer.parseInt(flowStatus));
				planChangeService.setData(planEntity);
				// 进度计划变更审批后，更新进度计划表
				Plan plan = planService.find(planEntity.getPlan().getId());
				plan.setPlanName(planEntity.getPlanName());
				plan.setPlanVersion(planEntity.getPlanVersion());
				plan.setRemark(planEntity.getRemark());
				planService.setData(plan);
				// 进度计划变更审批后，更新计划任务表数据
				CommonFilter f = new CommonFilter();
				f = new CommonFilter().addExact("planChange.id", planEntity.getId());
				List<PlanChangeTask> tasks = planChangeTaskService.list(f,null);
				PlanTask planTask;
				for (int i = 0; i < tasks.size(); i++) {
					PlanChangeTask task = tasks.get(i);
					planTask = planTaskService.find(task.getPlanTask().getId());
//					planTask.setId(task.getPlanChange().getId());
					
					planTask.setmDatetime(task.getmDatetime());
					task.setCreator(task.getCreator());
					task.setModifier(task.getModifier());

//					if (!StringUtils.isEmpty(task.getPlan())) {
//						planTask.setPlan(task.getPlan());
//					}

					planTask.setTaskNum(task.getTaskNum());
					planTask.setPid(task.getPid());
					planTask.setTaskLayer(task.getTaskLayer());
					planTask.setTaskName(task.getTaskName());
					planTask.setIsSummary(task.getIsSummary());
					planTask.setIsMilestone(task.getIsMilestone());
					planTask.setIsCritical(task.getIsCritical());
					planTask.setDurationDays(task.getDurationDays());

					if (!StringUtils.isEmpty(task.getPlanStartDate())) {
						planTask.setPlanStartDate(task.getPlanStartDate());
					}

					if (!StringUtils.isEmpty(task.getPlanEndDate())) {
						planTask.setPlanEndDate(task.getPlanEndDate());
					}

//					planTask.setDueProgressDays(task.getDueProgressDays());
					planTask.setWbs(task.getWbs());
					planTask.setWorkload(task.getWorkload());
					planTask.setWeight(task.getWeight());
//					planTask.setPriority(task.getPriority());
					planTask.setLeader(task.getLeader());

					planTask.setParticipantNameList(task.getParticipantNameList());
					planTask.setDeadlineDate(task.getDeadlineDate());
					planTask.setConstraintType(task.getConstraintType());

					planTask.setConstraintDate(task.getConstraintDate());
					planTask.setIsSiteTask(task.getIsSiteTask());
					planTask.setConstructionStatus(task.getConstructionStatus());
					planTask.setSiteSegmentId(task.getSiteSegmentId());
					planTask.setParticipantList(task.getParticipantList());
					planTask.setParticipantNameList(task.getParticipantNameList());

//					planTask.setActualStartDate(task.getActualStartDate());

//					planTask.setActualEndDate(task.getActualEndDate());

//					planTask.setActualDurationDays(task.getActualDurationDays());
//					planTask.setProgress(task.getProgress());
//					planTask.setProgressDays(task.getProgressDays());
//					planTask.setIsWarning(task.getIsWarning());
//					planTask.setIsBehindPlanStart(task.getIsBehindPlanStart());
//					planTask.setIsBehindPlanEnd(task.getIsBehindPlanEnd());
//					planTask.setIsBehindSchedule(task.getIsBehindSchedule());
//					planTask.setIsDelay(task.getIsDelay());
//					planTask.setDelayCause(task.getDelayCause());
//
//					planTask.setProgressDate(task.getProgressDate());

					planTask.setTaskType(task.getTaskType());
//					planTask.setScore(task.getScore());
//					planTask.setProgressRemark(task.getProgressRemark());
					planTask.setAchievement(task.getAchievement());
					planTask.setDelivery(task.getDelivery());
//					planTask.setIsComment(task.getIsComment());
					planTask.setRemark(task.getRemark());
					planTaskService.update(planTask);
				}
				// t_plan_task_pre_task表更新
//				CommonFilter prefilter = new CommonFilter();
//				prefilter = new CommonFilter().addExact("planChangeId", planEntity.getId());
//				List<PlanTaskPreTask> preTasks = preTaskService.list(prefilter, null);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECTCHANGE.value()).equals(kind)){
				// 11 项目变更审核接口
				ProjectChange projectChange = projectChangeService.find(entity.getRelatedFormId());
				projectChange.setFlowStatus(Integer.parseInt(flowStatus));
				projectChange.setFlowEndDate(new Date());
				projectChangeService.setData(projectChange);
				//项目变更审批成功后，将变更信息更新原始数据表
				Project project = projectService.find(projectChange.getProject().getId());
				project.setProjectKind(projectChange.getProjectKind());
				project.setGovernmentProjectNum(projectChange.getGovernmentProjectNum());
				project.setApplicant(projectChange.getApplicant());
				project.setApplicantMobile(projectChange.getApplicantMobile());
				project.setProjectSet(projectChange.getProjectSet());
				projectService.setData(project);
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECT_CHECK.value()).equals(kind)){
				// 12 检查申请审核接口
				ProjectCheck projectCheck = projectCheckService.find(entity.getRelatedFormId());
				projectCheck.setFlowStatus(Integer.parseInt(flowStatus));
				projectCheck.setFlowEndDate(new Date());
				projectCheckService.setData(projectCheck);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECT_INSPECT.value()).equals(kind)){
				// 13 巡查计划审核接口
				ProjectInspect projectInspect = projectInspectService.find(entity.getRelatedFormId());
				projectInspect.setFlowStatus(Integer.parseInt(flowStatus));
				projectInspect.setFlowEndDate(new Date());
				projectInspectService.setData(projectInspect);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECT_PERFORMANCE_AWARD.value()).equals(kind)){
				// 14 绩效奖罚审核接口
				ProjectPerformanceAward projectPerformanceAward = projectPerformanceAwardService.find(entity.getRelatedFormId());
				projectPerformanceAward.setFlowStatus(Integer.parseInt(flowStatus));
				projectPerformanceAward.setFlowEndDate(new Date());
				projectPerformanceAwardService.setData(projectPerformanceAward);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECT_ACCEPTANCE.value()).equals(kind)){
				// 15 验收申请审核接口
				AcceptanceCheck acceptanceCheck = acceptanceCheckService.find(entity.getRelatedFormId());
				acceptanceCheck.setFlowStatus(Integer.parseInt(flowStatus));
				acceptanceCheck.setFlowEndDate(new Date());
				acceptanceCheckService.setData(acceptanceCheck);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_PROJECT_REVIEW.value()).equals(kind)){
				// 16 评审会申请审核接口
				Review review = reviewService.find(entity.getRelatedFormId());
				review.setFlowStatus(Integer.parseInt(flowStatus));
				review.setFlowEndDate(new Date());
				reviewService.setData(review);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_ARCHIVE_LEND.value()).equals(kind)){
				// 19 借阅申请审核接口
				PaperLibraryLendHistory paperLibraryLendHistory = paperLibraryLendHistoryService.find(entity.getRelatedFormId());
				paperLibraryLendHistory.setFlowStatus(Integer.parseInt(flowStatus));
				paperLibraryLendHistoryService.setData(paperLibraryLendHistory);
				
				PaperLibrary paperLibrary = paperLibraryService.find(paperLibraryLendHistory.getPaperLibrary().getId());
				int waitTake = PaperLibraryStatusEnum.WAIT_TAKE.value();
				paperLibrary.setStatus((short)waitTake);
				paperLibraryService.update(paperLibrary);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_CAPITALPLAN.value()).equals(kind)){
				// 20 资金计划审批接口
				CapitalApply capitalApply = capitalApplyService.find(entity.getRelatedFormId());
				capitalApply.setFlowStatus(Integer.parseInt(flowStatus));
				//capitalApply.setFlowEndDate(new Date());
				capitalApplyService.setData(capitalApply);
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_BUDGET.value()).equals(kind)){
				// 21 成本预算审批接口
				BudgetEstimateMain budgetEstimateMain = budgetEstimateMainService.find(entity.getRelatedFormId());
				budgetEstimateMain.setFlowStatus(Integer.parseInt(flowStatus));
				budgetEstimateMain.setFlowEndDate(new Date());
				budgetEstimateMainService.setData(budgetEstimateMain);
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_BUDGETCHANGE.value()).equals(kind)){
				// 22 成本预算变更审核接口
				String[] str = entity.getRelatedFormId().split(",");
				String projectId = str[0];
				String year = str[1];
				String groupId = str[2];
				
	  			CommonFilter f = new CommonFilter();
	  			f = new CommonFilter().addExact("project.id", projectId).addExact("group.id", groupId).addExact("year", Integer.parseInt(year)).addExact("auditStatus", "1");
	  			List<BudgetEstimateChange> budgetChangeList = this.budgetEstimateChangeService.list(f, null);
	  			
	  			//审批通过生成版本，将原数据加上版本号
		    	if(flowStatus.equals("2")) {
		    		//查所有版本，为了生成版本号
					List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(null, null);
					
			        //版本号——年月日+0000+_年份（例：202004130001）
			        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
			        java.util.Date currTime = new java.util.Date();
			        String curTime = formatter.format(currTime);
			        String code = curTime+String.format("%04d", versionList.size()+1); 
			        
			        //新增版本
					BudgetEstimateVersionBean budgetEstimateVersionBean = new BudgetEstimateVersionBean();
					budgetEstimateVersionBean.setYear(Integer.parseInt(year));
					budgetEstimateVersionBean.setVersion(code);
					budgetEstimateVersionBean.setName("1");
					budgetEstimateVersionBean.setProject_(projectId);
					BudgetEstimateVersionBeanConverter converter = new BudgetEstimateVersionBeanConverter();
					AbstractSessionUserBean userBean = new AbstractSessionUserBean(); 
					userBean.setSessionUserId(entity.getCreator());
					BudgetEstimateVersion budgetEstimateVersion = converter.toEntity(budgetEstimateVersionBean, null, userBean, new Date());
					budgetEstimateVersionService.create(budgetEstimateVersion);
			        
			        //将版本号加到预算表中的记录里
	    			for (BudgetEstimateChange budgetEntity : budgetChangeList) {
	    				budgetEntity.setAuditStatus(flowStatus);
		    			budgetEntity.setAuditDatetime(new Date());
		    			budgetEstimateChangeService.setData(budgetEntity);
	    			}
	    			
	    			/*将初版赋值版本号后新增到预算表中*/
	    			for(int i=0;i<budgetChangeList.size();i++) {
	    				BudgetEstimate budget1 = new BudgetEstimate();
	    				budget1.setProject(budgetChangeList.get(i).getProject());
	    				budget1.setCode(budgetChangeList.get(i).getCode());
	    				budget1.setInnerCode(budgetChangeList.get(i).getInnerCode());
	    				budget1.setOrderNumber(budgetChangeList.get(i).getOrderNumber());
	    				budget1.setName(budgetChangeList.get(i).getName());
	    				budget1.setVersion(budgetEstimateVersion.getId());
	    				budget1.setGroup(budgetChangeList.get(i).getGroup());
	    				budget1.setMeasurementUnit(budgetChangeList.get(i).getMeasurementUnit());
	    				budget1.setCount(budgetChangeList.get(i).getCount());
	    				budget1.setTaxInclusivePrice(budgetChangeList.get(i).getTaxInclusivePrice());
	    				budget1.setTaxExcludingPrice(budgetChangeList.get(i).getTaxExcludingPrice());
	    				budget1.setTaxTotalPrice(budgetChangeList.get(i).getTaxTotalPrice());
	    				budget1.setRemarks(budgetChangeList.get(i).getRemarks());
	    				budget1.setYear(budgetChangeList.get(i).getYear());
	    				budget1.setTaxPoint(budgetChangeList.get(i).getTaxPoint());
	    				budget1.setDeviceClassify(budgetChangeList.get(i).getDeviceClassify());
	    				budget1.setPersonMonth(budgetChangeList.get(i).getPersonMonth());
	    				budget1.setPersonPrice(budgetChangeList.get(i).getPersonPrice());
	    				budget1.setCreator(userBean.getSessionUserId());
	    				budget1.setcDatetime(new Date());
	    				budget1.setmDatetime(new Date());

	    				this.budgetEstimateService.create(budget1);
	    			}
		    	}
				
			} else if(String.valueOf(OAAuditApiType.OA_TYPE_BUDGETFINAL.value()).equals(kind)){
				// 23 经费决算审核接口
				String[] str = entity.getRelatedFormId().split(",");
				String projectId = str[0];
				String year = str[1];
				
	  			CommonFilter f = new CommonFilter();
	  			f = new CommonFilter().addExact("project.id", projectId).addExact("year", Integer.parseInt(year)).addExact("auditStatus", "1");
	  			List<BudgetFinal> budgetFinalList = this.budgetFinalService.list(f, null);
	  			
	  			//审批通过生成版本，将原数据加上版本号
		    	if(flowStatus.equals("2")) {
		    		//查所有版本，为了生成版本号
					List<BudgetFinalVersion> versionList = budgetFinalVersionService.list(null, null);
					
			        //版本号——年月日+0000+_年份（例：202004130001）
			        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
			        java.util.Date currTime = new java.util.Date();
			        String curTime = formatter.format(currTime);
			        String code = curTime+String.format("%04d", versionList.size()+1); 
			        
			        //新增版本
			        BudgetFinalVersionBean budgetFinalVersionBean = new BudgetFinalVersionBean();
			        budgetFinalVersionBean.setVersion(code);
			        budgetFinalVersionBean.setProject_(projectId);
			        BudgetFinalVersionBeanConverter converter = new BudgetFinalVersionBeanConverter();
					AbstractSessionUserBean userBean = new AbstractSessionUserBean(); 
					userBean.setSessionUserId(entity.getCreator());
					BudgetFinalVersion budgetFinalVersion = converter.toEntity(budgetFinalVersionBean, null, userBean, new Date());
					budgetFinalVersionService.create(budgetFinalVersion);
					
					/*根据版本号查找刚新增的版本id*/
			        CommonFilter f2 = new CommonFilter().addExact("version", code).addExact("project.id", projectId);
					List<BudgetEstimateVersion> versionList1 = budgetEstimateVersionService.list(f2, null);
					String versionId = versionList1.get(0).getId();
			        
			        //将版本号加到资金计划表中的记录里
			    	for(BudgetFinal budgetEntity:budgetFinalList) {
			    		budgetEntity.setVersionId(budgetFinalVersion.getId());
			    		budgetEntity.setAuditStatus(flowStatus);
		    			budgetEntity.setAuditDatetime(new Date());
		    			budgetFinalService.setData(budgetEntity);
			    	}
		    	}
			}
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage("通知处理成功");
		} else {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage("请求数据异常未查到对应的数据");
		}
		return jsonResult;
	}

	public JsonResultBean projectInitAuditHandler(String dataId, String status) {
		JsonResultBean jsonResult = new JsonResultBean();
		Project project = projectService.find(dataId);

		if (project != null) {
			DataOption dataOption = null;
			// 项目目前状态  0:建议, 1:可研, 2_0:初设, 2_1:方案, 3:概算,  4_0:建设, 4_1:实施,  5:阶段性验收, 6:初验, 7:终验, 8:运维
			String projectStatusId = project.getProjectStatus().getId();
			// 项目类别  0:工程类  1:科研类  2:复合类
			String projectKindId = project.getProjectKind().getId();
			
			if (projectStatusId.equals("PROJECT_STATUS_0")) {
				// 建议 >> 可研
				dataOption = dataOptionService.find("PROJECT_STATUS_1");
				project.setFlowStatusProject(FlowStatusEnum.UNSUBMITTED.value());
			} else if (projectStatusId.equals("PROJECT_STATUS_1") && projectKindId.equals("PROJECT_KIND_0")) {
				// 可研(工程类) >> 初设
				dataOption = dataOptionService.find("PROJECT_STATUS_2_0");
				project.setFlowStatusProject(FlowStatusEnum.UNSUBMITTED.value());
			} else if (projectStatusId.equals("PROJECT_STATUS_1") && projectKindId.equals("PROJECT_KIND_1")) {
				// 可研(科研类) >> 方案
				dataOption = dataOptionService.find("PROJECT_STATUS_2_1");
				project.setFlowStatusProject(FlowStatusEnum.UNSUBMITTED.value());
			} else if (projectStatusId.equals("PROJECT_STATUS_2_1") || projectStatusId.equals("PROJECT_STATUS_2_0")) {
				// 初设 >> 概算
				dataOption = dataOptionService.find("PROJECT_STATUS_3");
// 项目立项阶段不进行以下状态的迁移
//			} else if (projectStatusId.equals("PROJECT_STATUS_3") && projectKindId.equals("PROJECT_KIND_0")) {
//				// 概算(工程类) >> 建设
//				dataOption = dataOptionService.find("PROJECT_STATUS_4_0");
//			} else if (projectStatusId.equals("PROJECT_STATUS_3") && projectKindId.equals("PROJECT_KIND_1")) {
//				// 可研(科研类) >> 实施
//				dataOption = dataOptionService.find("PROJECT_STATUS_4_1");
//			} else if (projectStatusId.equals("PROJECT_STATUS_4_1")|| projectStatusId.equals("PROJECT_STATUS_4_0")) {
//				// 建设 >> 阶段性验收
//				dataOption = dataOptionService.find("PROJECT_STATUS_5");
//			} else if (projectStatusId.equals("PROJECT_STATUS_5")) {
//				// 阶段性验收 >> 初验
//				dataOption = dataOptionService.find("PROJECT_STATUS_6");
//			} else if (projectStatusId.equals("PROJECT_STATUS_6")) {
//				// 初验 >> 终验
//				dataOption = dataOptionService.find("PROJECT_STATUS_7");
//			} else if (projectStatusId.equals("PROJECT_STATUS_7")) {
//				// 终验 >> 运维
//				dataOption = dataOptionService.find("PROJECT_STATUS_8");
			}
			if (dataOption != null) {
				project.setProjectStatus(dataOption);
				projectService.setData(project);
				jsonResult.setStatus(JsonStatus.OK);
				jsonResult.setMessage("业务处理成功");
			} else {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage("系统异常数据字典异常");
			}

		} else {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage("没有对应的project数据");
		}
		return jsonResult;
	}
}
