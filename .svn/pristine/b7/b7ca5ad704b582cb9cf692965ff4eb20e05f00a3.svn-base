package project.edge.dao.project;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.ProjectChange;


/**
 * [t_project_change]对应的DAO。
 */
@Repository
public class ProjectChangeDaoImpl extends HibernateDaoImpl<ProjectChange, String>
        implements ProjectChangeDao {

    @Override
    public List<ProjectChange> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<ProjectChange> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        // 默认是 inner join，这里显示指定left join
        query.createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment", "segment", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectKind", "projectKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectSet", "projectSet", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectStatus", "projectStatus", JoinType.LEFT_OUTER_JOIN)
                .createAlias("mainLeader", "mainLeader", JoinType.LEFT_OUTER_JOIN);

        query.createAlias("projectChangeAttachments", "projectChangeAttachments",
                JoinType.LEFT_OUTER_JOIN).createAlias("projectChangeAttachments.archive",
                        "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many

    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public ProjectChange find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ProjectChange projectChange = (ProjectChange) session().createCriteria(this.type)
                .createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment", "segment", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectKind", "projectKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectSet", "projectSet", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectStatus", "projectStatus", JoinType.LEFT_OUTER_JOIN)
                .createAlias("mainLeader", "mainLeader", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectChangeAttachments", "projectChangeAttachments",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectChangeAttachments.archive", "archives",
                        JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return projectChange;
    }

}
