package project.edge.service.project;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.PlanProgressAttachmentDao;
import project.edge.domain.entity.PlanProgressAttachment;

@Service
public class PlanProgressAttachmentServiceImpl
        extends GenericServiceImpl<PlanProgressAttachment, String>
        implements PlanProgressAttachmentService {

    @Resource
    private PlanProgressAttachmentDao planProgressAttachmentDao;

    @Override
    public Dao<PlanProgressAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planProgressAttachmentDao;
    }
}
