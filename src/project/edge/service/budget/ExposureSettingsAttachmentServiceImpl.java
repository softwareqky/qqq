package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.ExposureSettingsAttachmentDao;
import project.edge.domain.entity.ExposureSettingsAttachment;

/**
 * @author angel_000
 *         [t_plan_history_task_attachment]对应的 Service。
 */
@Service
public class ExposureSettingsAttachmentServiceImpl
        extends GenericServiceImpl<ExposureSettingsAttachment, String>
        implements ExposureSettingsAttachmentService {

    @Resource
    private ExposureSettingsAttachmentDao exposureSettingsAttachmentDao;

    @Override
    public Dao<ExposureSettingsAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.exposureSettingsAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
