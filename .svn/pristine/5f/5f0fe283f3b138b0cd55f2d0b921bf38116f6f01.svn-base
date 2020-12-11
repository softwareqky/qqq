/**
 * 
 */
package project.edge.domain.converter;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.PredefinedRule;
import project.edge.domain.view.PredefinedRuleBean;


/**
 * @author angel_000
 *         转换预警配置信息对应的view和entity的转换器。
 */
public class PredefinedRuleBeanConverter implements ViewConverter<PredefinedRule, PredefinedRuleBean> {

    @Override
    public PredefinedRuleBean fromEntity(PredefinedRule entity, MessageSource messageSource,
            Locale locale) {
        // TODO Auto-generated method stub
        
    	PredefinedRuleBean bean = new PredefinedRuleBean();
        
        bean.setId(entity.getId());
        bean.setConfigValue(entity.getConfigValue());
        
        return bean;
    }

    @Override
    public PredefinedRule toEntity(PredefinedRuleBean bean, PredefinedRule oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub
        
    	PredefinedRule entity = oldEntity;

        if (entity == null) { // 表示新建
            entity = new PredefinedRule();

        } else {
            entity.setModifier(user.getSessionUserId());
        }
        
        bean.setId(entity.getId()); // ID必须赋值
        entity.setConfigValue(bean.getConfigValue());
        entity.setmDatetime(now);        
        
        return entity;
    }

}
