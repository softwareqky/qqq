package project.edge.domain.mobile.converter;

import java.util.Locale;

import org.springframework.context.MessageSource;

import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.mobile.view.UserLoginBean;


/**
 * @author angel_000
 *
 */
public class UserLoginBeanConverter {

    /**
     * 转换登录信息，包括用户信息、角色信息。
     * 
     * @param user
     * @param messageSource
     * @param locale
     * @return
     */
    public UserLoginBean fromEntity(SessionUserBean user, MessageSource messageSource, Locale locale) {

        UserLoginBean bean = new UserLoginBean();

        bean.setUserId(user.getSessionUserId());
        bean.setRoleId(user.getRoleId());

        return bean;

    }
}
