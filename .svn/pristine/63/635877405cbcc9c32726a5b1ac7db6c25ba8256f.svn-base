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
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.OaFlowHistory;
import project.edge.domain.view.OaFlowHistoryBean;

/**
 * @author angel_000
 *         转换OA流程审批历史表对应的view和entity的转换器。
 */
public class OaFlowHistoryBeanConverter implements ViewConverter<OaFlowHistory, OaFlowHistoryBean> {

    @Override
    public OaFlowHistoryBean fromEntity(OaFlowHistory entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        OaFlowHistoryBean bean = new OaFlowHistoryBean();

        bean.setId(entity.getId());
        bean.setCategory(entity.getCategory());
        bean.setRelatedFormId(entity.getRelatedFormId());
        bean.setFlowAction(entity.getFlowAction());
        bean.setFlowActionText(messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowAction()), null, locale));

        if (entity.getFlowDatetime() != null) {
            bean.setFlowDatetime(
                    DateUtils.date2String(entity.getFlowDatetime(), Constants.DATE_FORMAT));
        }

        bean.setCreator(entity.getCreator());
        bean.setRemark(entity.getRemark());

        return bean;
    }

    @Override
    public OaFlowHistory toEntity(OaFlowHistoryBean bean, OaFlowHistory oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        OaFlowHistory entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new OaFlowHistory();

            entity.setRemark("");
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setCategory(bean.getCategory());
        entity.setRelatedFormId(bean.getRelatedFormId());
        entity.setFlowAction(bean.getFlowAction());

        if (!StringUtils.isEmpty(bean.getFlowDatetime())) {
            try {
                entity.setFlowDatetime(
                        DateUtils.string2Date(bean.getFlowDatetime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }
        entity.setRemark(bean.getRemark());


        return entity;
    }

}
