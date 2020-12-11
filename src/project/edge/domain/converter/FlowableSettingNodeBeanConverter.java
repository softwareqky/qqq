package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.FlowableSettingNode;
import project.edge.domain.view.FlowableSettingNodeBean;


public class FlowableSettingNodeBeanConverter
        implements ViewConverter<FlowableSettingNode, FlowableSettingNodeBean> {

    @Override
    public FlowableSettingNodeBean fromEntity(FlowableSettingNode entity,
            MessageSource messageSource, Locale locale) {
        FlowableSettingNodeBean bean = new FlowableSettingNodeBean();

        bean.setId(entity.getId());

        if (entity.getFlowableSetting() != null) {
            bean.setFlowableSetting_(entity.getFlowableSetting().getId());
        }

        bean.setNodeName(entity.getNodeName());
        bean.setNodeType(entity.getNodeType());
        bean.setNodeOrder(entity.getNodeOrder());
        bean.setParticipantAuditList(entity.getParticipantAuditList());
        bean.setParticipantAuditNameList(entity.getParticipantAuditNameList());
        bean.setAuditRemark(entity.getAuditRemark());

        return bean;
    }

    @Override
    public FlowableSettingNode toEntity(FlowableSettingNodeBean bean, FlowableSettingNode oldEntity,
            AbstractSessionUserBean user, Date now) {

        FlowableSettingNode entity = oldEntity;

        if (entity == null) {// 表示新建
            entity = new FlowableSettingNode();
        } else {// 表示修改

        }

        bean.setId(entity.getId()); // ID必须赋值

        if (!StringUtils.isEmpty(bean.getFlowableSetting_())) {

            FlowableSetting setting = new FlowableSetting();
            setting.setId(bean.getFlowableSetting_());
            
            entity.setFlowableSetting(setting);
        }

        entity.setNodeName(bean.getNodeName());
        entity.setNodeType(bean.getNodeType());
        entity.setNodeOrder(bean.getNodeOrder());
        
        entity.setParticipantAuditList(bean.getParticipantAuditList());
        entity.setParticipantAuditNameList(bean.getParticipantAuditNameList());
        entity.setAuditRemark(entity.getAuditRemark());
        
        return entity;
    }

}
