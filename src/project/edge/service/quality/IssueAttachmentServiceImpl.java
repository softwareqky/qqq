/**
 * 
 */
package project.edge.service.quality;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.quality.IssueAttachmentDao;
import project.edge.domain.entity.IssueAttachment;


/**
 * @author angel_000
 *         [t_issue_attachment]对应的Service。
 */
@Service
public class IssueAttachmentServiceImpl extends GenericServiceImpl<IssueAttachment, String>
        implements IssueAttachmentService {

    @Resource
    private IssueAttachmentDao issueAttachmentDao;

    @Override
    public Dao<IssueAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.issueAttachmentDao;
    }

}
