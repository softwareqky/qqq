package project.edge.service.hr;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.hr.ExpertProfessionalTitleDao;
import project.edge.domain.entity.ExpertProfessionalTitle;

/**
 * @author angel_000
 *         [t_expert_professional_title]对应的 Service。
 */
@Service
public class ExpertProfessionalTitleServiceImpl
        extends GenericServiceImpl<ExpertProfessionalTitle, String>
        implements ExpertProfessionalTitleService {

    @Resource
    private ExpertProfessionalTitleDao expertProfessionalTitleDao;

    @Override
    public Dao<ExpertProfessionalTitle, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.expertProfessionalTitleDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
