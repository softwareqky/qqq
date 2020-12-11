/**
 * 
 */
package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanChangeTaskDao;
import project.edge.dao.schedule.PlanChangeTaskPreTaskDao;
import project.edge.domain.entity.PlanChangeTask;
import project.edge.domain.entity.PlanChangeTaskPreTask;


/**
 * @author angel_000
 *         [t_plan_change_task]对应的 Service。
 */
@Service
public class PlanChangeTaskServiceImpl extends GenericServiceImpl<PlanChangeTask, String>
        implements PlanChangeTaskService {

    @Resource
    private PlanChangeTaskDao planChangeTaskDao;

    @Resource
    private PlanChangeTaskPreTaskDao planChangeTaskPreTaskDao;

    @Override
    public Dao<PlanChangeTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planChangeTaskDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("taskNum", false);
    }

    @Override
    @Transactional
    public void createTasksAndLinks(String planId, List<PlanChangeTask> planTasks,
            List<PlanChangeTaskPreTask> planTaskPreTasks) {

        // delete all history
        this.planChangeTaskDao.deleteTasksWithPlanId(planId);
        this.planChangeTaskPreTaskDao.deletePreTaskWithPlanId(planId);

        // create tasks and links
        if (!planTasks.isEmpty()) {
            this.planChangeTaskDao.create(planTasks);
        }

        if (!planTaskPreTasks.isEmpty()) {
            this.planChangeTaskPreTaskDao.create(planTaskPreTasks);
        }
    }

}
