/**
 * 
 */
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.CapitalPlan;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.CapitalPlanBean;


/**
 * @author wwy
 *         转换资金计划构成信息对应的view和entity的转换器。
 */
public class CapitalPlanBeanConverter implements ViewConverter<CapitalPlan, CapitalPlanBean> {

    @Override
    public CapitalPlanBean fromEntity(CapitalPlan entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        CapitalPlanBean bean = new CapitalPlanBean();
        bean.setId(entity.getId());

		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
        
        Project project = entity.getProject();
        if (project != null) {
        	bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
       
        bean.setFirstClassify(entity.getFirstClassify());
        bean.setSecondClassify(entity.getSecondClassify());
        bean.setThirdClassify(entity.getThirdClassify());
        bean.setRemarks(entity.getRemarks());
        bean.setCapitalPlan(entity.getCapitalPlan());
        bean.setYear(entity.getYear());
        bean.setVersionId(entity.getVersionId());

        return bean;
    }

    @Override
    public CapitalPlan toEntity(CapitalPlanBean bean, CapitalPlan oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        CapitalPlan entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new CapitalPlan();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);
        entity.setModifier(user.getSessionUserId());

        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }
        
        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }
        
        entity.setFirstClassify(bean.getFirstClassify());
        entity.setSecondClassify(bean.getSecondClassify());
        entity.setThirdClassify(bean.getThirdClassify());
        entity.setRemarks(bean.getRemarks());
        entity.setCapitalPlan(bean.getCapitalPlan());
        entity.setYear(bean.getYear());
        entity.setVersionId(bean.getVersionId());

        return entity;
    }

}
