package project.edge.dao.acceptance;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.ReviewExpertComment;

/**
 * @author angel_000
 *         [t_review_expert_comment]对应的DAO。
 */
@Repository
public class ReviewExpertCommentDaoImpl extends HibernateDaoImpl<ReviewExpertComment, String>
        implements ReviewExpertCommentDao {

}
