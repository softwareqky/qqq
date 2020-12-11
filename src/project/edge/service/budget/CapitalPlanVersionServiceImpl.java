package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.CapitalPlanVersionDao;
import project.edge.domain.entity.CapitalPlanVersion;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
@Service
public class CapitalPlanVersionServiceImpl extends GenericServiceImpl<CapitalPlanVersion, String>
        implements CapitalPlanVersionService {

    @Resource
    private CapitalPlanVersionDao capitalPlanVersionDao;

    @Override
    public Dao<CapitalPlanVersion, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.capitalPlanVersionDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
    
}
