package project.edge.domain.converter;


import java.util.Date;
import java.util.Locale;
import org.springframework.context.MessageSource;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.view.BudgetFeeBean;

/**
 * @author angel_000 转换项目成员对应的view和entity的转换器。
 *
 */
public class BudgetFeeBeanConverter
        implements ViewConverter<BudgetFee, BudgetFeeBean> {

    @Override
    public BudgetFeeBean fromEntity(BudgetFee entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        BudgetFeeBean bean = new BudgetFeeBean();

        bean.setId(entity.getId());
        bean.setBh(entity.getBh());
        bean.setZymc(entity.getZymc());
        bean.setDw(entity.getDw());
        bean.setSl(entity.getSl());
        bean.setDjy(entity.getDjy());
        bean.setBxjey(entity.getBxjey());
        bean.setZdmc(entity.getZdmc());
        bean.setFyxm(entity.getFyxm());
        bean.setBz(entity.getBz());
        bean.setMainid(entity.getMainid());
        bean.setLkr(entity.getLkr());
        bean.setGzz(entity.getGzz());
        bean.setApprovalId(entity.getApprovalId());
        bean.setApprover(entity.getApprover());
        if (entity.getApprovalTime() != null) {
        	bean.setApprovalTime(DateUtils.date2String(entity.getApprovalTime(), Constants.DATE_FORMAT));
        }
        if (entity.getcDatetime() != null) {
            bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
        }
        
        return bean;
    }

    @Override
    public BudgetFee toEntity(BudgetFeeBean bean, BudgetFee oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        BudgetFee entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new BudgetFee();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
        } else { // 表示修改
            entity.setModifier(user.getSessionUserId());
         
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);
        entity.setBh(bean.getBh());
        entity.setZymc(bean.getZymc());
        entity.setDw(bean.getDw());
        entity.setSl(bean.getSl());
        entity.setDjy(bean.getDjy());
        entity.setBxjey(bean.getBxjey());
        entity.setZdmc(bean.getZdmc());
        entity.setFyxm(bean.getFyxm());
        entity.setBz(bean.getBz());
        entity.setMainid(bean.getMainid());
        entity.setLkr(bean.getLkr());
        entity.setGzz(bean.getGzz());

        return entity;
    }

}
