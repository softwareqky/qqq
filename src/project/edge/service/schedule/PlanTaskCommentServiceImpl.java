package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanTaskCommentDao;
import project.edge.domain.entity.PlanTaskComment;

/**
 * @author angel_000
 *         [t_plan_task_comment]对应的 Service。
 */
@Service
public class PlanTaskCommentServiceImpl extends GenericServiceImpl<PlanTaskComment, String>
        implements PlanTaskCommentService {

    @Resource
    private PlanTaskCommentDao planTaskCommentDao;

    @Override
    public Dao<PlanTaskComment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planTaskCommentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
