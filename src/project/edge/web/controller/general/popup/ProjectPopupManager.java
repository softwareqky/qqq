package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.project.ProjectInitController;

/**
 * 项目弹出选择画面。
 *
 */
@Component
public class ProjectPopupManager extends AbstractPopupManager {

    @Resource
    private ProjectInitController projectInitController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.projectInitController;
    }
}
