/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import project.edge.domain.entity.AcceptanceCheck;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.ProjectAcceptanceListItemBean;

/**
 * @author angel_000
 *
 */
public class ProjectAcceptanceListItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param a
     * @param messageSource
     * @param locale
     * @return
     */
    public ProjectAcceptanceListItemBean fromEntity(AcceptanceCheck a, MessageSource messageSource,
            Locale locale) {

        if (a == null) {
            return null;
        }

        ProjectAcceptanceListItemBean bean = new ProjectAcceptanceListItemBean();
        
        bean.setProjectId(a.getProject().getId());

        // 项目名称
        FieldBean projectName = new FieldBean(
                messageSource.getMessage("ui.fields.project.project.name", null, locale), "");
        
        // 验收类别
        FieldBean acceptanceCheckType = new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.acceptance.check.type", null, locale), "");

        // 验收单位
        FieldBean checkingUnit = new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.checking.unit", null, locale), "");
        
        // 验收责任人
        FieldBean checker = new FieldBean(
                messageSource.getMessage("ui.fields.acceptance.check.checker", null, locale), "");
        
        bean.getFieldList().add(projectName);
        bean.getFieldList().add(acceptanceCheckType);
        bean.getFieldList().add(checkingUnit);
        bean.getFieldList().add(checker);
        
        if(a.getProject() != null) {
            projectName.setFieldValue(a.getProject().getProjectName());
        }
        
        if(a.getAcceptanceCheckType() != null) {
            acceptanceCheckType.setFieldValue(a.getAcceptanceCheckType().getOptionName());
        }
        
        checkingUnit.setFieldValue(a.getCheckingUnit());
//        checker.setFieldValue(a.getChecker());
        
        return bean;
    }
}
