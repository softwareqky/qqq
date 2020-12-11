package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetEstimateMainDao;
import project.edge.domain.entity.BudgetEstimateMain;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
@Service
public class BudgetEstimateMainServiceImpl extends GenericServiceImpl<BudgetEstimateMain, String>
        implements BudgetEstimateMainService {

    @Resource
    private BudgetEstimateMainDao budgetEstimateMainDao;

    @Override
    public Dao<BudgetEstimateMain, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetEstimateMainDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
    
	@Override
	@Transactional
	public void setData(BudgetEstimateMain entity) {
		// TODO Auto-generated method stub
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
	}

}
