package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanChangeTask;


/**
 * [t_plan_change_task]对应的DAO。
 */
@Repository
public class PlanChangeTaskDaoImpl extends HibernateDaoImpl<PlanChangeTask, String>
        implements PlanChangeTaskDao {

    @Override
    public void deleteTasksWithPlanId(String planId) {
        session().createQuery("delete from PlanChangeTask where planChange.id = :planId")
                .setString("planId", planId).executeUpdate();
    }

}
