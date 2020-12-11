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
import project.edge.dao.budget.BudgetFinalDao;
import project.edge.domain.entity.BudgetFinal;


/**
 * @author wwy
 *         [t_budget_final]对应的 Service。
 */
@Service
public class BudgetFinalServiceImpl extends GenericServiceImpl<BudgetFinal, String> implements BudgetFinalService {

    @Resource
    private BudgetFinalDao budgetFinalDao;

    @Override
    public Dao<BudgetFinal, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetFinalDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
    
	@Override
	@Transactional
	public void setData(BudgetFinal entity) {
		// TODO Auto-generated method stub
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
	}
}
