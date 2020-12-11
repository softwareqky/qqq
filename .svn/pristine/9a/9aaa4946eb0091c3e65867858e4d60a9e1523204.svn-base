/**
 * 
 */
package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetEstimateSumDao;
import project.edge.domain.entity.BudgetEstimateSum;


/**
 * @author wwy
 *         [t_budget_estimate_sum]对应的 Service。
 */
@Service
public class BudgetEstimateSumServiceImpl extends GenericServiceImpl<BudgetEstimateSum, String> implements BudgetEstimateSumService {

    @Resource
    private BudgetEstimateSumDao budgetEstimatesumDao;

    @Override
    public Dao<BudgetEstimateSum, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetEstimatesumDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
