package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPersonChange;
import project.edge.domain.entity.ProjectPersonChangeDetail;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ProjectPersonChangeDetailBean;

/**
 * 转换项目成员变更申请明细对应的view和entity的转换器。
 *
 */
public class ProjectPersonChangeDetailBeanConverter
        implements ViewConverter<ProjectPersonChangeDetail, ProjectPersonChangeDetailBean> {

    @Override
    public ProjectPersonChangeDetailBean fromEntity(ProjectPersonChangeDetail entity,
            MessageSource messageSource, Locale locale) {

        ProjectPersonChangeDetailBean bean = new ProjectPersonChangeDetailBean();

        bean.setId(entity.getId());

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

        Person person = entity.getPerson();
        if (person != null) {
            bean.setPerson_(person.getId());
            bean.setPersonText(person.getPersonName());
        }

        ProjectRole projectRole = entity.getProjectRole();
        if (projectRole != null) {
            bean.setProjectRole_(projectRole.getId());
            bean.setProjectRoleText(projectRole.getProjectRoleName());
        }

        VirtualOrg virtualOrg = entity.getVirtualOrg();
        if (virtualOrg != null) {
            bean.setVirtualOrg_(virtualOrg.getId());
            bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
        }

        bean.setRemark(entity.getRemark());

        //bean.setFlowStatus(entity.getProjectPersonChange().getFlowStatus());
        bean.setFlowStatus(entity.getFlowStatus());
        bean.setFlowStatusText(messageSource
                .getMessage(FlowStatusEnum.getResouceName(bean.getFlowStatus()), null, locale));

        return bean;
    }

    @Override
    public ProjectPersonChangeDetail toEntity(ProjectPersonChangeDetailBean bean,
            ProjectPersonChangeDetail oldEntity, AbstractSessionUserBean user, Date now) {

        ProjectPersonChangeDetail entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ProjectPersonChangeDetail();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

            // 项目关联和成员变更不修改，所以只在新建里处理
            ProjectPersonChange projectPersonChange = new ProjectPersonChange();
            projectPersonChange.setChangeReason("");
            projectPersonChange.setCreator(user.getSessionUserId());
            projectPersonChange.setcDatetime(now);
            projectPersonChange.setmDatetime(now);

            entity.setProjectPersonChange(projectPersonChange);

            if (!StringUtils.isEmpty(bean.getProject_())) {
                Project project = new Project();
                project.setId(bean.getProject_());
                project.setProjectName(bean.getProjectText());
                entity.setProject(project);
                projectPersonChange.setProject(project);
            }

            if (!StringUtils.isEmpty(bean.getPerson_())) {
                Person person = new Person();
                person.setId(bean.getPerson_());
                entity.setPerson(person);
            }

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getProjectRole_())) {
            ProjectRole projectRole = new ProjectRole();
            projectRole.setId(bean.getProjectRole_());
            entity.setProjectRole(projectRole);
        }

        if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
            VirtualOrg virtualOrg = new VirtualOrg();
            virtualOrg.setId(bean.getVirtualOrg_());
            entity.setVirtualOrg(virtualOrg);
        }

        entity.setRemark(bean.getRemark());

        return entity;
    }

}
