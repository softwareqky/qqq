package project.edge.domain.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.PaperLibraryStatusEnum;
import project.edge.domain.entity.PaperLibrary;
import project.edge.domain.entity.PaperLibraryLendHistory;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.view.PaperLibraryLendHistoryBean;

public class PaperLibraryLendHistoryBeanConverter
		implements ViewConverter<PaperLibraryLendHistory, PaperLibraryLendHistoryBean> {

	@Override
	public PaperLibraryLendHistoryBean fromEntity(PaperLibraryLendHistory entity, MessageSource messageSource,
			Locale locale) {
		// TODO Auto-generated method stub
		PaperLibraryLendHistoryBean bean = new PaperLibraryLendHistoryBean();
		bean.setId(entity.getId());
		
		PaperLibrary paperLibrary = entity.getPaperLibrary();
		if (paperLibrary != null) {
			bean.setPaperLibrary_(paperLibrary.getId());
			bean.setPaperLibraryText(paperLibrary.getArchiveName());
			bean.setStatus(String.valueOf(paperLibrary.getStatus()));
		}
		
		Person lendPerson = entity.getLendPerson();
		if (lendPerson != null) {
			bean.setLendPerson_(lendPerson.getId());
			bean.setLendPersonText(lendPerson.getPersonName());
		}
		
		bean.setContactMobile(entity.getContactMobile());
		bean.setLendDays(entity.getLendDays());
		if (StringUtils.isEmpty(entity.getRemark())) {
			bean.setRemark(entity.getRemark());
		}
		bean.setFlowStatus(entity.getFlowStatus());
		bean.setFlowStatusText(messageSource
                .getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
		
		Person getPerson = entity.getGetPerson();
		if (getPerson != null) {
			bean.setGetPerson_(getPerson.getId());
			bean.setGetPersonText(getPerson.getPersonName());
		}
		
		if (entity.getGetTime() != null) {
			bean.setGetTime(DateUtils.date2String(entity.getGetTime(), Constants.DATE_FORMAT));
		}
		
		Person returnPerson = entity.getReturnPerson();
		if (returnPerson != null) {
			bean.setReturnPerson_(returnPerson.getId());
			bean.setReturnPersonText(returnPerson.getPersonName());
		}
		
		if (entity.getReturnTime() != null) {
			bean.setReturnTime(DateUtils.date2String(entity.getReturnTime(), Constants.DATE_FORMAT));
		}
		
		return bean;
	}

	@Override
	public PaperLibraryLendHistory toEntity(PaperLibraryLendHistoryBean bean, PaperLibraryLendHistory oldEntity,
			AbstractSessionUserBean user, Date now) {
		// TODO Auto-generated method stub
		PaperLibraryLendHistory entity = oldEntity;
		boolean isNew = false;
		if (entity == null) {
			// 新增
			entity = new PaperLibraryLendHistory();
			entity.setCreator(user.getSessionUserId());
			entity.setcDatetime(now);
			entity.setModifier("");
			isNew = true;
		} else {
			// 变更
			entity.setModifier(user.getSessionUserId());
		}
		
		bean.setId(entity.getId());
		
		// 库存状态更新
		if (!StringUtils.isEmpty(bean.getPaperLibrary_())) {
			PaperLibrary paperLibrary = new PaperLibrary();
			paperLibrary.setId(bean.getPaperLibrary_());

			int status = PaperLibraryStatusEnum.IN_LIBRARY.value();
			if (!StringUtils.isEmpty(bean.getActionType())) {
				if ("take".equals(bean.getActionType())) {
					status = PaperLibraryStatusEnum.OUT_LIBRARY.value();
				}
				paperLibrary.setStatus((short)status);
			} else {
				if (isNew) {
					status = PaperLibraryStatusEnum.REQ_LIBRARY.value();
					paperLibrary.setStatus((short)status);
				}
			}
			entity.setPaperLibrary(paperLibrary);
		}
		
		if (!StringUtils.isEmpty(bean.getLendPerson_())) {
			Person person = new Person();
			person.setId(bean.getLendPerson_());
			entity.setLendPerson(person);
		}
		entity.setContactMobile(bean.getContactMobile());
		
		entity.setLendDays(bean.getLendDays());
		entity.setRemark(bean.getRemark());
		entity.setFlowStatus(bean.getFlowStatus());
		
		if (!StringUtils.isEmpty(bean.getGetPerson_())) {
			Person person = new Person();
			person.setId(bean.getGetPerson_());
			entity.setGetPerson(person);
		}
		
		try {
			if (!StringUtils.isEmpty(bean.getGetTime()))
				entity.setGetTime(DateUtils.string2Date(bean.getGetTime(), Constants.DATE_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!StringUtils.isEmpty(bean.getReturnPerson_())) {
			Person person = new Person();
			person.setId(bean.getReturnPerson_());
			entity.setReturnPerson(person);
		}
		
		try {
			if (!StringUtils.isEmpty(bean.getReturnTime()))
				entity.setReturnTime(DateUtils.string2Date(bean.getReturnTime(), Constants.DATE_FORMAT));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entity.setmDatetime(now);
		
		return entity;
	}

}
