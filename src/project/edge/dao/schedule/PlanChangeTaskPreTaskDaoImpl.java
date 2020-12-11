package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanChangeTaskPreTask;


/**
 * [t_plan_change_task_pre_task]对应的DAO。
 */
@Repository
public class PlanChangeTaskPreTaskDaoImpl extends HibernateDaoImpl<PlanChangeTaskPreTask, String>
        implements PlanChangeTaskPreTaskDao {

    @Override
    public void deletePreTaskWithPlanId(String planId) {
        session().createQuery("delete from PlanChangeTaskPreTask where planChange.id = :planId")
                .setString("planId", planId).executeUpdate();
    }

}
