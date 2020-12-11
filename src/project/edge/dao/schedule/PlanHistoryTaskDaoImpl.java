package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanHistoryTask;

/**
 * @author angel_000
 *         [t_plan_history_task]对应的DAO。
 */
@Repository
public class PlanHistoryTaskDaoImpl extends HibernateDaoImpl<PlanHistoryTask, String>
        implements PlanHistoryTaskDao {

}
