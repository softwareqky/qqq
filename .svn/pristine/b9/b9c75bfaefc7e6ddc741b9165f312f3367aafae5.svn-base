package project.edge.web.controller.general.popup;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.project.ProjectMemberController;

/**
 * 人员弹出选择画面。
 *
 */
@Component
public class ProjectPersonPopupManager extends AbstractPopupManager {

    @Resource
    private ProjectMemberController projectMemberController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.projectMemberController;
    }
}
