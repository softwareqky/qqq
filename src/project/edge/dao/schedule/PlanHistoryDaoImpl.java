package project.edge.dao.schedule;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.PlanHistory;

/**
 * @author angel_000
 *         [t_plan_history]对应的DAO。
 */
@Repository
public class PlanHistoryDaoImpl extends HibernateDaoImpl<PlanHistory, String>
        implements PlanHistoryDao {

    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("plan", "plan", JoinType.LEFT_OUTER_JOIN)
                .createAlias("planChange", "planChange", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment", "segment", JoinType.LEFT_OUTER_JOIN)
                .createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
                .createAlias("planCalendar", "planCalendar", JoinType.LEFT_OUTER_JOIN)
                .createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN);


        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public PlanHistory find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        PlanHistory planHistory = (PlanHistory) session().createCriteria(this.type)
                .createAlias("plan", "plan", JoinType.LEFT_OUTER_JOIN)
                .createAlias("planChange", "planChange", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                .createAlias("segment", "segment", JoinType.LEFT_OUTER_JOIN)
                .createAlias("virtualOrg", "v", JoinType.LEFT_OUTER_JOIN)
                .createAlias("planCalendar", "planCalendar", JoinType.LEFT_OUTER_JOIN)
                .createAlias("creator", "creator", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return planHistory;
    }
}
