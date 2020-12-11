/**
 * 
 */
package project.edge.domain.mobile.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.Site;
import project.edge.domain.view.ProjectInspectBean;

/**
 * @author angel_000
 *
 */
public class ProjectInspectBeanConverter {


    /**
     * 转换项目巡查信息。
     * 
     * @param entity
     * @return
     */
    public ProjectInspectBean fromEntity(ProjectInspect entity, MessageSource messageSource,
            Locale locale) {

        ProjectInspectBean bean = new ProjectInspectBean();

        bean.setId(entity.getId());

        Site site = entity.getSite();
        if (site != null) {
            bean.setSite_(site.getStationName());
            bean.setSiteText(site.getStationName());
        }
        DataOption inspectType = entity.getInspectType();
        if (inspectType != null) {
            bean.setInspectType_(inspectType.getOptionName());
            bean.setInspectTypeText(inspectType.getOptionName());
        }

        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getProjectName());
            bean.setProjectText(project.getProjectName());
        }

        bean.setInspectPlan(entity.getInspectPlan());
        bean.setInspectArea(entity.getInspectArea());
        bean.setInspectBasis(entity.getInspectBasis());
        bean.setInspectContent(entity.getInspectContent());
        //bean.setInspector(entity.getInspector());
        Person inspector = entity.getInspector();
        if (inspector != null) {
        	bean.setInspector_(inspector.getId());
        	bean.setInspectorText(inspector.getPersonName());
        }
        bean.setIsImprove(entity.getIsImprove());
        
        bean.setIsImproveText((entity.getIsImprove() != null && entity.getIsImprove() == 1)
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        DataOption inspectResult = entity.getInspectResult();
        if (inspectResult != null) {
            bean.setInspectResult_(inspectResult.getOptionName());
            bean.setInspectResultText(inspectResult.getOptionName());
        }

        DataOption verifyResult = entity.getVerifyResult();
        if (verifyResult != null) {
            bean.setVerifyResult_(verifyResult.getOptionName());
            bean.setVerifyResultText(verifyResult.getOptionName());
        }

        if (entity.getInspectDate() != null) {
            bean.setInspectDate(
                    DateUtils.date2String(entity.getInspectDate(), Constants.DATE_FORMAT));
        }
        if (entity.getImproveReqDate() != null) {
            bean.setImproveReqDate(
                    DateUtils.date2String(entity.getImproveReqDate(), Constants.DATE_FORMAT));
        }
        if (entity.getImprovePlanDate() != null) {
            bean.setImprovePlanDate(
                    DateUtils.date2String(entity.getImprovePlanDate(), Constants.DATE_FORMAT));
        }
        if (entity.getImproveActualDate() != null) {
            bean.setImproveActualDate(
                    DateUtils.date2String(entity.getImproveActualDate(), Constants.DATE_FORMAT));
        }
        bean.setInspectResultContent(entity.getInspectResultContent());
        bean.setImproveReq(entity.getImproveReq());
        bean.setImprovePlan(entity.getImprovePlan());

        if (entity.getVerifyDate() != null) {
            bean.setVerifyDate(
                    DateUtils.date2String(entity.getVerifyDate(), Constants.DATE_FORMAT));
        }
        bean.setImproveResultVerify(entity.getImproveResultVerify());
        bean.setRemark(entity.getRemark());

        if (entity.getFlowStartDate() != null) {
            bean.setFlowStartDate(
                    DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getFlowEndDate() != null) {
            bean.setFlowEndDate(
                    DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
        }
        bean.setFlowStatus(entity.getFlowStatus());

        return bean;

    }
}
