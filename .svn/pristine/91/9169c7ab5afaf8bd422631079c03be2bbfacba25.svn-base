package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanCalendarExceptionsDao;
import project.edge.domain.entity.PlanCalendarExceptions;

/**
 * @author angel_000
 *         [t_plan_calendar_exceptions]对应的 Service。
 */
@Service
public class PlanCalendarExceptionsServiceImpl
        extends GenericServiceImpl<PlanCalendarExceptions, String>
        implements PlanCalendarExceptionsService {

    @Resource
    private PlanCalendarExceptionsDao planCalendarExceptionsDao;

    @Override
    public Dao<PlanCalendarExceptions, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planCalendarExceptionsDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
