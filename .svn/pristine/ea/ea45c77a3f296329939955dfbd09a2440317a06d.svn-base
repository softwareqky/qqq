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
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanProgressTask;
import project.edge.domain.view.PlanProgressTaskBean;

/**
 * @author angel_000
 *         转换实际进度对应的view和entity的转换器。
 */
public class PlanProgressTaskBeanConverter
        implements ViewConverter<PlanProgressTask, PlanProgressTaskBean> {

    @Override
    public PlanProgressTaskBean fromEntity(PlanProgressTask entity, MessageSource messageSource,
            Locale locale) {

        PlanProgressTaskBean bean = new PlanProgressTaskBean();
        bean.setId(entity.getId());

        Plan plan = entity.getPlan();
        if (plan != null) {
            bean.setPlan_(plan.getId());
            bean.setPlanText(plan.getPlanName());
        }

        if (entity.getProgressDate() != null) {
            bean.setProgressDate(
                    DateUtils.date2String(entity.getProgressDate(), Constants.DATE_FORMAT));
        }

        return bean;
    }

    @Override
    public PlanProgressTask toEntity(PlanProgressTaskBean bean, PlanProgressTask oldEntity,
            AbstractSessionUserBean user, Date now) {
        PlanProgressTask entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PlanProgressTask();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getPlan_())) {
            Plan plan = new Plan();
            plan.setId(bean.getPlan_());
            entity.setPlan(plan);
        }

        if (!StringUtils.isEmpty(bean.getProgressDate())) {
            try {
                entity.setProgressDate(
                        DateUtils.string2Date(bean.getProgressDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        return entity;
    }

}
