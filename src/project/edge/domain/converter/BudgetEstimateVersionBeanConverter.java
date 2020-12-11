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
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectGroup;
import project.edge.domain.view.BudgetEstimateVersionBean;


/**
 * @author wwy
 *         转换预算规划版本信息对应的view和entity的转换器。
 */
public class BudgetEstimateVersionBeanConverter
        implements ViewConverter<BudgetEstimateVersion, BudgetEstimateVersionBean> {

    @Override
    public BudgetEstimateVersionBean fromEntity(BudgetEstimateVersion entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        BudgetEstimateVersionBean bean = new BudgetEstimateVersionBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
		
		bean.setVersion(entity.getVersion());
		if (!StringUtils.isEmpty(entity.getYear())) {
			bean.setYear(entity.getYear());
		}
		
		if(entity.getName() != null) {
			if(entity.getName().equals("1")) {
				bean.setName("总预算");
			}
			else {
				bean.setName("分组预算");
			}
		}
		
		BudgetEstimateMain budgetEstimateMain = entity.getBudgetMain();
        if (budgetEstimateMain != null) {
            bean.setBudgetMain_(budgetEstimateMain.getId());
    		bean.setBudgetMainText(
    				messageSource.getMessage(FlowStatusEnum.getResouceName(budgetEstimateMain.getFlowStatus()), null, locale));
    		bean.setBudgetMainTotal(budgetEstimateMain.getBudgetTotal());
        }
		
		ProjectGroup group = entity.getGroup();
        if (group != null) {
            bean.setGroupId(group.getId());
            bean.setGroupText(group.getGroupName());
        }
		return bean;
    }

    @Override
    public BudgetEstimateVersion toEntity(BudgetEstimateVersionBean bean, BudgetEstimateVersion oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        BudgetEstimateVersion entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new BudgetEstimateVersion();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);
		
        if (!StringUtils.isEmpty(bean.getProject_())) {
            Project project = new Project();
            project.setId(bean.getProject_());
            project.setProjectName(bean.getProjectText());
            entity.setProject(project);
        }
        
/*		Project project = new Project();
		project.setId("02c53818-2d6a-4e6e-b802-bdd799ba6618");
		entity.setProject(project);*/
		
		entity.setVersion(bean.getVersion());
		
		if (!StringUtils.isEmpty(bean.getYear())) {
			entity.setYear(bean.getYear());
		}
		
		entity.setName(bean.getName());
		
        if (!StringUtils.isEmpty(bean.getGroupId())) {
        	ProjectGroup group = new ProjectGroup();
        	group.setId(bean.getGroupId());
            entity.setGroup(group);
        }
        
        if (!StringUtils.isEmpty(bean.getBudgetMain_())) {
        	BudgetEstimateMain budgetEstimateMain = new BudgetEstimateMain();
        	budgetEstimateMain.setId(bean.getBudgetMain_());
            entity.setBudgetMain(budgetEstimateMain);
        }
		
		return entity;
    }

}
