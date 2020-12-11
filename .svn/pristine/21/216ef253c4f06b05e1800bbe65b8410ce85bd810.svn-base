package project.edge.domain.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.BudgetMainfee;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetMainfeeBean;

public class BudgetMainfeeBeanConverter implements ViewConverter<BudgetMainfee, BudgetMainfeeBean> {

	@Override
	public BudgetMainfeeBean fromEntity(BudgetMainfee entity, MessageSource messageSource, Locale locale) {
		// TODO Auto-generated method stub
		BudgetMainfeeBean bean = new BudgetMainfeeBean();
		bean.setId(entity.getId());
		bean.setMainid(entity.getMainid());
		if (entity.getRequestPerson() != null) {
		    bean.setRequestPerson_(entity.getRequestPerson().getId());
		    bean.setRequestPersonText(entity.getRequestPerson().getPersonName());
		}
		
		if (entity.getVirtualOrg() != null) {
			bean.setVirtualOrg_(entity.getVirtualOrg().getId());
			bean.setVirtualOrgText(entity.getVirtualOrg().getVirtualOrgName());
		}
		
		bean.setWorkflowName(entity.getWorkflowName());
		bean.setApprovelId(entity.getApprovalId());
		
		if (entity.getApprover() != null) {
		    bean.setApprover_(entity.getApprover().getId());
		    bean.setApproverText(entity.getApprover().getPersonName());
		}
		
		if (entity.getApprovalTime() != null)
		    bean.setApprovalTime(DateUtils.date2String(entity.getApprovalTime(), Constants.DATE_FORMAT));
		
		if (entity.getRequestTime() != null)
		    bean.setRequestTime(DateUtils.date2String(entity.getRequestTime(), Constants.DATE_FORMAT));
		
		bean.setPayRatio(entity.getPayRatio());
		bean.setPayAmount(entity.getPayAmount());
		bean.setPayType(entity.getPayType());
		bean.setPurchaseNo(entity.getPurchaseNo());
		bean.setStockNo(entity.getStockNo());
		bean.setCreator(entity.getCreator());
		if (entity.getcDatetime() != null)
		    bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
		bean.setDataSource(entity.getDataSource());
		
		return bean;
	}

	@Override
	public BudgetMainfee toEntity(BudgetMainfeeBean bean, BudgetMainfee oldEntity, AbstractSessionUserBean user,
			Date now) {
		// TODO Auto-generated method stub
		BudgetMainfee entity = oldEntity;		
		if (entity == null) { // 表示新建
            entity = new BudgetMainfee();
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
    		if (bean.getDataSource() == null) bean.setDataSource(1); // 0:oa同步；1:管理平台人工录入

        } else { // 表示修改
            
        }

		
		bean.setId(entity.getId()); // ID必须赋值
		entity.setMainid(bean.getMainid());
		
		if (StringUtils.isNotEmpty(bean.getRequestPerson_())) {
			Person p = new Person();
			p.setId(bean.getRequestPerson_());
			entity.setRequestPerson(p);
		}
		
		if (StringUtils.isNotEmpty(bean.getVirtualOrg_())) {
			VirtualOrg v = new VirtualOrg();
			v.setId(bean.getVirtualOrg_());
			entity.setVirtualOrg(v);
		}
		
		entity.setWorkflowName(bean.getWorkflowName());
		entity.setApprovalId(bean.getApprovelId());
		entity.setPayType(bean.getPayType());
		entity.setPurchaseNo(bean.getPurchaseNo());
		entity.setStockNo(bean.getStockNo());
		
		if (StringUtils.isNotEmpty(bean.getApprover_())) {
			Person p = new Person();
			p.setId(bean.getApprover_());
			entity.setApprover(p);
		}
		
		if (StringUtils.isNoneEmpty(bean.getApprovalTime())) {
			try {
                entity.setApprovalTime(
                        DateUtils.string2Date(bean.getApprovalTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
		}
		
		if (StringUtils.isNoneEmpty(bean.getRequestTime())) {
			try {
                entity.setRequestTime(
                        DateUtils.string2Date(bean.getRequestTime(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
		}
		
		entity.setDataSource(bean.getDataSource());
		
		return entity;
	}

}
