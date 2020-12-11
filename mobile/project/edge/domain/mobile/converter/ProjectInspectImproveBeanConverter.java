package project.edge.domain.mobile.converter;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.mobile.view.ProjectInspectImproveBean;

/**
 * @author angel_000
 *
 */
public class ProjectInspectImproveBeanConverter {

    /**
     * 转换巡查整改提交信息。
     * 
     * @param bean
     * @param creator
     * @return
     */
    public ProjectInspect toEntity(ProjectInspectImproveBean bean, String creator) {

        ProjectInspect entity = new ProjectInspect();

        entity.setcDatetime(new Date());
        entity.setCreator(creator);
        entity.setmDatetime(entity.getcDatetime());


        Project project = new Project();
        project.setId(bean.getProjectInspectId());
        entity.setProject(project);

        if (!StringUtils.isEmpty(bean.getImprovePlanDate())) {
            try {
                entity.setImprovePlanDate(
                        DateUtils.string2Date(bean.getImprovePlanDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getImproveActualDate())) {
            try {
                entity.setImproveActualDate(
                        DateUtils.string2Date(bean.getImproveActualDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setImproveReq(bean.getImproveReq());
        entity.setImprovePlan(bean.getImprovePlan());
        entity.setRemark(bean.getRemark());

        return entity;
    }
}
