package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.PrequalificationDao;
import project.edge.domain.entity.Prequalification;

/**
 * [t_prequalification]对应的 Service。
 */
@Service
public class PrequalificationServiceImpl extends GenericServiceImpl<Prequalification, String>
        implements PrequalificationService {

    @Resource
    private PrequalificationDao prequalificationDao;

    @Override
    public Dao<Prequalification, String> getDefaultDao() {
        return this.prequalificationDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }

}
