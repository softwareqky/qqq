package project.edge.dao.contract;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.PaymentContractAttachment;

@Repository
public class PaymentContractAttachmentDaoImpl extends
HibernateDaoImpl<PaymentContractAttachment, String> implements PaymentContractAttachmentDao {
	@Override
    protected DetachedCriteria prepareConditions(DetachedCriteria criteria, DataFilter filter) {

        criteria.createAlias("paymentContract", "paymentContract", JoinType.LEFT_OUTER_JOIN)
                .createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN);

        return super.prepareConditions(criteria, filter);
    }

    @Override
    public PaymentContractAttachment find(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        PaymentContractAttachment paymentContractAttachment =
                (PaymentContractAttachment) session().createCriteria(this.type)
                        .createAlias("paymentContract", "paymentContract", JoinType.LEFT_OUTER_JOIN)
                        .createAlias("archive", "archive", JoinType.LEFT_OUTER_JOIN)
                        .add(Restrictions.eq("id", id)).uniqueResult();
        return paymentContractAttachment;
    }
}
