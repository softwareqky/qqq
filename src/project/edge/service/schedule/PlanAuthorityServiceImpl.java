package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanAuthorityDao;
import project.edge.domain.entity.PlanAuthority;

/**
 * @author angel_000
 *         [t_plan_authority]对应的 Service。
 */
@Service
public class PlanAuthorityServiceImpl extends GenericServiceImpl<PlanAuthority, String>
        implements PlanAuthorityService {

    @Resource
    private PlanAuthorityDao planAuthorityDao;

    @Override
    public Dao<PlanAuthority, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planAuthorityDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
