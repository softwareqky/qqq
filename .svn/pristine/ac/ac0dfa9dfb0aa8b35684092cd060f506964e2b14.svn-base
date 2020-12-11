package project.edge.service.project;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectMilestoneDao;
import project.edge.domain.entity.ProjectMilestone;

/**
 * @author angel_000
 *         [t_virtual_org]对应的 Service。
 */
@Service
public class ProjectMilestoneServiceImpl extends GenericServiceImpl<ProjectMilestone, String>
        implements ProjectMilestoneService {

    @Resource
    private ProjectMilestoneDao projectMilestoneDao;

    @Override
    public Dao<ProjectMilestone, String> getDefaultDao() {
        return this.projectMilestoneDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

}
