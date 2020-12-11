/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectHistoryDao;
import project.edge.domain.entity.ProjectHistory;


/**
 * @author angel_000
 *         [t_project_history]对应的Service。
 */
@Service
public class ProjectHistoryServiceImpl extends GenericServiceImpl<ProjectHistory, String>
        implements ProjectHistoryService {

    @Resource
    private ProjectHistoryDao projectHistoryDao;

    @Override
    public Dao<ProjectHistory, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectHistoryDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("projectNum", false);
    }

}
