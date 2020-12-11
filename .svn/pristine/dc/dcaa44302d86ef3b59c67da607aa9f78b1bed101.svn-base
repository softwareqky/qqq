package project.edge.service.flowable;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.flowable.FlowableSettingDao;
import project.edge.domain.entity.FlowableSetting;

/**
 * [t_flowable_setting]对应的Service。
 */
@Service
public class FlowableSettingServiceImpl extends GenericServiceImpl<FlowableSetting, String>
        implements FlowableSettingService {

    @Resource
    private FlowableSettingDao flowableSettingDao;

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("flowName");
    }

    @Override
    public Dao<FlowableSetting, String> getDefaultDao() {
        return this.flowableSettingDao;
    }

    @Override
    @Transactional
    public void updateFlowableStatus(String businessEntity, String correlateDataId, Integer value) {
        this.flowableSettingDao.updateFlowableStatus(businessEntity, correlateDataId, value);
    }
}
