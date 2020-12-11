package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectSetDao;
import project.edge.domain.entity.ProjectSet;

/**
 * @author angel_000
 *         [t_project_set]对应的Service。
 */
@Service
public class ProjectSetServiceImpl extends GenericServiceImpl<ProjectSet, String>
        implements ProjectSetService {

    @Resource
    private ProjectSetDao projectSetDao;

    @Override
    public Dao<ProjectSet, String> getDefaultDao() {
        return this.projectSetDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("projectNum", false);
    }

}
