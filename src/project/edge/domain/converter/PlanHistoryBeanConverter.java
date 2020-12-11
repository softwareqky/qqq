package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.entity.PlanChange;
import project.edge.domain.entity.PlanHistory;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.PlanHistoryBean;

/**
 * @author angel_000
 *         转换计划版本对应的view和entity的转换器。
 */
public class PlanHistoryBeanConverter implements ViewConverter<PlanHistory, PlanHistoryBean> {

    @Override
    public PlanHistoryBean fromEntity(PlanHistory entity, MessageSource messageSource,
            Locale locale) {
        PlanHistoryBean bean = new PlanHistoryBean();
        bean.setId(entity.getId());

        Plan plan = entity.getPlan();
        if (plan != null) {
            bean.setPlan_(plan.getId());
            bean.setPlanText(plan.getPlanName());
        }

        PlanChange planChange = entity.getPlanChange();
        if (planChange != null) {
            bean.setPlanChange_(planChange.getId());
            bean.setPlanChangeText(planChange.getPlanName());
        }

        bean.setIsChangePassed(entity.getIsChangePassed());
        bean.setIsChangePassedText(entity.getIsChangePassed() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        Site site = entity.getSite();
        if (site != null) {
            bean.setSite_(site.getId());
            bean.setSiteText(site.getStationName());
        }

        Segment segment = entity.getSegment();
        if (segment != null) {
            bean.setSegment_(segment.getId());
            
            if (segment.getSystemName() != null) {
	        	bean.setSegmentText(segment.getSystemName().getName());
            }
        }

        bean.setPlanName(entity.getPlanName());
        bean.setPlanVersion(entity.getPlanVersion());
        
        VirtualOrg virtualOrg = entity.getVirtualOrg();
        if(virtualOrg != null ) {
            bean.setVirtualOrg_(virtualOrg.getId());
            bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
        }
        
        bean.setRemark(entity.getRemark());

        PlanCalendar planCalendar = entity.getPlanCalendar();
        if (planCalendar != null) {
            bean.setPlanCalendar_(planCalendar.getId());
            bean.setPlanCalendarText(planCalendar.getCalendarName());
        }

        if (entity.getPlanStartDate() != null) {
            bean.setPlanStartDate(
                    DateUtils.date2String(entity.getPlanStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getPlanEndDate() != null) {
            bean.setPlanEndDate(
                    DateUtils.date2String(entity.getPlanEndDate(), Constants.DATE_FORMAT));
        }

        bean.setDurationDays(entity.getDurationDays());

        if (entity.getActualStartDate() != null) {
            bean.setActualStartDate(
                    DateUtils.date2String(entity.getActualStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getActualEndDate() != null) {
            bean.setActualEndDate(
                    DateUtils.date2String(entity.getActualEndDate(), Constants.DATE_FORMAT));
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
        bean.setIsComplete(entity.getIsComplete());
        bean.setIsCompleteText(entity.getIsComplete() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        if (entity.getFlowStartDate() != null) {
            bean.setFlowStartDate(
                    DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getFlowEndDate() != null) {
            bean.setFlowEndDate(
                    DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
        }

        bean.setFlowStatus(entity.getFlowStatus());
        bean.setFlowStatusText(messageSource
                .getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));

        if (entity.getVersionDatetime() != null) {
            bean.setVersionDatetime(
                    DateUtils.date2String(entity.getVersionDatetime(), Constants.DATE_FORMAT));
        }
        
        if(entity.getcDatetime() != null) {
            bean.setcDatetime(
                    DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
        }
        
        
        Person person = entity.getCreator();
        if (person != null) {
            bean.setCreator_(person.getId());
            bean.setCreatorText(person.getPersonName());
        }

        return bean;
    }

    @Override
    public PlanHistory toEntity(PlanHistoryBean bean, PlanHistory oldEntity,
            AbstractSessionUserBean user, Date now) {

        PlanHistory entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PlanHistory();
            if (!StringUtils.isEmpty(user.getSessionUserId())) {
                Person person = new Person();
                person.setId(user.getSessionUserId());
                entity.setCreator(person);
            }
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPlan_())) {
            Plan plan = new Plan();
            plan.setId(bean.getPlan_());
            entity.setPlan(plan);
        }

        if (!StringUtils.isEmpty(bean.getPlanChange_())) {
            PlanChange planChange = new PlanChange();
            planChange.setId(bean.getPlanChange_());
            entity.setPlanChange(planChange);
        }

        entity.setIsChangePassed(bean.getIsChangePassed());

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
        
        if(!StringUtils.isEmpty(bean.getVirtualOrg_())) {
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

        if (!StringUtils.isEmpty(bean.getPlanStartDate())) {
            try {
                entity.setPlanStartDate(
                        DateUtils.string2Date(bean.getPlanStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getPlanEndDate())) {
            try {
                entity.setPlanEndDate(
                        DateUtils.string2Date(bean.getPlanEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setDurationDays(bean.getDurationDays());

        if (!StringUtils.isEmpty(bean.getActualStartDate())) {
            try {
                entity.setActualStartDate(
                        DateUtils.string2Date(bean.getActualStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getActualEndDate())) {
            try {
                entity.setActualEndDate(
                        DateUtils.string2Date(bean.getActualEndDate(), Constants.DATE_FORMAT));
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
                entity.setFlowStartDate(
                        DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
            try {
                entity.setFlowEndDate(
                        DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setFlowStatus(bean.getFlowStatus());

        if (!StringUtils.isEmpty(bean.getVersionDatetime())) {
            try {
                entity.setVersionDatetime(
                        DateUtils.string2Date(bean.getVersionDatetime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        
        

        return entity;
    }

}
