package project.edge.service.schedule;

import java.util.List;
import java.util.Locale;

import garage.origin.domain.view.JsonResultBean;
import garage.origin.service.Service;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000 [t_plan]对应的 Service。
 */
public interface PlanService extends Service<Plan, String> {

	/**
	 * 创建新建站点时的默认任务
	 * 
	 * @param plan
	 * @param planTasks
	 */
	void createPresetPlan(Plan plan, List<PlanTask> planTasks);
	
	List<Plan> findList(String projectId, String virtualOrgId, String nowDate);

	public void setData(Plan entity);

	public JsonResultBean list(String commonFilterJson, int page, int rows, String sort, String order, Locale locale);
}
