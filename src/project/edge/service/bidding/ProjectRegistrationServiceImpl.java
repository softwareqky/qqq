package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.ProjectRegistrationDao;
import project.edge.domain.entity.ProjectRegistration;

/**
 * [t_project_registration]对应的 Service。
 */
@Service
public class ProjectRegistrationServiceImpl extends GenericServiceImpl<ProjectRegistration, String>
        implements ProjectRegistrationService {

    @Resource
    private ProjectRegistrationDao proctRegistrationDao;

    @Override
    public Dao<ProjectRegistration, String> getDefaultDao() {
        return this.proctRegistrationDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
