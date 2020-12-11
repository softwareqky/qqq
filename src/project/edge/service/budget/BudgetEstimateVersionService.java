package project.edge.service.budget;

import java.util.Date;
import java.util.List;

import garage.origin.service.Service;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
public interface BudgetEstimateVersionService extends Service<BudgetEstimateVersion, String> {
    /**
     * 年度使用计划
     */
	public List<BudgetEstimateVersion> getVersionByYear(int year,String project);
	
    /**
     * 根据年份获取总计划所有版本
     */
	public List<Plan> getPlanVersionByYear(int year,String project);
	
    /**
     * 根据计划id获取该版本下所有任务
     */
	public List<PlanTask> getTaskByPlanId(String planId);
	
    /**
     * 根据任务的创建时间，项目组关键字获取预算金额
     */
	public List<BudgetEstimate> getbudgetByGroup(Date cDatetime, String groupKey);
	
    /**
     * 根据查找的年份中最新的版本id，查询内部编码是4位的各专业组的预算金额
     */
	public List<BudgetEstimate> getLastBudgetByGroup(String year,String projectId, String groupId);
}
