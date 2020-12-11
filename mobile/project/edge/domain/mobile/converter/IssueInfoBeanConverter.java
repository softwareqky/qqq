/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.Issue;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.FieldListWrapperBean;

/**
 * @author angel_000
 *
 */
public class IssueInfoBeanConverter {

    /**
     * 
     * 使用项目详情默认配置。
     * 
     * @param a
     * @param messageSource
     * @param locale
     * @return
     */
    public FieldListWrapperBean fromEntity(Issue issue, MessageSource messageSource,
            Locale locale) {

        if (issue == null) {
            return null;
        }

        FieldListWrapperBean bean = new FieldListWrapperBean();

        List<FieldBean> fields = bean.getFieldList();

        // 项目
        if (issue.getProject() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.name", null, locale),
                    issue.getProject().getProjectName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.name", null, locale), ""));
        }

        fields.add(new FieldBean(messageSource.getMessage("ui.fields.issue.title", null, locale),
                issue.getIssueTitle()));

        if (issue.getIssueType() != null) {
            fields.add(new FieldBean(messageSource.getMessage("ui.fields.issue.type", null, locale),
                    issue.getIssueType().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource.getMessage("ui.fields.issue.type", null, locale),
                    ""));
        }

        if (issue.getInputDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.input.date", null, locale), DateUtils
                            .date2String(issue.getInputDate(), Constants.SIMPLE_DATE_TIME_FORMAT)));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.input.date", null, locale), ""));
        }


        // private DataOption solveStatus;

        if (issue.getAssignTo() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.assign.to", null, locale),
                    issue.getAssignTo().getPerson().getPersonName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.assign.to", null, locale), ""));
        }

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.issue.internal.verify", null, locale),
                issue.getInternalVerify()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.issue.internal.verify.feedback", null, locale),
                issue.getInternalVerifyFeedback()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.issue.external.verify", null, locale),
                issue.getExternalVerify()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.issue.external.verify.feedback", null, locale),
                issue.getExternalVerifyFeedback()));

        fields.add(new FieldBean(messageSource.getMessage("ui.fields.issue.desc", null, locale),
                issue.getIssueDesc()));

        if (issue.getReassignTo() != null) {

            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.reassign.to", null, locale),
                    issue.getReassignTo().getPerson().getPersonName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.reassign.to", null, locale), ""));
        }

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.issue.reassign.reason", null, locale),
                issue.getReassignReason()));

        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.issue.solve.content", null, locale),
                issue.getSolveContent()));

        if (issue.getSolveStatus() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.solve.status", null, locale),
                    issue.getSolveStatus().getOptionName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.issue.solve.status", null, locale), ""));
        }

        return bean;
    }
}
