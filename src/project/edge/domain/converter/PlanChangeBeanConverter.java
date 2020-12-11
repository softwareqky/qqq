package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.CheckUtils;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanChange;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.PlanChangeBean;

public class PlanChangeBeanConverter implements ViewConverter<PlanChange, PlanChangeBean> {

    @Override
    public PlanChangeBean fromEntity(PlanChange entity, MessageSource messageSource,
            Locale locale) {
        PlanChangeBean bean = new PlanChangeBean();

        bean.setId(entity.getId());
        Plan plan = entity.getPlan();
        if (plan != null) {
            bean.setPlan_(plan.getId());
            bean.setPlanText(plan.getPlanName());
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        // PlanCalendar planCalendar = entity.getPlanCalendar();
        /*
         * if (planCalendar != null) {
         * bean.setPlanCalendarId_(planCalendar.getId());
         * bean.setPlanCalendarIdText(planCalendar.getCalendarName());
         * }
         */
        
        VirtualOrg virtualOrg = entity.getVirtualOrg();
        if(virtualOrg != null ) {
            bean.setVirtualOrg_(virtualOrg.getId());
            bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
        }
        

        Person applicant = entity.getApplicant();
        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
            bean.setApplicantName(applicant.getPersonName());
        }

        bean.setPlanVersion(entity.getPlanVersion());
        bean.setPlanName(entity.getPlanName());
        bean.setChangeReason(CheckUtils.checkString(entity.getChangeReason()));
        bean.setChangeReasonHtmlEscaped(HtmlUtils.htmlEscape(bean.getChangeReason()));
        bean.setChangeRemark(entity.getChangeRemark());
        bean.setRemark(CheckUtils.checkString(entity.getRemark()));
        bean.setRemarkHtmlEscaped(HtmlUtils.htmlEscape(bean.getRemark()));
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
    public PlanChange toEntity(PlanChangeBean bean, PlanChange oldEntity,
            AbstractSessionUserBean user, Date now) {

        PlanChange entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PlanChange();

            // 仅新建的时候，需要处理计划变更关联的计划和项目，修改的时候关联不变
            if (!StringUtils.isEmpty(bean.getProject_())) {
                Project project = new Project();
                project.setId(bean.getProject_());
                project.setProjectName(bean.getProjectText());
                entity.setProject(project);
            }

            if (!StringUtils.isEmpty(bean.getPlan_())) {
                Plan plan = new Plan();
                plan.setId(bean.getPlan_());
                entity.setPlan(plan);
            }

            if (!StringUtils.isEmpty(user.getSessionUserId())) {
                Person person = new Person();
                person.setId(user.getSessionUserId());
                entity.setCreator(person);
            }
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setPlanName(bean.getPlanName());
        entity.setPlanVersion(bean.getPlanVersion());
        entity.setRemark(bean.getRemark());

        /*
         * if (!StringUtils.isEmpty(bean.getPlanCalendarId_())) {
         * PlanCalendar calendar = new PlanCalendar();
         * calendar.setId(bean.getPlanCalendarId_());
         * entity.setPlanCalendar(calendar);
         * }
         */

        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
        }
        entity.setChangeReason(bean.getChangeReason());
        entity.setChangeRemark(bean.getChangeRemark());
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
        if (bean.getFlowStatus() != null) {
            entity.setFlowStatus(bean.getFlowStatus());
        } else {
            entity.setFlowStatus(0);
        }
        entity.setIsDeleted(OnOffEnum.OFF.value());
        
        if(!StringUtils.isEmpty(bean.getVirtualOrg_())) {
            VirtualOrg virtualOrg = new VirtualOrg();
            virtualOrg.setId(bean.getVirtualOrg_());
            entity.setVirtualOrg(virtualOrg);
        }
        
        return entity;

    }

}
