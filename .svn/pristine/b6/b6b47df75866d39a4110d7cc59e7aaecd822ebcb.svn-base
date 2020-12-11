package project.edge.web.apiService.plan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanProgress;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SiteService;
import project.edge.service.schedule.PlanProgressService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskService;

@Service
@Transactional
public class PlanApiServiceImpl implements PlanApiService {
	
	@Resource
	private PlanService planService;
	
	@Resource
	private PlanTaskService planTaskService;
	
	@Resource
	private PlanProgressService planProgressService;
	
	@Resource
	private SiteService siteService;
	
	@Resource
	private SegmentService segmentService;
	
	@Override
	public void removePlan(String planId) {

		CommonFilter filter = new CommonFilter();
        filter.addExact("plan.id", planId);
        
		List<PlanProgress> progressList = planProgressService.list(filter, null);
		System.out.println("待删除的计划进度，长度=" + progressList.size());
		for (PlanProgress progress : progressList) {
			planProgressService.delete(progress);
		}
		
		List<PlanTask> taskList = planTaskService.list(filter, null);
		System.out.println("待删除的计划任务，长度=" + taskList.size());
		for (PlanTask task : taskList) {
			planTaskService.delete(task);
		}
		
		Plan plan = planService.find(planId);
		planService.delete(plan);
	}
	
	@Override
	public void updateSiteProgress(String siteId) {
		
		if (siteId == null) {
			return;
		}
		
		Site site = siteService.find(siteId);
		if (site == null) {
			return;
		}
		
		// 取得计划列表
		CommonFilter filter = new CommonFilter().addExact("si.id", siteId);
		List<Plan> planList = planService.list(filter, null);
		
		site.setProgrammableNetworkProgress("");
		site.setSdnNetworkProgress("");
		site.setBasicNetworkProgress("");
		
		// 统计已完成的任务数
		int completeTaskCount = 0;
		int totalTaskCount = 0;
		
		// 对于每一个Plan，找出最新PlanTask状态
		for (Plan plan: planList) {
			
			
			/************** Step1: 更新计划的进度状态 **************/
			
			filter = new CommonFilter().addExact("p.id", plan.getId());
			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));
			List<PlanTask> planTaskList = planTaskService.list(filter, orders);
			
			PlanTask lastTask = null;
			
			for (int i = 0; i < planTaskList.size(); i++) {
				PlanTask pTask = planTaskList.get(i);
				if (i == 0) {
					if (!StringUtils.isEmpty(pTask.getProgress()) && pTask.getProgress() != 100) {
						plan.setStatus(pTask.getConstructionStatus());
						break;
					} else {
						plan.setActualEndDate(pTask.getActualEndDate());
						plan.setStatus(pTask.getConstructionStatus());
						continue;
					}
				} else {
					lastTask = planTaskList.get(i - 1);
					if (StringUtils.isEmpty(pTask.getProgress()) || pTask.getProgress() < 100) {
						plan.setStatus(lastTask.getConstructionStatus());
						break;
					} else {
						if (i == planTaskList.size() - 1) {
							plan.setActualEndDate(pTask.getActualEndDate());
						}
						plan.setStatus(pTask.getConstructionStatus());
						continue;
					}
				}
			}
			
			/***************** Step2: 更新对应的Plan **********************/
			planService.update(plan);
			
			/***************** Step3: 更新站点中进展字段 *********************/
			String progressText = "";
			
			totalTaskCount += planTaskList.size();
			for (int i = 0; i < planTaskList.size(); i++) {

				PlanTask pTask = planTaskList.get(i);
				Double taskProgress = pTask.getProgress();
				
				if (i == 0) {
					progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
				}
				else if (taskProgress != null) {
					progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
				}
				
				if (taskProgress != null && taskProgress.intValue() == 100) {
					completeTaskCount++;
				}
			}

			if(progressText.equals("")) {
				PlanTask firstPlanTask = planTaskList.get(0);
				progressText = "[" + firstPlanTask.getTaskNum() + "-" + firstPlanTask.getTaskName() + "]";
			}
			
			if (plan.getPlanName().indexOf("边缘节点") < 0) {
				if (plan.getVirtualOrg().getVirtualOrgName().startsWith("SDN")) {
					site.setSdnNetworkProgress(progressText);
				} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("基础网络")) {
					site.setBasicNetworkProgress(progressText);
				} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("可编程")) {
					site.setProgrammableNetworkProgress(progressText);
				}
			} else {
				if (plan.getVirtualOrg().getVirtualOrgName().startsWith("SDN")) {
					site.setSdnEdgeProgress(progressText);
				} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("可编程")) {
					site.setProgrammableEdgeProgress(progressText);
				}
			}
			
			
		}
		
		// 更新站点的总进度字段
		int totalProgress = (int) (new Float(completeTaskCount) / new Float(totalTaskCount) * 100);
		System.out.println(site.getStationName() + " cur=" + completeTaskCount + " total=" + totalTaskCount + " progress=" + totalProgress);
		site.setTotalProgress(totalProgress);
		
		siteService.update(site);
		
	}

	@Override
	public void updateSegmentProgress(String segmentId) {

		if (segmentId == null) {
			return;
		}
		
		Segment segment = segmentService.find(segmentId);
		if (segment == null) {
			return;
		}
		
		// 取得计划列表
		CommonFilter filter = new CommonFilter().addExact("se.id", segmentId);
		List<Plan> planList = planService.list(filter, null);
		
//		site.setProgrammableNetworkProgress("");
//		site.setSdnNetworkProgress("");
//		site.setBasicNetworkProgress("");
		
		// 对于每一个Plan，找出最新PlanTask状态
		for (Plan plan: planList) {
			
			/************** Step1: 更新计划的进度状态 **************/
			
			filter = new CommonFilter().addExact("p.id", plan.getId());
			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));
			List<PlanTask> planTaskList = planTaskService.list(filter, orders);
			
			for (int i = 0; i < planTaskList.size(); i++) {

				PlanTask pTask = planTaskList.get(i);
				
				if (pTask.getProgress() == null || pTask.getProgress() != 100) {
					plan.setStatus(pTask.getConstructionStatus());
					break;
				} else {
					if (i == planTaskList.size() - 1) {
						plan.setActualEndDate(pTask.getActualEndDate());
					}
					plan.setStatus(pTask.getConstructionStatus());
					continue;
				}
			}
			
			/***************** Step2: 更新对应的Plan **********************/
			planService.update(plan);
			
			/***************** Step3: 更新站点中进展字段 *********************/
//			Collections.reverse(planTaskList);
			String progressText = "";
//			for (PlanTask pTask: planTaskList) {
//				if (pTask.getProgress() != null && pTask.getProgress() > 0) {
//					progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
//					break;
//				}
//			}
			for (int i = 0; i < planTaskList.size(); i++) {
				PlanTask pTask = planTaskList.get(i);
				
				if (i == 0 && StringUtils.isEmpty(pTask.getProgress())) {
					progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
					break;
				}
				
				if (i != 0 && StringUtils.isEmpty(pTask.getProgress())) {
					break;
				}
				
				if ("0.0".equals(pTask.getProgress().toString())) {
					break;
				}
				
				if (pTask.getProgress() != 100) {
					progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
					break;
				} else {
					progressText = "[" + pTask.getTaskNum() + "-" + pTask.getTaskName() + "]";
					continue;
				}
			}
			if(progressText.equals("")) {
				PlanTask firstPlanTask = planTaskList.get(planTaskList.size() - 1);
				progressText = "[" + firstPlanTask.getTaskNum() + "-" + firstPlanTask.getTaskName() + "]";
			}
			
//			if (plan.getVirtualOrg().getVirtualOrgName().startsWith("SDN")) {
//				site.setSdnNetworkProgress(progressText);
//			} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("基础网络")) {
//				site.setBasicNetworkProgress(progressText);
//			} else if (plan.getVirtualOrg().getVirtualOrgName().startsWith("可编程")) {
//				site.setProgrammableNetworkProgress(progressText);
//			}
			segment.setProgress(progressText);
			segmentService.update(segment);
		}
	}
}
