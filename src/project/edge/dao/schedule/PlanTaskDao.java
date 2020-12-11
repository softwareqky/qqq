package project.edge.dao.schedule;

import java.util.List;

import garage.origin.dao.Dao;
import project.edge.domain.entity.PlanTask;

/**
 * @author angel_000
 *         [t_plan_task]对应的DAO。
 */
public interface PlanTaskDao extends Dao<PlanTask, String> {


    /**
     * 删除计划相关任务
     */
    void deleteTasksWithPlanId(String planId);

    List<PlanTask> findList(String projectId, String virtualOrgId, String nowDate);
    
    List<PlanTask> findOverdueList();
}
