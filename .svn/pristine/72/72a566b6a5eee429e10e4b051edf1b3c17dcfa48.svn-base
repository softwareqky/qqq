package project.edge.dao.project;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.ProjectGroup;

/**
 * @author angel_000
 *         [t_project_group]对应的DAO。
 */
@Repository
public class ProjectGroupDaoImpl extends HibernateDaoImpl<ProjectGroup, String>
        implements ProjectGroupDao {

    @Override
    public List<ProjectGroup> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<ProjectGroup> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        // 默认是 inner join，这里显示指定left join
        query.createAlias("leader", "leader", JoinType.LEFT_OUTER_JOIN);

        query.createAlias("archives", "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many

    }

    @Override
    public ProjectGroup find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ProjectGroup group = (ProjectGroup) session().createCriteria(this.type)
                .createAlias("leader", "leader", JoinType.LEFT_OUTER_JOIN)
                .createAlias("archives", "archives", JoinType.LEFT_OUTER_JOIN) // one-to-many
                .add(Restrictions.eq("isDeleted", OnOffEnum.OFF.value()))
                .add(Restrictions.eq("id", id)).uniqueResult();
        return group;
    }
}
