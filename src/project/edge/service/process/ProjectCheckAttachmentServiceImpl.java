package project.edge.service.process;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.process.ProjectCheckAttachmentDao;
import project.edge.domain.entity.ProjectCheckAttachment;

/**
 * @author angel_000
 *         [t_project_check_attachment]对应的 Service。
 */
@Service
public class ProjectCheckAttachmentServiceImpl
        extends GenericServiceImpl<ProjectCheckAttachment, String>
        implements ProjectCheckAttachmentService {

    @Resource
    private ProjectCheckAttachmentDao projectCheckAttachmentDao;

    @Override
    public Dao<ProjectCheckAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectCheckAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }

}
