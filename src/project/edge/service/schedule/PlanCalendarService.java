package project.edge.service.schedule;

import java.util.List;

import garage.origin.service.Service;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.entity.PlanCalendarExceptions;

/**
 * @author angel_000
 *         [t_plan_calendar]对应的 Service。
 */
public interface PlanCalendarService extends Service<PlanCalendar, String> {

    /**
     * 更新日历与例外日期
     * 
     * @param planCalendar
     * @param planCalendarExceptions
     */
    void updateCalendarAndExceptions(PlanCalendar planCalendar,
            List<PlanCalendarExceptions> planCalendarExceptions);

}
