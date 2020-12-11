/**
 * 
 */
package project.edge.dao.auth;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.RoleDataFields;


/**
 * @author angel_000
 *         [t_role_data_fields]对应的DAO。
 */
@Repository
public class RoleDataFieldsDaoImpl extends HibernateDaoImpl<RoleDataFields, String>
        implements RoleDataFieldsDao {

}
