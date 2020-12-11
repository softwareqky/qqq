package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetMainfeeDao;
import project.edge.domain.entity.BudgetMainfee;

@Service
public class BudgetMainfeeServiceImpl extends GenericServiceImpl<BudgetMainfee, String> implements BudgetMainfeeService {

	@Resource
    private BudgetMainfeeDao budgetMainfeeDao;
	
	@Override
	public Dao<BudgetMainfee, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return budgetMainfeeDao;
	}

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("mainid", true);
    }

}
