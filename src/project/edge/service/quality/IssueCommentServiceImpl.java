package project.edge.service.quality;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.quality.IssueCommentDao;
import project.edge.domain.entity.IssueComment;

/**
 * @author angel_000
 *         [t_it_issue_commentssue]对应的 Service。
 */
@Service
public class IssueCommentServiceImpl extends GenericServiceImpl<IssueComment, String>
        implements IssueCommentService {

    @Resource
    private IssueCommentDao issueCommentDao;

    @Override
    public Dao<IssueComment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.issueCommentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
}
