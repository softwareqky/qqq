/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectFavoriteDao;
import project.edge.domain.entity.ProjectFavorite;


/**
 * @author angel_000
 *
 */
@Service
public class ProjectFavoriteServiceImpl extends GenericServiceImpl<ProjectFavorite, String>
        implements ProjectFavoriteService {

    @Resource
    private ProjectFavoriteDao projectFavoriteDao;
    
    @Override
    public Dao<ProjectFavorite, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectFavoriteDao;
    }

}
