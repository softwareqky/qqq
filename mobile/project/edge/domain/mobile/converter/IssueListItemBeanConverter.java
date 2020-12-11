/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.entity.Issue;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.IssueListItemBean;

/**
 * @author angel_000
 *
 */
public class IssueListItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param issue
     * @param messageSource
     * @param locale
     * @return
     */
    public IssueListItemBean fromEntity(Issue issue, MessageSource messageSource, Locale locale) {

        if (issue == null) {
            return null;
        }

        IssueListItemBean bean = new IssueListItemBean();

        bean.setIssueId(issue.getId());

        // 问题标题
        FieldBean issueTitle =
                new FieldBean(messageSource.getMessage("ui.fields.issue.title", null, locale), "");

        // 问题类别
        FieldBean issueType =
                new FieldBean(messageSource.getMessage("ui.fields.issue.type", null, locale), "");

        // 指派给
        FieldBean assignTo = new FieldBean(
                messageSource.getMessage("ui.fields.issue.assign.to", null, locale), "");

        // 解决状态
        FieldBean solveStatus = new FieldBean(
                messageSource.getMessage("ui.fields.issue.solve.status", null, locale), "");

        bean.getFieldList().add(issueTitle);
        bean.getFieldList().add(issueType);
        bean.getFieldList().add(assignTo);
        bean.getFieldList().add(solveStatus);

        issueTitle.setFieldValue(issue.getIssueTitle());

        if (issue.getIssueType() != null) {
            issueType.setFieldValue(issue.getIssueType().getOptionName());
        }

        if (issue.getAssignTo() != null) {
            assignTo.setFieldValue(issue.getAssignTo().getPerson().getPersonName());
        }

        if (issue.getSolveStatus() != null) {
            solveStatus.setFieldValue(issue.getSolveStatus().getOptionName());
        }

        return bean;
    }

}
