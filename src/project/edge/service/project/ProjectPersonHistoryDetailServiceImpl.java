package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectPersonHistoryDetailDao;
import project.edge.domain.entity.ProjectPersonHistoryDetail;

/**
 * @author angel_000
 *         [t_project_person_history_detail]对应的 Service。
 */
@Service
public class ProjectPersonHistoryDetailServiceImpl
        extends GenericServiceImpl<ProjectPersonHistoryDetail, String>
        implements ProjectPersonHistoryDetailService {

    @Resource
    private ProjectPersonHistoryDetailDao projectPersonHistoryDetailDao;

    @Override
    public Dao<ProjectPersonHistoryDetail, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectPersonHistoryDetailDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
