package project.edge.dao.schedule;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PlanCalendarExceptions;

/**
 * @author angel_000
 *         [t_plan_calendar_exceptions]对应的DAO。
 */
@Repository
public class PlanCalendarExceptionsDaoImpl extends HibernateDaoImpl<PlanCalendarExceptions, String>
        implements PlanCalendarExceptionsDao {

    @Override
    public void deleteExceptionWithCalendarId(String planCalendarId) {
        session().createQuery("delete from PlanCalendarExceptions where calendar.id = :calendarId")
                .setString("calendarId", planCalendarId).executeUpdate();
    }

}
