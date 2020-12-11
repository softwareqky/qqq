package project.edge.service.process;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.process.CompletedInfoAttachmentDao;
import project.edge.domain.entity.CompletedInfoAttachment;

/**
 * @author angel_000
 *         [t_completed_info_attachment]对应的 Service。
 */
@Service
public class CompletedInfoAttachmentServiceImpl
        extends GenericServiceImpl<CompletedInfoAttachment, String>
        implements CompletedInfoAttachmentService {

    @Resource
    private CompletedInfoAttachmentDao completedInfoAttachmentDao;

    @Override
    public Dao<CompletedInfoAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.completedInfoAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
