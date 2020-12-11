package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.org.RelatedUnitController;

/**
 * 往来单位弹出选择画面。
 *
 */
@Component
public class RelatedUnitPopupManager extends AbstractPopupManager {

    @Resource
    private RelatedUnitController relatedUnitController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.relatedUnitController;
    }
}
