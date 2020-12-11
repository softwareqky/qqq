/**
 * 
 */
package project.edge.dao.auth;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.PasswordChangeLog;


/**
 * @author angel_000
 *         [t_password_change_log]对应的DAO。
 */
@Repository
public class PasswordChangeLogDaoImpl extends HibernateDaoImpl<PasswordChangeLog, String>
        implements PasswordChangeLogDao {

}
