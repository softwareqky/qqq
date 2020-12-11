package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanTaskPreTask;

/**
 * [t_plan_task_pre_task]对应的DAO。
 */
@Repository
public class PlanTaskPreTaskDaoImpl extends HibernateDaoImpl<PlanTaskPreTask, String>
        implements PlanTaskPreTaskDao {

    @Override
    public void deletePreTaskWithPlanId(String planId) {
        session().createQuery("delete from PlanTaskPreTask where plan.id = :planId")
                .setString("planId", planId).executeUpdate();
    }

}
