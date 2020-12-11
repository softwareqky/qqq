package project.edge.dao.flowable;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.FlowableSettingNode;

/**
 * [t_flowable_setting_detail]对应的DAO。
 */
@Repository
public class FlowableSettingNodeDaoImpl extends HibernateDaoImpl<FlowableSettingNode, String>
        implements FlowableSettingNodeDao {

    @Override
    public void deleteAllByFlowableSettingId(String flowableSettingId) {
        session().createQuery("delete from FlowableSettingNode where flowableSetting.id =:id")
                .setString("id", flowableSettingId).executeUpdate();
    }
}
