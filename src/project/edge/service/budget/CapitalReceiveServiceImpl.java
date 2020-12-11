/**
 * 
 */
package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.CapitalReceiveDao;
import project.edge.domain.entity.CapitalReceive;


/**
 * @author wwy
 *         [t_capital_plan]对应的 Service。
 */
@Service
public class CapitalReceiveServiceImpl extends GenericServiceImpl<CapitalReceive, String> implements CapitalReceiveService {

    @Resource
    private CapitalReceiveDao capitalReceiveDao;

    @Override
    public Dao<CapitalReceive, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.capitalReceiveDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
