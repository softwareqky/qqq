package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Role;
import project.edge.domain.view.RoleBean;


/**
 * 转换角色信息对应的view和entity的转换器。
 */
public class RoleBeanConverter implements ViewConverter<Role, RoleBean> {

    @Override
    public RoleBean fromEntity(Role entity, MessageSource messageSource, Locale locale) {

        RoleBean bean = new RoleBean();

        bean.setId(entity.getId());
        bean.setRoleName(entity.getRoleName());
        bean.setRemark(entity.getRemark());

        bean.setIsSuper(entity.getIsSuper());
        bean.setIsSuperText(messageSource.getMessage(OnOffEnum.getResouceName(entity.getIsSuper()),
                null, locale));

        bean.setIsSystem(entity.getIsSystem());
        bean.setIsSystemText(messageSource
                .getMessage(OnOffEnum.getResouceName(entity.getIsSystem()), null, locale));

        return bean;
    }

    @Override
    public Role toEntity(RoleBean bean, Role oldEntity, AbstractSessionUserBean user, Date now) {

        Role entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new Role();

            entity.setIsSystem(OnOffEnum.OFF.value());

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setIsDeleted(OnOffEnum.OFF.value());

        entity.setRoleName(bean.getRoleName());
        entity.setIsSuper(bean.getIsSuper());
        entity.setRemark(bean.getRemark());

        // 每次重新赋值pages，使得新建/修改时，一并调整角色的page关联（中间表）
        entity.setPages(new HashSet<Page>());
        if (bean.getPageIdList() != null) {
            for (String pageId : bean.getPageIdList()) {
                Page page = new Page();
                page.setId(pageId);
                entity.getPages().add(page);
            }
        }

        return entity;
    }

}
