package project.edge.domain.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.CheckUtils;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.CombineTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.PlanBean;

/**
 * @author angel_000 转换计划表对应的view和entity的转换器。
 *
 */
public class PlanBeanConverter implements ViewConverter<Plan, PlanBean> {

	@Override
	public PlanBean fromEntity(Plan entity, MessageSource messageSource, Locale locale) {
		PlanBean bean = new PlanBean();

		bean.setId(entity.getId());

		Project project = entity.getProject();
		Site site = entity.getSite();
		Segment segment = entity.getSegment();
		PlanCalendar planCalendar = entity.getPlanCalendar();
		VirtualOrg virtualOrg = entity.getVirtualOrg();

		if (project != null) {
			bean.setProject_(project.getId());
			bean.setProjectText(project.getProjectName());
		}

		if (site != null) {
			bean.setSite_(site.getId());
			bean.setSiteText(site.getStationName());
		}

		if (segment != null) {
			bean.setSegment_(segment.getId());

			String format = "%1$s - %2$s";
			if ((segment.getEndA() != null) && (segment.getEndB() != null)) {
				bean.setSegmentText(
						String.format(format, segment.getEndA().getStationName(), segment.getEndB().getStationName()));
			}
		}

		if (virtualOrg != null) {
			bean.setVirtualOrg_(virtualOrg.getId());
			bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
		}

		if (planCalendar != null) {
			bean.setPlanCalendar_(planCalendar.getId());
			bean.setPlanCalendarText(planCalendar.getCalendarName());
		}

		bean.setPlanYear(entity.getPlanYear());
		if (entity.getCombineType() != null) {
			bean.setCombineType(entity.getCombineType());
			bean.setCombineTypeText(
					messageSource.getMessage(CombineTypeEnum.getResouceName(entity.getCombineType()), null, locale));
		}

		bean.setPlanName(entity.getPlanName());
		bean.setPlanVersion(entity.getPlanVersion());
		bean.setRemark(CheckUtils.checkString(entity.getRemark()));
		bean.setRemarkHtmlEscaped(HtmlUtils.htmlEscape(bean.getRemark()));
		if (entity.getPlanStartDate() != null) {
			bean.setPlanStartDate(DateUtils.date2String(entity.getPlanStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getPlanEndDate() != null) {
			bean.setPlanEndDate(DateUtils.date2String(entity.getPlanEndDate(), Constants.DATE_FORMAT));
		}
		bean.setDurationDays(entity.getDurationDays());
		if (entity.getActualStartDate() != null) {
			bean.setActualStartDate(DateUtils.date2String(entity.getActualStartDate(), Constants.DATE_FORMAT));
		}
		if (entity.getActualEndDate() != null) {
			bean.setActualEndDate(DateUtils.date2String(entity.getActualEndDate(), Constants.DATE_FORMAT));
		}
		bean.setSumTaskDurationDays(entity.getSumTaskDurationDays());
		bean.setSumTaskProgressDays(entity.getSumTaskProgressDays());
		bean.setProgress(entity.getProgress());
		bean.setTotalTaskCount(entity.getTotalTaskCount());
		bean.setTotalFinishTaskCount(entity.getTotalFinishTaskCount());
		bean.setTotalWarningTaskCount(entity.getTotalWarningTaskCount());
		bean.setTotalDelayTaskCount(entity.getTotalDelayTaskCount());
		bean.setReqDelayTaskCount(entity.getReqDelayTaskCount());
		bean.setSolutionDelayTaskCount(entity.getSolutionDelayTaskCount());
		bean.setHrDelayTaskCount(entity.getHrDelayTaskCount());
		bean.setBudgetDelayTaskCount(entity.getBudgetDelayTaskCount());

		if (entity.getIsComplete() != null) {
			bean.setIsComplete(entity.getIsComplete());
			bean.setIsCompleteText(
					entity.getIsComplete() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		if (entity.getFlowStartDate() != null) {
			bean.setFlowStartDate(DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getFlowEndDate() != null) {
			bean.setFlowEndDate(DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		}
		bean.setFlowStatus(entity.getFlowStatus());
		bean.setFlowStatusText(
				messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));

		if (entity.getVersionDatetime() != null) {
			bean.setVersionDatetime(DateUtils.date2String(entity.getVersionDatetime(), Constants.DATE_FORMAT));
		}

		if (entity.getcDatetime() != null) {
			bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
		}

		// if (!StringUtils.isEmpty(entity.getCreator())) {
		// Person p = this.personService.find(entity.getCreator());
		// bean.setCreator(p.getPersonName());
		// }

		Person person = entity.getCreator();
		if (person != null) {
			bean.setCreator_(person.getId());
			bean.setCreatorText(person.getPersonName());
		}

		if (entity.getIsYear() != null) {
			bean.setIsYear(entity.getIsYear());
			bean.setIsYearText(
					entity.getIsYear() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
			if(entity.getIsYear() == 1) {
				bean.setPersonInCharge_(bean.getCreator_());
				bean.setPersonInChargeText(bean.getCreatorText());
			}
		}
		if (entity.getSite() != null && entity.getSite().getPersonInCharge() != null) {
			if(entity.getVirtualOrg() != null) {//专业组是可编程或sdn，就取对应负责人
				 if(entity.getVirtualOrg().getId().equals("74526f1e-b232-4c9c-8b61-a95172496cf4")) {
					 if(entity.getSite().getProgrammablePersonInCharge() != null) {
						 bean.setPersonInCharge_(entity.getSite().getProgrammablePersonInCharge().getId());
						 bean.setPersonInChargeText(entity.getSite().getProgrammablePersonInCharge().getPersonName()); 
					 }
				 }
				 else if(entity.getVirtualOrg().getId().equals("710a9408-ac98-456a-b731-332dd91f69a9")) {
					 if(entity.getSite().getSdnPersonInCharge() != null) {
						 bean.setPersonInCharge_(entity.getSite().getSdnPersonInCharge().getId());
						 bean.setPersonInChargeText(entity.getSite().getSdnPersonInCharge().getPersonName());
					 }
				 }
				 else if(entity.getVirtualOrg().getId().equals("ae26cdfd-ac69-4de4-a44e-8a2b4cee6282")) {
					 if(entity.getSite().getPersonInCharge() != null) {
						bean.setPersonInCharge_(entity.getSite().getPersonInCharge().getId());
						bean.setPersonInChargeText(entity.getSite().getPersonInCharge().getPersonName());
					 }
				 }
			}
/*			else {
				bean.setPersonInCharge_(entity.getSite().getPersonInCharge().getId());
				bean.setPersonInChargeText(entity.getSite().getPersonInCharge().getPersonName());
			}*/
		} else if (entity.getSegment() != null && entity.getSegment().getPersonInCharge() != null) {
			bean.setPersonInCharge_(entity.getSegment().getPersonInCharge().getId());
			bean.setPersonInChargeText(entity.getSegment().getPersonInCharge().getPersonName());
		} else {
			if (!StringUtils.isEmpty(entity.getPersonInCharge()) && !StringUtils.isEmpty(entity.getPersonInCharge().getId())) {
				bean.setPersonInCharge_(entity.getPersonInCharge().getId());
				bean.setPersonInChargeText(entity.getPersonInCharge().getPersonName());
			}
		}


/*		if (bean.getPersonInCharge_() == null) {
			bean.setPersonInCharge_(bean.getCreator_());
			bean.setPersonInChargeText(bean.getCreatorText());
		}*/
		return bean;
	}

	@Override
	public Plan toEntity(PlanBean bean, Plan oldEntity, AbstractSessionUserBean user, Date now) {

		Plan entity = oldEntity;

		if (entity == null) { // 表示新建
			entity = new Plan();

			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				entity.setCreator(person);
			}
			// entity.setCreator(user.getSessionUserName());
			entity.setcDatetime(now);
		} else { // 表示修改
			entity.setModifier(user.getSessionUserId());
			entity.setIsDeleted(OnOffEnum.OFF.value());
		}

		bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);

		if (!StringUtils.isEmpty(bean.getProject_())) {
			Project project = new Project();
			project.setId(bean.getProject_());
			project.setProjectName(bean.getProjectText());
			entity.setProject(project);
		}

		if (!StringUtils.isEmpty(bean.getSite_())) {
			Site site = new Site();
			site.setId(bean.getSite_());
			entity.setSite(site);
		}

		if (!StringUtils.isEmpty(bean.getSegment_())) {
			Segment segment = new Segment();
			segment.setId(bean.getSegment_());
			entity.setSegment(segment);
		}

		if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
			VirtualOrg virtualOrg = new VirtualOrg();
			virtualOrg.setId(bean.getVirtualOrg_());
			entity.setVirtualOrg(virtualOrg);
		}

		entity.setPlanName(bean.getPlanName());
		entity.setPlanVersion(bean.getPlanVersion());
		entity.setRemark(bean.getRemark());

		if (!StringUtils.isEmpty(bean.getPlanCalendar_())) {
			PlanCalendar planCalendar = new PlanCalendar();
			planCalendar.setId(bean.getPlanCalendar_());
			entity.setPlanCalendar(planCalendar);
		}

		entity.setPlanYear(bean.getPlanYear());
		entity.setCombineType(bean.getCombineType());
		if (!StringUtils.isEmpty(bean.getPlanStartDate())) {
			try {
				entity.setPlanStartDate(DateUtils.string2Date(bean.getPlanStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getPlanEndDate())) {
			try {
				entity.setPlanEndDate(DateUtils.string2Date(bean.getPlanEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}
		entity.setDurationDays(bean.getDurationDays());

		if (!StringUtils.isEmpty(bean.getActualStartDate())) {
			try {
				entity.setActualStartDate(DateUtils.string2Date(bean.getActualStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getActualEndDate())) {
			try {
				entity.setActualEndDate(DateUtils.string2Date(bean.getActualEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setSumTaskDurationDays(bean.getSumTaskDurationDays());
		entity.setSumTaskProgressDays(bean.getSumTaskProgressDays());
		entity.setProgress(bean.getProgress());
		entity.setTotalTaskCount(bean.getTotalTaskCount());
		entity.setTotalFinishTaskCount(bean.getTotalFinishTaskCount());
		entity.setTotalWarningTaskCount(bean.getTotalWarningTaskCount());
		entity.setTotalDelayTaskCount(bean.getTotalDelayTaskCount());
		entity.setReqDelayTaskCount(bean.getReqDelayTaskCount());
		entity.setSolutionDelayTaskCount(bean.getSolutionDelayTaskCount());
		entity.setHrDelayTaskCount(bean.getHrDelayTaskCount());
		entity.setBudgetDelayTaskCount(bean.getBudgetDelayTaskCount());
		entity.setIsComplete(bean.getIsComplete());
		if (!StringUtils.isEmpty(bean.getFlowStartDate())) {
			try {
				entity.setFlowStartDate(DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
			try {
				entity.setFlowEndDate(DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}
		entity.setFlowStatus(bean.getFlowStatus());

		if (!StringUtils.isEmpty(bean.getVersionDatetime())) {
			try {
				entity.setVersionDatetime(DateUtils.string2Date(bean.getVersionDatetime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (bean.getIsYear() != null) {
			entity.setIsYear(bean.getIsYear());
		}
		
		Person person = new Person();
		person.setPersonName(bean.getPersonInChargeText());
		person.setId(bean.getPersonInCharge_());
		entity.setPersonInCharge(person);
		
		return entity;
	}

}
