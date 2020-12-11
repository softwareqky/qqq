package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.ProjectCheckListItemBean;

/**
 * @author angel_000
 *
 */
public class ProjectCheckListItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public ProjectCheckListItemBean fromEntity(ProjectCheck p, MessageSource messageSource,
            Locale locale) {

        if (p == null) {
            return null;
        }

        ProjectCheckListItemBean bean = new ProjectCheckListItemBean();

        bean.setProjectCheckId(p.getId());

        // 项目
        FieldBean project = new FieldBean(
                messageSource.getMessage("ui.fields.project.check.project.name", null, locale), "");

        // 检查类别
        FieldBean checkType = new FieldBean(
                messageSource.getMessage("ui.fields.project.check.check.type", null, locale), "");

        // 检查单位
        FieldBean checkingUnit = new FieldBean(
                messageSource.getMessage("ui.fields.project.check.checking.unit", null, locale),
                "");

        // 受检单位
        FieldBean chechedUnit = new FieldBean(
                messageSource.getMessage("ui.fields.project.check.cheched.unit", null, locale), "");

        // 检查责任人
        FieldBean checker = new FieldBean(
                messageSource.getMessage("ui.fields.project.check.checker", null, locale), "");

        // 检查结果
        FieldBean checkResult = new FieldBean(
                messageSource.getMessage("ui.fields.project.check.check.result", null, locale), "");

        // 申请开始日期
        FieldBean flowStartDate = new FieldBean(
                messageSource.getMessage("ui.fields.flow.start.date", null, locale), "");

        // 申请结束日期
        FieldBean flowEndDate = new FieldBean(
                messageSource.getMessage("ui.fields.flow.end.date", null, locale), "");

        // 申请流程状态
        FieldBean flowStatus =
                new FieldBean(messageSource.getMessage("ui.fields.flow.status", null, locale), "");

        bean.getFieldList().add(project);
        bean.getFieldList().add(checkType);
        bean.getFieldList().add(checkingUnit);
        bean.getFieldList().add(chechedUnit);
        bean.getFieldList().add(checker);
        bean.getFieldList().add(checkResult);
        bean.getFieldList().add(flowStartDate);
        bean.getFieldList().add(flowEndDate);
        bean.getFieldList().add(flowStatus);

        project.setFieldValue(p.getProject().getId());
        checkType.setFieldValue(p.getCheckType().getDataType());
        checkingUnit.setFieldValue(p.getCheckingUnit());
        chechedUnit.setFieldValue(p.getChechedUnit());
        checker.setFieldValue(p.getChecker());
        if(p.getCheckResult() != null) {
            checkResult.setFieldValue(p.getCheckResult().getOptionName());
        }
        if(p.getFlowStartDate() != null) {
            flowStartDate.setFieldValue(
                DateUtils.date2String(p.getFlowStartDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
        }
        if(p.getFlowEndDate() != null) {
            flowEndDate.setFieldValue(
                DateUtils.date2String(p.getFlowEndDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
        }
        flowStatus.setFieldValue(messageSource
                .getMessage(FlowStatusEnum.getResouceName(p.getFlowStatus()), null, locale));

        return bean;

    }
}
