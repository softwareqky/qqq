package project.edge.domain.view;

import garage.origin.domain.view.ViewBean;

/**
 * 信息立项。
 *
 */
public class InfoApplicationBean implements ViewBean {

    private String id;

    @Override
    public String getId() {
        return this.id;
    }

}
