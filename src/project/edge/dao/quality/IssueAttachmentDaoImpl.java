/**
 * 
 */
package project.edge.dao.quality;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.IssueAttachment;


/**
 * @author angel_000
 *         [t_issue_attachment]对应的DAO。
 */
@Repository
public class IssueAttachmentDaoImpl extends HibernateDaoImpl<IssueAttachment, String>
        implements IssueAttachmentDao {

}
