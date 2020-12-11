package project.edge.web.controller.general.popup;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import project.edge.web.controller.budget.BudgetEstimateSumController;
import project.edge.web.controller.common.SingleGridController;

/**
 * 概算册弹出选择画面。
 * 
 */
@Component
public class BudgetEstimateSumManager extends AbstractPopupManager {

    @Resource
    private BudgetEstimateSumController budgetEstimateSumController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.budgetEstimateSumController;
    }
}
