package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanHistoryTaskDao;
import project.edge.domain.entity.PlanHistoryTask;

/**
 * @author angel_000
 *         [t_plan_history_task]对应的 Service。
 */
@Service
public class PlanHistoryTaskServiceImpl extends GenericServiceImpl<PlanHistoryTask, String>
        implements PlanHistoryTaskService {

    @Resource
    private PlanHistoryTaskDao planHistoryTaskDao;

    @Override
    public Dao<PlanHistoryTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planHistoryTaskDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
