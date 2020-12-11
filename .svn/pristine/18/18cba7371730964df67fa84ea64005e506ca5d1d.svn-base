/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.SystemConfig;
import project.edge.domain.view.SystemConfigBean;


/**
 * @author angel_000
 *         转换系统配置信息对应的view和entity的转换器。
 */
public class SystemConfigBeanConverter implements ViewConverter<SystemConfig, SystemConfigBean> {

    @Override
    public SystemConfigBean fromEntity(SystemConfig entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        
        SystemConfigBean bean = new SystemConfigBean();
        
        bean.setId(entity.getId());
        bean.setConfigValue(entity.getConfigValue());
        
        return bean;
    }

    @Override
    public SystemConfig toEntity(SystemConfigBean bean, SystemConfig oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        
        SystemConfig entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new SystemConfig();

        } else {
            entity.setModifier(user.getSessionUserId());
        }
        
        bean.setId(entity.getId()); // ID必须赋值
        entity.setConfigValue(bean.getConfigValue());
        entity.setmDatetime(now);        
        
        return entity;
    }

}
