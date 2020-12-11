package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanHistoryTaskCommentDao;
import project.edge.domain.entity.PlanHistoryTaskComment;

/**
 * @author angel_000
 *         [t_plan_history_task_comment]对应的 Service。
 */
@Service
public class PlanHistoryTaskCommentServiceImpl
        extends GenericServiceImpl<PlanHistoryTaskComment, String>
        implements PlanHistoryTaskCommentService {

    @Resource
    private PlanHistoryTaskCommentDao planHistoryTaskCommentDao;

    @Override
    public Dao<PlanHistoryTaskComment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planHistoryTaskCommentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
