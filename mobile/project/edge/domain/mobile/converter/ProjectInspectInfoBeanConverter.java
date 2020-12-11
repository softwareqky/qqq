package project.edge.domain.mobile.converter;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import garage.origin.common.util.DateUtils;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.ProjectInspectAttachment;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.FieldListWrapperBean;

public class ProjectInspectInfoBeanConverter {

    /**
     * 
     * 使用项目详情默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public FieldListWrapperBean fromEntity(ProjectInspect p, MessageSource messageSource,
            Locale locale) {

        if (p == null) {
            return null;
        }

        FieldListWrapperBean bean = new FieldListWrapperBean();

        List<FieldBean> fields = bean.getFieldList();
        
        Set<ProjectInspectAttachment> projectInspectAttachments =p.getProjectInspectAttachments();
        
        //巡查内容
        if (p.getInspectContent() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.inspect.inspect.content",
                            null, locale), p.getInspectContent()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.content", null, locale), ""));
        }
        
        // 巡查类型
        if (p.getInspectType() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.inspect.inspect.type",
                            null, locale), p.getInspectType().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.type", null, locale), ""));
        }
        
        // 巡查结果
        if (p.getInspectResult() != null) {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.result", null, locale),
                    p.getInspectResult().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.result", null, locale), ""));
        }

        // 项目
        if (p.getProject() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.inspect.project.id", null, locale),
                    p.getProject().getProjectName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.inspect.project.id", null, locale),
                    ""));
        }
        
        // 站点
        if (p.getSite() != null) {
            fields.add(new FieldBean(messageSource.getMessage("ui.datatype.site", null, locale),
                    p.getSite().getStationName()));
        } else {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.datatype.site", null, locale), ""));
        }
        
        //巡查计划
        if (p.getInspectPlan() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.inspect.inspect.plan",
                            null, locale), p.getInspectPlan()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.plan", null, locale), ""));
        }
        
        //巡查区域
        if (p.getInspectArea() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.inspect.inspect.area",
                            null, locale), p.getInspectArea()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.area", null, locale), ""));
        }
        
        //巡查依据
        if (p.getInspectBasis() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.inspect.inspect.basis",
                            null, locale), p.getInspectBasis()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.basis", null, locale), ""));
        }
        
        //附件
        String archiveName1 = null;
        for (ProjectInspectAttachment pa : projectInspectAttachments) {
            if (pa.getIsImprove() == OnOffEnum.OFF.value()) {
                archiveName1=pa.getArchive().getArchiveName();
            }
        }
        if(archiveName1 != null) {
            fields.add(new FieldBean(messageSource
                .getMessage("ui.fields.archive", null, locale),archiveName1));
        }else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.archive", null, locale),""));
        }
        
        // 巡查责任人
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.inspect.inspector", null, locale),
                p.getInspector().getPersonName()));
        
        //巡查具体内容
        if (p.getInspectResultContent() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.inspect.inspect.result.content",
                            null, locale), p.getInspectResultContent()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.result.content", null, locale), ""));
        }
        
        // 是否需要整改
        if(p.getIsImprove() == OnOffEnum.ON.value()) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.inspect.isimprove", null, locale),
                    "是"));
        }
        if(p.getIsImprove() == OnOffEnum.OFF.value()) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.inspect.isimprove", null, locale),
                    "否"));
        }
        

        //巡查日期
        if (p.getInspectDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.inspect.inspect.date", null, locale),
                    DateUtils.date2String(p.getInspectDate(),Constants.DATE_FORMAT)));
        }else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.inspect.inspect.date", null, locale),""));
        }
        
        //要求整改完成日期
        if (p.getImproveReqDate() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.req.date", null, locale),
                    DateUtils.date2String(p.getImproveReqDate(),Constants.DATE_FORMAT)));
        }else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.improve.req.date", null, locale),""));
        }
        
        // 巡查状态
        if (p.getInspectStatus() != null) {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.status", null, locale),
                    p.getInspectStatus().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.inspect.status", null, locale), ""));
        }
        
        //备注
        if (p.getRemark() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.remark",
                            null, locale), p.getRemark()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.remark", null, locale), ""));
        }
        
        // 申请开始日期
        if (p.getFlowStartDate() != null) {

            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.flow.start.date", null, locale),
                    DateUtils.date2String(p.getFlowStartDate(),Constants.DATE_FORMAT)));
        }

        // 申请结束日期
        if (p.getFlowEndDate() != null) {

            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.flow.end.date", null, locale),
                            DateUtils.date2String(p.getFlowEndDate(),Constants.DATE_FORMAT)));
        }

        // 申请流程状态
        fields.add(new FieldBean(messageSource.getMessage("ui.fields.flow.status", null, locale),
                messageSource.getMessage(FlowStatusEnum.getResouceName(p.getFlowStatus()), null,
                        locale)));

        // 计划整改完成日期
        if (p.getImprovePlanDate() != null) {

            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.plan.date", null, locale),
                            DateUtils.date2String(p.getImprovePlanDate(),Constants.DATE_FORMAT)));
        }else {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.plan.date", null, locale),""));
        }
        
        // 实际整改完成日期
        if (p.getImproveActualDate() != null) {

            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.actual.date", null, locale),
                            DateUtils.date2String(p.getImproveActualDate(),Constants.DATE_FORMAT)));
        }else {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.actual.date", null, locale),""));
        }
        
        //整改要求
        if (p.getImproveReq() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.req",
                            null, locale), p.getImproveReq()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.improve.req", null, locale), ""));
        }
        
        //整改计划
        if (p.getImprovePlan() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.plan",
                            null, locale), p.getImprovePlan()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.improve.plan", null, locale), ""));
        }
        
        // 验证结果
        if (p.getVerifyResult() != null) {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.verify.result", null, locale),
                    p.getVerifyResult().getOptionName()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.project.inspect.verify.result", null, locale), ""));
        }
        
        //附件
        String archiveName2 = null;
        for (ProjectInspectAttachment pa : projectInspectAttachments) {
            if (pa.getIsImprove() == OnOffEnum.ON.value()) {
                archiveName2=pa.getArchive().getArchiveName();
            }
        }
        if(archiveName2 != null) {
            fields.add(new FieldBean(messageSource
                .getMessage("ui.fields.archive", null, locale),archiveName2));
        }else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.archive", null, locale),""));
        }
        
        //验证日期
        if (p.getVerifyDate() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.verify.date", null, locale),
                            DateUtils.date2String(p.getVerifyDate(),Constants.DATE_FORMAT)));
        }else {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.verify.date", null, locale),""));
        }
        
        //整改结果验证
        if (p.getImproveResultVerify() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.improve.result.verify",
                            null, locale), p.getImproveResultVerify()));
        } else {
            fields.add(new FieldBean(messageSource
                    .getMessage("ui.fields.improve.result.verify", null, locale), ""));
        }
        return bean;

    }
}
