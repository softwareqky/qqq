package project.edge.web.controller.general.popup;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.facility.LinkController;

/**
 * 项目人员弹出选择画面。
 * 
 */
@Component
public class LinkManager extends AbstractPopupManager {

    @Resource
    private LinkController linkController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.linkController;
    }
}
