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
import project.edge.dao.budget.BudgetEstimateChangeDao;
import project.edge.domain.entity.BudgetEstimateChange;


/**
 * @author wwy
 *         [t_budget_estimate]对应的 Service。
 */
@Service
public class BudgetEstimateChangeServiceImpl extends GenericServiceImpl<BudgetEstimateChange, String> implements BudgetEstimateChangeService {

    @Resource
    private BudgetEstimateChangeDao budgetEstimateChangeDao;

    @Override
    public Dao<BudgetEstimateChange, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetEstimateChangeDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
 
    @Transactional
    public void setData(BudgetEstimateChange entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
}
