package project.edge.web.controller.general.popup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import project.edge.web.controller.common.SingleGridController;
import project.edge.web.controller.hr.PersonController;

/**
 * 人员弹出选择画面。
 *
 */
@Component
public class PersonPopupManager extends AbstractPopupManager {

    @Resource
    private PersonController personController;

    @Override
    protected SingleGridController<?, ?> getController() {
        return this.personController;
    }
}
