package project.edge.service.notice;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.notice.NotifySettingDao;
import project.edge.domain.entity.NotifySetting;

@Service
public class NotifySettingServiceImpl extends GenericServiceImpl<NotifySetting, String> 
	implements NotifySettingService {
	
	@Resource
	private NotifySettingDao notifySettingDao;

	@Override
	public Dao<NotifySetting, String> getDefaultDao() {
		return notifySettingDao;
	}

}
