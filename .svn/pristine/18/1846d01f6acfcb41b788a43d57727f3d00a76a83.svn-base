package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.AcceptanceInfoDao;
import project.edge.domain.entity.AcceptanceInfo;

/**
 * [t_acceptance_info]对应的 Service。
 */
@Service
public class AcceptanceInfoServiceImpl extends GenericServiceImpl<AcceptanceInfo, String>
        implements AcceptanceInfoService {

    @Resource
    private AcceptanceInfoDao acceptanceInfoDao;

    @Override
    public Dao<AcceptanceInfo, String> getDefaultDao() {
        return this.acceptanceInfoDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
