package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanHistoryTaskComment;

/**
 * @author angel_000
 *         [t_plan_history_task_comment]对应的DAO。
 */
@Repository
public class PlanHistoryTaskCommentDaoImpl extends HibernateDaoImpl<PlanHistoryTaskComment, String>
        implements PlanHistoryTaskCommentDao {

}
