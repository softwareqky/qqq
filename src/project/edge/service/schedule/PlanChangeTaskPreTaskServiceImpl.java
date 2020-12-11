/**
 * 
 */
package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanChangeTaskPreTaskDao;
import project.edge.domain.entity.PlanChangeTaskPreTask;


/**
 * @author angel_000
 *         [t_plan_change_task_pre_task]对应的 Service。
 */
@Service
public class PlanChangeTaskPreTaskServiceImpl extends
        GenericServiceImpl<PlanChangeTaskPreTask, String> implements PlanChangeTaskPreTaskService {
    
    @Resource
    private PlanChangeTaskPreTaskDao planChangeTaskPreTaskDao;

    @Override
    public Dao<PlanChangeTaskPreTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planChangeTaskPreTaskDao;
    }

}
