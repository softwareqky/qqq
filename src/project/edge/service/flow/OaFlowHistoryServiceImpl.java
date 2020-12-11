/**
 * 
 */
package project.edge.service.flow;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.flow.OaFlowHistoryDao;
import project.edge.domain.entity.OaFlowHistory;


/**
 * @author angel_000
 *         [t_oa_flow_history]对应的Service。
 */
@Service
public class OaFlowHistoryServiceImpl extends GenericServiceImpl<OaFlowHistory, String>
        implements OaFlowHistoryService {

    @Resource
    private OaFlowHistoryDao oaFlowHistoryDao;

    @Override
    public Dao<OaFlowHistory, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.oaFlowHistoryDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
