/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.AcceptanceCheck;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.FieldListWrapperBean;

/**
 * @author angel_000
 *
 */
public class AcceptanceCheckInfoBeanConverter {

    /**
     * 
     * 使用项目详情默认配置。
     * 
     * @param a
     * @param messageSource
     * @param locale
     * @return
     */
    public FieldListWrapperBean fromEntity(AcceptanceCheck a, MessageSource messageSource,
            Locale locale) {

        if (a == null) {
            return null;
        }

        FieldListWrapperBean bean = new FieldListWrapperBean();

        List<FieldBean> fields = bean.getFieldList();

        // 项目
        if (a.getProject() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.name", null, locale),
                    a.getProject().getProjectName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.name", null, locale), ""));
        }

        if (a.getAcceptanceCheckType() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.datatype.acceptance.check.type", null, locale),
                    a.getAcceptanceCheckType().getOptionName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.datatype.acceptance.check.type", null, locale),
                    ""));
        }

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.checking.unit", null, locale),
                a.getCheckingUnit()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.cheched.unit", null, locale),
                a.getChechedUnit()));

        fields.add(
                new FieldBean(messageSource.getMessage("ui.fields.acceptance.check.checked.content",
                        null, locale), a.getCheckedContent()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.check.basis", null, locale),
                a.getCheckBasis()));

//        fields.add(new FieldBean(
//                messageSource.getMessage("ui.fields.acceptance.check.checker", null, locale),
//                a.getChecker()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.is.improve", null, locale),
                a.getIsImprove() == 1
                        ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                        : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale)));

        if (a.getAcceptanceCheckResult() != null) {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.acceptance.check.acceptance.check.result", null, locale),
                    a.getAcceptanceCheckResult().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource.getMessage(
                    "ui.fields.acceptance.check.acceptance.check.result", null, locale), ""));
        }

        if (a.getAcceptanceVerifyResult() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.acceptance.check.acceptance.verify.result",
                            null, locale),
                    a.getAcceptanceVerifyResult().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource.getMessage(
                    "ui.fields.acceptance.check.acceptance.verify.result", null, locale), ""));
        }

        if (a.getCheckDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.acceptance.check.check.date", null, locale),
                    DateUtils.date2String(a.getCheckDate(), Constants.SIMPLE_DATE_TIME_FORMAT)));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.acceptance.check.check.date", null, locale),
                    ""));
        }

        if (a.getImproveReqDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.req.date", null, locale),
                    DateUtils.date2String(a.getImproveReqDate(),
                            Constants.SIMPLE_DATE_TIME_FORMAT)));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.req.date", null, locale), ""));
        }

        if (a.getImprovePlanDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.plan.date", null, locale),
                    DateUtils.date2String(a.getImprovePlanDate(),
                            Constants.SIMPLE_DATE_TIME_FORMAT)));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.plan.date", null, locale), ""));
        }

        if (a.getImproveActualDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.actual.date", null, locale),
                    DateUtils.date2String(a.getImproveActualDate(),
                            Constants.SIMPLE_DATE_TIME_FORMAT)));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.actual.date", null, locale), ""));
        }

        fields.add(new FieldBean(messageSource
                .getMessage("ui.fields.acceptance.check.check.result.content", null, locale),
                a.getCheckResultContent()));

        fields.add(new FieldBean(messageSource.getMessage("ui.fields.improve.req", null, locale),
                a.getImproveReq()));

        fields.add(new FieldBean(messageSource.getMessage("ui.fields.improve.plan", null, locale),
                a.getImprovePlan()));

        if (a.getVerifyDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.verify.date", null, locale),
                    DateUtils.date2String(a.getVerifyDate(), Constants.SIMPLE_DATE_TIME_FORMAT)));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.verify.date", null, locale), ""));
        }

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.improve.result.verify", null, locale),
                a.getImproveResultVerify()));

        fields.add(new FieldBean(messageSource.getMessage("ui.fields.remark", null, locale),
                a.getRemark()));


        return bean;
    }
}
