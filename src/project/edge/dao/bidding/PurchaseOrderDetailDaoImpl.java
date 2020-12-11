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
import project.edge.domain.entity.PurchaseOrderDetail;

/**
 * [t_purchase_order_detail]对应的DAO。
 */
@Repository
public class PurchaseOrderDetailDaoImpl extends HibernateDaoImpl<PurchaseOrderDetail, String>
        implements PurchaseOrderDetailDao {

    @Override
    public List<PurchaseOrderDetail> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<PurchaseOrderDetail> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }



    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("purchaseOrder", "purchaseOrder", JoinType.LEFT_OUTER_JOIN);

        query.createAlias("purchaseOrderDetailAttachments", "purchaseOrderDetailAttachments", JoinType.LEFT_OUTER_JOIN)
        	.createAlias("purchaseOrderDetailAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN)
        	.createAlias("budgetEstimateSum", "budgetEstimateSum", JoinType.LEFT_OUTER_JOIN)
            .createAlias("purchaseKind", "purchaseKind", JoinType.LEFT_OUTER_JOIN)
            .createAlias("province", "province", JoinType.LEFT_OUTER_JOIN)
            .createAlias("city", "city", JoinType.LEFT_OUTER_JOIN)
            .createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
            .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
        	.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN); // one-to-many

    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public PurchaseOrderDetail find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        PurchaseOrderDetail purchaseOrderDetail =
                (PurchaseOrderDetail) session().createCriteria(this.type)
                        .createAlias("purchaseOrder", "purchaseOrder", JoinType.LEFT_OUTER_JOIN).createAlias("purchaseOrderDetailAttachments", "purchaseOrderDetailAttachments",
                                JoinType.LEFT_OUTER_JOIN)
                        .createAlias("purchaseOrderDetailAttachments.archive", "archives",
                                JoinType.LEFT_OUTER_JOIN)
                        .createAlias("budgetEstimateSum", "budgetEstimateSum", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("purchaseKind", "purchaseKind", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("province", "province", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("city", "city", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("site", "site", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("applicant", "applicant", JoinType.LEFT_OUTER_JOIN)
                		.createAlias("project", "pj", JoinType.LEFT_OUTER_JOIN)
                        .add(Restrictions.eq("id", id)).uniqueResult();
        return purchaseOrderDetail;
    }
}
