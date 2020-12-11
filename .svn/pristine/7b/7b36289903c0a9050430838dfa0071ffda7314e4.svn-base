package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.facility.SiteController;

/**
 * 站点弹出选择画面。
 *
 */
@Component
public class SitePopupManager extends AbstractPopupManager {

    @Resource
    private SiteController siteController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.siteController;
    }
}
