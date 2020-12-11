/**
 * 
 */
package project.edge.service.bidding;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.bidding.PurchaseOrderDetailAttachmentDao;
import project.edge.domain.entity.PurchaseOrderDetailAttachment;


/**
 * @author angel_000
 *
 */
@Service
public class PurchaseOrderDetailAttachmentServiceImpl extends GenericServiceImpl<PurchaseOrderDetailAttachment, String>
        implements PurchaseOrderDetailAttachmentService {
    
    @Resource
    private PurchaseOrderDetailAttachmentDao purchaseOrderDetailAttachmentDao;

    @Override
    public Dao<PurchaseOrderDetailAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.purchaseOrderDetailAttachmentDao;
    }

}
