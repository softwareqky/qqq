package project.edge.service.auth;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.Md5Encrypt;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.auth.UserDao;
import project.edge.domain.entity.Role;
import project.edge.domain.entity.User;


/**
 * 用户对应的Service。
 * 
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Dao<User, String> getDefaultDao() {
        return this.userDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("loginName");
    }

    /**
     * 根据用户名和密码获取用户信息。
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象
     */
    @Override
    @Transactional
    public SessionUserBean login(String userName, String password) {

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return null;
        }

        CommonFilter userFilter = new CommonFilter();
        userFilter.addExact("loginName", userName);
        userFilter.addExact("loginPwd", Md5Encrypt.createDigest(password));

        List<User> userList = this.list(userFilter, null);

        // 判断登录用户是否存在
        if (userList.size() == 0) {
            return null;
        }

        User user = userList.get(0);
        if (user.getIsDeleted() == OnOffEnum.ON.value()) {
            return null;
        }

        SessionUserBean userBean = new SessionUserBean();
        userBean.setSessionUserId(user.getId());
        userBean.setSessionLoginName(user.getLoginName()); // 登录用户名
        userBean.setIsSuper(OnOffEnum.ON.value().equals(user.getIsSuper()));

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.COMMA);

        // 功能权限
        Role role = user.getRole();
        if (role != null) {

            // 角色ID
            userBean.setRoleId(role.getId());

            // 能否访问所有功能
            userBean.setAccessAllPages(OnOffEnum.ON.value().equals(role.getIsSuper()));
        }

        return userBean;
    }

    @Override
    @Transactional
    public SessionUserBean loginMobile(String loginName, String password) {

        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            return null;
        }

        CommonFilter userFilter = new CommonFilter();
        userFilter.addExact("loginName", loginName);
        userFilter.addExact("loginPwd", Md5Encrypt.createDigest(password));

        List<User> userList = this.list(userFilter, null);

        // 判断登录用户是否存在
        if (userList.size() == 0) {
            return null;
        }

        User user = userList.get(0);

        SessionUserBean userBean = new SessionUserBean();
        userBean.setSessionUserId(user.getId());
        userBean.setSessionLoginName(user.getLoginName()); // 登录用户名
        userBean.setIsSuper(OnOffEnum.ON.value().equals(user.getIsSuper()));

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.COMMA);

        // 功能权限
        Role role = user.getRole();
        if (role != null) {

            // 角色ID
            userBean.setRoleId(role.getId());

            // 能否访问所有功能
            userBean.setAccessAllPages(OnOffEnum.ON.value().equals(role.getIsSuper()));
        }

        return userBean;
    }

}
