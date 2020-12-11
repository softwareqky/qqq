package project.edge.service.hr;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.hr.ExpertQualificationDao;
import project.edge.domain.entity.ExpertQualification;

/**
 * @author angel_000
 *         [t_expert_qualification]对应的 Service。
 */
@Service
public class ExpertQualificationServiceImpl extends GenericServiceImpl<ExpertQualification, String>
        implements ExpertQualificationService {

    @Resource
    private ExpertQualificationDao expertQualificationDao;

    @Override
    public Dao<ExpertQualification, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.expertQualificationDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
