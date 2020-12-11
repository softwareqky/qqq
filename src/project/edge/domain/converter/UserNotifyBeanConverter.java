package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.UserNotify;
import project.edge.domain.view.UserNotifyBean;

public class UserNotifyBeanConverter implements ViewConverter<UserNotify, UserNotifyBean> {
	

	@Override
	public UserNotifyBean fromEntity(UserNotify entity, MessageSource messageSource, Locale locale) {
		
		UserNotifyBean bean = new UserNotifyBean();
		
		bean.setId(entity.getId());
		bean.setRead(entity.getRead());
		bean.setUserId(entity.getUserId());
		bean.setNotify(entity.getNotify());
		bean.setCreateAt(DateUtils.date2String(entity.getCreateAt(), "yyyy-MM-dd HH:mm"));
		
		return bean;
	}

	@Override
	public UserNotify toEntity(UserNotifyBean bean, UserNotify oldEntity, AbstractSessionUserBean user, Date now) {

		UserNotify entity = new UserNotify();
		
		entity.setId(bean.getId());
		entity.setRead(bean.getRead());
		entity.setUserId(bean.getUserId());
		entity.setNotify(bean.getNotify());
		
		try {
			entity.setCreateAt(DateUtils.string2Date(bean.getCreateAt(), "yyyy-MM-dd HH:mm"));
		} catch (Exception e) {
			
		}
		
		return entity;
	}

}
