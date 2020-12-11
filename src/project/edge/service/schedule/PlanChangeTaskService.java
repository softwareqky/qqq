/**
 * 
 */
package project.edge.service.schedule;

import java.util.List;

import garage.origin.service.Service;
import project.edge.domain.entity.PlanChangeTask;
import project.edge.domain.entity.PlanChangeTaskPreTask;


/**
 * @author angel_000
 *         [t_plan_change_task]对应的 Service。
 */
public interface PlanChangeTaskService extends Service<PlanChangeTask, String> {

    void createTasksAndLinks(String id, List<PlanChangeTask> planTasks,
            List<PlanChangeTaskPreTask> planTaskPreTasks);

}
