package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.entity.ProjectCheckExpert;
import project.edge.domain.view.ProjectCheckExpertBean;

public class ProjectCheckExpertBeanConverter implements ViewConverter<ProjectCheckExpert, ProjectCheckExpertBean> {
	
	@Override
    public ProjectCheckExpertBean fromEntity(ProjectCheckExpert entity,
            MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ProjectCheckExpertBean bean = new ProjectCheckExpertBean();

        bean.setId(entity.getId());
        
        ProjectCheck projectCheck = entity.getProjectCheck();
        Expert expert = entity.getExpert();

        if (projectCheck != null) {
            bean.setCheckedContent(projectCheck.getCheckedContent());
        }
        if (expert != null) {
            bean.setExpert_(expert.getId());
            bean.setExpertText(expert.getExpertName());
        }

        return bean;
    }

    @Override
    public ProjectCheckExpert toEntity(ProjectCheckExpertBean bean, ProjectCheckExpert oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectCheckExpert entity = oldEntity;

        bean.setId(entity.getId()); // ID必须赋值

        if (!StringUtils.isEmpty(bean.getProjectCheck_())) {
            ProjectCheck projectCheck = new ProjectCheck();
            projectCheck.setId(bean.getProjectCheck_());
            entity.setProjectCheck(projectCheck);
        }

        if (!StringUtils.isEmpty(bean.getExpert_())) {
            Expert expert = new Expert();
            expert.setId(bean.getExpert_());
            entity.setExpert(expert);
        }

        return entity;
    }
}
