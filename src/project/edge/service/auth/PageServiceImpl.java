package project.edge.service.auth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.auth.PageDao;
import project.edge.domain.entity.Page;
import project.edge.domain.filter.PageFilter;

/**
 * 画面对应的Service。
 * 
 */
@Service
public class PageServiceImpl extends GenericServiceImpl<Page, String> implements PageService {

    @Resource
    private PageDao pageDao;

    @Override
    public Dao<Page, String> getDefaultDao() {
        return this.pageDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("pageOrder");
    }

    /**
     * 根据用户信息，获取画面列表。
     * 
     * @param userBean 用户信息对象
     * @return 画面列表
     */
    @Override
    @Transactional
    public List<Page> loadPages(SessionUserBean userBean) {

        if (userBean == null || StringUtils.isEmpty(userBean.getRoleId())) {
            return new ArrayList<Page>();
        }

        // 根据pageOrder排序
        List<OrderByDto> orders = new ArrayList<OrderByDto>();
        orders.add(this.getDefaultOrder());

        PageFilter pageFilter = new PageFilter();
        // 总是加载启用的功能
        pageFilter.addExact("isEnabled", OnOffEnum.ON.value())
                .addExact("isCommonVisible", OnOffEnum.ON.value())
                .addExact("isMenuVisible", OnOffEnum.ON.value())
                .addExact("isWeb", OnOffEnum.ON.value());

        // 非功能节点
        pageFilter.addExact("isFunction", OnOffEnum.OFF.value());

        // 用户角色不为空，且该角色只能访问部分画面时，用role id过滤
        if (!StringUtils.isEmpty(userBean.getRoleId()) && !userBean.isAccessAllPages()) {
            pageFilter.setRoleId(userBean.getRoleId());

        }
        return this.pageDao.list(pageFilter, orders);

    }
}
