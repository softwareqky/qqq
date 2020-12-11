/**
 * 
 */
package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.ContractBudgetDao;
import project.edge.domain.entity.ContractBudget;


/**
 * @author wwy
 *         [t_budget_estimate]对应的 Service。
 */
@Service
public class ContractBudgetServiceImpl extends GenericServiceImpl<ContractBudget, String> implements ContractBudgetService {

    @Resource
    private ContractBudgetDao contractBudgetDao;

    @Override
    public Dao<ContractBudget, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.contractBudgetDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
