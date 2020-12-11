/**
 * 
 */
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateMainBean;


/**
 * @author wwy
 *         转换预算规划版本信息对应的view和entity的转换器。
 */
public class BudgetEstimateMainBeanConverter
        implements ViewConverter<BudgetEstimateMain, BudgetEstimateMainBean> {

    @Override
    public BudgetEstimateMainBean fromEntity(BudgetEstimateMain entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	BudgetEstimateMainBean bean = new BudgetEstimateMainBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();
        if (project != null) {
            bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
		
		if (!StringUtils.isEmpty(entity.getYear())) {
			bean.setYear(entity.getYear());
		}
		
		if (!StringUtils.isEmpty(entity.getIsAll())) {
			bean.setYear(entity.getYear());
		}
		
        if (entity.getIsAll() != null) {
            bean.setIsAll(entity.getIsAll());
            bean.setIsAllText(entity.getIsAll() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }
		
		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
        
        bean.setBudgetTotal(entity.getBudgetTotal());
        
        bean.setVersion(entity.getVersion());
        
        if (entity.getcDatetime() != null) {
			bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
		}
        
        if (entity.getmDatetime() != null) {
			bean.setmDatetime(DateUtils.date2String(entity.getmDatetime(), Constants.DATE_FORMAT));
		}

		Person person = entity.getCreator();
		if (person != null) {
			bean.setCreator_(person.getId());
			bean.setCreatorText(person.getPersonName());
		}
        
        if (entity.getFlowStartDate() != null) {
			bean.setFlowStartDate(DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getFlowEndDate() != null) {
			bean.setFlowEndDate(DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		}
		bean.setFlowStatus(entity.getFlowStatus());
		bean.setFlowStatusText(
				messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
		
		return bean;
    }

    @Override
    public BudgetEstimateMain toEntity(BudgetEstimateMainBean bean, BudgetEstimateMain oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	BudgetEstimateMain entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new BudgetEstimateMain();
			if (!StringUtils.isEmpty(user.getSessionUserId())) {
				Person person = new Person();
				person.setId(user.getSessionUserId());
				entity.setCreator(person);
			}
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
		
		if (!StringUtils.isEmpty(bean.getYear())) {
			entity.setYear(bean.getYear());
		}
		
		if (!StringUtils.isEmpty(bean.getIsAll())) {
			entity.setIsAll(bean.getIsAll());
		}
		
        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }
        
        entity.setVersion(bean.getVersion());
        entity.setBudgetTotal(bean.getBudgetTotal());
        
		return entity;
    }

}
