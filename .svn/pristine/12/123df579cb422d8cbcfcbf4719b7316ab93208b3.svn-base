package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.BiddingInformationApprovalDao;
import project.edge.domain.entity.BiddingInformationApproval;

/**
 * [t_bidding_information_approval]对应的 Service。
 */
@Service
public class BiddingInformationApprovalServiceImpl
        extends GenericServiceImpl<BiddingInformationApproval, String>
        implements BiddingInformationApprovalService {

    @Resource
    private BiddingInformationApprovalDao biddingInformationApprovalDao;

    @Override
    public Dao<BiddingInformationApproval, String> getDefaultDao() {
        return this.biddingInformationApprovalDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
}
