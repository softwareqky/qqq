package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanCalendarDao;
import project.edge.dao.schedule.PlanCalendarExceptionsDao;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.entity.PlanCalendarExceptions;

/**
 * @author angel_000
 *         [t_plan_calendar]对应的 Service。
 */
@Service
public class PlanCalendarServiceImpl extends GenericServiceImpl<PlanCalendar, String>
        implements PlanCalendarService {

    @Resource
    private PlanCalendarDao planCalendarDao;

    @Resource
    private PlanCalendarExceptionsDao planCalendarExceptionsDao;

    @Override
    public Dao<PlanCalendar, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planCalendarDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

    @Override
    @Transactional
    public void updateCalendarAndExceptions(PlanCalendar planCalendar,
            List<PlanCalendarExceptions> planCalendarExceptions) {

        this.planCalendarDao.update(planCalendar);
        this.planCalendarExceptionsDao.deleteExceptionWithCalendarId(planCalendar.getId());

        if (!planCalendarExceptions.isEmpty()) {
            planCalendarExceptionsDao.create(planCalendarExceptions);
        }
    }
}
