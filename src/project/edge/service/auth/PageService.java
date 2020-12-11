package project.edge.service.auth;

import java.util.List;

import garage.origin.domain.view.SessionUserBean;
import garage.origin.service.Service;
import project.edge.domain.entity.Page;

/**
 * 画面对应的Service。
 * 
 */
public interface PageService extends Service<Page, String> {

    /**
     * 根据用户信息，获取画面列表。
     * 
     * @param userBean 用户信息对象
     * @return 画面列表
     */
    List<Page> loadPages(SessionUserBean userBean);
}
