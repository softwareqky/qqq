package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.common.constant.NotifyWayEnum;
import project.edge.domain.entity.NotifySetting;
import project.edge.domain.view.NotifySettingBean;

public class NotifySettingBeanConverter implements ViewConverter<NotifySetting, NotifySettingBean> {

	@Override
	public NotifySettingBean fromEntity(NotifySetting entity, MessageSource messageSource, Locale locale) {
		
		NotifySettingBean bean = new NotifySettingBean();
		
		bean.setId(entity.getId());
		bean.setDaysInAdvance(entity.getDaysInAdvance());
		
		bean.setNotifyTargetType(entity.getNotifyTargetType());
		bean.setNotifyTargetTypeText(
			messageSource.getMessage(NotifyTargetTypeEnum.getResouceName(
					entity.getNotifyTargetType()), null, null, locale)
		);
		
		bean.setNotifyWay(entity.getNotifyWay());
		bean.setNotifyWayText(
			messageSource.getMessage(NotifyWayEnum.getResouceName(
					entity.getNotifyWay()), null, null, locale)
		);
		
		bean.setComment(entity.getComment());
		
		return bean;
	}

	@Override
	public NotifySetting toEntity(NotifySettingBean bean, NotifySetting oldEntity, AbstractSessionUserBean user,
			Date now) {
		
		NotifySetting entity = oldEntity;
		
		// 新建
		if (entity == null) {
			entity = new NotifySetting();
		}
		
		bean.setId(entity.getId());
		entity.setDaysInAdvance(bean.getDaysInAdvance());
		entity.setNotifyTargetType(bean.getNotifyTargetType());
		entity.setNotifyWay(bean.getNotifyWay());
		entity.setComment(bean.getComment());
		
		return entity;
	}
}
