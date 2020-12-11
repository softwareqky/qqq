package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.ProjectInspectListItemBean;

/**
 * @author angel_000
 *
 */
public class ProjectInspectListItemBeanConverter {

    /**
     * 使用系统默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public ProjectInspectListItemBean fromEntity(ProjectInspect p, MessageSource messageSource,
            Locale locale) {

        if (p == null) {
            return null;
        }

        ProjectInspectListItemBean bean = new ProjectInspectListItemBean();

        bean.setProjectInspectId(p.getId());
        
    	// 站点ID
        FieldBean siteId =
                new FieldBean("站点ID", p.getSite().getId());
        
        // 站点
        FieldBean site =
                new FieldBean(messageSource.getMessage("ui.datatype.site", null, locale), "");
        
        // 巡查类型ID
        FieldBean inspectTypeId = new FieldBean("巡查类型ID", p.getInspectType().getId());
        
        // 巡查类型
        FieldBean inspectType = new FieldBean(
                messageSource.getMessage("ui.fields.project.inspect.inspect.type", null, locale),
                "");

        // 项目ID
        FieldBean projectId = new FieldBean("项目ID", p.getProject().getId());
        
        // 项目
        FieldBean project = new FieldBean(
                messageSource.getMessage("ui.fields.project.inspect.project.id", null, locale), "");

        // 巡查责任人
        FieldBean inspector = new FieldBean(
                messageSource.getMessage("ui.fields.project.inspect.inspector", null, locale), "");

        // 巡查结果
        FieldBean inspectResult = new FieldBean(
                messageSource.getMessage("ui.fields.project.inspect.inspect.result", null, locale),
                "");

        // 申请开始日期
        FieldBean flowStartDate = new FieldBean(
                messageSource.getMessage("ui.fields.flow.start.date", null, locale), "");

        // 申请结束日期
        FieldBean flowEndDate = new FieldBean(
                messageSource.getMessage("ui.fields.flow.end.date", null, locale), "");

        // 申请流程状态
        FieldBean flowStatus =
                new FieldBean(messageSource.getMessage("ui.fields.flow.status", null, locale), "");
        
        
        
        bean.getFieldList().add(siteId);
        bean.getFieldList().add(site);
        bean.getFieldList().add(inspectTypeId);
        bean.getFieldList().add(inspectType);
        bean.getFieldList().add(projectId);
        bean.getFieldList().add(project);
        bean.getFieldList().add(inspector);
        bean.getFieldList().add(inspectResult);
        bean.getFieldList().add(flowStartDate);
        bean.getFieldList().add(flowEndDate);
        bean.getFieldList().add(flowStatus);
        
        //整改信息
        bean.getFieldList().add(new FieldBean("计划整改完成日期", p.getImprovePlanDate()== null ? "" : DateUtils.date2String(p.getImprovePlanDate())));
        bean.getFieldList().add(new FieldBean("实际整改完成日期", p.getImproveActualDate()== null ? "" : DateUtils.date2String(p.getImproveActualDate())));
        bean.getFieldList().add(new FieldBean("整改要求", p.getImproveReq()));
        bean.getFieldList().add(new FieldBean("整改计划", p.getImprovePlan()));
        
        //验证信息
        bean.getFieldList().add(new FieldBean("验证结果", p.getVerifyResult()== null ? "" : p.getVerifyResult().getOptionName()));
        bean.getFieldList().add(new FieldBean("验证日期", p.getVerifyDate() == null ? "" : DateUtils.date2String(p.getVerifyDate())));
        bean.getFieldList().add(new FieldBean("整改结果验证", p.getImproveResultVerify() == null ? "" : p.getImproveResultVerify()));
//        bean.getFieldList().add(new FieldBean("附件", p.getProjectInspectAttachments()));
        
        site.setFieldValue(p.getSite().getStationName());
        inspectType.setFieldValue(p.getInspectType().getOptionName());
        project.setFieldValue(p.getProject().getProjectName());
        inspector.setFieldValue(p.getInspector().getPersonName());
        if (p.getInspectResult() != null) {
            inspectResult.setFieldValue(p.getInspectResult().getOptionName());
        }

        if (p.getFlowStartDate() != null) {
            flowStartDate.setFieldValue(
                    DateUtils.date2String(p.getFlowStartDate(), Constants.DATE_FORMAT));
        }

        if (p.getFlowEndDate() != null) {
            flowEndDate.setFieldValue(
                    DateUtils.date2String(p.getFlowEndDate(), Constants.DATE_FORMAT));
        }
        
        flowStatus.setFieldValue(messageSource
                .getMessage(FlowStatusEnum.getResouceName(p.getFlowStatus()), null, locale));

        return bean;

    }
}
