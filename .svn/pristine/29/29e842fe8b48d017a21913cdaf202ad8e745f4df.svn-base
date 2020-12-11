package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.BiddingBasicInfoDao;
import project.edge.domain.entity.BiddingBasicInfo;

/**
 * [t_bidding_basic_info]对应的 Service。
 */
@Service
public class BiddingBasicInfoServiceImpl extends GenericServiceImpl<BiddingBasicInfo, String>
        implements BiddingBasicInfoService {

    @Resource
    private BiddingBasicInfoDao biddingBasicInfoDao;

    @Override
    public Dao<BiddingBasicInfo, String> getDefaultDao() {
        return this.biddingBasicInfoDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
