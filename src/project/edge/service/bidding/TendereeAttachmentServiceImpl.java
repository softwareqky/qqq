package project.edge.service.bidding;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.TendereeAttachmentDao;
import project.edge.domain.entity.TendereeAttachment;

/**
 * @author angel_000
 *         [t_plan_history_task_attachment]对应的 Service。
 */
@Service
public class TendereeAttachmentServiceImpl
        extends GenericServiceImpl<TendereeAttachment, String>
        implements TendereeAttachmentService {

    @Resource
    private TendereeAttachmentDao tendereeAttachmentDao;

    @Override
    public Dao<TendereeAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.tendereeAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
