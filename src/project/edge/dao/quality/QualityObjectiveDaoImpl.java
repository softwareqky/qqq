package project.edge.dao.quality;

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
import project.edge.domain.entity.QualityObjective;

/**
 * @author angel_000
 *         [t_quality_objective]对应的DAO。
 */
@Repository
public class QualityObjectiveDaoImpl extends HibernateDaoImpl<QualityObjective, String>
        implements QualityObjectiveDao {

    @Override
    public List<QualityObjective> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<QualityObjective> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }



    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("leader", "leader", JoinType.LEFT_OUTER_JOIN)
                .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
            	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("specification", "specification", JoinType.LEFT_OUTER_JOIN);

    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public QualityObjective find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        QualityObjective objective = (QualityObjective) session().createCriteria(this.type)
                .createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("leader", "leader", JoinType.LEFT_OUTER_JOIN)
                .createAlias("attachments", "attachments", JoinType.LEFT_OUTER_JOIN)
            	.createAlias("attachments.archive", "archive", JoinType.LEFT_OUTER_JOIN)
                .createAlias("specification", "specification", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return objective;
    }

}
