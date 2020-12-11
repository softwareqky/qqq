/**
 * 
 */
package project.edge.domain.mobile.converter;

import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.Project;
import project.edge.domain.mobile.view.FieldBean;
import project.edge.domain.mobile.view.FieldListWrapperBean;

/**
 * @author angel_000
 *
 */
public class ProjectDetailBeanConverter {

    /**
     * 
     * 使用项目详情默认配置。
     * 
     * @param p
     * @param messageSource
     * @param locale
     * @return
     */
    public FieldListWrapperBean fromEntity(Project p, MessageSource messageSource, Locale locale) {

        if (p == null) {
            return null;
        }

        FieldListWrapperBean bean = new FieldListWrapperBean();

        List<FieldBean> fields = bean.getFieldList();

        // 项目id
//        fields.add(new FieldBean(
//                messageSource.getMessage("ui.fields.project.project.id", null, locale), p.getId()));

        // 项目名称
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.name", null, locale),
                p.getProjectName()));
        
        // 关联站点
        if (p.getSite() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.site", null, locale),
                            p.getSite().getStationName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.site", null, locale), ""));
        }


        // 关联中继段
        if (p.getSegment() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.segment", null, locale),
                    p.getSegment().getId()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.segment", null, locale), ""));
        }


        // 项目类别
        if (p.getProjectKind() != null) {
            fields.add(
                    new FieldBean(messageSource.getMessage("ui.fields.project.kind", null, locale),
                            p.getProjectKind().getOptionName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.kind", null, locale), ""));
        }


        // 项目编号
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.num", null, locale),
                p.getProjectNum()));


        if (p.getProjectStartDate() != null) {
            // 计划开始日期
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.start.date", null, locale),
                    DateUtils.date2String(p.getProjectStartDate(),Constants.DATE_FORMAT)));
        }

        if (p.getProjectEndDate() != null) {
            // 计划结束日期
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.end.date", null, locale),
                    DateUtils.date2String(p.getProjectEndDate(),Constants.DATE_FORMAT)));
        }

        // 所属省市
        //2020-3-26
        if(p.getProvince() != null) {
            fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.city", null, locale),
                String.format("(%1$s) %2$s", p.getProvince().getProvinceName(),
                        p.getCity().getCityName())));
        }
        // 项目地点
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.address", null, locale),
                p.getProjectAddress()));

        // 工程工期
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.duration", null, locale),
                p.getProjectDuration()));

        // 工程量估算
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.effort.estimate", null, locale),
                p.getEffortEstimate()));

        // 项目类型
        if (p.getProjectType() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.type", null, locale),
                    p.getProjectType().getOptionName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.type", null, locale), ""));
        }


        // 项目属性
        if (p.getProjectCategory() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.category", null, locale),
                    p.getProjectCategory().getOptionName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.category", null, locale),
                    ""));
        }


        // 项目审核情况
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.audit.status", null, locale),
                p.getProjectAuditStatus()));

        // 招标代理单位
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.bidding.agent", null, locale),
                p.getBiddingAgent()));

        // 项目经理
        if (p.getProjectManager() != null) {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.manager", null, locale),
                    p.getProjectManager().getPersonName()));
        } else {
            fields.add(new FieldBean(
                    messageSource.getMessage("ui.fields.project.project.manager", null, locale),
                    ""));
        }


        // 项目经理姓名
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.manager.name", null, locale),
                p.getProjectManagerName()));

        // 项目经理联系方式
        fields.add(new FieldBean(
                messageSource.getMessage("ui.fields.project.project.manager.mobile", null, locale),
                p.getProjectManagerMobile()));

        if (p.getFlowProjectStartDate() != null) {
            // 立项申请开始日期
            fields.add(
                    new FieldBean(
                            messageSource.getMessage("ui.fields.project.flow.project.start.date",
                                    null, locale),
                            DateUtils.date2String(p.getFlowProjectStartDate())));
        }

        if (p.getFlowProjectEndDate() != null) {
            // 立项申请结束日期
            fields.add(
                    new FieldBean(
                            messageSource.getMessage("ui.fields.project.flow.project.end.date",
                                    null, locale),
                            DateUtils.date2String(p.getFlowProjectEndDate())));
        }


        return bean;

    }
}
