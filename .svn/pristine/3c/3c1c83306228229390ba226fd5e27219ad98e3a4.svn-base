package project.edge.service.process;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.process.ProjectInspectAttachmentDao;
import project.edge.domain.entity.ProjectInspectAttachment;

/**
 * @author angel_000
 *         [t_project_inspect_attachment]对应的 Service。
 */
@Service
public class ProjectInspectAttachmentServiceImpl
        extends GenericServiceImpl<ProjectInspectAttachment, String>
        implements ProjectInspectAttachmentService {

    @Resource
    private ProjectInspectAttachmentDao projectInspectAttachmentDao;

    @Override
    public Dao<ProjectInspectAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectInspectAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
