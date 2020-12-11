package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.AuthTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ArchiveAuthority;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Org;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.User;
import project.edge.domain.view.ArchiveAuthorityBean;

/**
 * 转换档案文件权限表信息对应的view和entity的转换器。
 */
public class ArchiveAuthorityBeanConverter
        implements ViewConverter<ArchiveAuthority, ArchiveAuthorityBean> {

    @Override
    public ArchiveAuthorityBean fromEntity(ArchiveAuthority entity, MessageSource messageSource,
            Locale locale) {

        ArchiveAuthorityBean bean = new ArchiveAuthorityBean();

        bean.setId(entity.getId());

        bean.setArchive_(entity.getArchive().getId());
        bean.setArchiveText(entity.getArchive().getArchiveName());

        String path = entity.getArchive().getPath();
        if (!StringUtils.isEmpty(path)) {
            String[] idArray = StringUtils.commaDelimitedListToStringArray(path);
            // 顺序是从根节点开始
            for (String id : idArray) {
                bean.getPathList().add(id);
            }
        }

        Person p = entity.getPerson();
        if (p != null) {
            bean.setPerson_(p.getId());
            bean.setPersonText(p.getPersonName());

            Org org = p.getOrg();
            if (org != null) {
                bean.setOrg(org.getOrgName());
            }

            Dept dept = p.getDept();
            if (dept != null) {
                bean.setDept(dept.getDeptName());
            }

            User user = p.getUser();
            if (user != null) {
                bean.setUser(user.getLoginName());
            }
        }

        bean.setAuthType(entity.getAuthType());
        bean.setAuthTypeText(messageSource
                .getMessage(AuthTypeEnum.getResouceName(entity.getAuthType()), null, locale));
        bean.setIsInherit(entity.getIsInherit());

        return bean;
    }

    @Override
    public ArchiveAuthority toEntity(ArchiveAuthorityBean bean, ArchiveAuthority oldEntity,
            AbstractSessionUserBean user, Date now) {

        ArchiveAuthority entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new ArchiveAuthority();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        Archive a = new Archive();
        a.setId(bean.getArchive_());
        entity.setArchive(a);

        Person p = new Person();
        p.setId(bean.getPerson_());
        entity.setPerson(p);

        entity.setAuthType(bean.getAuthType());

        return entity;
    }

}
