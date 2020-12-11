/**
 * 
 */
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.SystemLog;
import project.edge.domain.view.SystemLogBean;


public class SystemLogBeanConverter implements ViewConverter<SystemLog, SystemLogBean> {

    @Override
    public SystemLogBean fromEntity(SystemLog entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        SystemLogBean bean = new SystemLogBean();

        bean.setId(entity.getId());
        bean.setLoginName(entity.getLoginName());
        bean.setUserIp(entity.getUserIp());
        bean.setPersonName(entity.getPersonName());
        bean.setKind(entity.getAction());
        bean.setDescription(entity.getDescription());
        if (entity.getcDatetime() != null) {
        	bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.SIMPLE_DATE_TIME_FORMAT));
        }
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public SystemLog toEntity(SystemLogBean bean, SystemLog oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        SystemLog entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new SystemLog();
            entity.setcDatetime(now);
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setLoginName(bean.getLoginName());
        entity.setUserIp(bean.getUserIp());
        entity.setPersonName(bean.getPersonName());
        entity.setAction(bean.getAction());
        entity.setDescription(bean.getDescription());
        entity.setRemark(bean.getRemark());

        return entity;
    }

}
