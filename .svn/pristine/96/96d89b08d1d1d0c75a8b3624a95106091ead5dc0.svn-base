/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.Md5Encrypt;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Role;
import project.edge.domain.entity.User;
import project.edge.domain.view.UserBean;


/**
 * @author angel_000
 *         转换用户信息对应的view和entity的转换器。
 *
 */
public class UserBeanConverter implements ViewConverter<User, UserBean> {

    @Override
    public UserBean fromEntity(User entity, MessageSource messageSource, Locale locale) {
        // TODO Auto-generated method stub

        UserBean bean = new UserBean();

        bean.setId(entity.getId());

        Role role = entity.getRole();
        Person person = entity.getPerson();

        if (role != null) {
            bean.setRole_(role.getId());
            bean.setRoleText(role.getRoleName());
        }

        if (person != null) {
            bean.setPerson_(person.getId());
            bean.setPersonText(person.getPersonName());
        }

        bean.setLoginName(entity.getLoginName());
        bean.setIsSystem(entity.getIsSystem());
        bean.setIsSystemText(entity.getIsSystem() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        bean.setIsSuper(entity.getIsSuper());
        bean.setIsSuperText(entity.getIsSuper() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));

        return bean;
    }

    @Override
    public User toEntity(UserBean bean, User oldEntity, AbstractSessionUserBean user, Date now) {
        // TODO Auto-generated method stub

        User entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new User();

            entity.setIsSystem(OnOffEnum.OFF.value());
            
            //entity.setLoginPwd(SshaEncrypt.getInstance().createDigest(Constants.DEFAULT_PASSWORD));
            
            entity.setLoginPwd(Md5Encrypt.createDigest(Constants.DEFAULT_PASSWORD));
            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else {
            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getRole_())) {
            Role role = new Role();
            role.setId(bean.getRole_());
            entity.setRole(role);
        }

        if (!StringUtils.isEmpty(bean.getPerson_())) {
            Person person = new Person();
            person.setId(bean.getPerson_());
            entity.setPerson(person);
        }

        entity.setLoginName(bean.getLoginName());
        entity.setIsSuper(bean.getIsSuper());
        entity.setIsSystem(bean.getIsSystem());

        return entity;
    }

}
