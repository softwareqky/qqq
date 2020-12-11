package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanTaskComment;

/**
 * @author angel_000
 *         [t_plan_task_comment]对应的DAO。
 */
@Repository
public class PlanTaskCommentDaoImpl extends HibernateDaoImpl<PlanTaskComment, String>
        implements PlanTaskCommentDao {

}
