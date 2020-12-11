package project.edge.dao.flowable;

import garage.origin.dao.Dao;
import project.edge.domain.entity.FlowableSetting;

/**
 * [t_flowable_setting]对应的DAO。
 */
public interface FlowableSettingDao extends Dao<FlowableSetting, String> {

    void updateFlowableStatus(String businessEntity, String correlateDataId, Integer value);

}
