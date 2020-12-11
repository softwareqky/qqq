package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.ProjectSet;
import project.edge.domain.view.ProjectSetBean;

/**
 * @author angel_000
 *         转换项目集表对应的view和entity的转换器。
 *
 */
public class ProjectSetBeanConverter implements ViewConverter<ProjectSet, ProjectSetBean> {

    @Override
    public ProjectSetBean fromEntity(ProjectSet entity, MessageSource messageSource,
            Locale locale) {

        ProjectSetBean bean = new ProjectSetBean();

        bean.setId(entity.getId());

        bean.setGovernmentNum(entity.getGovernmentNum());
        bean.setProjectNum(entity.getProjectNum());
        bean.setProjectName(entity.getProjectName());

        DataOption dataOption = entity.getProjectKind();

        if (dataOption != null) {
            bean.setProjectKind_(dataOption.getId());
            bean.setProjectKindText(dataOption.getOptionName());
        }

        Person applicant = entity.getApplicant();

        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
        }

        bean.setApplicantMobile(entity.getApplicantMobile());


        return bean;
    }

    @Override
    public ProjectSet toEntity(ProjectSetBean bean, ProjectSet oldEntity,
            AbstractSessionUserBean user, Date now) {

        ProjectSet entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ProjectSet();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setGovernmentNum(bean.getGovernmentNum());
        entity.setProjectNum(bean.getProjectNum());
        entity.setProjectName(bean.getProjectName());

        if (!StringUtils.isEmpty(bean.getProjectKind_())) {
            DataOption dataOption = new DataOption();
            dataOption.setId(bean.getProjectKind_());
            entity.setProjectKind(dataOption);
        }

        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
        }
        entity.setApplicantMobile(bean.getApplicantMobile());


        return entity;
    }

}
