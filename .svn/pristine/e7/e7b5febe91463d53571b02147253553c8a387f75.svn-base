package project.edge.service.auth;

import garage.origin.domain.view.SessionUserBean;
import garage.origin.service.Service;
import project.edge.domain.entity.User;



/**
 * 用户对应的Service。
 * 
 */
public interface UserService extends Service<User, String> {

    /**
     * 根据用户名和密码获取用户信息。
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象
     */
    SessionUserBean login(String userName, String password);

    /**
     * 根据用户名和密码获取用户信息，包括用户基本信息、角色信息。
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 用户对象
     */
    
    SessionUserBean loginMobile(String loginName, String password);
}
