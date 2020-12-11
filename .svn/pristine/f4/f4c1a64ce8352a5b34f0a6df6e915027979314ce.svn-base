/**
 * 
 */
package project.edge.service.budget;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.CapitalPlanSumDao;
import project.edge.domain.entity.CapitalPlanSum;


/**
 * @author wwy
 *         [t_capital_plan]对应的 Service。
 */
@Service
public class CapitalPlanSumServiceImpl extends GenericServiceImpl<CapitalPlanSum, String> implements CapitalPlanSumService {

    @Resource
    private CapitalPlanSumDao capitalPlanSumDao;

    @Override
    public Dao<CapitalPlanSum, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.capitalPlanSumDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
