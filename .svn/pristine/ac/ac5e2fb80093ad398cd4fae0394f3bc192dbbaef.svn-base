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
import project.edge.domain.entity.BudgetEstimateChange;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateChangeBean;


/**
 * @author wwy
 *         转换预算规划信息对应的view和entity的转换器。
 */
public class BudgetEstimateChangeBeanConverter
        implements ViewConverter<BudgetEstimateChange, BudgetEstimateChangeBean> {

    @Override
    public BudgetEstimateChangeBean fromEntity(BudgetEstimateChange entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        BudgetEstimateChangeBean bean = new BudgetEstimateChangeBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();

        if (project != null) {
        	bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
		
		bean.setCode(entity.getCode());
		bean.setInnerCode(entity.getInnerCode());
		bean.setOrderNumber(entity.getOrderNumber());
		bean.setName(entity.getName());
		bean.setKeyPerformance(entity.getKeyPerformance());
		bean.setBrand(entity.getBrand());
		bean.setUnit(entity.getMeasurementUnit());
		if (!StringUtils.isEmpty(entity.getCount())) {
			bean.setCount(entity.getCount());
		}
		

		bean.setTaxInclusivePrice(entity.getTaxInclusivePrice());
		bean.setTaxExclusivePrice(entity.getTaxExcludingPrice());
		bean.setTaxInclusivePriceTotal(entity.getTaxTotalPrice());
		bean.setRemarks(entity.getRemarks());
		bean.setVersion(entity.getVersion());
		if (!StringUtils.isEmpty(entity.getYear())) {
			bean.setYear(entity.getYear());
		}
		if (!StringUtils.isEmpty(entity.getTaxPoint())) {
			bean.setTaxPoint(entity.getTaxPoint());
		}
		bean.setDeviceClassify(entity.getDeviceClassify());
		if (!StringUtils.isEmpty(entity.getPersonMonth())) {
			bean.setPersonMonth(entity.getPersonMonth());
		}
		bean.setPersonPrice(entity.getPersonPrice());
		
		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
		
		return bean;
    }

    @Override
    public BudgetEstimateChange toEntity(BudgetEstimateChangeBean bean, BudgetEstimateChange oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        BudgetEstimateChange entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new BudgetEstimateChange();

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
		
	
		entity.setCode(bean.getCode());
		entity.setInnerCode(bean.getInnerCode());
		entity.setOrderNumber(bean.getOrderNumber());
		entity.setName(bean.getName());
		entity.setKeyPerformance(bean.getKeyPerformance());
		entity.setBrand(bean.getBrand());
		entity.setMeasurementUnit(bean.getUnit());
		if (!StringUtils.isEmpty(bean.getCount())) {
			entity.setCount(bean.getCount());
		}
		entity.setTaxInclusivePrice(bean.getTaxInclusivePrice());
		entity.setTaxExcludingPrice(bean.getTaxExclusivePrice());
		entity.setTaxTotalPrice(bean.getTaxInclusivePriceTotal());
		entity.setRemarks(bean.getRemarks());
		entity.setVersion(bean.getVersion());
		
		if (!StringUtils.isEmpty(bean.getYear())) {
			entity.setYear(bean.getYear());
		}
		if (!StringUtils.isEmpty(bean.getTaxPoint())) {
			entity.setTaxPoint(bean.getTaxPoint());
		}
		entity.setDeviceClassify(bean.getDeviceClassify());
		entity.setPersonMonth(bean.getPersonMonth());
		entity.setPersonPrice(bean.getPersonPrice());
		
        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }

		return entity;
    }

}
