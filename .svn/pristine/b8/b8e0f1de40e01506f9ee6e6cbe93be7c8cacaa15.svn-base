package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.project.ProjectRoleController;

/**
 * 项目角色弹出选择画面。
 *
 */
@Component
public class ProjectRolePopupManager extends AbstractPopupManager {

    @Resource
    private ProjectRoleController projectRoleController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.projectRoleController;
    }
}
