package project.edge.domain.mobile.converter;

import java.text.ParseException;
import java.util.Date;

import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.Site;
import project.edge.domain.mobile.view.ProjectInspectFieldBean;

/**
 * @author angel_000
 *
 */
public class ProjectInspectFieldBeanConverter {

    /**
     * 转换新建巡查提交。
     * 
     * @param pages
     * @param messageSource
     * @param locale
     * @return
     */
    public ProjectInspect toEntity(ProjectInspectFieldBean bean, String creator) {

        ProjectInspect entity = new ProjectInspect();

        entity.setcDatetime(new Date());
        entity.setCreator(creator);
        entity.setmDatetime(entity.getcDatetime());


        Project project = new Project();
        project.setId(bean.getProjectId());
        entity.setProject(project);

        Site site = new Site();
        site.setId(bean.getSiteId());
        entity.setSite(site);


        DataOption inspectType = new DataOption();
        inspectType.setId(bean.getInspectType());
        entity.setInspectType(inspectType);

        entity.setInspectPlan(bean.getInspectPlan());
        entity.setInspectArea(bean.getInspectArea());
        entity.setInspectBasis(bean.getInspectBasis());
        entity.setInspectContent(bean.getInspectContent());
        //entity.setInspector(bean.getInspector());
        if (!StringUtils.isEmpty(bean.getInspector())) {
        	Person inspector = new Person();
        	inspector.setId(bean.getInspector());
        	entity.setInspector(inspector);
        }
        entity.setIsImprove(bean.getIsImprove());

        DataOption inspectResult = new DataOption();
        inspectResult.setId(bean.getInspectResult());
        entity.setInspectResult(inspectResult);

        if (!StringUtils.isEmpty(bean.getInspectDate())) {
            try {
                entity.setInspectDate(
                        DateUtils.string2Date(bean.getInspectDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getImproveReqDate())) {
            try {
                entity.setImproveReqDate(
                        DateUtils.string2Date(bean.getImproveReqDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        entity.setInspectResultContent(bean.getInspectResultContent());
        entity.setRemark(bean.getRemark());

        return entity;
    }
}
