package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.CapitalApplyAttachmentDao;
import project.edge.domain.entity.CapitalApplyAttachment;

@Service
public class CapitalApplyAttachmentServiceImpl
        extends GenericServiceImpl<CapitalApplyAttachment, String>
        implements CapitalApplyAttachmentService {

    @Resource
    private CapitalApplyAttachmentDao capitalApplyAttachmentDao;

    @Override
    public Dao<CapitalApplyAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.capitalApplyAttachmentDao;
    }

}
