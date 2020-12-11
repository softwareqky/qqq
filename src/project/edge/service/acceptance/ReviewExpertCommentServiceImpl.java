package project.edge.service.acceptance;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.acceptance.ReviewExpertCommentDao;
import project.edge.domain.entity.ReviewExpertComment;

/**
 * @author angel_000
 *         [t_review_expert_comment]对应的 Service。
 */
@Service
public class ReviewExpertCommentServiceImpl extends GenericServiceImpl<ReviewExpertComment, String>
        implements ReviewExpertCommentService {

    @Resource
    private ReviewExpertCommentDao reviewExpertCommentDao;

    @Override
    public Dao<ReviewExpertComment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.reviewExpertCommentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
