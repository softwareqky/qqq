package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.PlanCalendar;
import project.edge.domain.view.PlanCalendarBean;

/**
 * @author angel_000
 *         计划日历信息对应的view和entity的转换器。
 */
public class PlanCalendarBeanConverter implements ViewConverter<PlanCalendar, PlanCalendarBean> {

    @Override
    public PlanCalendarBean fromEntity(PlanCalendar entity, MessageSource messageSource,
            Locale locale) {
        PlanCalendarBean bean = new PlanCalendarBean();

        bean.setId(entity.getId());
        bean.setCalendarName(entity.getCalendarName());
        bean.setWeekdays(entity.getWeekdays());

        return bean;
    }

    @Override
    public PlanCalendar toEntity(PlanCalendarBean bean, PlanCalendar oldEntity,
            AbstractSessionUserBean user, Date now) {

        PlanCalendar entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PlanCalendar();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setCalendarName(bean.getCalendarName());
        entity.setWeekdays(bean.getWeekdays());

        return entity;
    }

}
