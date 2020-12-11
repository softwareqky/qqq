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
import project.edge.domain.entity.BudgetFinal;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetFinalBean;


/**
 * @author wwy
 *         转换预算规划信息对应的view和entity的转换器。
 */
public class BudgetFinalBeanConverter
        implements ViewConverter<BudgetFinal, BudgetFinalBean> {

    @Override
    public BudgetFinalBean fromEntity(BudgetFinal entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
    	BudgetFinalBean bean = new BudgetFinalBean();
		bean.setId(entity.getId());
		
        Project project = entity.getProject();
        if (project != null) {
        	bean.setProject_(project.getId());
            bean.setProjectText(project.getProjectName());
        }
		bean.setCode(entity.getCode());
		bean.setOrderNumber(entity.getOrderNumber());
		bean.setName(entity.getName());
		bean.setUnit(entity.getMeasurementUnit());
		if (!StringUtils.isEmpty(entity.getCount())) {
			bean.setCount(entity.getCount());
		}
		bean.setTaxInclusivePrice(entity.getTaxInclusivePrice());
		bean.setTaxInclusivePriceTotal(entity.getTaxTotalPrice());
		if (!StringUtils.isEmpty(entity.getYear())) {
			bean.setYear(entity.getYear());
		}
		VirtualOrg group = entity.getGroup();
        if (group != null) {
            bean.setGroup_(group.getId());
            bean.setGroupText(group.getVirtualOrgName());
        }
        bean.setReimburseAmount(entity.getReimburseAmount());
        bean.setReimburseCount(entity.getReimburseCount());
        bean.setBalance(entity.getBalance());
        bean.setVersionId(entity.getVersionId());
		
		return bean;
    }

    @Override
    public BudgetFinal toEntity(BudgetFinalBean bean, BudgetFinal oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

    	BudgetFinal entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new BudgetFinal();

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
		entity.setOrderNumber(bean.getOrderNumber());
		entity.setName(bean.getName());
		entity.setMeasurementUnit(bean.getUnit());
		if (!StringUtils.isEmpty(bean.getCount())) {
			entity.setCount(bean.getCount());
		}
		entity.setTaxInclusivePrice(bean.getTaxInclusivePrice());
		entity.setTaxTotalPrice(bean.getTaxInclusivePriceTotal());
		
		if (!StringUtils.isEmpty(bean.getYear())) {
			entity.setYear(bean.getYear());
		}
        if (!StringUtils.isEmpty(bean.getGroup_())) {
        	VirtualOrg group = new VirtualOrg();
        	group.setId(bean.getGroup_());
            entity.setGroup(group);
        }
        entity.setReimburseAmount(bean.getReimburseAmount());
        entity.setReimburseCount(entity.getReimburseCount());
        entity.setBalance(bean.getBalance());
        entity.setVersionId(bean.getVersionId());

		return entity;
    }

}
