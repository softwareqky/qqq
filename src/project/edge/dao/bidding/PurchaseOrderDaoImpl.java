package project.edge.dao.bidding;

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
import project.edge.domain.entity.PurchaseOrder;

/**
 * [t_purchase_order]对应的DAO。
 */
@Repository
public class PurchaseOrderDaoImpl extends HibernateDaoImpl<PurchaseOrder, String>
        implements PurchaseOrderDao {

    @Override
    public List<PurchaseOrder> list(DataFilter filter, List<OrderByDto> orders, PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<PurchaseOrder> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }


    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
             .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
             .createAlias("auditApplicant", "auditApplicant", JoinType.LEFT_OUTER_JOIN)
             .createAlias("paymentType", "paymentType", JoinType.LEFT_OUTER_JOIN)
             .createAlias("purchaseType", "purchaseType", JoinType.LEFT_OUTER_JOIN)
             .createAlias("purchaseKind", "purchaseKind", JoinType.LEFT_OUTER_JOIN)
             .createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
             .createAlias("inputApplicant", "inputApplicant", JoinType.LEFT_OUTER_JOIN)
             .createAlias("purchaseOrderAttachments", "purchaseOrderAttachments",JoinType.LEFT_OUTER_JOIN)
             .createAlias("purchaseOrderAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN); // one-to-many

    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public PurchaseOrder find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        PurchaseOrder purchaseOrder = (PurchaseOrder) session().createCriteria(this.type)
        		.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("auditApplicant", "auditApplicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("paymentType", "paymentType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("purchaseType", "purchaseType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("purchaseKind", "purchaseKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
                .createAlias("inputApplicant", "inputApplicant", JoinType.LEFT_OUTER_JOIN)
                .createAlias("purchaseOrderAttachments", "purchaseOrderAttachments",
                        JoinType.LEFT_OUTER_JOIN)
                .createAlias("purchaseOrderAttachments.archive", "archives",
                        JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return purchaseOrder;
    }
}
