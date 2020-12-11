package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectPersonChangeDetailDao;
import project.edge.domain.entity.ProjectPersonChangeDetail;

/**
 * [t_project_person_change_detail]对应的 Service。
 */
@Service
public class ProjectPersonChangeDetailServiceImpl
        extends GenericServiceImpl<ProjectPersonChangeDetail, String>
        implements ProjectPersonChangeDetailService {

    @Resource
    private ProjectPersonChangeDetailDao projectPersonChangeDetailDao;

    @Override
    public Dao<ProjectPersonChangeDetail, String> getDefaultDao() {
        return this.projectPersonChangeDetailDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
