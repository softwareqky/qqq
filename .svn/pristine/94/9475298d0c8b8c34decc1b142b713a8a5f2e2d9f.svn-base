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
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.ProjectRegistration;
import project.edge.domain.view.ProjectRegistrationBean;


/**
 * @author angel_000
 *
 */
public class ProjectRegistrationBeanConverter
        implements ViewConverter<ProjectRegistration, ProjectRegistrationBean> {

    @Override
    public ProjectRegistrationBean fromEntity(ProjectRegistration entity,
            MessageSource messageSource, Locale locale) {
        ProjectRegistrationBean bean = new ProjectRegistrationBean();

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

        if (entity.getRegistrationDate() != null) {
            bean.setRegistrationDate(
                    DateUtils.date2String(entity.getRegistrationDate(), Constants.DATE_FORMAT));
        }

        bean.setFegistrationFee(entity.getFegistrationFee());
        bean.setRegistrationInformation(entity.getRegistrationInformation());
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public ProjectRegistration toEntity(ProjectRegistrationBean bean, ProjectRegistration oldEntity,
            AbstractSessionUserBean user, Date now) {
        ProjectRegistration entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ProjectRegistration();

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

        if (!StringUtils.isEmpty(bean.getRegistrationDate())) {
            try {
                entity.setRegistrationDate(
                        DateUtils.string2Date(bean.getRegistrationDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setFegistrationFee(bean.getFegistrationFee());
        entity.setRegistrationInformation(bean.getRegistrationInformation());
        entity.setRemark(bean.getRemark());

        return entity;
    }

}
