package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.schedule.PlanController;

/**
 * 计划弹出选择画面。
 *
 */
@Component
public class PlanPopupManager extends AbstractPopupManager {

    @Resource
    private PlanController planController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.planController;
    }
}
