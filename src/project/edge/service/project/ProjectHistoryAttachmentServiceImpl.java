/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectHistoryAttachmentDao;
import project.edge.domain.entity.ProjectHistoryAttachment;


/**
 * @author angel_000
 *
 */
@Service
public class ProjectHistoryAttachmentServiceImpl
        extends GenericServiceImpl<ProjectHistoryAttachment, String>
        implements ProjectHistoryAttachmentService {

    @Resource
    private ProjectHistoryAttachmentDao projectHistoryAttachmentDao;

    @Override
    public Dao<ProjectHistoryAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectHistoryAttachmentDao;
    }

}
