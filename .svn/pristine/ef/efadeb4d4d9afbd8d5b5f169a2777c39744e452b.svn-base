package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.entity.TenderingPurchase;
import project.edge.domain.view.TenderingPurchaseBean;

public class TenderingPurchaseBeanConverter implements ViewConverter<TenderingPurchase, TenderingPurchaseBean> {
	@Override
    public TenderingPurchaseBean fromEntity(TenderingPurchase entity,
            MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
		TenderingPurchaseBean bean = new TenderingPurchaseBean();

        bean.setId(entity.getId());
        
        PurchaseOrder purchaseOrder = entity.getPurchaseOrder();
        TenderingPlan tenderingPlan = entity.getTenderingPlan();

        if (purchaseOrder != null) {
        	bean.setPurchaseOrder_(purchaseOrder.getId());
            bean.setPurchaseOrderText(purchaseOrder.getPurchaseName());
        }
        if (tenderingPlan != null) {
            bean.setTenderingPlan_(tenderingPlan.getId());
            bean.setTenderingPlanText(tenderingPlan.getTenderingName());
        }
        
        bean.setCreator(entity.getCreator());
        if (entity.getcDatetime() != null) {
            bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
        }

        return bean;
    }

    @Override
    public TenderingPurchase toEntity(TenderingPurchaseBean bean, TenderingPurchase oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
    	TenderingPurchase entity = oldEntity;

    	if (entity == null) { // 表示新建
            entity = new TenderingPurchase();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
        	
        }
    	
        bean.setId(entity.getId()); // ID必须赋值

        if (!StringUtils.isEmpty(bean.getPurchaseOrder_())) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setId(bean.getPurchaseOrder_());
            entity.setPurchaseOrder(purchaseOrder);
        }

        if (!StringUtils.isEmpty(bean.getTenderingPlan_())) {
            TenderingPlan tenderingPlan = new TenderingPlan();
            tenderingPlan.setId(bean.getTenderingPlan_());
            entity.setTenderingPlan(tenderingPlan);
        }

        return entity;
    }
}
