/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.AcceptanceCheck;
import project.edge.domain.mobile.view.AcceptanceCheckListItemBean;
import project.edge.domain.mobile.view.FieldBean;

/**
 * @author angel_000
 *
 */
public class AcceptanceCheckListItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param a
     * @param messageSource
     * @param locale
     * @return
     */
    public AcceptanceCheckListItemBean fromEntity(AcceptanceCheck a, MessageSource messageSource,
            Locale locale) {

        if (a == null) {
            return null;
        }

        AcceptanceCheckListItemBean bean = new AcceptanceCheckListItemBean();

        bean.setAcceptanceCheckId(a.getId());

        // 项目名称
        FieldBean projectName = new FieldBean(
                messageSource.getMessage("ui.fields.project.project.name", null, locale), "");

        // 验收类别
        FieldBean acceptanceCheckType = new FieldBean(messageSource
                .getMessage("ui.fields.acceptance.check.acceptance.check.type", null, locale), "");

        // 验收责任人
        FieldBean checker = new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.checker", null, locale), "");

        // 验收日期
        FieldBean checkDate = new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.check.date", null, locale),
                "");

        bean.getFieldList().add(projectName);
        bean.getFieldList().add(acceptanceCheckType);
        bean.getFieldList().add(checker);
        bean.getFieldList().add(checkDate);

        if (a.getProject() != null) {
            projectName.setFieldValue(a.getProject().getProjectName());
        }

        if (a.getAcceptanceCheckType() != null) {
            acceptanceCheckType.setFieldValue(a.getAcceptanceCheckType().getOptionName());
        }

        if (a.getCheckDate() != null) {
            checkDate.setFieldValue(
                    DateUtils.date2String(a.getCheckDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
        }

//        checker.setFieldValue(a.getChecker()); // to do change

        return bean;
    }
}
