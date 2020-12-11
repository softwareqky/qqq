package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FacilityStatusEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.domain.entity.PresetTask;
import project.edge.domain.view.PresetTaskBean;

/**
 * @author angel_000
 *         转换站点和中继段相关的预设任务表对应的view和entity的转换器。
 *
 */
public class PresetTaskBeanConverter implements ViewConverter<PresetTask, PresetTaskBean> {

    @Override
    public PresetTaskBean fromEntity(PresetTask entity, MessageSource messageSource,
            Locale locale) {

        PresetTaskBean bean = new PresetTaskBean();

        bean.setId(entity.getId());
        bean.setTaskNum(entity.getTaskNum());
        bean.setPreTaskNum(entity.getPreTaskNum());
        bean.setTaskName(entity.getTaskName());
        bean.setTaskType(entity.getTaskType());
        bean.setIsSiteTask(entity.getIsSiteTask());
        bean.setIsSiteTaskText(entity.getIsSiteTask() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        bean.setProjectGroup(entity.getProjectGroup());
        bean.setProjectGroupText(messageSource.getMessage(
                ProjectGroupEnum.getResouceName(entity.getProjectGroup()), null, locale));
        bean.setStartDateDayOffset(entity.getStartDateDayOffset());
        bean.setEndDateDayOffset(entity.getEndDateDayOffset());
        bean.setDurationDays(entity.getDurationDays());

        if (entity.getConstructionStatus() != null) {
            bean.setConstructionStatus(entity.getConstructionStatus());
            bean.setConstructionStatusText(messageSource.getMessage(
            		FacilityStatusEnum.getResouceName(entity.getConstructionStatus()), null,
                    locale));
        }


        return bean;
    }

    @Override
    public PresetTask toEntity(PresetTaskBean bean, PresetTask oldEntity,
            AbstractSessionUserBean user, Date now) {
        PresetTask entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PresetTask();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());

        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        entity.setTaskNum(bean.getTaskNum());
        entity.setPreTaskNum(bean.getPreTaskNum());
        entity.setTaskName(bean.getTaskName());
        entity.setTaskType(bean.getTaskType());
        entity.setIsSiteTask(bean.getIsSiteTask());
        entity.setProjectGroup(bean.getProjectGroup());
        entity.setStartDateDayOffset(bean.getStartDateDayOffset());
        entity.setEndDateDayOffset(bean.getEndDateDayOffset());
        entity.setDurationDays(bean.getDurationDays());
        entity.setConstructionStatus(bean.getConstructionStatus());

        return entity;
    }

}
