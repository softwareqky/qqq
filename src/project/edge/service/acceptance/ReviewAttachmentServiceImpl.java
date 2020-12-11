package project.edge.service.acceptance;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.acceptance.ReviewAttachmentDao;
import project.edge.domain.entity.ReviewAttachment;

/**
 * @author angel_000
 *         [t_review_attachment]对应的 Service。
 */
@Service
public class ReviewAttachmentServiceImpl extends GenericServiceImpl<ReviewAttachment, String>
        implements ReviewAttachmentService {

    @Resource
    private ReviewAttachmentDao reviewAttachmentDao;

    @Override
    public Dao<ReviewAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.reviewAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
