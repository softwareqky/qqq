package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.SyncStatusEnum;
import project.edge.domain.entity.Org;
import project.edge.domain.view.OrgBean;

/**
 * @author angel_000
 *         转换组织信息对应的view和entity的转换器。
 */
public class OrgBeanConverter implements ViewConverter<Org, OrgBean> {

    @Override
    public OrgBean fromEntity(Org entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        OrgBean bean = new OrgBean();
        bean.setId(entity.getId());
        bean.setOrgName(entity.getOrgName());
        bean.setShortName(entity.getShortName());
        bean.setOrgCode(entity.getOrgCode());
        bean.setPid(entity.getPid());
        bean.setLevel(entity.getLevel());
        bean.setOrgOrder(entity.getOrgOrder());

        bean.setSyncStatusText(messageSource
                .getMessage(SyncStatusEnum.getResouceName(entity.getSyncStatus()), null, locale));


        return bean;

    }

    @Override
    public Org toEntity(OrgBean bean, Org oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        Org entity = oldEntity;

        if (entity == null) {// 表示新建
            entity = new Org();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else {// 表示修改
            entity.setModifier(user.getSessionUserId());
        }
        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setOrgName(bean.getOrgName());
        entity.setShortName(bean.getShortName());
        entity.setOrgCode(bean.getOrgCode());
        entity.setPid(bean.getPid());
        entity.setLevel(bean.getLevel());
        entity.setOrgCode(bean.getOrgCode());

        return entity;
    }

}
