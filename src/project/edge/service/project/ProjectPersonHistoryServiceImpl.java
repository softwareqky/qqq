package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectPersonHistoryDao;
import project.edge.domain.entity.ProjectPersonHistory;

/**
 * @author angel_000
 *         [t_project_person_history]对应的 Service。
 */
@Service
public class ProjectPersonHistoryServiceImpl extends
        GenericServiceImpl<ProjectPersonHistory, String> implements ProjectPersonHistoryService {

    @Resource
    private ProjectPersonHistoryDao projectPersonHistoryDao;

    @Override
    public Dao<ProjectPersonHistory, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectPersonHistoryDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("versionDatetime", false);
    }

}
