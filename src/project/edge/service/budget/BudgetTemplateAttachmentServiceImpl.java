package project.edge.service.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.budget.BudgetTemplateAttachmentDao;
import project.edge.domain.entity.BudgetTemplateAttachment;

/**
 * @author angel_000
 *         [t_plan_history_task_attachment]对应的 Service。
 */
@Service
public class BudgetTemplateAttachmentServiceImpl
        extends GenericServiceImpl<BudgetTemplateAttachment, String>
        implements BudgetTemplateAttachmentService {

    @Resource
    private BudgetTemplateAttachmentDao budgetTemplateAttachmentDao;

    @Override
    public Dao<BudgetTemplateAttachment, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.budgetTemplateAttachmentDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id", false);
    }
}
