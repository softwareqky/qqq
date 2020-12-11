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
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateSumBean;

/**
 * @author wwy 转换预算规划总表信息对应的view和entity的转换器。
 */
public class BudgetEstimateSumBeanConverter implements ViewConverter<BudgetEstimateSum, BudgetEstimateSumBean> {

	@Override
	public BudgetEstimateSumBean fromEntity(BudgetEstimateSum entity, MessageSource messageSource, Locale locale) {
		// TODO Auto-generated method stub
		BudgetEstimateSumBean bean = new BudgetEstimateSumBean();
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
		
		if (!StringUtils.isEmpty(entity.getLevel())) {
			bean.setLevel(entity.getLevel());
		}

		bean.setTaxInclusivePrice(entity.getTaxInclusivePrice());
		bean.setTaxInclusivePriceTotal(entity.getTaxTotalPrice());
		bean.setVersion(entity.getVersion());
		
		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
		
		return bean;
	}

	@Override
	public BudgetEstimateSum toEntity(BudgetEstimateSumBean bean, BudgetEstimateSum oldEntity,
			AbstractSessionUserBean user, Date now) {
		// TODO Auto-generated method stub

		BudgetEstimateSum entity = oldEntity;
		if (entity == null) { // 表示新建
			entity = new BudgetEstimateSum();

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
		
		entity.setLevel(bean.getLevel());
		entity.setTaxInclusivePrice(bean.getTaxInclusivePrice());
		entity.setTaxTotalPrice(bean.getTaxInclusivePriceTotal());
		entity.setVersion(bean.getVersion());
		
        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }

		return entity;
	}

}
