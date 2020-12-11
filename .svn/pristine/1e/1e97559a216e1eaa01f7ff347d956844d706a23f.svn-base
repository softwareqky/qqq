/**
 * 
 */
package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.CapitalPlanDao;
import project.edge.domain.entity.CapitalPlan;


/**
 * @author wwy
 *         [t_capital_plan]对应的 Service。
 */
@Service
public class CapitalPlanServiceImpl extends GenericServiceImpl<CapitalPlan, String> implements CapitalPlanService {

    @Resource
    private CapitalPlanDao capitalPlanDao;

    @Override
    public Dao<CapitalPlan, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.capitalPlanDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
    
	@Override
	@Transactional
	public void setData(CapitalPlan entity) {
		// TODO Auto-generated method stub
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
	}

}
