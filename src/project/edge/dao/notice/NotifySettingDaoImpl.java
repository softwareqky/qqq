package project.edge.dao.notice;

import org.springframework.stereotype.Repository;

import garage.origin.dao.HibernateDaoImpl;
import project.edge.domain.entity.NotifySetting;

@Repository
public class NotifySettingDaoImpl extends HibernateDaoImpl<NotifySetting, String> 
	implements NotifySettingDao {

	
}
