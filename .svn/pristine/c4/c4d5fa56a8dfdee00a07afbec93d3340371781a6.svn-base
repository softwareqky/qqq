package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.OutbiddingInfoDao;
import project.edge.domain.entity.OutbiddingInfo;

/**
 * [t_outbidding_info]对应的 Service。
 */
@Service
public class OutbiddingInfoServiceImpl extends GenericServiceImpl<OutbiddingInfo, String>
        implements OutbiddingInfoService {

    @Resource
    private OutbiddingInfoDao outbiddingInfoDao;

    @Override
    public Dao<OutbiddingInfo, String> getDefaultDao() {
        return this.outbiddingInfoDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
