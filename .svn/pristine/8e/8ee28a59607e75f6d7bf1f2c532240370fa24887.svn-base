/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.Project;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.ProjectListItemBean;

/**
 * @author angel_000
 *
 */
public class ProjectApproveItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public ProjectListItemBean fromEntity(Project p, MessageSource messageSource, Locale locale) {

        if (p == null) {
            return null;
        }

        ProjectListItemBean bean = new ProjectListItemBean();

        bean.setProjectId(p.getId());

        // 项目名称
        FieldBean projectName = new FieldBean(
                messageSource.getMessage("ui.fields.project.project.name", null, locale), "");

        // 主要负责人
        FieldBean mainLeader = new FieldBean(
                messageSource.getMessage("ui.fields.project.main.leader", null, locale), "");

        // 计划开始日期
        FieldBean startDate = new FieldBean(
                messageSource.getMessage("ui.fields.project.project.start.date", null, locale), "");

        // 流程状态
        FieldBean projectStatus = new FieldBean(
                messageSource.getMessage("ui.fields.project.flow.status.project", null, locale),
                "");

        bean.getFieldList().add(projectName);
        bean.getFieldList().add(mainLeader);
        bean.getFieldList().add(startDate);
        bean.getFieldList().add(projectStatus);

        projectName.setFieldValue(p.getProjectName());
        mainLeader.setFieldValue(p.getMainLeaderName());
        startDate.setFieldValue(
                DateUtils.date2String(p.getProjectStartDate(), Constants.SIMPLE_DATE_TIME_FORMAT));
        projectStatus.setFieldValue(messageSource
                .getMessage(FlowStatusEnum.getResouceName(p.getFlowStatusProject()), null, locale));

        return bean;

    }
}
