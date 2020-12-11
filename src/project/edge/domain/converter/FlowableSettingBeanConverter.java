package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.Page;
import project.edge.domain.view.FlowableSettingBean;


public class FlowableSettingBeanConverter
        implements ViewConverter<FlowableSetting, FlowableSettingBean> {

    @Override
    public FlowableSettingBean fromEntity(FlowableSetting entity, MessageSource messageSource,
            Locale locale) {
        FlowableSettingBean bean = new FlowableSettingBean();

        bean.setId(entity.getId());
        bean.setFlowName(entity.getFlowName());

        if (entity.getIsComplete() != null) {
            bean.setIsComplete(entity.getIsComplete());
            bean.setIsCompleteText(messageSource
                    .getMessage(OnOffEnum.getResouceName(bean.getIsComplete()), null, locale));
        }

        if (entity.getIsDisable() != null) {
            bean.setIsDisable(entity.getIsDisable());
            bean.setIsDisableText(messageSource
                    .getMessage(OnOffEnum.getResouceName(bean.getIsDisable()), null, locale));
        }

        if (entity.getPage() != null) {
            bean.setPage_(entity.getPage().getId());
        }

/*        if (!entity.getProjectRoles().isEmpty()) {
            for (ProjectRole pr : entity.getProjectRoles()) {
                bean.getProjectRoleIdList().add(pr.getId());
            }
        }*/

        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public FlowableSetting toEntity(FlowableSettingBean bean, FlowableSetting oldEntity,
            AbstractSessionUserBean user, Date now) {

        FlowableSetting entity = oldEntity;

        if (entity == null) {// 表示新建
            entity = new FlowableSetting();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else {// 表示修改
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPage_())) {
            Page page = new Page();
            page.setId(bean.getPage_());

            entity.setPage(page);
        }

        entity.setFlowName(bean.getFlowName());
        entity.setIsComplete(bean.getIsComplete());
        entity.setIsDisable(bean.getIsDisable());
        entity.setRemark(bean.getRemark());

        return entity;
    }

}
