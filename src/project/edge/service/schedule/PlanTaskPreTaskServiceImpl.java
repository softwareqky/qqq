package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanTaskPreTaskDao;
import project.edge.domain.entity.PlanTaskPreTask;

/**
 * @author angel_000
 *         [t_plan_task_pre_task]对应的 Service。
 */
@Service
public class PlanTaskPreTaskServiceImpl extends GenericServiceImpl<PlanTaskPreTask, String>
        implements PlanTaskPreTaskService {

    @Resource
    private PlanTaskPreTaskDao planTaskPreTaskDao;

    @Override
    public Dao<PlanTaskPreTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planTaskPreTaskDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
