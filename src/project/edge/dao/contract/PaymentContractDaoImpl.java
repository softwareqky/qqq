package project.edge.dao.contract;

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
import project.edge.domain.entity.PaymentContract;

/**
 * [t_payment_contract]对应的DAO。
 */
@Repository
public class PaymentContractDaoImpl extends HibernateDaoImpl<PaymentContract, String>
        implements PaymentContractDao {

    @Override
    public List<PaymentContract> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<PaymentContract> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }



    @Override
    protected void setListCriteriaAlias(Criteria query) {
        query.createAlias("purchaseOrder", "purchaseOrder", JoinType.LEFT_OUTER_JOIN)
                .createAlias("signingPeople", "signingPeople", JoinType.LEFT_OUTER_JOIN)
                .createAlias("entryPerson", "entryPerson", JoinType.LEFT_OUTER_JOIN)
                .createAlias("sealType", "sealType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("contractKind", "contractKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
                .createAlias("paymentContractAttachments", "paymentContractAttachments",JoinType.LEFT_OUTER_JOIN)
                .createAlias("paymentContractAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN);
    }

    /**
     * 获取单条实体。
     * 
     * @param id 主键
     * @return 实体对象，如果没有找到实体，则返回null
     */
    @Override
    public PaymentContract find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        // 这里将关联的信息全部取出，主要是为了配合新建/修改后重新获取实体信息，然后将这个完整的实体信息显示到一览画面中
        PaymentContract paymentContract = (PaymentContract) session().createCriteria(this.type)
                .createAlias("purchaseOrder", "purchaseOrder", JoinType.LEFT_OUTER_JOIN)
                .createAlias("signingPeople", "signingPeople", JoinType.LEFT_OUTER_JOIN)
                .createAlias("entryPerson", "entryPerson", JoinType.LEFT_OUTER_JOIN)
                .createAlias("sealType", "sealType", JoinType.LEFT_OUTER_JOIN)
                .createAlias("contractKind", "contractKind", JoinType.LEFT_OUTER_JOIN)
                .createAlias("project", "project", JoinType.LEFT_OUTER_JOIN)
                .createAlias("virtualOrg", "virtualOrg", JoinType.LEFT_OUTER_JOIN)
                .createAlias("paymentContractAttachments", "paymentContractAttachments",JoinType.LEFT_OUTER_JOIN)
                .createAlias("paymentContractAttachments.archive", "archives", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", id)).uniqueResult();
        return paymentContract;
    }
}
