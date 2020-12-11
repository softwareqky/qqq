package project.edge.dao.bidding;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.TenderingPlanAttachment;

/**
 * @author angel_000
 *         [t_plan_history_task_attachment]对应的DAO。
 */
@Repository
public class TenderingPlanAttachmentDaoImpl
        extends HibernateDaoImpl<TenderingPlanAttachment, String>
        implements TenderingPlanAttachmentDao {
    @Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("tenderingPlan", "tenderingPlan", JoinType.LEFT_OUTER_JOIN).createAlias("archive",
                "archive", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public TenderingPlanAttachment find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        TenderingPlanAttachment tenderingPlanAttachment =
                (TenderingPlanAttachment) session().createCriteria(this.type)
                        .createAlias("tenderingPlan", "tenderingPlan", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN)
                        .add(Restrictions.eq("id", id)).uniqueResult();
        return tenderingPlanAttachment;
    }
}