package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanCalendar;

/**
 * @author angel_000
 *         [t_plan_calendar]对应的DAO。
 */
@Repository
public class PlanCalendarDaoImpl extends HibernateDaoImpl<PlanCalendar, String>
        implements PlanCalendarDao {

}
