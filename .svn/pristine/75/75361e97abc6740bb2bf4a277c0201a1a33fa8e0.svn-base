package project.edge.service.notice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.notice.NoticeUserNotifyDao;
import project.edge.domain.entity.UserNotify;

@Service
public class NoticeUserNotifyServiceImpl extends GenericServiceImpl<UserNotify, String> 
	implements NoticeUserNotifyService {
	
	@Resource
	private NoticeUserNotifyDao userNotifyDao;

	@Override
	public Dao<UserNotify, String> getDefaultDao() {
		return userNotifyDao;
	}

}
