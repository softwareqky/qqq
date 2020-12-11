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
import project.edge.domain.entity.CapitalPlanSum;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.CapitalPlanSumBean;


/**
 * @author wwy
 *         转换资金计划总表信息对应的view和entity的转换器。
 */
public class CapitalPlanSumBeanConverter
        implements ViewConverter<CapitalPlanSum, CapitalPlanSumBean> {

    @Override
    public CapitalPlanSumBean fromEntity(CapitalPlanSum entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        CapitalPlanSumBean bean = new CapitalPlanSumBean();
        bean.setId(entity.getId());
        
        Project project = entity.getProject();
        if (project != null) {
        	bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }

		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
        
        bean.setTarget(entity.getTarget());
        bean.setCapitalPlan(entity.getCapitalPlan());
        bean.setYear(entity.getYear());

        return bean;
    }

    @Override
    public CapitalPlanSum toEntity(CapitalPlanSumBean bean, CapitalPlanSum oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        CapitalPlanSum entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new CapitalPlanSum();

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
        
        entity.setTarget(bean.getTarget());
        entity.setCapitalPlan(bean.getCapitalPlan());
        entity.setYear(bean.getYear());

        return entity;
    }

}
