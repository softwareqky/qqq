package project.edge.service.notice;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.notice.NoticeNotifySubscriptionDao;
import project.edge.domain.entity.Notify;
import project.edge.domain.entity.NotifySubscription;

@Service
public class NoticeNotifySubscriptionServiceImpl extends GenericServiceImpl<NotifySubscription, String>
	implements NoticeNotifySubscriptionService {
	
	@Resource NoticeNotifySubscriptionDao notifySubscriptionDao;

	@Override
	public Dao<NotifySubscription, String> getDefaultDao() {
		return notifySubscriptionDao;
	}

	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("create_at", true);
	}
	
	/**
	 * 用户订阅某提醒
	 * @param notify
	 * @param userId
	 * @param applyAfter
	 */
	@Transactional
	public void subscribe(Notify notify, String userId, Date applyAfter) {
		NotifySubscription subscription = new NotifySubscription();
		subscription.setAction(notify.getAction());
		subscription.setApplyAfter(applyAfter);
		subscription.setCreateAt(new Date());
		subscription.setUserId(userId);
		subscription.setTargetId(notify.getId());
		subscription.setTargetType(notify.getTargetType());
		notifySubscriptionDao.create(subscription);
	}
}
