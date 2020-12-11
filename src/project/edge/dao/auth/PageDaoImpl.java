package project.edge.dao.auth;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.Page;
import project.edge.domain.filter.PageFilter;


/**
 * [t_page]对应的DAO。
 * 
 */
@Repository
public class PageDaoImpl extends HibernateDaoImpl<Page, String> implements PageDao {

    /**
     * 根据过滤器，设置查询的Where条件，这些条件既可用于分页查询，又可用于非分页查询。如果需要，子类应重写此方法。
     * 
     * @param criteria 查询接口
     * @param filter 过滤条件
     * @return DetachedCriteria对象
     */
    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria = super.prepareConditions(criteria, filter);

        if (filter == null) {
            return criteria;
        }

        PageFilter f = null;
        if (filter instanceof PageFilter) {
            f = (PageFilter) filter;
        }

        if (f == null) {
            return criteria;
        }

        // roleId过滤
        if (f.getRoleId() != null) {
            // 查找roleId关联的，或者不用于角色配置的
            DetachedCriteria dc = DetachedCriteria.forClass(Page.class).createAlias("roles", "rs")
                    .add(Restrictions.eq("rs.id", f.getRoleId()))
                    .setProjection(Projections.property("id"));

            // criteria.add(Restrictions.or(Restrictions.eq("isRoleVisible", OnOffEnum.OFF.value()),
            // Subqueries.propertyIn("id", dc)));
            criteria.add(Subqueries.propertyIn("id", dc));
        }

        return criteria;
    }
}
