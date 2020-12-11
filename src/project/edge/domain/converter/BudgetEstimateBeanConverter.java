/**
 * 
 */
package project.edge.domain.converter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateBean;


/**
 * @author wwy
 *         转换预算规划信息对应的view和entity的转换器。
 */
public class BudgetEstimateBeanConverter
        implements ViewConverter<BudgetEstimate, BudgetEstimateBean> {

    @Override
    public BudgetEstimateBean fromEntity(BudgetEstimate entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        BudgetEstimateBean bean = new BudgetEstimateBean();
        
        BudgetEstimateMain budgetEstimateMain = entity.getBudgetEstimateMain();
        if (budgetEstimateMain != null) {
            bean.setBudgetEstimateMain_(budgetEstimateMain.getId());
            bean.setBudgetEstimateMainText(budgetEstimateMain.getId());
        }
        
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
		bean.setPlanRemark(entity.getPlanRemark());
		if (!StringUtils.isEmpty(entity.getLevel())) {
			bean.setLevel(entity.getLevel());
		}
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
        
        bean.setTaxInclusivePriceTotal(entity.getTaxTotalPrice());
        bean.setPaymentPercent(entity.getPaymentPercent());
        bean.setPaymentAmount(entity.getPaymentAmount());
        bean.setPaymentCount(entity.getPaymentCount());
        bean.setPayAmountTotal(entity.getPayAmountTotal());
        
        DataOption budgetType = entity.getBudgetType();
        if (budgetType != null) {
            bean.setBudgetType_(budgetType.getId());
            bean.setBudgetTypeText(budgetType.getOptionName());
        }
        
        //硬件类才需算上年下年
        if(budgetType != null && budgetType.getId().equals("BUDGET_TYPE_1")) {
        	
    		bean.setNextYearPercent("0.00");
    		bean.setNextYearAmount("0.00");
            bean.setNextYearCount("0.00");
          
            if (!StringUtils.isEmpty(entity.getPaymentPercent()) && !StringUtils.isEmpty(entity.getPaymentCount()) && !StringUtils.isEmpty(entity.getTaxInclusivePrice())) {
            	
            	if (Integer.parseInt(entity.getPaymentPercent()) != 100 && Integer.parseInt(entity.getPaymentPercent()) != 0) {
            		int nextYearPercent = 100 - Integer.parseInt(entity.getPaymentPercent());
            		bean.setNextYearPercent(String.valueOf(nextYearPercent));
//                    bean.setNextYearAmount(entity.getPaymentCount() * entity.getTaxInclusivePrice() * nextYearPercent / 100);
            		BigDecimal nextYearAmount = new BigDecimal(Double.toString(entity.getTaxTotalPrice())).subtract(new BigDecimal(Double.toString(entity.getPaymentAmount())));
            		bean.setNextYearAmount(String.valueOf(nextYearAmount.doubleValue()));
                    bean.setNextYearCount(String.valueOf(entity.getPaymentCount()));
            	}
                
            }
        }
        else {
        	bean.setNextYearPercent("");
    		bean.setNextYearAmount("");
            bean.setNextYearCount("");
        }
        
		return bean;
    }

    @Override
    public BudgetEstimate toEntity(BudgetEstimateBean bean, BudgetEstimate oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        BudgetEstimate entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new BudgetEstimate();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);
		
        if (!StringUtils.isEmpty(bean.getBudgetEstimateMain_())) {
        	BudgetEstimateMain budgetEstimateMain = new BudgetEstimateMain();
        	budgetEstimateMain.setId(bean.getBudgetEstimateMain_());
            entity.setBudgetEstimateMain(budgetEstimateMain);
        }
		
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
		if (!StringUtils.isEmpty(bean.getCount()) && !StringUtils.isEmpty(bean.getTaxInclusivePrice())) {
			entity.setTaxTotalPrice(bean.getCount() * bean.getTaxInclusivePrice());
		} else {
			entity.setTaxTotalPrice(bean.getTaxInclusivePriceTotal());
		}
		
		entity.setRemarks(bean.getRemarks());
		entity.setVersion(bean.getVersion());
		entity.setPlanRemark(bean.getPlanRemark());
		entity.setLevel(bean.getLevel());
		
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
        
        entity.setPaymentAmount(bean.getPaymentAmount());
        
        entity.setPaymentCount(bean.getPaymentCount());
        entity.setPaymentPercent(bean.getPaymentPercent());
        entity.setPayAmountTotal(bean.getPayAmountTotal());
        
        if (!StringUtils.isEmpty(bean.getBudgetType_())) {
            DataOption budgetType = new DataOption();
            budgetType.setId(bean.getBudgetType_());
            entity.setBudgetType(budgetType);
        }

		return entity;
    }

}
