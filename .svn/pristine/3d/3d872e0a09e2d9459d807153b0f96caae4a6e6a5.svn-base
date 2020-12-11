package project.edge.service.bidding;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.TenderingPurchaseDao;
import project.edge.domain.entity.TenderingPurchase;

@Service
public class TenderingPurchaseServiceImpl extends GenericServiceImpl<TenderingPurchase, String>
		implements TenderingPurchaseService {
	@Resource
    private TenderingPurchaseDao tenderingPurchaseDao;
    

    @Override
    public Dao<TenderingPurchase, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.tenderingPurchaseDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
    
}
