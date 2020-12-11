package project.edge.service.flowable;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.flowable.FlowableSettingNodeDao;
import project.edge.domain.entity.FlowableSettingNode;

/**
 * [t_flowable_setting_node]对应的Service。
 */
@Service
public class FlowableSettingNodeServiceImpl extends GenericServiceImpl<FlowableSettingNode, String>
        implements FlowableSettingNodeService {

    @Resource
    private FlowableSettingNodeDao flowableSettingNodeDao;

    @Override
    public Dao<FlowableSettingNode, String> getDefaultDao() {
        return this.flowableSettingNodeDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("nodeOrder", true);
    }

    @Override
    @Transactional
    public void deleteAllAndCreate(String flowableSettingId, List<FlowableSettingNode> nodes) {
        
        this.flowableSettingNodeDao.deleteAllByFlowableSettingId(flowableSettingId);
        this.flowableSettingNodeDao.create(nodes);
    }
}
