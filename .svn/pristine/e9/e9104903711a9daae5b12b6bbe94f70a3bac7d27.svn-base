package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanHistoryTaskPreTaskDao;
import project.edge.domain.entity.PlanHistoryTaskPreTask;

/**
 * @author angel_000
 *         [t_plan_history_task_pre_task]对应的 Service。
 */
@Service
public class PlanHistoryTaskPreTaskServiceImpl
        extends GenericServiceImpl<PlanHistoryTaskPreTask, String>
        implements PlanHistoryTaskPreTaskService {

    @Resource
    private PlanHistoryTaskPreTaskDao planHistoryTaskPreTaskDao;

    @Override
    public Dao<PlanHistoryTaskPreTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planHistoryTaskPreTaskDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
