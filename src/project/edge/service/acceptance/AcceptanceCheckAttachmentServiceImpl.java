package project.edge.service.acceptance;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.acceptance.AcceptanceCheckAttachmentDao;
import project.edge.domain.entity.AcceptanceCheckAttachment;

/**
 * @author angel_000
 *         [t_acceptance_check_attachment]对应的 Service。
 */
@Service
public class AcceptanceCheckAttachmentServiceImpl
        extends GenericServiceImpl<AcceptanceCheckAttachment, String>
        implements AcceptanceCheckAttachmentService {

    @Resource
    private AcceptanceCheckAttachmentDao acceptanceCheckAttachmentDao;

    @Override
    public Dao<AcceptanceCheckAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.acceptanceCheckAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
