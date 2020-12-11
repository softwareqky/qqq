package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.hr.PostController;

/**
 * 岗位弹出选择画面。
 *
 */
@Component
public class PostPopupManager extends AbstractPopupManager {

    @Resource
    private PostController postController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.postController;
    }
}
