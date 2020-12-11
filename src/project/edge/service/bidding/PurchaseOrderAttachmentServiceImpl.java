package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.PurchaseOrderAttachmentDao;
import project.edge.domain.entity.PurchaseOrderAttachment;

@Service
public class PurchaseOrderAttachmentServiceImpl
        extends GenericServiceImpl<PurchaseOrderAttachment, String>
        implements PurchaseOrderAttachmentService {

    @Resource
    private PurchaseOrderAttachmentDao purchaseOrderAttachmentDao;

    @Override
    public Dao<PurchaseOrderAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.purchaseOrderAttachmentDao;
    }

}
