/**
 * 
 */
package project.edge.dao.process;

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
import project.edge.domain.entity.ProjectInspect;


/**
 * @author angel_000
 *         [t_project_inspect]对应的DAO。
 */
@Repository
public class ProjectInspectDaoImpl extends HibernateDaoImpl<ProjectInspect, String>
        implements ProjectInspectDao {

    @Override
    public List<ProjectInspect> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<ProjectInspect> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        // 默认是 inner join，这里显示指定left join
        query.createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspectType", "inspectType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspectResult", "inspectResult", JoinType.LEFT_OUTER_JOIN)
                .createAlias("verifyResult", "verifyResult", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspectStatus", "inspectStatus", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspector", "inspector", JoinType.LEFT_OUTER_JOIN);
        // .createAlias("archives", "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many
        query.createAlias("projectInspectAttachments", "projectInspectAttachments",
                JoinType.LEFT_OUTER_JOIN).createAlias("projectInspectAttachments.archive",
                        "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many
    }


    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public ProjectInspect find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        ProjectInspect projectInspect = (ProjectInspect) session().createCriteria(this.type)
                .createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspectType", "inspectType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspectResult", "inspectResult", JoinType.LEFT_OUTER_JOIN)
                .createAlias("verifyResult", "verifyResult", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspectStatus", "inspectStatus", JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectInspectAttachments", "projectInspectAttachments",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("projectInspectAttachments.archive", "archives",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("inspector", "inspector", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        
        return projectInspect;
    }
}
