/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectChangeAttachmentDao;
import project.edge.domain.entity.ProjectChangeAttachment;


/**
 * @author angel_000
 *
 */
@Service
public class ProjectChangeAttachmentServiceImpl
        extends GenericServiceImpl<ProjectChangeAttachment, String>
        implements ProjectChangeAttachmentService {

    @Resource
    private ProjectChangeAttachmentDao projectChangeAttachmentDao;

    @Override
    public Dao<ProjectChangeAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectChangeAttachmentDao;
    }

}
