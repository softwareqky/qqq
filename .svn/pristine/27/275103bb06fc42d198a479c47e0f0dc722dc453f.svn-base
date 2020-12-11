package project.edge.dao.notice;

import java.util.List;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.DataFilter;
import project.edge.domain.entity.NotifySubscription;

@Repository
public class NoticeNotifySubscriptionDaoImpl extends HibernateDaoImpl<NotifySubscription, String> implements NoticeNotifySubscriptionDao {

	@Override
    public List<NotifySubscription> list(DataFilter filter, List<OrderByDto> orders,
            PageCtrlDto page) {
        return getList(filter, orders, page);
    }

    @Override
    public List<NotifySubscription> list(DataFilter filter, List<OrderByDto> orders) {
        return getList(filter, orders, null);
    }
}