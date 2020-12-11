package project.edge.service.notice;

import java.util.Date;

import garage.origin.service.Service;
import project.edge.domain.entity.Notify;
import project.edge.domain.entity.NotifySubscription;

public interface NoticeNotifySubscriptionService extends Service<NotifySubscription, String> {

	public void subscribe(Notify notify, String userId, Date applyAfter);
}
