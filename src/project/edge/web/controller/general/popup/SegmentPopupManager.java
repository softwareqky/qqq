package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.facility.SegmentController;

/**
 * 中继段弹出选择画面。
 *
 */
@Component
public class SegmentPopupManager extends AbstractPopupManager {

    @Resource
    private SegmentController segmentController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.segmentController;
    }
}
