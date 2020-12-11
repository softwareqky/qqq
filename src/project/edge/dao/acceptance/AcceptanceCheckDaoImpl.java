/**
 * 
 */
package project.edge.dao.acceptance;

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
import project.edge.domain.entity.AcceptanceCheck;


/**
 * @author angel_000
 *         [t_acceptance_check]对应的DAO。
 */
@Repository
public class AcceptanceCheckDaoImpl extends HibernateDaoImpl<AcceptanceCheck, String>
        implements AcceptanceCheckDao {

    @Override
    public List<AcceptanceCheck> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<AcceptanceCheck> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }

    // @Override
    // protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {
    //
    // criteria.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
    // .createAlias("acceptanceCheckType", "act", JoinType.LEFT_OUTER_JOIN)
    // .createAlias("acceptanceCheckResult", "acceptanceCheckResult",
    // JoinType.LEFT_OUTER_JOIN)
    // .createAlias("acceptanceVerifyResult", "acceptanceVerifyResult",
    // JoinType.LEFT_OUTER_JOIN);
    //
    // return super.prepareConditions(criteria, filter);
    // }

    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckType", "act", JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckKind", "acceptanceCheckKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckResult", "acceptanceCheckResult",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceVerifyResult", "acceptanceVerifyResult",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckStatus", "acceptanceCheckStatus",
                        JoinType.LEFT_OUTER_JOIN);


        query.createAlias("acceptanceCheckAttachments", "acceptanceCheckAttachments",
                JoinType.LEFT_OUTER_JOIN).createAlias("acceptanceCheckAttachments.archive",
                        "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many

    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public AcceptanceCheck find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        AcceptanceCheck acceptanceCheck = (AcceptanceCheck) session().createCriteria(this.type)
                .createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckType", "act", JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckKind", "acceptanceCheckKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckResult", "acceptanceCheckResult",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceVerifyResult", "acceptanceVerifyResult",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckStatus", "acceptanceCheckStatus",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckAttachments", "acceptanceCheckAttachments",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("acceptanceCheckAttachments.archive", "archives",
                        JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return acceptanceCheck;
    }

}
