package project.edge.service.schedule;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import garage.origin.service.Service;
import project.edge.domain.entity.PlanProgress;
import project.edge.domain.entity.PlanProgressTask;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.PlanTaskPreTask;

/**
 * [t_plan_task]对应的 Service。
 */
public interface PlanTaskService extends Service<PlanTask, String> {

    /**
     * 保存gantt数据
     * 
     * @param planId
     * @param planTasks
     * @param planTaskPreTasks
     */
    void createTasksAndLinks(String planId, List<PlanTask> planTasks,
            List<PlanTaskPreTask> planTaskPreTasks) throws IllegalAccessException, InvocationTargetException;

    /**
     * 保存进度上报数据
     */
    void createPlanProgress(PlanProgress planProgress, List<PlanProgressTask> progressTasks,
            List<PlanTask> planTasks);
    
    List<PlanTask> findList(String projectId, String virtualOrgId, String nowDate);
    
    List<PlanTask> findOverdueList();
}
