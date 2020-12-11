package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetFinalVersionDao;
import project.edge.domain.entity.BudgetFinalVersion;

/**
 * @author angel_000
 *         [t_project_person]对应的 Service。
 */
@Service
public class BudgetFinalVersionServiceImpl extends GenericServiceImpl<BudgetFinalVersion, String>
        implements BudgetFinalVersionService {

    @Resource
    private BudgetFinalVersionDao budgetFinalVersionDao;

    @Override
    public Dao<BudgetFinalVersion, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetFinalVersionDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
    
}
