package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanTaskAttachmentDao;
import project.edge.domain.entity.PlanTaskAttachment;

/**
 * @author angel_000
 *         [t_plan_task_attachment]对应的 Service。
 */
@Service
public class PlanTaskAttachmentServiceImpl extends GenericServiceImpl<PlanTaskAttachment, String>
        implements PlanTaskAttachmentService {

    @Resource
    private PlanTaskAttachmentDao planTaskAttachmentDao;

    @Override
    public Dao<PlanTaskAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planTaskAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
