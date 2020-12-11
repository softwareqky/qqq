package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.ProjectInspectExpert;
import project.edge.domain.view.ProjectInspectExpertBean;

public class ProjectInspectExpertBeanConverter implements ViewConverter<ProjectInspectExpert, ProjectInspectExpertBean> {
	
	@Override
    public ProjectInspectExpertBean fromEntity(ProjectInspectExpert entity,
            MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        ProjectInspectExpertBean bean = new ProjectInspectExpertBean();

        bean.setId(entity.getId());
        
        ProjectInspect projectInspect = entity.getProjectInspect();
        Expert expert = entity.getExpert();

        if (projectInspect != null) {
            bean.setInspectContent(projectInspect.getInspectContent());
        }
        if (expert != null) {
            bean.setExpert_(expert.getId());
            bean.setExpertText(expert.getExpertName());
        }

        return bean;
    }

    @Override
    public ProjectInspectExpert toEntity(ProjectInspectExpertBean bean, ProjectInspectExpert oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectInspectExpert entity = oldEntity;

        bean.setId(entity.getId()); // ID必须赋值

        if (!StringUtils.isEmpty(bean.getProjectInspect_())) {
            ProjectInspect projectInspect = new ProjectInspect();
            projectInspect.setId(bean.getProjectInspect_());
            entity.setProjectInspect(projectInspect);
        }

        if (!StringUtils.isEmpty(bean.getExpert_())) {
            Expert expert = new Expert();
            expert.setId(bean.getExpert_());
            entity.setExpert(expert);
        }

        return entity;
    }
}
