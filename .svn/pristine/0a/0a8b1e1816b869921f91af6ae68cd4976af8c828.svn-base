package project.edge.service.bidding;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.TenderingPlanAttachmentDao;
import project.edge.domain.entity.TenderingPlanAttachment;

/**
 * [t_tenderee]对应的 Service。
 */
@Service
public class TenderingPlanAttachmentServiceImpl extends GenericServiceImpl<TenderingPlanAttachment, String>
        implements TenderingPlanAttachmentService {

    @Resource
    private TenderingPlanAttachmentDao tenderingPlanAttachmentDao;

    @Override
    public Dao<TenderingPlanAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.tenderingPlanAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
    
}
