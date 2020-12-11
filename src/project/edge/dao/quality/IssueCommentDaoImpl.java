package project.edge.dao.quality;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.IssueComment;

/**
 * @author angel_000
 *         [t_issue_comment]对应的DAO。
 */
@Repository
public class IssueCommentDaoImpl extends HibernateDaoImpl<IssueComment, String>
        implements IssueCommentDao {

}
