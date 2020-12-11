/**
 * 
 */
package project.edge.service.auth;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.auth.PasswordChangeLogDao;
import project.edge.domain.entity.PasswordChangeLog;


/**
 * @author angel_000
 *         [t_password_change_log]对应的 Service。
 */
@Service
public class PasswordChangeLogServiceImpl extends GenericServiceImpl<PasswordChangeLog, String>
        implements PasswordChangeLogService {

    @Resource
    private PasswordChangeLogDao passwordChangeLogDao;

    @Override
    public Dao<PasswordChangeLog, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.passwordChangeLogDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("userId", false);
    }

}
