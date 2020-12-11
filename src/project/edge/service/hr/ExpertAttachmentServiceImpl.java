package project.edge.service.hr;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.hr.ExpertAttachmentDao;
import project.edge.domain.entity.ExpertAttachment;

@Service
public class ExpertAttachmentServiceImpl extends GenericServiceImpl<ExpertAttachment, String>
        implements ExpertAttachmentService {

    @Resource
    private ExpertAttachmentDao expertAttachmentDao;

    @Override
    public Dao<ExpertAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.expertAttachmentDao;
    }
}
