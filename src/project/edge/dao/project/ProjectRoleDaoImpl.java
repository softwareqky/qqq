/**
 * 
 */
package project.edge.dao.project;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.ProjectRole;


/**
 * @author angel_000 [t_project_role]对应的DAO。
 */
@Repository
public class ProjectRoleDaoImpl extends HibernateDaoImpl<ProjectRole, String>
        implements ProjectRoleDao {

    @Override
    public List<ProjectRole> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        orders.add(new OrderByDto("projectRoleOrder"));
        return getList(filter, orders, page);
    }

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("pid", "pid", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public ProjectRole find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ProjectRole projectRole = (ProjectRole) session().createCriteria(this.type)
                .createAlias("pid", "pid", JoinType.LEFT_OUTER_JOIN).add(Restrictions.eq("id", id))
                .uniqueResult();

        return projectRole;
    }

}
