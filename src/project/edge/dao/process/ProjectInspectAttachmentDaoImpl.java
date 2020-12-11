package project.edge.dao.process;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.ProjectInspectAttachment;

/**
 * @author angel_000
 *         [t_project_inspect_attachment]对应的DAO。
 */
@Repository
public class ProjectInspectAttachmentDaoImpl extends
        HibernateDaoImpl<ProjectInspectAttachment, String> implements ProjectInspectAttachmentDao {

}
