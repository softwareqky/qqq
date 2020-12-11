/**
 * 
 */
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
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Prequalification;
import project.edge.domain.entity.Project;
import project.edge.domain.view.PrequalificationBean;


/**
 * @author angel_000
 *
 */
public class PrequalificationBeanConverter
        implements ViewConverter<Prequalification, PrequalificationBean> {

    @Override
    public PrequalificationBean fromEntity(Prequalification entity, MessageSource messageSource,
            Locale locale) {
        PrequalificationBean bean = new PrequalificationBean();

        bean.setId(entity.getId());

        Person applicant = entity.getApplicant();
        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
        }

        if (entity.getApplicantDate() != null) {
            bean.setApplicantDate(
                    DateUtils.date2String(entity.getApplicantDate(), Constants.DATE_FORMAT));
        }

        Project project = entity.getProject();

        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        DataOption dataOption = entity.getProjectKind();

        if (dataOption != null) {
            bean.setProjectKind_(dataOption.getId());
            bean.setProjectKindText(dataOption.getOptionName());
        }

        bean.setConstructionUnit(entity.getConstructionUnit());
        bean.setIsBusinessSupport(entity.getIsBusinessSupport());
        bean.setIsBusinessSupportText(entity.getIsBusinessSupport() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        bean.setHeadman(entity.getHeadman());

        if (entity.getClosingDate() != null) {
            bean.setClosingDate(
                    DateUtils.date2String(entity.getClosingDate(), Constants.DATE_FORMAT));
        }

        if (entity.getPretrialDate() != null) {
            bean.setPretrialDate(
                    DateUtils.date2String(entity.getPretrialDate(), Constants.DATE_FORMAT));
        }

        bean.setQualificationAuditDeposit(entity.getQualificationAuditDeposit());

        if (entity.getRegistrationDate() != null) {
            bean.setRegistrationDate(
                    DateUtils.date2String(entity.getRegistrationDate(), Constants.DATE_FORMAT));
        }

        bean.setPrequalificationInformation(entity.getPrequalificationInformation());
        bean.setRemark(entity.getRemark());
        bean.setSpecialCaseDescription(entity.getSpecialCaseDescription());

        return bean;
    }

    @Override
    public Prequalification toEntity(PrequalificationBean bean, Prequalification oldEntity,
            AbstractSessionUserBean user, Date now) {
        Prequalification entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new Prequalification();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
        }

        if (!StringUtils.isEmpty(bean.getApplicantDate())) {
            try {
                entity.setApplicantDate(
                        DateUtils.string2Date(bean.getApplicantDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }

        if (!StringUtils.isEmpty(bean.getProjectKind_())) {
            DataOption projectKind = new DataOption();
            projectKind.setId(bean.getProjectKind_());
            entity.setProjectKind(projectKind);
        }

        entity.setConstructionUnit(bean.getConstructionUnit());

        if (bean.getIsBusinessSupport() != null) {
            if (OnOffEnum.ON.value().equals(bean.getIsBusinessSupport())) {
                entity.setIsBusinessSupport(OnOffEnum.ON.value());
            } else {
                entity.setIsBusinessSupport(OnOffEnum.OFF.value());
            }
        }

        entity.setHeadman(bean.getHeadman());

        if (!StringUtils.isEmpty(bean.getClosingDate())) {
            try {
                entity.setClosingDate(
                        DateUtils.string2Date(bean.getClosingDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getPretrialDate())) {
            try {
                entity.setPretrialDate(
                        DateUtils.string2Date(bean.getPretrialDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setQualificationAuditDeposit(bean.getQualificationAuditDeposit());

        if (!StringUtils.isEmpty(bean.getRegistrationDate())) {
            try {
                entity.setRegistrationDate(
                        DateUtils.string2Date(bean.getRegistrationDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setPrequalificationInformation(bean.getPrequalificationInformation());
        entity.setRemark(bean.getRemark());
        entity.setSpecialCaseDescription(bean.getSpecialCaseDescription());

        return entity;
    }

}
