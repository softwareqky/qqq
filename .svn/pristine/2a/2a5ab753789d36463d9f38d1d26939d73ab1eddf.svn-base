package project.edge.service.auth;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.auth.RoleDao;
import project.edge.domain.entity.Role;


/**
 * [t_role]对应的 Service。
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, String> implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public Dao<Role, String> getDefaultDao() {
        return this.roleDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("roleName");
    }
}
