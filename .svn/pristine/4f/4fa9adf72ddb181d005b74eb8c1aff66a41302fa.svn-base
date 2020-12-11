package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.view.ProjectRoleBean;

/**
 * @author angel_000
 *         转换项目角色对应的view和entity的转换器。
 */
public class ProjectRoleBeanConverter implements ViewConverter<ProjectRole, ProjectRoleBean> {

    @Override
    public ProjectRoleBean fromEntity(ProjectRole entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        ProjectRoleBean bean = new ProjectRoleBean();

        bean.setId(entity.getId());
        bean.setProjectRoleName(entity.getProjectRoleName());
        bean.setShortName(entity.getShortName());
        bean.setProjectRoleCode(entity.getProjectRoleCode());

        ProjectRole p = entity.getPid();
        if (p != null) {
            bean.setPid_(p.getId());
            bean.setPidText(p.getProjectRoleName());
        }

        bean.setLevel(entity.getLevel());
        bean.setProjectRoleOrder(entity.getProjectRoleOrder());


        return bean;
    }

    @Override
    public ProjectRole toEntity(ProjectRoleBean bean, ProjectRole oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        ProjectRole entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ProjectRole();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
            entity.setIsDeleted(OnOffEnum.OFF.value());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setProjectRoleName(bean.getProjectRoleName());
        entity.setShortName(bean.getShortName());
        entity.setProjectRoleCode(bean.getProjectRoleCode());

        if (!StringUtils.isEmpty(bean.getPid_())) {
            ProjectRole p = new ProjectRole();
            p.setId(bean.getPid_());
            entity.setPid(p);
        }

        entity.setLevel(bean.getLevel());
        entity.setProjectRoleOrder(bean.getProjectRoleOrder());

        return entity;
    }

}
