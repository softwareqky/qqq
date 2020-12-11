package project.edge.domain.view;

import garage.origin.domain.view.AbstractSessionUserBean;

/**
 * Session保存用户信息的DTO
 * 
 */
public class SessionUserBean extends AbstractSessionUserBean {

    /** 是否是超级用户 */
    private boolean isSuper;

    public boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(boolean isSuper) {
        this.isSuper = isSuper;
    }

}
