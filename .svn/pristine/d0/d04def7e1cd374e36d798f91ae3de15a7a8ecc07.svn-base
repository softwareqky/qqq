package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanProgressTaskDao;
import project.edge.domain.entity.PlanProgressTask;

/**
 * @author angel_000
 *         [t_plan_progress_task]对应的 Service。
 */
@Service
public class PlanProgressTaskServiceImpl extends GenericServiceImpl<PlanProgressTask, String>
        implements PlanProgressTaskService {

    @Resource
    private PlanProgressTaskDao planProgressTaskDao;

    @Override
    public Dao<PlanProgressTask, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planProgressTaskDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
