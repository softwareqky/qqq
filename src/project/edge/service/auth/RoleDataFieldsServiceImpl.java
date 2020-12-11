/**
 * 
 */
package project.edge.service.auth;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.auth.RoleDataFieldsDao;
import project.edge.domain.entity.RoleDataFields;


/**
 * @author angel_000
 *         [t_role_data_fields]对应的 Service。
 */
@Service
public class RoleDataFieldsServiceImpl extends GenericServiceImpl<RoleDataFields, String>
        implements RoleDataFieldsService {

    @Resource
    private RoleDataFieldsDao roleDataFieldsDao;

    @Override
    public Dao<RoleDataFields, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.roleDataFieldsDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("roldId", false);
    }
}
