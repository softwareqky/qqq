/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.Locale;

import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.AccountInfo;
import project.edge.domain.view.AccountBean;


/**
 * @author wwy
 *         转换账户信息对应的view和entity的转换器。
 */
public class AccountInfoBeanConverter implements ViewConverter<AccountInfo, AccountBean> {

    @Override
    public AccountBean fromEntity(AccountInfo entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub
        AccountBean bean = new AccountBean();
        bean.setId(entity.getId());
        bean.setCompanyName(entity.getCompanyName());
        bean.setTaxNumber(entity.getTaxNumber());
        bean.setAddress(entity.getAddress());
        bean.setTelephone(entity.getTelephone());
        bean.setOpeningBank(entity.getOpeningBank());
        bean.setBankAccount(entity.getBankAccount());
        bean.setHeader(entity.getHeader());
        bean.setContactPhone(entity.getContactPhone());

        return bean;
    }

    @Override
    public AccountInfo toEntity(AccountBean bean, AccountInfo oldEntity,
            AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        AccountInfo entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new AccountInfo();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);
            entity.setModifier("");
        } else {
            entity.setModifier(user.getSessionUserId());
        }

        entity.setmDatetime(now);

        bean.setId(entity.getId()); // ID必须赋值
        entity.setCompanyName(bean.getCompanyName());
        entity.setTaxNumber(bean.getTaxNumber());
        entity.setAddress(bean.getAddress());
        entity.setTelephone(bean.getTelephone());
        entity.setOpeningBank(bean.getOpeningBank());
        entity.setBankAccount(bean.getBankAccount());
        entity.setHeader(bean.getHeader());
        entity.setContactPhone(bean.getContactPhone());

        return entity;
    }

}
