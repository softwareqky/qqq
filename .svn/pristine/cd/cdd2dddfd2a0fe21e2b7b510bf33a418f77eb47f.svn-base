package project.edge.domain.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.FacilityHistory;
import project.edge.domain.view.FacilityHistoryBean;

public class FacilityHistoryBeanConverter implements ViewConverter<FacilityHistory, FacilityHistoryBean> {

	@Override
	public FacilityHistoryBean fromEntity(FacilityHistory entity, MessageSource messageSource, Locale locale) {
		
		FacilityHistoryBean bean = new FacilityHistoryBean();
		
		bean.setId(entity.getId());
		bean.setContent(entity.getContent());
		bean.setCreateType(entity.getCreateType());
		bean.setTargetId(entity.getTargetId());
		bean.setTargetType(entity.getTargetType());
		
		Person createBy = entity.getCreateBy();
		if (createBy != null) {
			bean.setPersonInCharge_(createBy.getId());
			bean.setPersonInChargeText(createBy.getPersonName());
		}
		
		if (entity.getCreateAt() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bean.setCreateAt(sdf.format(entity.getCreateAt()));
		}
		
		return bean;
	}

	@Override
	public FacilityHistory toEntity(FacilityHistoryBean bean, FacilityHistory oldEntity, AbstractSessionUserBean user, Date now) {
		
		return null;
	}
	
}
