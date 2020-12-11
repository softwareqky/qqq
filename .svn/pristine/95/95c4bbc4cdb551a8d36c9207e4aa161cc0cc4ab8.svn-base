/**
 * 
 */
package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectAttachmentDao;
import project.edge.domain.entity.ProjectAttachment;


/**
 * @author angel_000
 *
 */
@Service
public class ProjectAttachmentServiceImpl extends GenericServiceImpl<ProjectAttachment, String>
        implements ProjectAttachmentService {

    @Resource
    private ProjectAttachmentDao projectAttachmentDao;

    @Override
    public Dao<ProjectAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectAttachmentDao;
    }

}
