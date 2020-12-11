package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanHistoryDao;
import project.edge.domain.entity.PlanHistory;

/**
 * @author angel_000
 *         [t_plan_history]对应的 Service。
 */
@Service
public class PlanHistoryServiceImpl extends GenericServiceImpl<PlanHistory, String>
        implements PlanHistoryService {

    @Resource
    private PlanHistoryDao planHistoryDao;

    @Override
    public Dao<PlanHistory, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planHistoryDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
