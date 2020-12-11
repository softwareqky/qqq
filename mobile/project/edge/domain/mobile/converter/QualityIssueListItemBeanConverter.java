/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.Issue;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.QualityIssueListItemBean;

/**
 * @author angel_000
 *
 */
public class QualityIssueListItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param a
     * @param messageSource
     * @param locale
     * @return
     */
    public QualityIssueListItemBean fromEntity(Issue issue, MessageSource messageSource,
            Locale locale) {

        if (issue == null) {
            return null;
        }

        QualityIssueListItemBean bean = new QualityIssueListItemBean();

        bean.setProjectId(issue.getProject().getId());

        // 项目名称
        FieldBean projectName = new FieldBean(
                messageSource.getMessage("ui.fields.project.project.name", null, locale), "");

        // 问题标题
        FieldBean issueTitle =
                new FieldBean(messageSource.getMessage("ui.fields.issue.title", null, locale), "");

        // 问题类别
        FieldBean issueType =
                new FieldBean(messageSource.getMessage("ui.fields.issue.type", null, locale), "");

        // 登记日期
        FieldBean inputDate = new FieldBean(
                messageSource.getMessage("ui.fields.issue.input.date", null, locale), "");

        bean.getFieldList().add(projectName);
        bean.getFieldList().add(issueTitle);
        bean.getFieldList().add(issueType);
        bean.getFieldList().add(inputDate);

        if (issue.getProject() != null) {
            projectName.setFieldValue(issue.getProject().getProjectName());
        }

        if (issue.getIssueType() != null) {
            issueType.setFieldValue(issue.getIssueType().getOptionName());
        }

        issueTitle.setFieldValue(issue.getIssueTitle());

        if (issue.getInputDate() != null) {
            inputDate.setFieldValue(
                    DateUtils.date2String(issue.getInputDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
        }

        return bean;
    }
}
