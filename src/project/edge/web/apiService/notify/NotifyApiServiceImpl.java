package project.edge.web.apiService.notify;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.edge.domain.entity.Notify;
import project.edge.domain.entity.User;
import project.edge.service.notice.NoticeNotifyService;
import project.edge.service.notice.NoticeNotifySubscriptionService;

@Service
@Transactional
public class NotifyApiServiceImpl implements NotifyApiService {
	
	@Resource
	private NoticeNotifyService notifyService;
	
	@Resource
	private NoticeNotifySubscriptionService notifySubscriptionService;

	public void sendNotify(List<User> userList, String subject, String message, Integer targetType) {
		
		for (User user: userList) {
			sendNotify(user, subject, message, targetType);
		}
	}
	
	public void sendNotify(User user, String subject, String message, Integer targetType) {
		
		Notify notify = new Notify();
    	notify.setType(1);
    	notify.setAction("NONE");
    	notify.setCreateAt(new Date());
    	notify.setCreateBy(user.getId());
    	notify.setTargetId("");
    	notify.setTargetType(targetType);
    	notify.setContent(message);
    	notifyService.create(notify);
    	
    	notifySubscriptionService.subscribe(notify, user.getId(), new Date());
	}
	
	public void sendNotifyAll(String subject, String message, Integer targetType) {
		
		Notify notify = new Notify();
    	notify.setType(2);
    	notify.setAction("NONE");
    	notify.setCreateAt(new Date());
    	notify.setCreateBy("system");
    	notify.setTargetId("");
    	notify.setTargetType(targetType);
    	notify.setContent(message);
    	notifyService.create(notify);
	}
}
