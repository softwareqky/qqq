package project.edge.service.schedule;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanHistoryTaskAttachmentDao;
import project.edge.domain.entity.PlanHistoryTaskAttachment;

/**
 * @author angel_000
 *         [t_plan_history_task_attachment]对应的 Service。
 */
@Service
public class PlanHistoryTaskAttachmentServiceImpl
        extends GenericServiceImpl<PlanHistoryTaskAttachment, String>
        implements PlanHistoryTaskAttachmentService {

    @Resource
    private PlanHistoryTaskAttachmentDao planHistoryTaskAttachmentDao;

    @Override
    public Dao<PlanHistoryTaskAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.planHistoryTaskAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
