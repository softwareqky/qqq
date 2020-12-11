package project.edge.dao.budget;

import java.util.Date;
import java.util.List;

import garage.origin.dao.Dao;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000
 *         [t_project_person]对应的DAO。
 */
public interface BudgetEstimateVersionDao extends Dao<BudgetEstimateVersion, String> {
	
	public List<BudgetEstimateVersion> getVersionByYear(int year,String project);
	
	public List<Plan> getPlanVersionByYear(int year,String project);
	
	public List<PlanTask> getTaskByPlanId(String planId);
	
	public List<BudgetEstimate> getbudgetByGroup(Date cDatetime, String groupKey);
	
	public List<BudgetEstimate> getLastBudgetByGroup(String year,String projectId, String groupId);

}
