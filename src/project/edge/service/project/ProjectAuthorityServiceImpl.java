/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectAuthorityDao;
import project.edge.domain.entity.ProjectAuthority;


/**
 * @author angel_000
 *
 */
@Service
public class ProjectAuthorityServiceImpl extends GenericServiceImpl<ProjectAuthority, String>
        implements ProjectAuthorityService {

    @Resource
    private ProjectAuthorityDao projectAuthorityDao;

    @Override
    public Dao<ProjectAuthority, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectAuthorityDao;
    }

}
