package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.domain.entity.Notify;
import project.edge.domain.view.NotifyBean;

public class NotifyBeanConverter implements ViewConverter<Notify, NotifyBean> {

	@Override
	public NotifyBean fromEntity(Notify entity, MessageSource messageSource, Locale locale) {
		
		NotifyBean bean = new NotifyBean();
		
		bean.setId(entity.getId());
		bean.setContent(entity.getContent());
		bean.setAction(entity.getAction());
		bean.setTargetId(entity.getTargetId());
		bean.setTargetType(NotifyTargetTypeEnum.getResouceName(entity.getTargetType()));
		bean.setType(entity.getType());
		bean.setCreateAt(DateUtils.date2String(entity.getCreateAt(), "yyyy-MM-dd HH:mm"));
		bean.setCreateBy(entity.getCreateBy());
		
		return bean;
	}

	@Override
	public Notify toEntity(NotifyBean bean, Notify oldEntity, AbstractSessionUserBean user, Date now) {
		
		Notify entity = new Notify();
		
		entity.setId(bean.getId());
		entity.setContent(bean.getContent());
		entity.setAction(bean.getAction());
		entity.setTargetId(bean.getTargetId());
		entity.setTargetType(NotifyTargetTypeEnum.getResouceValue(bean.getTargetType()));
		entity.setType(bean.getType());
		
		try {
			entity.setCreateAt(DateUtils.string2Date(bean.getCreateAt(), "yyyy-MM-dd HH:mm"));
		} catch (Exception e) {
			
		}
		
		entity.setCreateBy(bean.getCreateBy());
		
		return entity;
	}

	
}
