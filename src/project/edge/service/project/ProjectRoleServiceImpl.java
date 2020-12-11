/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectRoleDao;
import project.edge.domain.entity.ProjectRole;


/**
 * @author angel_000
 *         [t_project_role]对应的Service。
 */
@Service
public class ProjectRoleServiceImpl extends GenericServiceImpl<ProjectRole, String>
        implements ProjectRoleService {

    @Resource
    private ProjectRoleDao projectRoleDao;

    @Override
    public Dao<ProjectRole, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectRoleDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("projectRoleName", false);
    }

}
